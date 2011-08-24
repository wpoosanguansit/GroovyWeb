import com.pdmaf.business.models.ServiceProvider
import com.pdmaf.service.persistence.PersistenceService

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 21, 2009
 * Time: 10:21:05 AM
 *  
 */

public class ServiceProviderRPCController {

  final PersistenceService<ServiceProvider> database = new PersistenceService<ServiceProvider>()

  def retrieveServiceProviderByUserid = {
    final String userid = params.userid
    final String serviceProvider = database.findAsJSON('ServiceProvider', 'byUserid', userid)
    response.getWriter().flush()
    response.getWriter().append(serviceProvider)
    response.flushBuffer()
  }

}