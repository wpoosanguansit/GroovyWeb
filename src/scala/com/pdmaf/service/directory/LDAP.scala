package com.pdmaf.service.directory

import com.pdmaf.business.models._
import com.pdmaf.utils.aop.ContextAccessor
import org.springframework.ldap.core.LdapTemplate
import javax.naming.directory.Attributes
import javax.naming.directory.BasicAttribute
import javax.naming.directory.BasicAttributes
import org.springframework.ldap.core.DistinguishedName
import com.pdmaf.utils.enums.InstanceStatus
import scala.collection.mutable.HashMap
import java.util.{ArrayList => JList}
import java.util.{HashMap => JHashMap}
import com.pdmaf.utils.service._

object LDAP {
  
  class RichUserEntity(val self: User, val ldap: Directory[User]) {
    
    def save : User = {
      ldap save self
    }
    
    def remove : User = {
      ldap remove self
    }
    
    def  update : User = {
      ldap update self
    }
    
    def retrieve : User = {
      ldap retrieve self
    } 
    
    def authenticate(user: User) : Boolean = {
      ldap authenticate user
    }
    
    def authenticate(username: String, password: String) : Boolean = {
      ldap.authenticate(username, password)
    }

  }
  
  class RichUserType(val self: Class[_ <: User], val ldap: Directory[User]) {
    def retrieveById(uid: String) : User = ldap.retrieveById(uid)
    def authenticate(username: String, password: String) : Boolean = ldap.authenticate(username, password)
    def createServiceRequestEmail(hashKeyUID: String, email: String) : Unit =
      ldap.createServiceRequestEmail(hashKeyUID, email)
    def removeServiceRequestEmail(hashKeyUID: String) : Unit =
      ldap.removeServiceRequestEmail(hashKeyUID)
    def unbind(id: String) : Unit = ldap.unbind(id)
  }
  
  class GenericDirectory(val entityClass: Class[User]) extends Directory[User]{}  
    
}

class LDAP {
  import LDAP._
  
  implicit def userTypeWrapper(ut: Class[_ <: User])= new RichUserType(ut, getOrCreateDirectory(ut)) 
    
  implicit def userWrapper(u: User) =  new RichUserEntity(u, getOrCreateDirectory(u.getClass.asInstanceOf[Class[User]]))

  /**
   * This retrieve the ldap with aspect proxy
   * using ManageComponentFactory and with Interceptor
   */
  private def getOrCreateDirectory(clz: Class[_ <: User]) = {
    ManagedComponentFactory.createComponentProxy[Directory[User]](
        classOf[Directory[User]],
        new ManagedComponentProxy(new GenericDirectory(clz.asInstanceOf[Class[User]])) with DirectoryBeforeAndAfterAspectsInvocator)
  }
  
}
