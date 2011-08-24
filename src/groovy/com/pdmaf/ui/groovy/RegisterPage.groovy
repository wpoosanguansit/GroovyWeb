/**
 * The secondary pages that inherit from the Main page,
 * it uses the main page elements as default and override 
 * them as appropriate.
 */
package com.pdmaf.ui.groovy
/**
 * @author watt poosanguansit
 *
 */
public class RegisterPage extends MainPage {

   def protected secondaryHeaderInclude = {
            mkp.yieldUnescaped """
               <!--[if lte IE 7]>
			      <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen" />
               <![endif]-->
            """
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.ezpz_hint.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/register.js") { mkp.yield ' '}
   }

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }


    def protected pageBodyMainContent = {
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp' }
        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
        div(class: 'span-9') {
            mkp.yieldUnescaped("""
                        <form id="registration" name="registration"
                            enctype="multipart/form-data" method="post" action="${url}userManagement/register">
							<fieldset>
								<legend>Please fill in the form below :</legend>
									<p>
                                        <label class="desc" id="username-label" for="username1">
                                            Username :
                                        </label>
										<br>
                                        <span>
                                            <input id="username1" name="username1" type="text" class="field text" title="Please type in your email as your password..." value="" size="25" tabindex="1" />
                                        </span>
										<span id="username-checkmark" ></span>
										<p>
										    Please provide your active email as your username.  An email will be sent for confirmation.
										</p>
										</p><br>
										<P>
                                          <label class="desc" id="password-label" for="password1">
                                              Password :
                                          </label>
										  <br>
                                          <span>
                                              <input id="password1" name="password1" type="password" class="field text" title="Password must be at least 6 characters..." value="" size="25" tabindex="2" />
										 </span>
                                          <span>
                                              <input id="password2" name="password2" type="password" class="field text" title="Please confirm your password..." value="" size="25" tabindex="3" />
                                          </span>
                                          <span id="password-checkmark" ></span>
										  <p>
											Please provide a password of at least 6 characters and confirm your password.
										  </p>
										</p>
										<p><br>
                                           <span>
                                               <input id="agreement" name="agreeement" type="checkbox" class="field checkbox" value="" tabindex="4" />
                                                By clicking the box above and on 'Submit' below, you confirm that you are over 13 years of age and accept the
                                                <a href="${url}termOfService.xhtml">Terms of Service</a> .
                                           </span>
										</p>
										<div>
                                          <span>
                                            <input id="saveform" class="button" type="submit" value="Submit" tabindex="5" />
                                          </span>
										</div>
							</fieldset>
                         </form>
			            	  """)
                         }

    }
}


