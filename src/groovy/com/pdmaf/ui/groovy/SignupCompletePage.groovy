package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 28, 2009
 * Time: 2:34:16 PM
 * 
 */

public class SignupCompletePage extends MainPage {
    
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
			            	mkp.yieldUnescaped("""
                                <h6>
                                Your account has been successfully created.  Please try logging in with your
                                newly created account.
                                <br><br>
                                Thank you and we hope you will enjoy the site as much as we do!
                                <br><br>
                                Ciao!
                                </h6>    
			            	  """)
                         }

    }
}