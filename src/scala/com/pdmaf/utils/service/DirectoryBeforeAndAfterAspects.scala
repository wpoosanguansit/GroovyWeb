package com.pdmaf.utils.service

import com.pdmaf.service.directory.Directory
import com.pdmaf.business.models._
import exceptions.{NameAlreadyExistException, LoginException}
import scala.collection.mutable.HashMap

/**
 * This makes use of the Proxy and AspectJ pointcuts
 * We also take care of the exception from the CouchDB 
 * in this class.
 */
trait DirectoryBeforeAndAfterAspectsInvocator extends Invocator {
  
  abstract override def invoke(invocation: Invocation): AnyRef = {
    var result: AnyRef = null
    
    try {

      result = super.invoke(invocation)
        
    } catch {
      case (e: java.lang.reflect.InvocationTargetException) =>
        //we have to unwrap the invocation exception to get the cause
        e.getCause match {
                case (e: org.springframework.ldap.NamingException) =>
                  if (e.getCause.toString.contains("LDAP: error code 49")) {
                          throw new LoginException("Invalid Credential (username or/and password!)"  + e)
                  } else if (e.getCause.toString.contains("error code 68")) {
                          throw new NameAlreadyExistException("The name already exist in the directory!"  + e)
                  } else {
                  throw new com.pdmaf.utils.exceptions.DirectoryException("Directory Exception at " +
                                                                               invocation.target + ": calling method " + invocation.method +
                                                                               " with argument " + invocation.args + " " + e.getCause, e)
                  }
                case e =>
                  throw new com.pdmaf.utils.exceptions.IntegrationException("Exception at caught in Directory access " +
                                                                               invocation.target + ": calling method " + invocation.method + 
                                                                               " with argument " + invocation.args + " " + e.getCause, e)
                  /**
                   * @todo write to log
                   */
        }
      case e => {
        throw new com.pdmaf.utils.exceptions.IntegrationException("Exception at caught in Directory access " +
                                                                     invocation.target + ": calling method " + invocation.method +  
                                                                     " with argument " + invocation.args + e.getCause.getCause, e.getCause)
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
}


