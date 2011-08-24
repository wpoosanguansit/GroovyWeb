import com.pdmaf.business.models.Entity
import com.pdmaf.service.persistence.PersistenceService
import com.pdmaf.utils.database.KeyOption
import com.pdmaf.utils.database.FullTextSearchOption
import java.lang.IllegalArgumentException

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 7, 2009
 * Time: 1:48:42 PM
 *  
 */

public class QueryRPCController {

  def getAllServiceRequest = {
    
    PersistenceService<Entity> database = new PersistenceService<Entity>()
    String result = database.retrieveAsJSON('ServiceRequest', 'byActiveStatus', "")

    render(text: result, contentType: "text/json")
  }

  def getSelectedServiceRequest = {
    final String byTerm = params.byTerm
    final String alphabet = params.alphabet
    String searchTerm = null
    switch(byTerm) {
      case "city"     : searchTerm = "city:"
                        break;
      case "category" : searchTerm = "category:"
                        break;
      default         : throw new IllegalArgumentException("QueryRPCController : getAllServiceRequest - search term of either City or Category must be provided.")
    }

    PersistenceService<Entity> database = new PersistenceService<Entity>()
    String result = database.retrieveFullTextSearchAsJSON('_fti', 'ServiceRequest', 'selected', FullTextSearchOption.instance().q(searchTerm + alphabet + '*').includeDocs(true))    

    render(text: result, contentType: "text/json")
  }

  def getServiceRequestByUserType = {
    final String type = params.type
    String searchTerm = null
    switch(type) {
      case "registered" :   searchTerm = "+poster:[a* TO z* ] AND -poster:annonymous"
                            break;
      case "unregistered" : searchTerm = "poster:annonymous"
                            break;
      default         : throw new IllegalArgumentException("QueryRPCController : getServiceRequestByUserType - search term of either Registered or Unregistered must be provided.")
    }


    PersistenceService<Entity> database = new PersistenceService<Entity>()
    String result = database.retrieveFullTextSearchAsJSON('_fti', 'ServiceRequest', 'selected', FullTextSearchOption.instance().q(searchTerm).includeDocs(true))

    render(text: result, contentType: "text/json")
  }

  def search = {
    final String searchTerm = params.searchTerm
    PersistenceService<Entity> database = new PersistenceService<Entity>()
    String result = database.retrieveFullTextSearchAsJSON('_fti', 'ServiceRequest', 'all', FullTextSearchOption.instance().q(searchTerm + '*').includeDocs(true))

    render(text: result, contentType: "text/json")
  }

}