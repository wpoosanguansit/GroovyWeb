package com.pdmaf.utils.service

import com.pdmaf.service.persistence.Dao
import com.pdmaf.business.models._
import scala.collection.mutable.HashMap

/**
 * This uses the straight trait facility
 */
trait DaoBeforeAndAfterAspects extends Dao[Entity] {
  
  var map = new HashMap[String, Entity]()
  
     abstract override def save(instance: Entity) : Entity = {
       val result = super.save(instance)
       result
     } 
}

/**
 * This makes use of the Proxy and AspectJ pointcuts
 * We also take care of the exception from the CouchDB 
 * in this class.
 */
trait DaoBeforeAndAfterAspectsInvocator extends Invocator {
  
  val persistence = new com.pdmaf.service.persistence.Persistence()

  import persistence._
  
  val savePointcut = parser.parsePointcutExpression("execution(* *.save(..))")
  val withinSavePointcut = parser.parsePointcutExpression("withincode(* *.save(..))")
  
  abstract override def invoke(invocation: Invocation): AnyRef = {
    var result: AnyRef = null
    
    try {

      result = super.invoke(invocation)
      if (matches(savePointcut , invocation) && matches(withinSavePointcut , invocation)) { 
         this.afterInvocationForSaveComment(invocation)
      } 

    } catch {
      case (e: java.lang.reflect.InvocationTargetException) =>
        //we have to unwrap the invocation exception to get the cause
        e.getCause match {
                case (e: org.jcouchdb.exception.CouchDBException) => 
                  throw new com.pdmaf.utils.exceptions.DatabaseException("DB Exception at " +
                                                                               invocation.target + ": calling method " + invocation.method + 
                                                                               " with argument " + invocation.args + e.printStackTrace, e)
                case e =>
                  throw new com.pdmaf.utils.exceptions.IntegrationException("Exception at caught in Dao " +
                                                                               invocation.target + ": calling method " + invocation.method + 
                                                                               " with argument " + invocation.args + e.printStackTrace, e)
                  /**
                   * @todo write to log
                   */
        }
      case e => {
        throw new com.pdmaf.utils.exceptions.IntegrationException("Exception at caught in Dao " +
                                                                     invocation.target + ": calling method " + invocation.method +  
                                                                     " with argument " + invocation.args + e.printStackTrace, e)
      /**
       * @todo write this to the log
       */
      }
    } finally {
      /**
       * @todo write this to the log
       */
    }

    result
  }
    
  private def afterInvocationForSaveComment(invocation: Invocation) {
//       if (invocation.target.asInstanceOf[Dao[Comment]].entityClass == classOf[Comment]) {
//         val comment = invocation.args(0).asInstanceOf[Comment]
//         val Article = classOf[Article]
//         val fields = comment.getClass.getDeclaredFields
//         val articleId = fields.find(_.getName.equals("article")) match {
//              case None => System.out.println("raise flag!!!!")
//              case Some(f) => f.setAccessible(true)
//                              f.get(comment)
//         }
//         val article =  Article.retrieveById(articleId.asInstanceOf[String]).asInstanceOf[Article]
//         article.addComment(comment)
//         article.update
//      }
  }
}
