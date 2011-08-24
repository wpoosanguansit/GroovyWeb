package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 28, 2009
 * Time: 4:47:22 PM
 * 
 */

public class EmailPasswordResetPage extends MainPage {
    
    String tempPassword

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
                             Your password has been reset.  Your temporary password is ${tempPassword}.  Please
                             click on the link below to login with your new password.
                             <br>
                             <br>
                               <a href=${url}/application/index>${url}</a> 
                             <br>
                             <br>
                             We apologize if this email is incorrectly sent to your mail box.  Please discard this email.
                             <br>
                             <br>
                             Sincerely,
                             <br>
                             <br>
                             PDMAF team
			            	  """)
                         }

    }

    def protected pageFooter = {

    }
}