package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 6, 2009
 * Time: 3:34:36 PM
 *  
 */

public class ErrorPage extends MainPage {

    String errorMessage

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: ' ')
            }
        }

    }


    def protected pageBodyMainContent = {
        final String introduction = messageSource.getMessage("errorPage.introduction", null,  locale)
        final String redirectionError = messageSource.getMessage("errorPage.redirectionError",null, locale)
        final String displayedErrorMessage = errorMessage ? (introduction + errorMessage) :  redirectionError
                        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp' }
                        div(class: 'span-7') { mkp.yieldUnescaped '&nbsp' }
		                div(class: 'span-9') {
			            	mkp.yieldUnescaped("""
                                  <h5> ${displayedErrorMessage} </h5>
			            	  """)
                         }

    }
}