package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 25, 2009
 * Time: 6:35:59 AM
 * 
 */

public class ServiceRequestListingPage extends MainPage {

    def secondaryHeaderInclude = {
            link(rel: "stylesheet", href: url + "css/smoothness/jquery-ui-1.7.1.custom.css", type: "text/css", media: "screen, projection")
            mkp.yieldUnescaped """
            <!--[if lte IE 7]>
			  <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen" />
            <![endif]-->
            """
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-ui-1.7.1.custom.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/servicerequestlisting.js") { mkp.yield ' '}
    }

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
            }
        }

    }

    def protected pageBodyMainContent = {
      div(class: 'container') {
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
		div(class: 'span-24') {
           mkp.yieldUnescaped("""
                      		<div class="span-24">
                            <div id="service-listing-tabs">
                                          <ul id="main-tabs">
                                              <li><a href="#search-list">Search</a></li>
                                              <li><a href="#service-requests-list">Service Requests</a></li>
                                              <li><a href="#service-providers-list">Service Providers</a></li>
                                              <li><a href="#donor-list">Donor List</a></li>
                                          </ul>
                                          <div id="search-list">
                                            <div class="span-18"> &nbsp; </div>
                                            <a id="searchlink" class="span-5 last" href="#">Advanced Search</a>
                                            <form id="search-form" name="search-form" class="normalform span-20 prepend-2" action="" onsubmit="service_requests_search(); return false;" >
                                                    <br>
                                                    <span id="search-container"></span>
                                                    <span id="default-search-span" class="span-16" style="display : none;">
                                                            <div class="prepend-4">
                                                                <input type="text" class="field text" name="search" value="" id="search" title="Search service request..." tabIndex="1" />
                                                                <input class="button" type="submit" value="Search" />
                                                            </div>
                                                    </span>
                                                    <span id="advanced-search-span" style="display : none;">
                                                            <br>
                                                            <fieldset>
                                                                <ol>
                                                                   <li>
                                                                    <label class="desc" id="allthewords-label" for="allthewords">
                                                                       all of the words :
                                                                    </label>
                                                                    <input id="allthewords" name="allthewords" type="text" class="field text" title="All of these words..." value="" size="25" tabindex="1" />
                                                                   </li>
                                                                   <li>
                                                                    <label class="desc" id="exactphrase-label" for="exactphrase">
                                                                       the exact phrase :
                                                                     </label>
                                                                     <input id="exactphrase" name="exactphrase" type="text" class="field text" title="Exact phrase..." value="" size="25" tabindex="2" />
                                                                   </li>
                                                                   <li>
                                                                    <label class="desc" id="atleastoneofthesewords-label" for="atleastoneofthesewords">
                                                                       at least one of the words  :
                                                                     </label>
                                                                     <input id="atleastoneofthesewords" name="atleastoneofthesewords" type="text" class="field text" title="At least one of these words..." value="" size="25" tabindex="3" />
                                                                   </li>
                                                                   <li>
                                                                    <label class="desc" id="noneofthesewords-label" for="noneofthesewords">
                                                                       none of the words  :
                                                                     </label>
                                                                     <input id="noneofthesewords" name="noneofthesewords" type="text" class="field text" title="None of these words..." value="" size="25" tabindex="4" />
                                                                   </li><br>
                                                                 <p>
                                                                   <input id="advancedsearch" name="advancedsearch" class="button" type="submit" value="Advanced search" tabindex="5" />
                                                                 </p>
                                                              </ol>
                                                            </fieldset>
                                                    </span>
                                            </form>
                                            <div id="search-result-panel" style="display: block">
                                                <div id="search-result" class="span-20 prepend-2">
                                                    <div class="pagination"><span class="current">1</span><a href="#?page=2">2</a><a href="#?page=3">3</a><a href="#?page=4">4</a><a href="#?page=5">5</a><a href="#?page=6">6</a><a href="#?page=7">7</a>...<a href="#?page=199">199</a><a href="#?page=200">200</a><a href="#?page=2" class="next">&#9658;</a></div>
                                                </div>
                                            </div>
                                          </div>
                                          <div id="service-requests-list">
                                            <div class="span-18">&nbsp;</div>
                                            <div id="post-service-request" ><a href="${url}application/postServiceRequest">Post New Request</a></div>
                                            <div id="sidebar" class="span-1">
                                                <div id="sidebar-menu">
                                                    <a id="registered-users" href="/"><img src="${url}images/tab_employers_1.gif" alt="Registered Users" /></a>
                                                    <a id="non-registered-users" href="/candidates/"><img src="${url}images/tab_employers_2.gif" alt="Non Registered Users" /></a>
                                                    <a href="/employers/"><img src="${url}images/tab_employers_3.gif" alt="Employers" /></a>
                                                </div>
                                              </div>
                                              <div id="service-request-by-registered-users" class="span-25">registered users</div>
                                              <div id="service-request-by-non-registered-users" class="span-25" style="display: none" >non registered users</div>
                                          </div>
                                          <div id="service-providers-list">
                                            <div class="span-16">&nbsp;</div>
                                            <div id="sing-up-as-service-provider" >To be listed as Service Provider : <a href="${url}application/register">Sign Up</a></div>
                                          </div>
                                          <div id="donor-list">
                                            <div class="span-18">&nbsp;</div>
                                            <div id="make-donation" >
                                              <a href="${url}application/makeDonation">Make Donation</a>
                                              &nbsp;&nbsp;|&nbsp;&nbsp;
                                              <a href="${url}application/register">Sign Up</a>
                                            </div>
                                          </div>
                                </div>
                            </div>
                            </div>
                      """)
        }
      }
    }
}