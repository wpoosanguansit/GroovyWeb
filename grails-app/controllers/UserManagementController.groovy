/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: Mar 6, 2009
 * Time: 10:17:58 PM
 *
 * This is a dispatch for registration process code.
 *
 */

import com.pdmaf.service.directory.*;
import com.pdmaf.business.models.User
import com.pdmaf.ui.groovy.EmailSignupConfirmationPage
import com.pdmaf.ui.groovy.SignupConfirmationPage
import com.pdmaf.ui.groovy.SignupCompletePage
import com.pdmaf.ui.groovy.EmailPasswordResetPage
import com.pdmaf.ui.groovy.PasswordResetConfirmationPage
import com.pdmaf.utils.exceptions.IntegrationException
import com.pdmaf.utils.exceptions.DirectoryException
import com.pdmaf.utils.security.Encrypter
import com.pdmaf.utils.exceptions.NameAlreadyExistException

public class UserManagementController {

    def messageSource
    final DirectoryService directory = new DirectoryService()

    def register = {
        //check if the username exist, if it does - show error page
        if (directory.retrieve(params.username1) != null) {
          String errorMessage  = messageSource.getMessage('userManagementController.register.errorMessage', null, request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": errorMessage])
          return;
        }

        final EmailSignupConfirmationPage emailConfirmation = new EmailSignupConfirmationPage(username: params.username1,
                                                                                key: Encrypter.encrypt(params.password1))

        //the user will be in a saved state and it has to be activated thru the email
        //we can also add more security by creating token and save it to the db for rechecking when the user links back
        sendMail {
                    to params.username1
                    from "watt@pdmaf.com"
                    subject "Email activation from PDMAF.com"
                    html emailConfirmation.render()   
        }

        //now render the confirmation and instruction to the user
        SignupConfirmationPage confirmationPage = new SignupConfirmationPage()
		render(text: confirmationPage.render(), contentType: "text/html")
    }

    def persist = {

        final User user = new User(params.username, Encrypter.decrypt(params.key))
        
        try {
            directory.save(user)
        } catch (NameAlreadyExistException nameAlreadyExistException) {
          log.error("Error has occurred in UserManagementController - persist for ", nameAlreadyExistException)
          final String nameAlreadyExistErrorMessage = message(code:'userManagementController.persist.nameAlreadyExistErrorMessage'
                 , null , locale: request.locale)
          redirect(controller: 'application', action: 'showError', params:["errorMessage": nameAlreadyExistErrorMessage])
        } catch (DirectoryException directoryException) {
            log.error("Error has occurred in UserManagementController - persist", directoryException)
            final String directoryErrorMessage  = messageSource.getMessage('userManagementController.persist.directoryErrorMessage'
                    , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": directoryErrorMessage])

        } catch (IntegrationException integrationException) {
            log.error("Error has occurred in UserManagementController - persist", integrationException)                      
            final String serverErrorMessage = messageSource.getMessage('userManagementController.persist.serverErrorMessage'
                    , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
        } catch (Exception exception) {
            log.error("Error has occurred in UserManagementController - persist", exception)                      
            final String serverErrorMessage = messageSource.getMessage('userManagementController.persist.serverErrorMessage'
                  , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])

        }

        SignupCompletePage signupCompletePage = new SignupCompletePage()
        render(text: signupCompletePage.render(), contentType: "text/html")
    }

    def resetPassword = {
        User user
        try {
          user = directory.retrieve(params.username)
        } catch (DirectoryException directoryException) {
            log.error("Error has occurred in UserManagementController - resetPassword", directoryException)
            final String directoryErrorMessage  = messageSource.getMessage('userManagementController.resetPassword.directoryErrorMessage'
                    , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": directoryErrorMessage])

        } catch (IntegrationException integrationException) {
            log.error("Error has occurred in UserManagementController - persist", integrationException)
            final String serverErrorMessage = messageSource.getMessage('userManagementController.resetPassword.serverErrorMessage'
                    , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
        } catch (Exception exception) {
            log.error("Error has occurred in UserManagementController - persist", exception)
            final String serverErrorMessage = messageSource.getMessage('userManagementController.resetPassword.serverErrorMessage'
                  , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": serverErrorMessage])
        }

        if (user != null) {
            String resetPassword = user.resetPassword();
            directory.update(user);

            final EmailPasswordResetPage resetEmail = new EmailPasswordResetPage(tempPassword: resetPassword)
            sendMail {
                    to user.username()
                    from "watt@pdmaf.com"
                    subject "Password reset on PDMAF.com"
                    html resetEmail.render()
            }

            final PasswordResetConfirmationPage resetConfirmationPage = new PasswordResetConfirmationPage()
            render(text: resetConfirmationPage.render(), contentType: "text/html")
        } else {
            final String passwordError = messageSource.getMessage('userManagementController.resetPassword.passwordError'
                    , null, request.locale)
            redirect(controller: 'application', action: 'showError', params:["errorMessage": passwordError])
        }
    }
}