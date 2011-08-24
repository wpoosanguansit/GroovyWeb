
package com.pdmaf.service.persistence


import com.pdmaf.business.models.Entity
import org.jcouchdb.document.Attachment
import com.pdmaf.utils.service.EntityClass
import scala.Symbol
import utils.database.{FullTextSearchOption, KeyOption}
/**
 * This serves as the facade between the higher layer like UI and the persistence code.
 */

class PersistenceService[T <: Entity] {

    val persistence = new Persistence()

    import persistence._

  /**
   * retrieve base on the view name and the key given
   */
  def retrieve(from: String, viewName: String, key: String): List[T] = {
    require(from ne null)
    require(viewName ne null)
    require(key ne null)

    val Persister = EntityClass(from)
    Persister.retrieve(Symbol(from), Symbol(viewName), key).asInstanceOf[List[T]]
  }
  /**
   *
   * @param id
   * @return Entity
   */
  def retrieveById(from: String, id: String): T = {
    require(id ne null)

    val Persister = EntityClass(from)
    Persister.retrieveById(id).asInstanceOf[T]
  }

  def retrieveAsJSON(from: String, viewName: String, key: String) : String = {
    val Persister = EntityClass(from)
    Persister.retrieveAsJSON(Symbol(from), Symbol(viewName), key)
  }

  def retrieveAsJSON(from: String, viewName: String, keyOptions: KeyOption) : String = {
    val Persister = EntityClass(from)
    Persister.retrieveAsJSON(Symbol(from), Symbol(viewName), keyOptions)
  }
  
  def retrieveByIdAsJSON(from: String, id: String): String = {
    val Persister = EntityClass(from)
    Persister.retrieveByIdAsJSON(id)
  }

  def retrieveFullTextSearchAsJSON(fullTextIndex: String, from: String, viewName: String, fullTextSearchOptions: FullTextSearchOption) : String = {
    val Persister = EntityClass(from)
    Persister.retrieveFullTextSearchAsJSON(Symbol(fullTextIndex), Symbol(from), Symbol(viewName), fullTextSearchOptions)
  }

  /**
   * find will return null if doc is not found instead of throwing exception.
   */
  def findFullTextSearchAsJSON(fullTextIndex: String, from: String, viewName: String, fullTextSearchOptions: FullTextSearchOption) : String = {
    val Persister = EntityClass(from)
    Persister.findFullTextSearchAsJSON(Symbol(fullTextIndex), Symbol(from), Symbol(viewName), fullTextSearchOptions)
  }

  def findAsJSON(from: String, viewName: String, keyOptions: KeyOption) : String = {
    val Persister = EntityClass(from)
    Persister.findAsJSON(Symbol(from), Symbol(viewName), keyOptions)
  }

  def save(instance: T) : T = {
    require(instance ne null)

    instance.save.asInstanceOf[T]
  }

  def remove(instance: T) : T = {
    require(instance ne null)

    instance.remove.asInstanceOf[T]
  }

  def update(instance: T) : T = {
    require(instance ne null)

    instance.update.asInstanceOf[T]
  }

  def retrieve(instance: T) : T = {
    require(instance ne null)

    instance.retrieve.asInstanceOf[T]
  }

  def attach(instance: T, attachmentId: String, contentType: String, data: Array[Byte]) : T = {
    require(instance ne null)
    require(attachmentId ne null)
    require(data ne null)

    instance.attach(attachmentId, contentType, data).asInstanceOf[T]
  }

  def retrieveAttachments(instance: T) : java.util.Map[String, Array[Byte]] = {
    require(instance ne null)
    instance.retrieveAttachments
  }

  def retrieveAttachment(instance: T, attachmentId: String) : Array[Byte] = {
    require(instance ne null)
    require(attachmentId ne null)
    instance.retrieveAttachment(attachmentId)
  }

  def unbind(from: String, id: String) : Unit = {
    require(id ne null)
    val Persister = EntityClass(from)
    Persister.unbind(id)
  } 
}