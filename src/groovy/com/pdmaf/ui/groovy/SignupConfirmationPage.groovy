package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 28, 2009
 * Time: 7:37:54 AM
 * 
 */

public class SignupConfirmationPage extends MainPage {
    
    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }


    def protected pageBodyMainContent = {
                        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-9') {
			            	mkp.yieldUnescaped """
                                <h6>
                                Thank you for signing up with us.  The email has been sent out for your confirmation.
                                <br><br><br>
                                Please check your email to complete your registration.
                                </h6>
			            	    """
                         }

    }
}