package com.pdmaf.ui.groovy

import com.pdmaf.business.models.ServiceRequest

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 11, 2009
 * Time: 9:19:02 AM
 *  
 */

public class ServiceRequestViewPage extends MainPage {
  ServiceRequest serviceRequest
  String message
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
              <form id="publish-service-request" class="normalform" action="${url}serviceRequest/showAddComment" method="POST">
                <input  type="hidden" value="${serviceRequest.id()}" name="serviceRequestID">
                    <fieldset>
                    <legend>Service Request</legend>
                        <h6>
                            ${flashMessage}
                        </h6><br>
                        <ol>
                            <li><label>Posting Title : </label> ${serviceRequest.getTitle()} </li>
       
                            </li>
                        </ol>
                      </fieldset>
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
        if (serviceRequest.getComments() != null && serviceRequest.getComments().size() > 0) {
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