package com.tmys.service.test


import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
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

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 24, 2009
 * Time: 1:57:26 PM
 *  
 */

class LDAPTest extends Spec with ShouldMatchers {

  val directory = new LDAP()

  import directory._
  //this is need to bring in spring because aspectJ ContextAccessor needs its existence to operate
  val context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml")
  val userid = "wpoosanguansit"+ Math.random +"@yahoo.com"

  describe("LDAP Test") {
    it("should save user object") {
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
  }
}
