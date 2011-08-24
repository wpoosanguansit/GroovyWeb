/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 10, 2009
 * Time: 12:14:40 PM
 * 
 */

class SecurityFilters {
   def filters = {
       loginCheck(controller:'*', action:'*') {
           before = {
               //accessControl { true }
           }

      }
   }
}