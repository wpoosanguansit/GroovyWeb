import org.jsecurity.subject.Subject
import com.pdmaf.business.models.ServiceRequest
import com.pdmaf.service.persistence.PersistenceService
import org.jsecurity.SecurityUtils
import com.pdmaf.service.directory.DirectoryService
import com.pdmaf.utils.exceptions.IntegrationException
import com.pdmaf.utils.exceptions.DatabaseException
import com.pdmaf.utils.exceptions.DirectoryException
import com.pdmaf.ui.groovy.PostServiceRequestConfirmationPage
import com.pdmaf.ui.groovy.EmailServiceRequestConfirmationPage
import com.pdmaf.ui.groovy.PostServiceRequestActivationPage
import com.pdmaf.utils.security.Encrypter
import com.pdmaf.ui.groovy.EditServiceRequestPage
import com.pdmaf.ui.groovy.EditServiceRequestConfirmationPage
import com.pdmaf.utils.exceptions.NameAlreadyExistException
import com.pdmaf.ui.groovy.DeleteServiceRequestConfirmationPage
import com.pdmaf.ui.groovy.PostServiceRequestCompletePage
import com.pdmaf.ui.groovy.ServiceRequestViewPage
import com.pdmaf.ui.groovy.ServiceRequestAddCommentPage
import com.pdmaf.business.models.Comment

class ServiceRequestController {

    final PersistenceService<ServiceRequest> database = new PersistenceService<ServiceRequest>()
    final DirectoryService directory = new DirectoryService()
    final Subject currentUser = SecurityUtils.getSubject()

