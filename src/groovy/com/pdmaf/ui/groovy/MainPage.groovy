/**
 * Default package for all views that generate html
 */
package com.pdmaf.ui.groovy;

/**
 * @author watt poosanguansit
 * This is the main page structure that all the pages in groovy package inherits,
 * it sets up the basic page layout in grid using blueprintCSS.
 *
 */

import org.codehaus.groovy.grails.commons.ConfigurationHolder 
import groovy.xml.StreamingMarkupBuilder
import org.jsecurity.subject.Subject
import org.jsecurity.SecurityUtils

public class MainPage {
    //this is for the controller to pass in params, message and locale object
    //and hence internationalized string to pages
    def params
    def messageSource
    def locale
    final String url = ConfigurationHolder.config.grails.serverURL
    final String secureUrl = ConfigurationHolder.config.grails.serverSecureURL
    //get the current user in the session
    final Subject currentUser = SecurityUtils.getSubject()
    final String pageName = this.class.name.substring(this.class.name.lastIndexOf('.') + 1)
    //we define each section that a page should render
    //then we override in the extended class to customize
    //we need { mkp.yield ' '} because it is a bug when there is no </script>
    //the next tag will not be read and output
	 			
    def protected pageHeader = {
        head {
            out << standardHeaderInclude
            out << secondaryHeaderInclude
            out << gwtInclude
        }
    }

    def protected standardHeaderInclude = {
            title("Please Do Me A Favor!")
            link(rel: "stylesheet", href: url + "css/blueprint/screen.css", type: "text/css", media: "screen")
            link(rel: "stylesheet", href: url + "css/blueprint/plugins/fancy-type/screen.css", type: "text/css", media: "screen")
            link(rel: "stylesheet", href: url + "css/page.css", type: "text/css", media: "screen, projection")
            mkp.yieldUnescaped """
            <!--[if IE]>
                <link rel="stylesheet" href="css/blueprint/ie.css" type="text/css" media="screen, projection" />
            <![endif]-->
            """
            meta(http_equiv: "Content-Type", content: "text/html", charset: "utf-8")
            //we setup the variables that will be passed on to gwt pages here
            script(type: "text/javascript") {
                String userName = ""
                if (currentUser.isAuthenticated())  {
                  userName = currentUser.getPrincipal() ?: ""
                }
                mkp.yieldUnescaped """
                  var isomorphicDir = "${url}gwt/com.pdmaf.ui.gwt.PDMAFWeb/sc/";
                  var pageName = "${pageName}";
                  var url = "${url}";
                  var secureUrl = "${secureUrl}";
                  var COOKIEDOMAIN = url;
                  var currentUser = "${userName}";
                """
            }

    }

    def protected secondaryHeaderInclude = {}
  
    def protected gwtInclude = {
        script(language: "javascript", src: url + "js/json.js") { mkp.yield ' '}
        script(language: "javascript", src: url + "gwt/com.pdmaf.ui.gwt.PDMAFWeb/com.pdmaf.ui.gwt.PDMAFWeb.nocache.js") { mkp.yield ' '}

    }

    def protected pageBody = {
        body {
            out << pageBodyContent
        }
    }

    def protected pageFooter = {
        //todo: about us, terms of use, privacy, help and feedback, abuse prevention, site map, faq, make donation
        //todo: contact
        div(id: 'footer') {
            p { mkp.yieldUnescaped """&copy; 2009. All Rights Reserved. Design by Watt Poosanguansit"""}
               
        }
    }
    
    def protected pageBodyContent = {
    	div(class: 'container showgrid') {
            out << pageBodyNavigation
	    	out << pageBodyHeader
	    	out << pageBodyBanner
	    	out << pageBodyMainContent
        }
    }
    
