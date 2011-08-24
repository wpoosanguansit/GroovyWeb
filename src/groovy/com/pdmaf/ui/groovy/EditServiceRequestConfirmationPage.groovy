package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 6, 2009
 * Time: 6:51:40 PM
 *  
 */

public class EditServiceRequestConfirmationPage extends MainPage {
    def protected pageBodyHeader = {
        div(class: 'span-17 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }


    def protected pageBodyMainContent = {
                        div(class: 'span-10') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-10') {
			            	mkp.yieldUnescaped("""
                                <h6>
                                Your service request has been successfully updated and published.
                                Thank you for posting your request with us.
                                <br><br>
                                Please continue to help us support your favorite charity!
                                <br><br>
                                </h6>
			            	  """)
                         }

    }
}