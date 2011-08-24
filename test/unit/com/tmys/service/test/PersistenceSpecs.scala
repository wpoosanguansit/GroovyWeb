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

object PersistenceSpecs extends Specification { 

  val persistence = new Persistence()

  import persistence._
  
  val context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml")

  "persistence service" should {
	  "save the article" in {
//	       val article = new Article("content", "author")
//           val comments = new java.util.ArrayList[Comment]
//	       article.save
//	       var fields = article.getClass.getDeclaredFields
//	       var id = fields.find(_.getName.equals("_id")) match {
//	         case None => System.out.println("raise flag!!!!")
//	         case Some(f) => f.setAccessible(true)
//	                         f.get(article)
//	       }
//           article.update
//           article.remove
//           println(article.comments)
//           val comment = new Comment(article, "text", "author")
//           comment.save
//           comment.update
//           val retrievedComment = comment.retrieve
//           println(retrievedComment.toJSON)
//           val retrievedArticle = article.retrieve
//          println(retrievedArticle.asInstanceOf[Article].comments.get(0).asInstanceOf[Comment].id)
//           retrievedArticle.asInstanceOf[Article].addComment(comment)
//           println(retrievedArticle.asInstanceOf[Article].comments.get(0).asInstanceOf[Comment].id)
//        id must notBeNull
	  }
//the windows couchdb version 0.8 does not support attachment yet.
      "save attachment" in {
//          val tempFile = new java.io.File("web-app/images/banner-main.jpg")
//          val file = new FileInputStream(tempFile)
//          val channel = file.getChannel
//          val photo = ByteBuffer.allocate(tempFile.length.asInstanceOf[Int])
//          channel.read(photo)
//          val article = new Article("content", "author")
//          val comments = new java.util.ArrayList[Comment]
//	      article.save
//
//          article.attach("test", "image/jpeg", photo.array)
//
//          val Article = classOf[Article]
//          val result = Article.retrieveAttachment(article, "test")
//
//          result must notBeNull
      }

      "update the article" in {
        
//        var foo = ManagedComponentFactory.createComponentInterceptorWithArguments[FooImpl](
//          classOf[FooImpl], Array(classOf[String]), Array("Yesh"),
//          new ManagedComponentInterceptor(new FooImpl("Yeash"))
//          with TestInterceptor)
//
//        foo.foo("foo")
//        foo.bar("bar")
//        foo.save()

//        val comment = new java.util.ArrayList[Comment]
//        val article = new Article("255f75e11218a25c7966d4a1f7222300", "347338391", "content", 
//                                  "author", comment, InstanceStatus.SAVED, null)
//         var fields = article.getClass.getDeclaredFields
//	       var comments = fields.find(_.getName.equals("comments")) match { 
//	         case None => System.out.println("raise flag!!!!")
//	         case Some(f) => f.setAccessible(true)
//	                         f.get(article)	       
//	       }
//        None
      }
      
         
      "retreive the object by id" in {
//	      var Article = classOf[Article]
//          var article = Article.retrieveById("255f75e11218a25c7966d4a1f7222300")
//          article.save
//          article = Article.retrieveById("471751b5c5610e3ea668b7de7c3b3404")
//          System.out.println(article.toJSON + "--------kkkkkkkkkkkkkkkkkkkkkkkkkk")
//          None 
	  }
      
      "retreive objects by view" in {
//	      var Comment = classOf[Comment]
//          val comments = Comment.retrieve('Comment, 'byArticle, "256765544452af0cc04716b6e6a46168")
//          comments
	  }
      
  }
}
