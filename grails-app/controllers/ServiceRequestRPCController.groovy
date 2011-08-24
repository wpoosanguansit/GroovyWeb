
import com.pdmaf.business.models.ServiceRequest
import com.pdmaf.ui.groovy.ServiceRequestViewPage
import com.pdmaf.utils.exceptions.DatabaseException
import com.pdmaf.utils.exceptions.IntegrationException
import com.pdmaf.service.persistence.PersistenceService
import com.pdmaf.service.directory.DirectoryService
import com.pdmaf.ui.groovy.ServiceRequestFragmentViewPage

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 28, 2009
 * Time: 12:05:11 PM
 * 
 */

public class ServiceRequestRPCController {
  
    final PersistenceService<ServiceRequest> database = new PersistenceService<ServiceRequest>()
    final DirectoryService directory = new DirectoryService()

    def viewServiceRequest = {
      final String serviceRequestID = params.serviceRequestID
      try {
          final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
          if (serviceRequest == null) {
            log.error("Error has occurred in ServiceRequestController - viewServiceRequest : null serviceRequest retrieval for " + serviceRequestID)
            final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.viewServiceRequest.nullServiceRequestMessage'
                    , args: [serviceRequestID], locale: request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
          }
          final ServiceRequestFragmentViewPage serviceRequestFragmentViewPage = new ServiceRequestFragmentViewPage(serviceRequest: serviceRequest)
          render(text: serviceRequestFragmentViewPage.render(), contentType:"text/html")
      } catch (DatabaseException databaseException) {
          log.error("Error has occurred in ServiceRequestController - viewServiceRequest for " + serviceRequestID, databaseException)
          final String serverErrorMessage = message(code:'serviceRequestController.viewServiceRequest.databaseErrorMessage'
                  , args: [serviceRequestID] , locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (IntegrationException integrationException) {
          log.error("Error has occurred in ServiceRequestController - viewServiceRequest for " + serviceRequestID, integrationException)
          final String serverErrorMessage = message(code:'serviceRequestController.viewServiceRequest.serverErrorMessage'
                  ,args: [serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (Exception exception) {
          log.error("Error has occurred in ServiceRequestController - viewServiceRequest for " + serviceRequestID, exception)
          final String serverErrorMessage = message(code:'serviceRequestController.viewServiceRequest.serverErrorMessage'
                  , args:[serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      }
  }

  def flag = {
    final String serviceRequestID = params.serviceRequestID
    final String flag = params.as
    try {
        final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
        if (serviceRequest == null) {
          log.error("Error has occurred in ServiceRequestController - flag : null serviceRequest retrieval for " + serviceRequestID)
          final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.flag.nullServiceRequestMessage'
                  , args: [serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
        }
        switch (flag) {
          case "spam"           : serviceRequest.flagAsSpamOrOverPost()
                                  break;
          case "miscategorized" : serviceRequest.flagAsMisCategorized()
                                  break;
          case "prohibited"     : serviceRequest.flagAsProhibited()
                                  break;
          case "great"          : serviceRequest.flagAsGreat()
                                  break;
        }
        serviceRequest = database.update(serviceRequest)
        final String flashMessage = "Thank you for flaging this post.  The status has been updated for further review."
        final ServiceRequestViewPage serviceRequestViewPage = new ServiceRequestViewPage(serviceRequest: serviceRequest, message: flashMessage)
        render(text: serviceRequestViewPage.render(), contentType:"text/html")
   } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - flag for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.flag.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - flag for " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.flag.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - flag for " + serviceRequestID, exception)
        final String serverErrorMessage = message(code:'serviceRequestController.flag.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    }
  }
}