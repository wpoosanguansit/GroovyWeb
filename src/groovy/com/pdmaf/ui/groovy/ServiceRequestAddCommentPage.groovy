package com.pdmaf.ui.groovy

import com.pdmaf.business.models.ServiceRequest

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 11, 2009
 * Time: 11:26:15 AM
 *  
 */

public class ServiceRequestAddCommentPage extends MainPage {
  ServiceRequest serviceRequest
  String message
  def secondaryHeaderInclude = {
            link(rel: "stylesheet", href: url + "css/jquery.autocomplete.css", type: "text/css", media: "screen, projection")
            mkp.yieldUnescaped """
            <!--[if lte IE 7]>
			  <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen" />
            <![endif]-->
            """
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/servicerequest-addcomment.js") { mkp.yield ' '}
  }
  def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
            }
        }

  }

  def protected pageBodyMainContent = {
    final String flashMessage = message ?: ""
    if(serviceRequest) {
        div(class: 'container') {
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
		div(class: 'span-16 prepend-2') {
           mkp.yieldUnescaped("""
              <form id="publish-service-request" class="normalform" action="${url}serviceRequest/addComment" method="POST">
                <input  type="hidden" value="${serviceRequest.id()}" name="serviceRequestID">
                <fieldset>
                    <legend>Service Request</legend>
                        <h6>
                            ${flashMessage}
                        </h6><br>
                        <ol>
                            <li><label>Posting Title : </label> ${serviceRequest.getTitle()} </li>
                            <li><label>Reply Email : </label> ${if (serviceRequest.getShowEmailPreference() == "showemail")
                                                                        serviceRequest.getReplyEmail()
                                                                 else if (serviceRequest.getShowEmailPreference() == "anonymizedemail")
                                                                        serviceRequest.getEmailHashKey()
                                                                 else
                                                                        return "No email shown"
                                                                } </li>
                            <li><label>Specific Location : </label> ${serviceRequest.getSpecificLocation()} </li>
                            <li><label>City : </label> ${serviceRequest.getCity()} </li>
                            <li><label>State : </label> ${serviceRequest.getState()} </li>
                            <li><label>Country : </label> ${serviceRequest.getcountry()} </li>
                            <li><label>Category :</label> ${serviceRequest.getCategory()}</li>
                            <li><label>Full Description :</label> ${serviceRequest.getDescription()}</li>
                            <li><label>Compensation Preference : </label> ${if(serviceRequest.getPointsOffered())
                                                                                serviceRequest.getPointsOffered() + " points"
                                                                            else if (serviceRequest.getOthercompensation())
                                                                                serviceRequest.getOthercompensation()
                                                                            else
                                                                                return "No Compensation"
                                                                           }
                            </li>
                        </ol>
                </fieldset>
                <p>
                    <li>
                    <span>
						<input id="author" name="author" type="text" class="field text" title="Plese fill in your name..." value="" size="25" tabindex="7" />
					</span>
                    <span id="author-checkmark" ></span>
                    </li>
                    </i>
                    <span>
                        <textarea cols="40" rows="5" name="text" type="text" class="field text" title="Please fill in your comment.  In appropriate content will be removed.  Please be considerate in providing accurate information..." value="" size="100" tabindex="17" ></textarea>
                    </span>
                    <span id="text-checkmark" ></span>    
                    </li>
                </p>
                <p>
                    <input id="addcomment" name="addcomment" class="button" type="submit" value="Add Comment" tabindex="1"/>
			    </p>
              </form>
           """)
          }
        div(class: 'span-4') {
           mkp.yieldUnescaped("""
		        <p> &nbsp; </p>
		        <p><a href="${url}serviceRequest/flag?as=spam&serviceRequestID=${serviceRequest.id()}">Flag this post as Spam</a></p>
              	<p><a href="${url}serviceRequest/flag?as=miscategorized&serviceRequestID=${serviceRequest.id()}">Flag this post as Miscategorized</a></p>
              	<p><a href="${url}serviceRequest/flag?as=prohibited&serviceRequestID=${serviceRequest.id()}">Flag this post as Should be Prohibited</a></p>
              	<p><a href="${url}serviceRequest/flag?as=great&serviceRequestID=${serviceRequest.id()}">Flag this post as Great</a></p>
	      """)
        }
        if (serviceRequest.getComments()) {
          div(class: 'span-16 prepend-2 append-6') {
            mkp.yieldUnescaped("""<p>Comments : </p>""")
            comments.each { comment ->
             mkp.yieldUnescaped("""
                    Posted By - ${comment.getAuthor()} <br>
                    At        - ${comments.getCreatedDate()} <br>
                    Content   - ${comments.getText()}<br>
             """)
            }
          }
        }
      }
    } else {
      final String nullServiceRequestError = messageSource.getMessage("serviceRequestViewPage.nullServiceRequest", null,  locale)
      div(class: 'container') {
      div(class: 'span-30') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
	  div(class: 'span-20 prepend-5') {
           mkp.yieldUnescaped("""
                   ${nullServiceRequestError}
            """)
          }
      }
    }
  }

}