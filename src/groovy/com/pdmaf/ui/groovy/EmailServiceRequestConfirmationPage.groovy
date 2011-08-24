package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 4, 2009
 * Time: 8:14:31 PM
 *  
 */

public class EmailServiceRequestConfirmationPage extends MainPage {

    def serviceRequestID

    def protected pageBodyHeader = {
         div(class: 'span-17 prepend-1') {
             p {
                 img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
             }
         }

     }

     def protected pageBodyMainContent = {
                        div(class: 'span-5') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-20') {
			            	mkp.yieldUnescaped("""
                             Thank you for posting your service request with us.  Please click on the link below to complete the process.
                             <br>
                             <br>
                                <a href=${url}serviceRequest/activateServiceRequest?serviceRequestID=${serviceRequestID.encodeAsURL()}>Click here</a>
                             <br>
                             <br>
                                or cut and paste this in your browser to continue:
                             <br>
                             <br>
                                ${url}serviceRequest/activateServiceRequest?serviceRequestID=${serviceRequestID.encodeAsURL()}
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