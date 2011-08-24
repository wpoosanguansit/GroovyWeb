package com.pdmaf.ui.groovy

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 27, 2009
 * Time: 9:23:59 PM
 * 
 */

public class EmailSignupConfirmationPage extends MainPage {

    String username
    String key

    def protected pageBodyHeader = {
        div(class: 'span-17 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }
    
    //we have to encode the key as url because when it comes in it has to be read in as sent out
    def protected pageBodyMainContent = {
                        div(class: 'span-5') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-20') {
			            	mkp.yieldUnescaped("""
                             Thank you for registering with us.  Please click on the link below to activate your account.
                             <br>
                             <br>
                                <a href=${url}userManagement/persist?username=${username}&amp;key=${key.encodeAsURL()}>Click here</a>
                             <br>
                             <br>
                                or cut and paste this in your browser to continue:
                             <br>
                             <br>
                                ${url}userManagement/persist?username=${username}&amp;key=${key.encodeAsURL()}
                             <br>
                             <br>
                             We apologize if this email is incorrectly sent to your mail box.  Please discard this email.
                             <br>
                             <br>
                             Sincerely,
                             <br>
                             <br>
                             PDMAF.com team.
			            	 """)
                         }

    }
        
    def protected pageFooter = {

    }
}