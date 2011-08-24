package com.pdmaf.ui.groovy

import com.pdmaf.business.models.ServiceRequest
import org.svenson.JSONParser

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 5, 2009
 * Time: 8:57:54 AM
 *  
 */

public class PostServiceRequestActivationPage extends MainPage {

  ServiceRequest serviceRequest
  def protected secondaryHeaderInclude = {
           mkp.yieldUnescaped """
               <!--[if lte IE 7]>
			      <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen" />
               <![endif]-->
           """
           script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
           script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
           script(language: "javascript", src: url + "js/jquery.ezpz_hint.min.js") { mkp.yield ' '}
           script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
           script(language: "javascript", src: url + "js/postservicerequest.js") { mkp.yield ' '}
  }

  def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
            }
        }

  }

  def protected pageBodyMainContent = {
    Map result  = null
    if(serviceRequest) {
        result  = JSONParser.defaultJSONParser().parse(Map.class, serviceRequest.toJSON())
        div(class: 'container') {
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
		div(class: 'span-20 prepend-2') {
           mkp.yieldUnescaped("""
              <form id="publish-service-request" class="normalform" action="${url}serviceRequest/publishServiceRequest" method="POST">
                <input  type="hidden" value="${serviceRequest.id()}" name="serviceRequestID">
                <fieldset>
                    <legend>Your Service Request</legend>
                        <h6>Please verify the information you have provided and confirm to publish this
                            service request by clicking on the confirm button below.

                        </h6><br>
                        <ol>
                            <li><label>Posting Title : </label> ${result.get("title")} </li>
                            <li><label>Specific Location : </label> ${result.get("specificLocation")} </li>
                            <li><label>City : </label> ${result.get("city")} </li>
                            <li><label>State : </label> ${result.get("state")} </li>
                            <li><label>Country : </label> ${result.get("country")} </li>
                            <li><label>Reply To : </label> ${result.get("replyEmail")} </li>
                            <li><label>Email Preference : </label> ${result.get("showEmailPreference")} </li>
                            <li><label>Category :</label> ${result.get("category")}</li>
                            <li><label>Full Description :</label> ${result.get("description")}</li>
                            <li><label>Compensation Preference : </label> ${if(result.get("pointsOffered"))
                                                                                result.get("pointsOffered") + " points"
                                                                            else if (result.get("othercompensation"))
                                                                                result.get("othercompensation")
                                                                            else
                                                                                return "No Compensation"
                                                                           }
                            </li>
                        </ol>
                </fieldset>
                <p><input id="confirmservicerequest" name="confirmservicerequest" class="button" type="submit" value="Confirm and Publish" tabindex="1"/>
			       <input id="deleteservicerequest" name="deleteservicerequest" class="button" type="submit" value="Cancel and Delete" tabindex="2"/>
			       <input id="editservicerequest" name="editservicerequest" class="button" type="submit" value="Edit" tabindex="3"/>
			    </p>
              </form>
            """)
          }
        }
    } else {
      final String nullServiceRequestError = messageSource.getMessage("postServiceRequestActivationPage.nullServiceRequest", null,  locale)
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