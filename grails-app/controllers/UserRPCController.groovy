import com.pdmaf.service.directory.DirectoryService
import com.pdmaf.utils.json.UserAssembler
import com.pdmaf.business.models.User
import org.jsecurity.authc.UsernamePasswordToken
import org.jsecurity.authc.AuthenticationException

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 15, 2009
 * Time: 9:03:33 PM
 * This is the service controller for client code like GWT to call.
 *
 */

public class UserRPCController {

    def jsecSecurityManager
    def directory = new DirectoryService()
  
    def signIn = {

      final String callback = params.callback
      final String username = params.username
      final String password = params.password
      final boolean rememberMe = params.rememberMe
      final UsernamePasswordToken authToken = new UsernamePasswordToken(username, password)

      // Support for "remember me"
      if (rememberMe) {
          authToken.rememberMe = true
      }

      try{
          // Perform the actual login. An AuthenticationException
          // will be thrown if the username is unrecognised or the
          // password is incorrect.
          this.jsecSecurityManager.login(authToken)

          //if we get here then we will write out the response to writer
          User user = directory.retrieve(username)
          final String userProfile = UserAssembler.assembleUserProfile(user)
          final String sessionId = jsecSecurityManager.getSubject().getSession().getId()
          response.getWriter().flush()
          response.getWriter().append(callback + """({ 'loginResponse' :  'SUCCESS',
                                                            'userProfile'   :  ${userProfile},
                                                            'rememberMe'    :  "${sessionId}" });""")
          response.getWriter().close()

      } catch (AuthenticationException ex){

          // Authentication failed, so display the appropriate message
          // on the login page.
          log.info "Authentication failure for user '${username}'."

          response.getWriter().flush()
          //we have to pass the status as a stright string else the receiver will fail if we pass it as string from enum
          response.getWriter().append(callback + """({ 'loginResponse' :  'BADUSERNAMEORBADPASSWORD',
                                                            'userProfile'   :  {},
                                                            'rememberMe'    :  "${rememberMe}" });""")

          response.getWriter().close()
      }
    }
}