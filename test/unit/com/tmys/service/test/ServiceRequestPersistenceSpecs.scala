package com.pdmaf.service.test

import java.nio.ByteBuffer
import java.io.FileInputStream
import org.specs._
import org.specs.runner._
import com.pdmaf.business.models._
import com.pdmaf.utils.enums._
import com.pdmaf.service.persistence._
import java.util.List
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pdmaf.utils._

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 30, 2009
 * Time: 10:10:54 AM
 * 
 */

object ServiceRequestPersistenceSpecs extends Specification {

  val persistence = new Persistence()

  import persistence._
  //we need the spring context because persistence class get database instance from the container
  val context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml")

  "service request persistence service" should {
	  "save the service request" in {
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

      rev must notBeNull
    }
  }

  "save attachment" in {
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

          result must notBeNull
   }
}