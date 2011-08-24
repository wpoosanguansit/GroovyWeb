package com.pdmaf.ui.groovy

import groovy.xml.StreamingMarkupBuilder
import com.pdmaf.business.models.ServiceRequest
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 15, 2009
 * Time: 2:50:59 PM
 *
 */

public class ServiceRequestFragmentViewPage {

  final String url = ConfigurationHolder.config.grails.serverURL
  ServiceRequest serviceRequest

  def protected pageBody = {
    final String showEmailPreference = serviceRequest.getShowEmailPreference()
    String email = ""
    switch (showEmailPreference) {
      case "showemail"        : email = serviceRequest.getReplyEmail()
      case "anonymizedemail"  : email = serviceRequest.getEmailHashKey()
      default                 : email = "No Email Shown"
    }
    div(id: 'service-request-detail') {
        div(id: 'flag-message') { mkp.yieldUnescaped ' ' }
                mkp.yieldUnescaped ("""
                  <form class="normalform">
                    <fieldset>
                      <legend>Service Request</legend>
                        <ol>
                            <li><label>Posting Title : </label> ${serviceRequest.getTitle()} </li>
                            <li><label>Reply Email : </label> ${email} </li>
                            <li><label>Specific Location : </label> ${serviceRequest.getSpecificLocation()} </li>
                            <li><label>City : </label> ${serviceRequest.getCity()} </li>
                            <li><label>State : </label> ${serviceRequest.getState()} </li>
                            <li><label>Country : </label> ${serviceRequest.getCountry()} </li>
                            <li><label>Category :</label> ${serviceRequest.getCategory()}</li>
                            <li><label>Full Description :</label> ${serviceRequest.getDescription()}</li>
                            <li><label>Compensation Preference : </label> ${serviceRequest.getCompensation()}</li>
                        </ol>
                      </fieldset>
                   </form> 
                     """)
               mkp.yieldUnescaped("""
                        <span>
                        <a href="#" class="flag" id="spam" serviceRequestID=${serviceRequest.id()}>Flag this post as Spam</a>&nbsp;
                        <a href="#" class="flag" id="miscategorized" serviceRequestID=${serviceRequest.id()}>Flag this post as Miscategorized</a><br>
                        <a href="#" class="flag" id="prohibited" serviceRequestID=${serviceRequest.id()}>Flag this post as Should be Prohibited</a>&nbsp;
                        <a href="#" class="flag" id="great" serviceRequestID=${serviceRequest.id()}>Flag this post as Great</a>
                        </span>
                  """)
    }
  }

  def private result = new StreamingMarkupBuilder().bind {
      out << pageBody
  }

  def render() {
      final StringWriter htmlWriter = new StringWriter()
      htmlWriter << result
      result = htmlWriter.toString().replaceAll("\r","")
  }
}
