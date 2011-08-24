package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 23, 2009
 * Time: 9:58:39 PM
 * 
 */

public class MakeDonationConfirmationPage extends MakeDonationPage {


      def protected pageBodyMainContent = {

      div(class: 'container') {
        div(class: 'span-30') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
		div(class: 'span-20 prepend-5', id: 'tab-set') {
           mkp.yieldUnescaped("""
              <div id="tabs">
				<ul>
					<li><a href="#donation-form"><span>Donation Form</span></a></li>
					<li><a href="#terms-and-condition"><span>Terms and Conditions</span></a></li>
					<li><a href="#join-our-network"><span>Join Our Charity Network</span></a></li>
				</ul>
				<div id="donation-form">
                    <fieldset>
								<legend>Thank you!</legend>
								<ol><br><br>
					                <h5>
                                      Thank you for your donation.  Your donation information should appear shortly in the donor list.
                                      Please provide your feedback if you see any discrepancies.  Else, thank you again and please help
                                      spread the karma!
                                    </h5>
                                    <br><br>
                                    <form method=post action="${url}application/makeDonation">
                                      <input type="submit" class="button" value="Make another donation">
								    </form>
                                </ol>
					</fieldset>
				</div>
				<div id="terms-and-condition">

				</div>
				<div id="join-our-network">
					If you are with a charity organization or you know of a charity organization that would like to join our network of
					receiving organizations, please fill in the form below.  Our staff will contact the organization and update our database
					shortly.

                    ${suggestionerror}

					<form action="${url}makeDonation/suggestOrganization" class="normalform" id="join-our-network-submission-form" >
									<p>Please complete the form below. Mandatory fields marked <em>*</em></p>
									<fieldset>
										<legend>Charity Organization Information</legend>
										<ol>
											<li><label for="organizationname">Organization Name <em>*</em></label> <input id="organizationname" class="field text" title="Charity Organization Name..." tabindex="1" /><span id="organizationname-checkmark"></span></li>
											<li><label for="organizationemail">Email <em>*</em></label> <input id="organizationemail" class="field text" title="Organization Email Address..." tabindex="2" /><span id="organizationemail-checkmark"></span></li>
											<li><label for="contactperson">Contact Person</label> <input id="contactperson" class="field text" title="Person to contact..." tabindex="3" /></li>
											<li><label for="oganizationstate">State which Organization is in</label> <input id="oganizationstate" class="field text" title="State which the Organization is in..." tabindex="4" /></li>

										</ol>
									</fieldset>
									<p><input class="button" type="submit" id="suggestorganization" value="Send Suggestion" tabindex="5" /></p>
					</form>
			  </div>
            """)
          }
        }
    }
}