    def postServiceRequest = {
      ServiceRequest serviceRequest
      final String description = params.description
      final String title = params.title
      final String replyEmail = params.email1
      final String showEmailPreference = params.showemail
      final String emailHashKey = "service-request" + "-" + (new java.util.Random().nextInt(10000000)).toString() + "@pdmaf.com"
      final String specificLocation = params.location
      final String city = params.city
      final String state = params.state
      final String country = params.country
      final String poster = currentUser.principal
      final String category = params.category
      final String compensation = params.compensation
      final String otherCompensation
      final String pointsOffered
      switch (compensation) {
        case "pointcompensation" : pointsOffered = params.pointsoffered
                                    break;
        case "othercompensation" : otherCompensation = params.othercompensation
                                    break;
        default                  :  break;
      }

      try {
        serviceRequest = new ServiceRequest(title,description, category, replyEmail, emailHashKey, showEmailPreference,
                                              specificLocation, city, state, country, poster, pointsOffered, otherCompensation)
        database.save(serviceRequest)
        EmailServiceRequestConfirmationPage emailServiceRequestConfirmationPage = new EmailServiceRequestConfirmationPage(serviceRequestID: Encrypter.encrypt(serviceRequest.id()))
        //the user will be in a saved state and it has to be activated thru the email
        //we can also add more security by creating token and save it to the db for rechecking when the user links back
        sendMail {
                    to params.email1
                    from "team@pdmaf.com"
                    subject "Email activation from PDMAF.com"
                    html emailServiceRequestConfirmationPage.render()
        }
        
      } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - postServiceRequest", databaseException)
        final String databaseErrorMessage = message(code:'serviceRequestController.postServiceRequest.databaseErrorMessage',
         args: null, locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": databaseErrorMessage])
      } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - postServiceRequest", integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.postServiceRequest.serverErrorMessage'
                , args: null, locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - postServiceRequest", exception)
        final String serverErrorMessage = message(code:'serviceRequestController.postServiceRequest.serverErrorMessage',
                args: null, locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])        
      }
      
      final PostServiceRequestConfirmationPage postServiceRequestConfirmationPage = new PostServiceRequestConfirmationPage()
      render(text: postServiceRequestConfirmationPage.render(), contentType: "text/html")
    }

    def activateServiceRequest = {
      final String serviceRequestID = Encrypter.decrypt(params.serviceRequestID)
      ServiceRequest serviceRequest = null
      try {
        serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
      } catch (NameAlreadyExistException nameAlreadyExistException) {
        log.error("Error has occurred in ServiceRequestController - publishServiceRequest for " + serviceRequestID, nameAlreadyExistException)
        final String nameAlreadyExistErrorMessage = message(code:'serviceRequestController.activateServiceRequest.nameAlreadyExistErrorMessage'
               , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": nameAlreadyExistErrorMessage])
      } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - activateServiceRequest for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.activateServiceRequest.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - activateServiceRequestfor " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.activateServiceRequest.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - activateServiceRequestfor " + serviceRequestID, exception)        
        final String serverErrorMessage = message(code:'serviceRequestController.activateServiceRequest.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      }
      if (serviceRequest != null) {
        PostServiceRequestActivationPage postServiceRequestActivationPage = new PostServiceRequestActivationPage(serviceRequest: serviceRequest)
        render(text: postServiceRequestActivationPage.render(), contentType: "text/html")
      } else {
        log.error("Error has occurred in ServiceRequestController - activateServiceRequest : null serviceRequest retrieval for " + serviceRequestID)
        final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.activateServiceRequest.nullServiceRequestMessage'
                , args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])                
      }
    }

  def publishServiceRequest = {
    final String serviceRequestID = params.serviceRequestID
    final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
    try {
         if(serviceRequest) {
            if(params.confirmservicerequest) {
                if (serviceRequest.hasBeenActivated()) {
                  final String errorMessage ="The service request id " + serviceRequestID +
                  " has already been activated"
                  redirect(controller: 'application', action: 'showError', params:["errorMessage": errorMessage])
                }
                directory.createServiceRequestEmail(serviceRequest.getEmailHashKey(), serviceRequest.getReplyEmail())
                serviceRequest.activate()
                database.update(serviceRequest)
                final PostServiceRequestCompletePage postServiceRequestCompletePage = new PostServiceRequestCompletePage()
                render(text: postServiceRequestCompletePage.render() + serviceRequestID , contentType: "text/html")
            } else if (params.editservicerequest) {
                if (serviceRequest.isActive() || serviceRequest.hasExpired()) {
                  final String errorMessage ="The service request id " + serviceRequestID +
                  " has already been published.  If you would like to edit the request, please delete the current request and repost new request."
                  redirect(controller: 'application', action: 'showError', params:["errorMessage": errorMessage])
                }
                redirect(action: 'editServiceRequest', params: params)
            } else if (params.deleteservicerequest) {
                database.unbind('ServiceRequest', serviceRequest.id())
                directory.unbind(serviceRequest.id())
                final DeleteServiceRequestConfirmationPage deleteServiceRequestConfirmationPage = new DeleteServiceRequestConfirmationPage()
                render(text: deleteServiceRequestConfirmationPage.render(), contentType: "text/html")
            } else {
                log.error("Error has occurred in ServiceRequestController - activateServiceRequestfor " + serviceRequestID + """
                            the client browser has selected unrecognized action.""")
                final String serverErrorMessage = message(code:'serviceRequestController.publishServiceRequest.serverErrorMessage'
                        , args:[serviceRequestID], locale: request.locale)
                redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
            }
          } else {
            log.error("Error has occurred in ServiceRequestController - publishServiceRequest : null serviceRequest retrieval for " + serviceRequestID)
            final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.publishServiceRequest.nullServiceRequestMessage'
                    , args: [serviceRequestID], locale: request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
          }
      } catch (NameAlreadyExistException nameAlreadyExistException) {
        log.error("Error has occurred in ServiceRequestController - publishServiceRequest for " + serviceRequestID, nameAlreadyExistException)
        final String nameAlreadyExistErrorMessage = message(code:'serviceRequestController.publishServiceRequest.nameAlreadyExistErrorMessage'
               , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": nameAlreadyExistErrorMessage])
      } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - publishServiceRequest for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.publishServiceRequest.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - publishServiceRequest " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.publishServiceRequest.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      } catch (DirectoryException directoryException) {
        log.error("Error has occurred in ServiceRequestController - postServiceRequest", directoryException)
        final String directoryErrorMessage = message(code:'serviceRequestController.publishServiceRequest.diretoryErrorMessage'
                ,args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": directoryErrorMessage])
      } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - activateServiceRequestfor " + serviceRequestID, exception)
        final String serverErrorMessage = message(code:'serviceRequestController.publishServiceRequest.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
      }
  }

  def editServiceRequest = {
    final String serviceRequestID = params.serviceRequestID
    final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
    if(serviceRequest) {
       final EditServiceRequestPage editServiceRequestPage = new EditServiceRequestPage(serviceRequest: serviceRequest)
       render(text: editServiceRequestPage.render(), contentType: "text/html")
    } else {
       log.error("Error has occurred in ServiceRequestController - editServiceRequest : null serviceRequest retrieval for " + serviceRequestID)
       final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.editServiceRequest.nullServiceRequestMessage'
               , args: [serviceRequestID], locale: request.locale)
       redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])

    }
  }

  def confirmEditServiceRequest = {
    if(params.backtoservicerequestconfirmation) {
      redirect(action: redisplayServiceRequestActivation, params: params)
    }
    final String serviceRequestID = params.serviceRequestID
    final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
    ServiceRequest updatedServiceRequest
    final String description = params.description
    final String title = params.title
    final String replyEmail = params.email1
    final String showEmailPreference = params.showemail
    final String specificLocation = params.location
    final String city = params.city
    final String state = params.state
    final String country = params.country
    final String poster = currentUser.principal
    final String category = params.category
    final String compensation = params.compensation
    final String otherCompensation
    final String pointsOffered
    switch (compensation) {
        case "pointcompensation" : pointsOffered = params.pointsoffered
                                    break;
        case "othercompensation" : otherCompensation = params.othercompensation
                                    break;
        default                  :  break;
    }
    try {
      directory.createServiceRequestEmail(serviceRequest.getEmailHashKey(), serviceRequest.getReplyEmail())
      updatedServiceRequest = new ServiceRequest(title,description, category, replyEmail, null, showEmailPreference,
                                            specificLocation, city, state, country, poster, pointsOffered, otherCompensation)
      serviceRequest.update(updatedServiceRequest)
      serviceRequest.activate()
      database.update(serviceRequest)
    } catch (NameAlreadyExistException nameAlreadyExistException) {
       log.error("Error has occurred in ServiceRequestController - confirmEditServiceRequest for " + serviceRequestID, nameAlreadyExistException)
       final String nameAlreadyExistErrorMessage = message(code:'serviceRequestController.confirmEditServiceRequest.nameAlreadyExistErrorMessage'
              , args: [serviceRequestID] , locale: request.locale)
       redirect(controller: 'application', action: 'showError', params:["errorMessage": nameAlreadyExistErrorMessage])
    } catch (DatabaseException databaseException) {
      log.error("Error has occurred in ServiceRequestController - confirmEditServiceRequest", databaseException)
      final String databaseErrorMessage = message(code:'serviceRequestController.confirmEditServiceRequest.databaseErrorMessage',
       args: [serviceRequestID], locale: request.locale)
      redirect(controller: 'application', action: 'showError', params:["errorMessage": databaseErrorMessage])
    } catch (DirectoryException directoryException) {
      log.error("Error has occurred in ServiceRequestController - confirmEditServiceRequest", directoryException)
      final String directoryErrorMessage = message(code:'serviceRequestController.confirmEditServiceRequest.diretoryErrorMessage'
              ,args: [serviceRequestID], locale: request.locale)
      redirect(controller: 'application', action: 'showError', params:["errorMessage": directoryErrorMessage])
    } catch (IntegrationException integrationException) {
      log.error("Error has occurred in ServiceRequestController - confirmEditServiceRequest", integrationException)
      final String serverErrorMessage = message(code:'serviceRequestController.confirmEditServiceRequest.serverErrorMessage'
              , args: [serviceRequestID], locale: request.locale)
      redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (Exception exception) {
      log.error("Error has occurred in ServiceRequestController - confirmEditServiceRequest", exception)
      final String serverErrorMessage = message(code:'serviceRequestController.confirmEditServiceRequest.serverErrorMessage',
              args: [serviceRequestID], locale: request.locale)
      redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    }

    final EditServiceRequestConfirmationPage editServiceRequestConfirmationPage = new EditServiceRequestConfirmationPage()
    render(text: editServiceRequestConfirmationPage.render(), contentType: "text/html")
  }

  def redisplayServiceRequestActivation = {
    final String serviceRequestID = params.serviceRequestID
    ServiceRequest serviceRequest = null
    try {
        serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
    } catch (NameAlreadyExistException nameAlreadyExistException) {
        log.error("Error has occurred in ServiceRequestController - redisplayServiceRequestActivation for " + serviceRequestID, nameAlreadyExistException)
        final String nameAlreadyExistErrorMessage = message(code:'serviceRequestController.redisplayServiceRequestActivationt.nameAlreadyExistErrorMessage'
               , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": nameAlreadyExistErrorMessage])
    } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - redisplayServiceRequestActivationt for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.redisplayServiceRequestActivation.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - redisplayServiceRequestActivation for " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.redisplayServiceRequestActivation.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - redisplayServiceRequestActivation for " + serviceRequestID, exception)
        final String serverErrorMessage = message(code:'serviceRequestController.redisplayServiceRequestActivation.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    }
    if (serviceRequest != null) {
        PostServiceRequestActivationPage postServiceRequestActivationPage = new PostServiceRequestActivationPage(serviceRequest: serviceRequest)
        render(text: postServiceRequestActivationPage.render(), contentType: "text/html")
    } else {
        log.error("Error has occurred in ServiceRequestController - redisplayServiceRequestActivation : null serviceRequest retrieval for " + serviceRequestID)
        final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.redisplayServiceRequestActivation.nullServiceRequestMessage'
                , args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
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

  def viewServiceRequest = {
    final String pageType = params.pageType
    final String serviceRequestID = params.serviceRequestID
    try {
        final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
        if (serviceRequest == null) {
          log.error("Error has occurred in ServiceRequestController - viewServiceRequest : null serviceRequest retrieval for " + serviceRequestID)
          final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.viewServiceRequest.nullServiceRequestMessage'
                  , args: [serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
        }
        final ServiceRequestViewPage serviceRequestViewPage = new ServiceRequestViewPage(serviceRequest: serviceRequest, message: params.flashMessage)
        render(text: serviceRequestViewPage.render(), contentType:"text/html")
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

  def addComment = {
    final String serviceRequestID = params.serviceRequestID
    final String author = params.author
    final String text = params.text
    final Comment comment = new Comment(text, author)
    try {
        final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
        if (serviceRequest == null) {
          log.error("Error has occurred in ServiceRequestController - addComment : null serviceRequest retrieval for " + serviceRequestID)
          final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.addComment.nullServiceRequestMessage'
                  , args: [serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
        }
        serviceRequest.addComment(comment)
        serviceRequest = database.update(serviceRequest)
        final ServiceRequestViewPage serviceRequestViewPage = new ServiceRequestViewPage(serviceRequest: serviceRequest)
        render(text: serviceRequestViewPage.render(), contentType:"text/html")
   } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - addComment for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.addComment.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - addComment for " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.addComment.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - addComment for " + serviceRequestID, exception)
        final String serverErrorMessage = message(code:'serviceRequestController.addComment.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    }
  }

  def showAddComment = {
    final String serviceRequestID = params.serviceRequestID
    try {
        final ServiceRequest serviceRequest = database.retrieveById('ServiceRequest', serviceRequestID)
        if (serviceRequest == null) {
          log.error("Error has occurred in ServiceRequestController - showAddComment : null serviceRequest retrieval for " + serviceRequestID)
          final String nullServiceRequestErrorMessage = message(code:'serviceRequestController.showAddComment.nullServiceRequestMessage'
                  , args: [serviceRequestID], locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": nullServiceRequestErrorMessage])
        }
        final ServiceRequestAddCommentPage serviceRequestAddCommentPage = new ServiceRequestAddCommentPage(serviceRequest: serviceRequest)
        render(text: serviceRequestAddCommentPage.render(), contentType:"text/html")
   } catch (DatabaseException databaseException) {
        log.error("Error has occurred in ServiceRequestController - showAddComment for " + serviceRequestID, databaseException)
        final String serverErrorMessage = message(code:'serviceRequestController.showAddComment.databaseErrorMessage'
                , args: [serviceRequestID] , locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (IntegrationException integrationException) {
        log.error("Error has occurred in ServiceRequestController - showAddComment for " + serviceRequestID, integrationException)
        final String serverErrorMessage = message(code:'serviceRequestController.showAddComment.serverErrorMessage'
                ,args: [serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    } catch (Exception exception) {
        log.error("Error has occurred in ServiceRequestController - showAddComment for " + serviceRequestID, exception)
        final String serverErrorMessage = message(code:'serviceRequestController.showAddComment.serverErrorMessage'
                , args:[serviceRequestID], locale: request.locale)
        redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
    }
  }
}
