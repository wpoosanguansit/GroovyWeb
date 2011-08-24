package com.pdmaf.service.test

import java.io.FileInputStream
import java.nio.ByteBuffer
import org.specs._
import org.specs.runner._
import com.pdmaf.business.models._
import com.pdmaf.utils.enums._
import java.util.{ArrayList => JList}
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pdmaf.utils._
import com.pdmaf.service.directory._

object LDAPSpecs extends Specification {

    val directory = new LDAP()

    import directory._
    //this is need to bring in spring because aspectJ ContextAccessor needs its existence to operate
    val context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml")
    val userid = "wpoosanguansit"+ Math.random +"@yahoo.com"
    
    "LDAP service" should {
      "save user" in {
        val telephone = new Telephone(TelephoneType.LANDLINE, "123456789")
        val email = new Email(EmailType.PERSONAL, "email@email.com")
        val address = new Address("839", "w. sheridan rd", City.CHICAGO, State.ILLINOIS, Country.USA, "60613")
        val tl = new JList[Telephone]()
        tl.add(telephone)
        val tempFile = new java.io.File("web-app/images/banner-main.jpg")
        val file = new FileInputStream(tempFile)
        val channel = file.getChannel
        val photo = ByteBuffer.allocate(tempFile.length.asInstanceOf[Int])
        channel.read(photo)  
        val user = new User("Watt", userid, "imagine", new java.util.Date(), address, email, tl, photo.array, "profileid")
        user.save
      }
      
//      "retrieve the user" in {
//        val User = classOf[User]
//        val user = User.retrieveById(userid)
//
//        user must notBeNull
//        user.username mustMatch userid
//      }
//
//      "delete the user" in {
//        val User = classOf[User]
//        val user = User.retrieveById(userid)
//        user.remove
//        val fields = user.getClass.getDeclaredFields
//        val status = fields.find(_.getName.equals("status")) match {
//          case None => throw new IllegalStateException("Directory : retrieveUser - field status of the object - " + user +
//                                                    " can not be found.")
//          case Some(f) => f.setAccessible(true)
//                          f.get(user).toString
//        }
//        user must notBeNull
//        status mustMatch InstanceStatus.DELETED.toString
//      }
//
//      "authenticate the user" in {
//        val telephone = new Telephone(TelephoneType.LANDLINE, "123456789")
//        val email = new Email(EmailType.PERSONAL, "email@email.com")
//        val address = new Address("839", "w. sheridan rd", City.CHICAGO, State.ILLINOIS, Country.USA, "60613")
//        val tl = new JList[Telephone]()
//        tl.add(telephone)
//        val id = "wpoosanguansit"+ Math.random +"@yahoo.com"
//        val user = new User("", id, "imagine", new java.util.Date(), address, email, tl)
//        user.save
//        val testUser = new User(id, "imagine")
//        val User = classOf[User]
//        val authenticated = User.authenticate(testUser.username, testUser.password)
//
//        val user1 = new User("user1", "imagine")
//        val user2 = new User("user2", "imagine")
//
//        authenticated must beTrue
//      }
      
      
    } 
}
