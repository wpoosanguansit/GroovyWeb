package com.pdmaf.service.persistence

import com.pdmaf.business.models._
import scala.collection.{Map, mutable=>m, jcl}
import com.pdmaf.utils.service._
import com.pdmaf.utils.aop.ContextAccessor
import utils.database.{FullTextSearchOption, KeyOption}
/**
 *this class and object are to provide somewhat transparent
 * persistence capability to the objects that extends Entity class
 * to use initialize persistence object in your class or objects
 * 
 * val persistence = new Persistence 
 * 
 * and import the persistence code as module by puting this next
 * 
 * import persistence._
 * 
 * when you issue object that extends Entity with the RichEntity method
 * like save the object will be decorated with the persistence capabilities
 * somewhat automatically by the compiler.
 * 
 * @important This class assume that the Entity class implements constructor that takes Map as
 * an argument.  The DAO will call on that contructor and pass the Map of JSON string
 * gotten back from CouchDB for the object to initialize itself.  Also the Entity class
 * has to implement toJSON method to return the JSON string representing itself.
 * 
 */
object Persistence {

  type EntityType = T forSome{type T<:Entity}
    
  class RichEntity(val self: EntityType, val dao: Dao[EntityType]) {
    
    def save : EntityType = {
      dao save self
    }
    
    def remove : EntityType = {
      dao remove self
    }
    
    def  update : EntityType = {
      dao update self
    }
    
    def retrieve : EntityType = {
      dao retrieve self
    } 
    
    def attach(attachmentId: String, contentType: String, data: Array[Byte]) : EntityType = {
      if (retrieveAttachments.containsKey(attachmentId)) {
          dao.updateAttachment(self, attachmentId, contentType, data)     
      } else {
          dao.attach(self, attachmentId, contentType, data)
      }
    }

    def updateAttachment(attachmentId: String, contentType: String, data: Array[Byte]) : EntityType = {
      dao.updateAttachment(self, attachmentId, contentType, data)
    }

    def retrieveAttachments : java.util.Map[String, Array[Byte]] = {
      dao retrieveAttachments self
    }

    def retrieveAttachment(attachmentId: String) : Array[Byte] = {
      dao retrieveAttachment(self, attachmentId)
    }
  }
  
  class RichEntityType(val self: Class[_ <: EntityType], val dao: Dao[EntityType]) {
    def retrieveById(id: String) : EntityType = dao.retrieveById(id)
    def retrieve(from: Symbol, viewName: Symbol, key: String): List[EntityType] = {
      dao.retrieve(from, viewName, key)
    }
    def attach(entity: Entity, attachmentId: String, contentType: String, data: Array[Byte]) : EntityType = {
      if (dao.retrieveAttachments(entity).containsKey(attachmentId)) {
            dao.updateAttachment(entity, attachmentId, contentType, data)
      } else {
            dao.attach(entity, attachmentId, contentType, data)
      }
    }
    def retrieveAttachment(entity: Entity, attachmentId: String) = {
        dao.retrieveAttachment(entity, attachmentId)
    }
    def retrieveAttachments(entity: Entity) : java.util.Map[String, Array[Byte]] = {
        dao.retrieveAttachments(entity)
    }
    def retrieveAsJSON(from: Symbol, viewName: Symbol, key: String) : String = {
        dao.retrieveAsJSON(from, viewName, key)
    }
    def retrieveAsJSON(from: Symbol, viewName: Symbol, keyOptions: KeyOption) : String = {
        dao.retrieveAsJSON(from, viewName, keyOptions)
    }
    def retrieveByIdAsJSON(id: String): String = {
        dao.retrieveByIdAsJSON(id)

    }
    def retrieveFullTextSearchAsJSON(fullTextIndex: Symbol, from: Symbol, viewName: Symbol, fullTextSearchOptions: FullTextSearchOption) : String = {
        dao.retrieveFullTextSearchAsJSON(fullTextIndex, from, viewName, fullTextSearchOptions)
    }
    def findFullTextSearchAsJSON(fullTextIndex: Symbol, from: Symbol, viewName: Symbol, fullTextSearchOptions: FullTextSearchOption) : String = {
        dao.findFullTextSearchAsJSON(fullTextIndex, from, viewName, fullTextSearchOptions)
    }
    def findAsJSON(from: Symbol, viewName: Symbol, keyOptions: KeyOption) : String = {
        dao.findAsJSON(from, viewName, keyOptions)
    }
    def unbind(id: String) : Unit = {
        dao.unbind(id)
    }

  }
  
  class GenericDao(val entityClass:Class[EntityType]) extends Dao[EntityType]{}  
    
}

class Persistence {
   import Persistence._

  private val daos = m.Map.empty[Class[EntityType], Dao[EntityType]]

  implicit def entityTypeWrapper(entityType: Class[_ <: EntityType])= new RichEntityType(entityType, getOrCreateDao(entityType))

  implicit def entityWrapper(entity: EntityType) =  new RichEntity(entity, getOrCreateDao(entity.getClass.asInstanceOf[Class[Entity]]))

//    ManagedComponentFactory.createComponentInterceptorWithArguments[RichEntity](
//          classOf[RichEntity], Array(classOf[Entity], classOf[Dao[Entity]]), Array(e.asInstanceOf[Entity], getOrCreateDao(e.getClass.asInstanceOf[Class[Entity]])),
//          new ManagedComponentInterceptor(new RichEntity(e, getOrCreateDao(e.getClass.asInstanceOf[Class[Entity]])))
//          with TestInterceptor)

  /**
   * This retrieve the dao with aspect proxy
   * using ManageComponentFactory and with Interceptor
   */
  private def getOrCreateDao(clazz:Class[_ <: Entity]) = {
    getDao(clazz) match {
      case Some(r)=> r
      case None => ManagedComponentFactory.createComponentProxy[Dao[EntityType]](
        classOf[Dao[EntityType]],
        new ManagedComponentProxy(new GenericDao(clazz.asInstanceOf[Class[EntityType]])) with DaoBeforeAndAfterAspectsInvocator)
    }
  }

  def getDao(clazz:Class[_<:Entity]) = daos.get(clazz.asInstanceOf[Class[EntityType]])
}
