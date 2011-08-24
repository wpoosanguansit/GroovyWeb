package com.pdmaf.service.test


import java.io.FileInputStream
import java.nio.ByteBuffer
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import pdmaf.business.models.ServiceRequest
import pdmaf.service.persistence.Persistence
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 3, 2009
 * Time: 8:34:01 AM
 *  
 */

class ServiceRequestPersistenceTest  extends Spec with ShouldMatchers {

  val persistence = new Persistence()

  import persistence._
  //we need the spring context because persistence class get database instance from the container
  val context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml")

  describe("ServiceRequest Persistence code") {

    it("service request persistence service") {
      val serviceRequest = new ServiceRequest("title", "description", "category", "email@email.com", "emailHashKey",
            "showEmailPreference", "specificLocation", "city", "state", "country", "poster",
            "pointOffered", "otherCompensation")
      serviceRequest.save
      var fields = serviceRequest.getClass.getDeclaredFields
	    var rev = fields.find(_.getName.equals("_rev")) match {
	         case None => System.out.println("raise flag!!!!")
	         case Some(f) => f.setAccessible(true)
	                         f.get(serviceRequest)
      }

      rev should not (be (null))

    }

    it("save attachment") {
      val tempFile = new java.io.File("web-app/images/banner-main.jpg")
      val file = new FileInputStream(tempFile)
      val channel = file.getChannel
      val photo = ByteBuffer.allocate(tempFile.length.asInstanceOf[Int])
      channel.read(photo)
      val serviceRequest = new ServiceRequest("title", "description", "category", "email@email.com", "emailHashKey",
        "showEmailPreference", "specificLocation", "city", "state", "country", "poster",
        "pointOffered", "otherCompensation")
      serviceRequest.save

      serviceRequest.attach("test", "image/jpeg", photo.array)

      val ServiceRequest = classOf[ServiceRequest]
      val result = ServiceRequest.retrieveAttachment(serviceRequest, "test")
      
      result should not (be (null))
    }

    it("retrieve as json string") {
      val tempFile = new java.io.File("web-app/images/banner-main.jpg")
      val file = new FileInputStream(tempFile)
      val channel = file.getChannel
      val photo = ByteBuffer.allocate(tempFile.length.asInstanceOf[Int])
      channel.read(photo)
      val serviceRequest = new ServiceRequest("title", "description", "category", "email@email.com", "emailHashKey",
        "showEmailPreference", "specificLocation", "city", "state", "country", "poster",
        "pointOffered", "otherCompensation")
      serviceRequest.save
	    var fields = serviceRequest.getClass.getDeclaredFields
	    var id = fields.find(_.getName.equals("_id")) match {
	         case None => System.out.println("raise flag!!!!")
	         case Some(f) => f.setAccessible(true)
	                         f.get(serviceRequest)
      }

      val ServiceRequest = classOf[ServiceRequest]
      val result = ServiceRequest.retrieveByIdAsJSON(id.asInstanceOf[String])
      println(result + "returned result!!!!!!!!!!!!!!!!!!!!!!!!!!!")
      result should not (be (null))
    }
  }
}
