package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 28, 2009
 * Time: 4:59:01 PM
 * 
 */

public class PasswordResetConfirmationPage extends MainPage {

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: ' ')
            }
        }

    }


    def protected pageBodyMainContent = {
                        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-9') {
			            	mkp.yieldUnescaped("""
                                <h6>
                                Your password has been reset.  Please check your email to relogin to the site.
                                <br><br>
                                Thank you and we hope to see you soon.
                                <br><br>
                                PDMAF.com team
                                <h6>
			            	  """)
                         }

    }
}