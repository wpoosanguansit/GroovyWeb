import com.pdmaf.service.persistence.PersistenceService
import com.pdmaf.business.models.ServiceRequest
import com.pdmaf.service.directory.DirectoryService
import org.jsecurity.subject.Subject
import org.jsecurity.SecurityUtils
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 20, 2009
 * Time: 9:17:50 AM
 *  
 */

public class ServiceProviderController {

    final PersistenceService<ServiceRequest> database = new PersistenceService<ServiceRequest>()
    final DirectoryService directory = new DirectoryService()
    final Subject currentUser = SecurityUtils.getSubject()


}