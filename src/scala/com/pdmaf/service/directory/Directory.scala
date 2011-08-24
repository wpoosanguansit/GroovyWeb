package com.pdmaf.service.directory

import com.pdmaf.business.models._
import com.pdmaf.utils.aop.ContextAccessor
import com.pdmaf.utils.enums.InstanceStatus
import com.pdmaf.utils.enums._
import javax.naming.directory.Attributes
import javax.naming.directory.BasicAttribute
import javax.naming.directory.BasicAttributes
import org.springframework.ldap.core.DistinguishedName
import org.springframework.ldap.filter.EqualsFilter
import org.springframework.ldap.filter.AndFilter
import org.springframework.ldap.core.NameClassPairCallbackHandler
import org.springframework.ldap.core.AttributesMapper
import org.springframework.ldap.core.support.LdapContextSource
import org.springframework.ldap.core.LdapTemplate
import scala.collection.mutable.HashMap
import java.util.{ArrayList => JList}
import java.util.{HashMap => JHashMap}
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NameClassPair

trait Directory[T <: User] {

    //this makes use of aspectJ to get the ldap instance from spring through 
  //ContextAccessor class
  val ldap: LdapTemplate = getLDAPInstance
  val baseDn = "ou=users,dc=pdmaf,dc=com"

  /**
   * We provide the CRUD into LDAP configured in spring applicationContext.
   * The abstraction we provide is through the User object that will be inserted
   * into the inetOrgPerson class in LDAP, appending to the base dn.
   */
  def save(instance: T): T = {
    require(instance ne null)

    if ((instance.username == null) || (instance.password == null)) {
          throw new IllegalArgumentException("Directory : save - instance passed cannot have " +
                  "username or password as null")
    }

    val attributes = retrieveAttributes(instance)
    //set the status through the description field
    attributes.put("description", "SAVED")
    val newContactDN = new DistinguishedName("ou=users")
    newContactDN.add("uid", instance.username)
    ldap.bind(newContactDN, null, attributes)

    val fields = instance.getClass.getDeclaredFields
    fields.find(_.getName.equals("status")) match {
      case None => throw new IllegalStateException("Directory : save - field status of the object - " + instance +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(instance, InstanceStatus.SAVED)
    }
      
    instance 
  }
  
  def update(instance: T) : T = {
    require(instance ne null)
        
    val attributes = retrieveAttributes(instance)
    val newContactDN = new DistinguishedName("ou=users")
    newContactDN.add("uid", instance.username)
    ldap.rebind(newContactDN, null, attributes)
    
    instance
  }
  
  def retrieve(instance: T) : T = {
    require(instance ne null)
    
    retrieveById(instance.username)
  }

  /**
   * this method should return null when no result is found
   */
  def retrieveById(uid: String) : T = {
    require(uid ne null)

    val andFilter = new AndFilter
	  andFilter.and(new EqualsFilter("uid", uid))
    val control = new SearchControls();
    control.setSearchScope(SearchControls.SUBTREE_SCOPE); 
    control.setCountLimit(1);
    // Specify the ids of the attributes to return
    val attributeIDs = Array[String]("uid", "userPassword", "businessCategory", "cn", "sn", "displayName", 
                           "jpegPhoto", "description", "postalAddress", "mail", "birthdate", "telephoneNumber", "mobile")
    control.setReturningAttributes(attributeIDs)

    val list = ldap.search("", andFilter.encode(), new AttributesMapper() {
      def mapFromAttributes(attributes: Attributes) : AnyRef = {
          //we will try to retrieve full object first then if there is an exception we try abreviated user
          //object which has only username and password initialized.  Else it will throw the exception again.
          try {
              retrieveUser(attributes)
          } catch {
              case e:Exception => retrieveAbreviatedUser(attributes)
          }
      }
    })
    if (list.size > 0) {
        list.get(0).asInstanceOf[T]
      } else {
        return null.asInstanceOf[T]
    }
  }
  
  /**
   * This would put the status of the user object to deleted
   * and reset the password to random number which would
   * disallow users to authenticate from that point on.
   */
  def remove(instance: T) : T = {
    require(instance ne null)
    
    val fields = instance.getClass.getDeclaredFields
    fields.find(_.getName.equals("status")) match { 
      case None => throw new IllegalStateException("Directory : remove - field status of the object - " + instance +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(instance, InstanceStatus.DELETED)
    }
    
    fields.find(_.getName.equals("password")) match { 
      case None => throw new IllegalStateException("Directory : remove - field password of the object - " + instance +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(instance, Math.random.toString)
    }
    
    update(instance)
  }
  
  def authenticate(instance: T) : Boolean = {
    require(instance ne null)
    
    authenticate(instance.username, instance.password)
  }
    
  def authenticate(username: String, password: String) : Boolean = {
    require(username ne null)
    require(password ne null)
    
    val filter = new AndFilter();
    filter.and(new EqualsFilter("uid", username))
    ldap.authenticate("", filter.toString(), password)
  }

  def createServiceRequestEmail(hashKeyUID: String, email: String) : Unit = {
    require(hashKeyUID ne null)
    require(email ne null)

    val attributes = new BasicAttributes
    val attribute = new BasicAttribute("objectclass")
    attribute.add("inetOrgPerson")
    attribute.add("organizationalPerson")
    attribute.add("person")
    attribute.add("user")
    attribute.add("top")
    attributes.put(attribute)
    attributes.put("cn", "Service Request Email")
    attributes.put("sn", "Service Request Email")
    attributes.put("mail", email)
    attributes.put("description", "SAVED")
    val serviceRequestDN = new DistinguishedName("ou=users")
    serviceRequestDN.add("uid", hashKeyUID)
    ldap.bind(serviceRequestDN, null, attributes)
  }

  def removeServiceRequestEmail(hashKeyUID: String) : Unit = {
    require(hashKeyUID ne null)

    unbind(hashKeyUID)
  }

  //we do the actual remove in case of roll back
  def unbind(id : String) : Unit = {
    require(id ne null)

    val serviceRequestDN = new DistinguishedName("ou=users")
    serviceRequestDN.add("uid", id)
    ldap.unbind(serviceRequestDN)
  }

  private def retrieveAttributes(instance: T) : Attributes = {
    require(instance ne null)

    val map = new JHashMap[String, Object]()
    val fields = instance.getClass.getDeclaredFields
    for(field <- fields) {
      field.setAccessible(true)    
      map.put(field.getName, field.get(instance))
    }
    val attributes = new BasicAttributes
    val attribute = new BasicAttribute("objectclass")
    attribute.add("inetOrgPerson")
    attribute.add("organizationalPerson")
    attribute.add("person")
    attribute.add("user")  
    attribute.add("top")
    attributes.put(attribute)
    attributes.put("userPassword", map.get("password"))
    attributes.put("cn", map.get("displayname"))
    attributes.put("sn", map.get("displayname"))
    attributes.put("displayName", map.get("displayname"))
    attributes.put("businessCategory", map.get("type").asInstanceOf[UserType].toString)  
    val birthdate = map.get("birthdate").asInstanceOf[java.util.Date]
    if (birthdate ne null) {
        attributes.put("birthdate", java.lang.Long.toString(birthdate.getTime))
    }
    //we keep the status of the user in description field.
    attributes.put("description", map.get("status").toString)
    attributes.put("mail", map.get("email").asInstanceOf[Email].toString)
    attributes.put("o", map.get("serviceProviderProfileID").toString)
    val address = map.get("address").asInstanceOf[Address]
    if (address ne null) {
      attributes.put("postalAddress", address.toString)  
    }
    val telephones = map.get("telephones").asInstanceOf[JList[Telephone]]
    if (telephones != null && !telephones.isEmpty) {
      val iterator = telephones.iterator
      while (iterator.hasNext) {
        val tl = iterator.next.asInstanceOf[Telephone]
        tl.`type` match {
          case "LANDLINE" => attributes.put("telephoneNumber", tl.toString) 
          case "MOBILE" => attributes.put("mobile", tl.toString)
          case _ => throw new IllegalStateException("Directory : save - telephone passed has got a wrong type.")
        }
      }  
    }
    val photo = map.get("photo").asInstanceOf[Array[Byte]]
    if (photo ne null) {
        attributes.put("jpegPhoto", photo)
    }

    attributes
  }

  private def retrieveAbreviatedUser(attributes: Attributes) : T = {
    require(attributes ne null)

    val username = attributes.get("uid").get.asInstanceOf[String]
    val password = new java.lang.String(attributes.get("userPassword").get.asInstanceOf[Array[Byte]])
    val displayname = attributes.get("displayName").get.asInstanceOf[String]
    val status = InstanceStatus.valueOf(attributes.get("description").get.asInstanceOf[String])
    val usertype = UserType.valueOf(attributes.get("businessCategory").get.asInstanceOf[String])
    val serviceProviderProfileID = attributes.get("o").get.asInstanceOf[String]
    val user = new User(username, password)
    val fields = user.getClass.getDeclaredFields
    fields.find(_.getName.equals("status")) match {
      case None => throw new IllegalStateException("Directory : retrieveAbreviatedUser - field status of the object - " + user +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(user, status)
    }
    fields.find(_.getName.equals("displayname")) match {
      case None => throw new IllegalStateException("Directory : retrieveAbreviatedUser - field displayname of the object - " + user +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(user, displayname)
    }
    if (serviceProviderProfileID ne null) {
      user.addServiceProviderProfileID(serviceProviderProfileID)
    }
    user.asInstanceOf[T]
  }

  private def retrieveUser(attributes: Attributes) : T = {
    require(attributes ne null)

    val username = attributes.get("uid").get.asInstanceOf[String]
    val password = new java.lang.String(attributes.get("userPassword").get.asInstanceOf[Array[Byte]])
    val displayname = attributes.get("displayName").get.asInstanceOf[String]
    val status = InstanceStatus.valueOf(attributes.get("description").get.asInstanceOf[String])
    val address = attributes.get("postalAddress").get.asInstanceOf[String]
    val email = attributes.get("mail").get.asInstanceOf[String]
    val usertype = UserType.valueOf(attributes.get("businessCategory").get.asInstanceOf[String])  
    val birthdate = new java.util.Date(java.lang.Long.parseLong(attributes.get("birthdate").get.asInstanceOf[String]))
    val serviceProviderProfileID = attributes.get("o").get.asInstanceOf[String]
    val phoneList = new JList[Telephone]()
    var mobile, landline : String = null
    var mobileObject, landlineObject : Telephone = null
    val telephoneContructor = classOf[Telephone].getDeclaredConstructor(classOf[String])
    telephoneContructor.setAccessible(true)
    if (attributes.get("mobile") ne null) {
       mobile = attributes.get("mobile").get.asInstanceOf[String]
       mobileObject = telephoneContructor.newInstance(email)
       phoneList.add(mobileObject)
    }
    if (attributes.get("telephoneNumber") ne null) {
      landline = attributes.get("telephoneNumber").get.asInstanceOf[String]
      landlineObject = telephoneContructor.newInstance(landline)
      phoneList.add(landlineObject)
    }  
    val emailContructor = classOf[Email].getDeclaredConstructor(classOf[String])
    emailContructor.setAccessible(true)
    val emailObject = emailContructor.newInstance(email)
    val addressContructor = classOf[Address].getDeclaredConstructor(classOf[String])
    addressContructor.setAccessible(true)
    val addressObject = addressContructor.newInstance(address)
    val photo = attributes.get("photo").get.asInstanceOf[Array[Byte]]
    val user = new User(displayname, username, password, birthdate, addressObject, emailObject, phoneList, photo, serviceProviderProfileID)
    val fields = user.getClass.getDeclaredFields
    fields.find(_.getName.equals("status")) match { 
      case None => throw new IllegalStateException("Directory : retrieveUser - field status of the object - " + user +
                                                    " can not be found.")
      case Some(f) => f.setAccessible(true)
                      f.set(user, status)
    }
    
    user.asInstanceOf[T]
  } 

    /**
     * We use the aspectJ to weave in the instance of the spring context
     * through ContextAccessor class
     */
    private def getLDAPInstance : LdapTemplate = {
      ContextAccessor.instance.getSpringBean("ldap").asInstanceOf[LdapTemplate]
    }
}
