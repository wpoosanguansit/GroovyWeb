package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 3, 2009
 * Time: 4:10:23 PM
 *  
 */

public class PostServiceRequestConfirmationPage extends MainPage {
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
                              Thank you for posting your service request.
                              <br><br>
                              Please check your email to activate your posting.
                              <br><br>
                              PDMAF.com team
                              <h6>
                            """)
                       }

  }
}