package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 7, 2009
 * Time: 8:27:15 AM
 *  
 */

public class PostServiceRequestCompletePage extends MainPage {
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
                                Thank you for posting service request with us.  We hope you will
                                be successful with your request.
                                <br><br>
                                Please continue to help us support your favorite charity!
                                </h6>
			            	  """)
                         }

    }
}