/**
 * 
 */
package com.pdmaf.ui.groovy



/**
 * @author watt poosanguansit
 *
 */
public class LoginPage extends MainPage {

    String username, password, targetUri, message
    Boolean rememberMe

    def protected secondaryHeaderInclude = {
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.ezpz_hint.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/login.js") { mkp.yield ' '}
    }

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: ' ')
            }
        }

    }

    def protected pageBodyMainContent = {

        username = username ?: ""
        message = message ?: ""
        targetUri = targetUri ?: "/application/profile"
        final String error = message ? "<div class='error'>${message}</div>" : ""

        String checked = rememberMe ? 'checked' : ""
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp' }
        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
		div(class: 'span-9') {
            mkp.yieldUnescaped("""

                    ${error}

                    <form id="login" action="${url}auth/signIn">
                      <fieldset>
					    <legend>Login :</legend>
                        <input type="hidden" name="targetUri" value="${targetUri}" />
                        <p>
                           <label class="desc" id="username-label" for="username">
                             Username :
                           </label>
						    <br>
                           <span>
                           <input id="username" name="username" type="text" class="field text" title="Please type in your email as your password..." value="${username}" size="25" tabindex="1"></input>
                           <span id="username-checkmark" ></span>
                           </span>
						 <p>
                         <P>
                            <label class="desc" id="password-label" for="password1">
                                Password :
                            </label>
							<br>
                            <span>
                                <input id="password" name="password" type="password" title="Please type in your password..." class="field text" value="" size="25" tabindex="2" />
							</span>
                            <span id="password-checkmark" ></span>
                         </p>
                         <p>
                            Remember me :
                            <input id="rememberMe" name="rememberMe" type="checkBox" class="checkBox" value="${rememberMe}" tabindex="3" ${checked} />
						    &nbsp;&nbsp;|&nbsp;&nbsp;
						    forget your password : <a href="${url}application/forgetPassword">click here</a>
                         </p>
                         <p>
                             <input id="signIn" class="button" type="submit" value="Sign In" tabindex="4"/>
                         </p>
                      </fieldset>
                    </form>
            """)
        }
    }

}
