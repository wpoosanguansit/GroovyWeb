package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 28, 2009
 * Time: 4:15:23 PM
 * 
 */

public class ForgetYourPasswordPage extends MainPage {

    def protected secondaryHeaderInclude = {
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/forgetpassword.js") { mkp.yield ' '}
    }


    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
            }
        }

    }

    def protected pageBodyMainContent = {
        String username =  ""
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp' }
        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
		div(class: 'span-9') {
            mkp.yieldUnescaped("""
                    <h6>
                        Please fill in your username below.  An email with temporary password will be sent to you at the
                        specified address.
                    </h6><br><br>
                    <form id="forgetpassword" action="${url}userManagement/resetPassword">
                      <fieldset>
					    <legend>Please provide your username :</legend>
                        <p>
                           <label class="desc" id="username-label" for="username">
                             Username :
                           </label>
						    <br>
                           <span>
                           <input id="username" name="username" type="text" class="field text" title="Please type in your email as your password..." value="${username}" size="25" tabindex="1"></input>
                           </span>
						 <p>
                         <p>
                             <input id="sendEmail" class="button" type="submit" value="Send Email" tabindex="2" />
                         </p>
                      </fieldset>
                    </form>
            """)
        }
    }
}