    def protected pageBodyNavigation = {
        div(class: 'span-11') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
        div(class: 'span-12', id: 'nav-panel', style: 'display: true; height: 47px;' ) {
             a(href: url + 'application/index', 'home')
             mkp.yieldUnescaped '&nbsp;|&nbsp;'
             a(href: url + 'application/howItWorks', 'how it works')
             mkp.yieldUnescaped '&nbsp;|&nbsp;'
             if (currentUser.isAuthenticated()) {
                mkp.yieldUnescaped("""
   					 <a href="${url}auth/signOut">logout</a>
   					 """)
             } else {
                mkp.yieldUnescaped("""
                    <span id="login-text">
   					  <a href="${url}auth/login?targetUri=/application/profile">login</a>
                    </span>
   					""")
             }
             mkp.yieldUnescaped '&nbsp;|&nbsp;'
             if (!currentUser.isAuthenticated()) {
                a(href: url + 'application/register', 'sign up')
                mkp.yieldUnescaped '&nbsp;|&nbsp;'
                a(href: url + 'application/makeDonation', 'make donation')
                mkp.yieldUnescaped '&nbsp;|&nbsp;'
                a(href: url + 'application/listing', 'go to listing')
             } else {
                a(href: url + 'application/profile', 'profile')
             }

        }
      
        div(class: 'span-12', id: 'nav-panel-login-form', style: 'display: none') {
               mkp.yieldUnescaped """
                        <div id="${pageName}_loginform" style="width: 100%;">
                            <form name="login_form" method="post" action="${url}auth/signIn?targetUri=/application/profile" onsubmit="login_submit_${pageName}(); return false;">
		                        <div>
                                    <span id="${pageName}_real" style="display: none">
                                        <input type="text" autocomplete="off" name="username" class="login" id="${pageName}_1"/>&nbsp;
                                        <input type="password" autocomplete="off" name="password" class="password" id="${pageName}_2"/>
                                        <input class="button" type="submit" value="Login" id="${pageName}_6" />
                                        <input class="button" type="submit" value="X" id="${pageName}_7" onclick="login_form_close_${pageName}(); return false;" />
                                    </span>
                                    <span id="${pageName}_dummy">
                                        <input class="instructional login" autocomplete="off" style="color: grey;" name="dummyUsername" type="text" value="username/email" id="${pageName}_3"/>&nbsp;
                                        <input class="instructional password" autocomplete="off" style="color: grey;" name="dummyPassword" type="text" value="password" id="${pageName}_4"/>
                                        <input class="buttonDisabled" disabled="true" type="submit" value="Login" id="${pageName}_8" />
                                        <input class="button" type="submit" value="X" id="${pageName}_9" onclick="login_form_close_${pageName}(); return false;" />
                                    </span>
		                        </div>
		                        <div>
			                        <input type="checkbox" checked="checked" id="${pageName}_5"/><span class="label">remember me</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style="visibility: hidden;" id="${pageName}_10"></span>
		                        </div>
                            </form>
                        </div>
              """
         }
         div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
         div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }

    }
    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p { 
                img(src: url + 'images/logo.jpg', class: 'top pull-1 left', alt: 'Please Do Me A Favor! Logo.')

            }
        }

    }

    def protected pageBodyBanner = {
        div(class: 'span-10 prepend-2') {
            p {
                a(href: "${url}application/index") {
                    img(src: url + 'images/pdmaf.jpg', border:  '0', class: 'top pull-1 left',
                            alt: 'Please Do Me A Favor! Banner.')
                }
            }
        }
    }

    def protected pageBodyMainContent = {
        div(class: 'span-8 last prepend-3') {
            h3('Putting fun into Charitable Givings!')
            blockquote { mkp.yieldUnescaped """I respect generosity in people, and I respect it in companies too,
                                               I do not look at it as philanthropy; I see it as an investment in
                                               the community."""

                        p { mkp.yieldUnescaped """Paul Newman - Respect - Generous - People - Giving""" }

            }
            p { mkp.yieldUnescaped("""PDMAF is a small attempt to bring together charitable givings and fun.
                                      We want to make it hip to reward people for being a force
                                      for good and have the most fun while being at it.  Let's bring back the
                                      spirit of giving!""")
            }
        }
        div(class: 'span-8 last prepend-3') {
            p { mkp.yieldUnescaped("""For a starter, sign up with us as a service
                            provider and earn points you can cash in for all the services you provide.""")
		                    a(href: 'application/provider', 'more detail...->')
            }
        }
        div(class: 'span-8 last prepend-3') {
            p { mkp.yieldUnescaped("""For people who need a little favor to ease your day, you can entice someone
                            with the skills you look for by rewarding them with points you earn from making donations
                            to your favorite charities.""")
                            a(href: 'application/donor', 'more detail...-> ')
            }
        }
    }

    def private result = new StreamingMarkupBuilder().bind {
        html() {
            out << pageHeader
	 		out << pageBody
            out << pageFooter
        }
    }

    def render() {
        def htmlWriter = new StringWriter()
        htmlWriter << result
        result = htmlWriter.toString().replaceAll("\r","")
    }
}
