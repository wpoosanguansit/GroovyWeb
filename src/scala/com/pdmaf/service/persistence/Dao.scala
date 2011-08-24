/*
 * Copyright 2008-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pdmaf.service.persistence

import java.io.{Serializable}
import com.pdmaf.business.models._
import com.pdmaf.utils.enums._
import com.pdmaf.utils.aop._
import org.jcouchdb.db.{Response, Database, Options}
import org.jcouchdb.document.{Attachment, BaseDocument}
import org.svenson.JSON
import utils.database.{FullTextSearchOption, KeyOption}
import utils.exceptions.{DatabaseException}
/**
 * Trait of Dao of single entity with type of [T].
 * 
 * @author Watt P.
 * @version 0.0.1 - $Revision: 77 $
 * @param [T] - type of Object which this Dao deals with.
 */
trait Dao[T <: Entity] {
  /**
   * The class of [T]
   */
  val entityClass: Class[T]
  
  //this makes use of the aspectj injection of spring context into the ContextAccessor
  val database: Database = getDatabaseInstance

  val VIEW_DOCUMENT_PREFIX : String = "_design/"
  val VIEW_DOCUMENT_INFIX : String = "/_view/"
  /**
   * retrieve base on the view name and the key given
   */
  def retrieve(from: Symbol, viewName: Symbol, key: String): List[T] = {
    retrieve(from, viewName, KeyOption.instance().key(key))
  }
      /**
      * retrieve base on the view name and the key options that will get translated to couchdb
      * keys.
      */
  def retrieve(from: Symbol, viewName: Symbol, keyOptions: KeyOption) : List[T] = {
    require(from ne null)
    require(viewName ne null)
    
    val viewResult = database.queryView(from.name + "/" + viewName.name, classOf[java.util.Map[String, Object]], new Options(keyOptions.asMap), null)
    var result = List[T]()
    val contructor = entityClass.getDeclaredConstructor(classOf[java.util.Map[String, Object]])
    contructor.setAccessible(true)
    val iterator = viewResult.getRows.iterator
    while (iterator.hasNext) {
      val map = iterator.next.getValue
      val instance = contructor.newInstance(map)
      result = result ::: List(instance)
    }
    result
  }

  def retrieveAsJSON(from: Symbol, viewName: Symbol, key: String) : String = {
    if (key == null || key.isEmpty) {
      retrieveAsJSON(from, viewName, KeyOption.instance)
    } else {
      retrieveAsJSON(from, viewName, KeyOption.instance.key(key))
    }
  }

  def retrieveAsJSON(from: Symbol, viewName: Symbol, keyOptions: KeyOption) : String = {
        require(from ne null)
        require(viewName ne null)

        var uri = "/" + database.getName + "/" + viewURIFromName(from.name + "/" + viewName.name)
        val server = database.getServer
        if (keyOptions != null && keyOptions.size != 0) {
            uri += keyOptions.toQuery();
        }

        var result: String = null;
        var response: Response = null;

        try {

            if (keyOptions == null || keyOptions.size == 0) {
                response = server.get(uri)
            } else {
                response = server.post(uri, (new JSON()).forValue(keyOptions))
            }

            if (!response.isOk()) {
                throw new DatabaseException("DAO: retrieveAsJSON - error with the database : from - " +
                        from.name + ", viewName - " + viewName.name + ", response from the server : " + response.toString)
            }

            result = response.getContentAsString
          
        } finally {
          
            if (response != null) {
                response.destroy()
            }
        }

    result
  }

  def retrieveByIdAsJSON(id: String): String = {
    require(id ne null)

    var uri = "/" + database.getName + "/" + id;
    val server = database.getServer
    var result: String = null;
    var response: Response = null;

    try {
            response = server.get(uri)
            result = response.getContentAsString
            if (!response.isOk()) {
                throw new DatabaseException("DAO: retrieveAsJSON - error with the database with id - " +
                        id + ", reponse from the server : " + response.toString)
            }
        } finally {

            if (response != null) {
                response.destroy();
            }
    }

    result
  }

