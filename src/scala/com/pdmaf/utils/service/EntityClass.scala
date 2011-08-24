package com.pdmaf.utils.service


import business.models.ServiceRequest

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 5, 2009
 * Time: 1:37:45 PM
 *  
 */
object EntityClass {
      def apply(clazz: String) = {
        clazz match {
          case "ServiceRequest" => classOf[com.pdmaf.business.models.ServiceRequest]
          case "ServiceProvider" => classOf[com.pdmaf.business.models.ServiceProvider]
          case _ => throw new IllegalArgumentException("EntityClass: argument pass is not a valid entityclass. "
                  + clazz.toString)
        }
      }
}
