package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 7, 2009
 * Time: 8:26:16 AM
 *  
 */

public class DeleteServiceRequestConfirmationPage extends MainPage {
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
                                Your service request has been complete removed from our system.
                                <br><br>
                                Thank you and we hope you will come back soon!
                                <br><br>
                                </h6>
			            	  """)
                         }

    }
}