  /**
   * This will do a full text search on couchdb with lucene index.
   * The format search string is :
   * http://localhost:5984/tmys/_fti/ServiceRequest/selected?q=description:fd* 
   */
  def retrieveFullTextSearchAsJSON(fullTextIndex: Symbol, from: Symbol, viewName: Symbol, fullTextSearchOptions: FullTextSearchOption) : String = {
        require(fullTextIndex ne null)
        require(from ne null)
        require(viewName ne null)
    
        var uri = "/" + database.getName + "/" + fullTextIndex.name + "/" + from.name + "/" + viewName.name
        val server = database.getServer
        if (fullTextSearchOptions != null && fullTextSearchOptions.size != 0) {
            uri += fullTextSearchOptions.toQuery();
        }

        var result: String = null;
        var response: Response = null;

        try {

            if (fullTextSearchOptions == null || fullTextSearchOptions.size == 0) {
                response = server.get(uri)
            } else {
                response = server.post(uri, "")
            }

            if (!response.isOk()) {
                throw new DatabaseException("DAO: retrieveFullTextSearchAsJSON - error with the database : " + response.toString)
            }

            result = response.getContentAsString

        } finally {

            if (response != null) {
                response.destroy()
            }
        }

    result
  }

  /**
   * find method will return null if search does not find any doc or in case of exception,
   * instead of throwing exception
   */
   def findAsJSON(from: Symbol, viewName: Symbol, keyOptions: KeyOption) : String = {
        require(from ne null)
        require(viewName ne null)
     
        var result: String = null
        try {
            result = retrieveAsJSON(from, viewName, keyOptions)
        } catch {
          case (exception: Exception) =>
        }

     result
   }

   def findFullTextSearchAsJSON(fullTextIndex: Symbol, from: Symbol, viewName: Symbol, fullTextSearchOptions: FullTextSearchOption) : String = {
        require(fullTextIndex ne null)
        require(from ne null)
        require(viewName ne null)

        var result: String = null
        try {
            result = retrieveFullTextSearchAsJSON(fullTextIndex, from, viewName, fullTextSearchOptions)
        } catch {
          case (exception: Exception) =>
        }

     result
   }
  /**
   * 
   * @param id 
   * @return T
   */
  def retrieveById(id: String): T = {
    require(id ne null)
    var map = database.getDocument(classOf[java.util.Map[String, Object]], id)
    val contructor = entityClass.getDeclaredConstructor(classOf[java.util.Map[String, Object]])
    contructor.setAccessible(true)
    val instance = contructor.newInstance(map)
    instance.asInstanceOf[T]
  }


  def save(instance: T) : T = {
    require(instance ne null)
    val document = new java.util.HashMap[String, Object]()
    val fields = instance.getClass.getDeclaredFields
    for(field <- fields) {
      field.setAccessible(true)
      var value = field.getName match {
        case "status" => field.set(instance, InstanceStatus.valueOf("SAVED"))
                         "SAVED"
        case _ => field.get(instance)
      }
      if (value == null) { value = "" }
      document.put(field.getName, value.toString)
    }
    /**
     * @todo add catch and retry retrieving the new _rev and try update again
     */
    if(!document.get("_rev").asInstanceOf[String].trim().isEmpty) {
      database.updateDocument(document)
    } else {
      document.remove("_rev")
      database.createDocument(document)
    }

    fields.find(_.getName.equals("_rev")) match { 
       case None => throw new IllegalStateException("Dao : save - field _rev of the object - " + instance +
                                                    " can not be found.")
       case Some(f) => f.set(instance, document.get("_rev"))
    }
    
    instance.asInstanceOf[T]
  
  }
 
  def remove(instance: T) : T = {
    require(instance ne null)
    val fields = instance.getClass.getDeclaredFields

    fields.find(_.getName.equals("status")) match { 
       case None => throw new IllegalStateException("DAO: remove - status of the instance is not set.")
       case Some(field) => field.setAccessible(true)
                           field.set(instance, InstanceStatus.DELETED)
    }
     
    this.update(instance)

  }
  //we do really remove the record from the database in case of roll back
  def unbind(id: String) : Unit = {
    require(id ne null)

    val instance: T = retrieveById(id)
    if (instance ne null) {
      val fields = instance.getClass.getDeclaredFields
      var rev: String = null
      fields.find(_.getName.equals("_rev")) match {
       case None => throw new IllegalStateException("DAO: unbind - rev of the instance is null.")
       case Some(field) => field.setAccessible(true)
                           rev = field.get(instance).asInstanceOf[String]
      }
      database.delete(id, rev)
    } 
  }

  def update(instance:T) : T = {
    require(instance ne null)
    val document = new java.util.HashMap[String, Object]()
    val fields = instance.getClass.getDeclaredFields
    for(field <- fields) {
      field.setAccessible(true)
      var value = field.getName match {
        case _ => field.get(instance)
      }
      if (value == null) { value = "" }
      document.put(field.getName, value.toString)
    }
    if(!document.get("_rev").asInstanceOf[String].trim().isEmpty) {
      database.updateDocument(document)
    } else {
      document.remove("_rev")
      database.createDocument(document)
    }

    fields.find(_.getName.equals("_rev")) match {
       case None => throw new IllegalStateException("Dao : save - field _rev of the object - " + instance +
                                                    " can not be found.")
       case Some(f) => f.set(instance, document.get("_rev"))
    }

    instance.asInstanceOf[T]
  }
  
  def retrieve(instance: T) : T = {
    require(instance ne null)
    val result = retrieveById(instance.id)
    result.asInstanceOf[T]
  }

    /***
     * When you query a documentument with attachments, the attachments will
     * have a null data property and the stub property will be set to true.
     * This limitation has its origin in the way couchdb works and is deliberately
     * kept that way to not introduce additional queries.  To get the actual content
     * of the attachment back, you have to use the Database#getMethod(String,String). 
     */
  def attach(instance: Entity, attachmentId: String, contentType: String, data: Array[Byte]) : T = {
    require(instance ne null)
    require(attachmentId ne null)
    require(data ne null)
      
    val fields = instance.getClass.getDeclaredFields  
    val rev = fields.find(_.getName.equals("_rev")) match { 
       case None => System.out.println("Error instance passed into attach was not saved before")
       case Some(field) => field.setAccessible(true)
                           field.get(instance)
    }

    database.createAttachment(instance.id, rev.toString, attachmentId, contentType, data)

    instance.asInstanceOf[T]  
  }

  def updateAttachment(instance: Entity, attachmentId: String, contentType: String, data: Array[Byte]) : T = {
    require(instance ne null)
    require(attachmentId ne null)
    require(data ne null)
      
    val fields = instance.getClass.getDeclaredFields  
    val rev = fields.find(_.getName.equals("_rev")) match {
       case None => System.out.println("Error instance passed into attach was not saved before")
       case Some(field) =>  field.setAccessible(true)
                            field.get(instance)
    }

    database.updateAttachment(instance.id, rev.toString, attachmentId, contentType, data)

    instance.asInstanceOf[T]
  }
    
  def retrieveAttachment(instance: Entity, attachmentId: String) : Array[Byte] = {
    database.getAttachment(instance.id, attachmentId)
  }

  def retrieveAttachments(instance: Entity) : java.util.Map[String, Array[Byte]] = {
    require(instance ne null)
  
    val document = database.getDocument(classOf[BaseDocument], instance.id)
    val map = document.getAttachments
    var result = new java.util.HashMap[String, Array[Byte]]
    if  (map ne null) {
        val key = map.keySet
        val iterator = key.iterator
        while (iterator.hasNext) {
            val attachmentId = iterator.next.asInstanceOf[String]
            val attachment = map.get(key).asInstanceOf[Attachment]
            if (attachment.isStub) {
                result.put(attachmentId, retrieveAttachment(instance, attachmentId))
            } else {
                result.put(attachmentId,attachment.getData.asInstanceOf[Array[Byte]])
            }
        }
    }
      
    result
  }
  def viewURIFromName(viewName: String) : String = {
        val slashPos = viewName.indexOf("/");
        if (slashPos < 0)
        {
            throw new IllegalArgumentException("viewName must contain a slash separating the design doc name from the view name");
        }
        return VIEW_DOCUMENT_PREFIX + viewName.substring(0,slashPos) + VIEW_DOCUMENT_INFIX + viewName.substring(slashPos + 1);
   }
  /**
   * a singleton initialization of the database instance
   * we get from Spring using aspectJ to weave in through
   * ContextAccessor class.
   */
  private def getDatabaseInstance : Database = {
    ContextAccessor.instance.getSpringBean("couchDBDatabase").asInstanceOf[Database]
  }
}
