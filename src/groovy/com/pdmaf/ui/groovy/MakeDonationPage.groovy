package com.pdmaf.ui.groovy

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 2, 2009
 * Time: 10:52:47 AM
 * 
 */

public class MakeDonationPage extends MainPage {

    def secondaryHeaderInclude = {
            link(rel: "stylesheet", href: url + "css/smoothness/jquery-ui-1.7.1.custom.css", type: "text/css", media: "screen, projection")
            link(rel: "stylesheet", href: url + "css/jquery.autocomplete.css", type: "text/css", media: "screen, projection")
            mkp.yieldUnescaped """
            <!--[if lte IE 7]>
			  <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen" />
            <![endif]-->
            """
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-ui-1.7.1.custom.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.autocomplete.min.js") {mkp.yield ' '}
            script(language: "javascript", src: url + "js/suggestion-data.js") {mkp.yield ' '}
            script(language: "javascript", src: url + "js/makedonation.js") { mkp.yield ' '}
    }

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'space.')
            }
        }

    }

    def protected pageBodyMainContent = {

      final String donationmessage = params?.donationmessage ?: ""
      final String suggestionmessage = params?.donationmessage ?: ""
      final String donationerror = donationmessage ? "<div class='error'>${donationmessage}</div>" : ""
      final String suggestionerror = suggestionmessage ? "<div class='error'>${suggestionmessage}</div>" : ""
      final String cardtype = params?.cardtype ?: ""
      final String zipcode = params?.zipcode ?: ""
      final String state = params?.state ?: ""
      final String cardnumber = params?.cardnumber ?: ""
      final String expirationyear = params?.expirationyear ?: ""
      final String country = params?.country ?: ""
      final String city = params?.city ?: ""
      final String organization = params?.organization ?: ""
      final String donationamount = params?.donationamount ?: ""
      final String expirationmonth = params?.expirationmonth ?: ""
      final String address = params?.address ?: ""
      final Boolean anonymouslisting = params?.anonymouslisting ? true : false
      final String fullname = params?.fullname ?: ""

      div(class: 'container') {
        div(class: 'span-24') { mkp.yieldUnescaped '&nbsp;&nbsp;' }
		div(class: 'span-20 prepend-2') {
           mkp.yieldUnescaped("""
              <div id="makedonation-tabs">
				<ul>
					<li><a href="#donation-form"><span>Donation Form</span></a></li>
					<li><a href="#terms-and-condition"><span>Terms and Conditions</span></a></li>
					<li><a href="#join-our-network"><span>Join Our Charity Network</span></a></li>
				</ul>
				<div id="donation-form">
                                <br>
                                  ${donationerror}
                                <br>
								<form action="${url}makeDonation/makeDonation" class="normalform" id="donation-submission-form" autocomplete="off">
									<p>Please complete the form below. Mandatory fields marked <em>*</em></p>
									<fieldset>
										<legend>Personal Information</legend>
										<ol>
											<li><label for="fullname">Full Name <em>*</em> : </label> <input id="fullname" name="fullname" type="text" class="field text"  value="${fullname}" title="Full Name as appears on card..." tabindex="1"/><span id="fullname-checkmark"></span></li>
											<li><label for="address">Address <em>*</em> : </label> <input id="address" name="address" type="text" class="field text" value="${address}" title="Address..." tabindex="2"/><span id="address-checkmark" ></span></li>
											<li><label for="city">City <em>*</em> : </label> <input id="city" name="city" type="text" class="field text" value="${city}" title="City..." tabindex="3"/><span id="city-checkmark" ></span></li>
											<li><label for="state">State <em>*</em> : </label> <input id="state"  name="state" type="text" class="field text" value="${state}" title="State..." tabindex="4"/><span id="state-checkmark" ></span></li>
											<li><label for="county">Country <em>*</em> : </label> <select id="country" name="country" tabindex="5"><optgroup label="">
																											<option value="c" selected="selected">Select Country</option>
																										</optgroup>
																										<optgroup label="common choices">
																											<option value="United States">United States</option>
																											<option value="United Kingdom">United Kingdom</option>
																											<option value="France">France</option>
																											<option value="Germany">Germany</option>
																											<option value="Spain">Spain</option>
																											<option value="Italy">Italy</option>
																											<option value="Canada">Canada</option>
																										</optgroup>
																										<optgroup label="other countries">
																											<option value="Afghanistan">Afghanistan</option>
																											<option value="Albania">Albania</option>
																											<option value="Algeria">Algeria</option>
																											<option value="American Samoa">American Samoa</option>
																											<option value="Andorra">Andorra</option>
																											<option value="Angola">Angola</option>
																											<option value="Anguilla">Anguilla</option>
																											<option value="Antarctica">Antarctica</option>
																											<option value="Antigua and Barbuda">Antigua and Barbuda</option>
																											<option value="Argentina">Argentina</option>
																											<option value="Armenia">Armenia</option>
																											<option value="Aruba">Aruba</option>
																											<option value="Australia">Australia</option>
																											<option value="Austria">Austria</option>
																											<option value="Azerbaijan">Azerbaijan</option>
																											<option value="Bahamas">Bahamas</option>
																											<option value="Bahrain">Bahrain</option>
																											<option value="Bangladesh">Bangladesh</option>
																											<option value="Barbados">Barbados</option>
																											<option value="Belarus">Belarus</option>
																											<option value="Belgium">Belgium</option>
																											<option value="Belize">Belize</option>
																											<option value="Benin">Benin</option>
																											<option value="Bermuda">Bermuda</option>
																											<option value="Bhutan">Bhutan</option>
																											<option value="Bolivia">Bolivia</option>
																											<option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
																											<option value="Botswana">Botswana</option>
																											<option value="Bouvet Island">Bouvet Island</option>
																											<option value="Brazil">Brazil</option>
																											<option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
																											<option value="Brunei Darussalam">Brunei Darussalam</option>
																											<option value="Bulgaria">Bulgaria</option>
																											<option value="Burkina Faso">Burkina Faso</option>
																											<option value="Burundi">Burundi</option>
																											<option value="Cambodia">Cambodia</option>
																											<option value="Cameroon">Cameroon</option>
																											<option value="Canada">Canada</option>
																											<option value="Cape Verde">Cape Verde</option>
																											<option value="Cayman Islands">Cayman Islands</option>
																											<option value="Central African Republic">Central African Republic</option>
																											<option value="Chad">Chad</option>
																											<option value="Chile">Chile</option>
																											<option value="China">China</option>
																											<option value="Christmas Island">Christmas Island</option>
																											<option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
																											<option value="Colombia">Colombia</option>
																											<option value="Comoros">Comoros</option>
																											<option value="Congo">Congo</option>
																											<option value="Congo, The Democratic Republic of The">Congo, The Democratic Republic of The</option>
																											<option value="Cook Islands">Cook Islands</option>
																											<option value="Costa Rica">Costa Rica</option>
																											<option value="Cote D'ivoire">Cote D'ivoire</option>
																											<option value="Croatia">Croatia</option>
																											<option value="Cuba">Cuba</option>
																											<option value="Cyprus">Cyprus</option>
																											<option value="Czech Republic">Czech Republic</option>
																											<option value="Denmark">Denmark</option>
																											<option value="Djibouti">Djibouti</option>
																											<option value="Dominica">Dominica</option>
																											<option value="Dominican Republic">Dominican Republic</option>
																											<option value="Ecuador">Ecuador</option>
																											<option value="Egypt">Egypt</option>
																											<option value="El Salvador">El Salvador</option>
																											<option value="Equatorial Guinea">Equatorial Guinea</option>
																											<option value="Eritrea">Eritrea</option>
																											<option value="Estonia">Estonia</option>
																											<option value="Ethiopia">Ethiopia</option>
																											<option value="Falkland Islands (Malvinas)">Falkland Islands (Malvinas)</option>
																											<option value="Faroe Islands">Faroe Islands</option>
																											<option value="Fiji">Fiji</option>
																											<option value="Finland">Finland</option>
																											<option value="France">France</option>
																											<option value="French Guiana">French Guiana</option>
																											<option value="French Polynesia">French Polynesia</option>
																											<option value="French Southern Territories">French Southern Territories</option>
																											<option value="Gabon">Gabon</option>
																											<option value="Gambia">Gambia</option>
																											<option value="Georgia">Georgia</option>
																											<option value="Germany">Germany</option>
																											<option value="Ghana">Ghana</option>
																											<option value="Gibraltar">Gibraltar</option>
																											<option value="Greece">Greece</option>
																											<option value="Greenland">Greenland</option>
																											<option value="Grenada">Grenada</option>
																											<option value="Guadeloupe">Guadeloupe</option>
																											<option value="Guam">Guam</option>
																											<option value="Guatemala">Guatemala</option>
																											<option value="Guinea">Guinea</option>
																											<option value="Guinea-bissau">Guinea-bissau</option>
																											<option value="Guyana">Guyana</option>
																											<option value="Haiti">Haiti</option>
																											<option value="Heard Island and Mcdonald Islands">Heard Island and Mcdonald Islands</option>
																											<option value="Holy See (Vatican City State)">Holy See (Vatican City State)</option>
																											<option value="Honduras">Honduras</option>
																											<option value="Hong Kong">Hong Kong</option>
																											<option value="Hungary">Hungary</option>
																											<option value="Iceland">Iceland</option>
																											<option value="India">India</option>
																											<option value="Indonesia">Indonesia</option>
																											<option value="Iran, Islamic Republic of">Iran, Islamic Republic of</option>
																											<option value="Iraq">Iraq</option>
																											<option value="Ireland">Ireland</option>
																											<option value="Israel">Israel</option>
																											<option value="Italy">Italy</option>
																											<option value="Jamaica">Jamaica</option>
																											<option value="Japan">Japan</option>
																											<option value="Jordan">Jordan</option>
																											<option value="Kazakhstan">Kazakhstan</option>
																											<option value="Kenya">Kenya</option>
																											<option value="Kiribati">Kiribati</option>
																											<option value="Korea, Democratic People's Republic of">Korea, Democratic People's Republic of</option>
																											<option value="Korea, Republic of">Korea, Republic of</option>
																											<option value="Kuwait">Kuwait</option>
																											<option value="Kyrgyzstan">Kyrgyzstan</option>
																											<option value="Lao People's Democratic Republic">Lao People's Democratic Republic</option>
																											<option value="Latvia">Latvia</option>
																											<option value="Lebanon">Lebanon</option>
																											<option value="Lesotho">Lesotho</option>
																											<option value="Liberia">Liberia</option>
																											<option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
																											<option value="Liechtenstein">Liechtenstein</option>
																											<option value="Lithuania">Lithuania</option>
																											<option value="Luxembourg">Luxembourg</option>
																											<option value="Macao">Macao</option>
																											<option value="Macedonia, The Former Yugoslav Republic of">Macedonia, The Former Yugoslav Republic of</option>
																											<option value="Madagascar">Madagascar</option>
																											<option value="Malawi">Malawi</option>
																											<option value="Malaysia">Malaysia</option>
																											<option value="Maldives">Maldives</option>
																											<option value="Mali">Mali</option>
																											<option value="Malta">Malta</option>
																											<option value="Marshall Islands">Marshall Islands</option>
																											<option value="Martinique">Martinique</option>
																											<option value="Mauritania">Mauritania</option>
																											<option value="Mauritius">Mauritius</option>
																											<option value="Mayotte">Mayotte</option>
																											<option value="Mexico">Mexico</option>
																											<option value="Micronesia, Federated States of">Micronesia, Federated States of</option>
																											<option value="Moldova, Republic of">Moldova, Republic of</option>
																											<option value="Monaco">Monaco</option>
																											<option value="Mongolia">Mongolia</option>
																											<option value="Montserrat">Montserrat</option>
																											<option value="Morocco">Morocco</option>
																											<option value="Mozambique">Mozambique</option>
																											<option value="Myanmar">Myanmar</option>
																											<option value="Namibia">Namibia</option>
																											<option value="Nauru">Nauru</option>
																											<option value="Nepal">Nepal</option>
																											<option value="Netherlands">Netherlands</option>
																											<option value="Netherlands Antilles">Netherlands Antilles</option>
																											<option value="New Caledonia">New Caledonia</option>
																											<option value="New Zealand">New Zealand</option>
																											<option value="Nicaragua">Nicaragua</option>
																											<option value="Niger">Niger</option>
																											<option value="Nigeria">Nigeria</option>
																											<option value="Niue">Niue</option>
																											<option value="Norfolk Island">Norfolk Island</option>
																											<option value="Northern Mariana Islands">Northern Mariana Islands</option>
																											<option value="Norway">Norway</option>
																											<option value="Oman">Oman</option>
																											<option value="Pakistan">Pakistan</option>
																											<option value="Palau">Palau</option>
																											<option value="Palestinian Territory, Occupied">Palestinian Territory, Occupied</option>
																											<option value="Panama">Panama</option>
																											<option value="Papua New Guinea">Papua New Guinea</option>
																											<option value="Paraguay">Paraguay</option>
																											<option value="Peru">Peru</option>
																											<option value="Philippines">Philippines</option>
																											<option value="Pitcairn">Pitcairn</option>
																											<option value="Poland">Poland</option>
																											<option value="Portugal">Portugal</option>
																											<option value="Puerto Rico">Puerto Rico</option>
																											<option value="Qatar">Qatar</option>
																											<option value="Reunion">Reunion</option>
																											<option value="Romania">Romania</option>
																											<option value="Russian Federation">Russian Federation</option>
																											<option value="Rwanda">Rwanda</option>
																											<option value="Saint Helena">Saint Helena</option>
																											<option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
																											<option value="Saint Lucia">Saint Lucia</option>
																											<option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
																											<option value="Saint Vincent and The Grenadines">Saint Vincent and The Grenadines</option>
																											<option value="Samoa">Samoa</option>
																											<option value="San Marino">San Marino</option>
																											<option value="Sao Tome and Principe">Sao Tome and Principe</option>
																											<option value="Saudi Arabia">Saudi Arabia</option>
																											<option value="Senegal">Senegal</option>
																											<option value="Serbia and Montenegro">Serbia and Montenegro</option>
																											<option value="Seychelles">Seychelles</option>
																											<option value="Sierra Leone">Sierra Leone</option>
																											<option value="Singapore">Singapore</option>
																											<option value="Slovakia">Slovakia</option>
																											<option value="Slovenia">Slovenia</option>
																											<option value="Solomon Islands">Solomon Islands</option>
																											<option value="Somalia">Somalia</option>
																											<option value="South Africa">South Africa</option>
																											<option value="South Georgia and The South Sandwich Islands">South Georgia and The South Sandwich Islands</option>
																											<option value="Spain">Spain</option>
																											<option value="Sri Lanka">Sri Lanka</option>
																											<option value="Sudan">Sudan</option>
																											<option value="Suriname">Suriname</option>
																											<option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
																											<option value="Swaziland">Swaziland</option>
																											<option value="Sweden">Sweden</option>
																											<option value="Switzerland">Switzerland</option>
																											<option value="Syrian Arab Republic">Syrian Arab Republic</option>
																											<option value="Taiwan, Province of China">Taiwan, Province of China</option>
																											<option value="Tajikistan">Tajikistan</option>
																											<option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
																											<option value="Thailand">Thailand</option>
																											<option value="Timor-leste">Timor-leste</option>
																											<option value="Togo">Togo</option>
																											<option value="Tokelau">Tokelau</option>
																											<option value="Tonga">Tonga</option>
																											<option value="Trinidad and Tobago">Trinidad and Tobago</option>
																											<option value="Tunisia">Tunisia</option>
																											<option value="Turkey">Turkey</option>
																											<option value="Turkmenistan">Turkmenistan</option>
																											<option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
																											<option value="Tuvalu">Tuvalu</option>
																											<option value="Uganda">Uganda</option>
																											<option value="Ukraine">Ukraine</option>
																											<option value="United Arab Emirates">United Arab Emirates</option>
																											<option value="United Kingdom">United Kingdom</option>
																											<option value="United States">United States</option>
																											<option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
																											<option value="Uruguay">Uruguay</option>
																											<option value="Uzbekistan">Uzbekistan</option>
																											<option value="Vanuatu">Vanuatu</option>
																											<option value="Venezuela">Venezuela</option>
																											<option value="Viet Nam">Viet Nam</option>
																											<option value="Virgin Islands, British">Virgin Islands, British</option>
																											<option value="Virgin Islands, U.S.">Virgin Islands, U.S.</option>
																											<option value="Wallis and Futuna">Wallis and Futuna</option>
																											<option value="Western Sahara">Western Sahara</option>
																											<option value="Yemen">Yemen</option>
																											<option value="Zambia">Zambia</option>
																											<option value="Zimbabwe">Zimbabwe</option>
																										</optgroup>
																									</select><span id="country-checkmark" tabindex="6"></span></li>
											<li><label for="zipcode">Zipcode <em>*</em> : </label> <input id="zipcode" name="zipcode" type="text" value="${zipcode}" class="field text" title="Zipcode..." tabindex="7"/><span id="zipcode-checkmark" ></span></li>

										</ol>
									</fieldset>
									<fieldset>
										<legend>Creditcard Information</legend>
										<ol>
											<li><label for="cardnumber">Card Number<em>*</em> : </label> <input id="cardnumber" name="cardnumber" type="text" value="${cardnumber}" class="field text" title="Creditcard Number..." size="16" maxlength="19" tabindex="8"><span id="cardnumber-checkmark" ></span></li>
											<li><label for="cardtype">Card Type<em>*</em> : </label> 		<select id="cardtype" name="cardtype" tabindex="9">
																												<optgroup label="">
																													<option value="c" selected="selected" >Select Creditcard</option>
																												</optgroup>
																												<option value="visacard">Visa</option>
																												<option value="mastercard">MasterCard</option>
																												<option value="americanexpress">American Express</option>
																												<option value="dinersclub">Diners Club</option>
																												<option value="discovercard">Discover</option>
																												<option value="enroute">enRoute</option>
																												<option value="jcb">JCB</option>
																											</select><span id="cardtype-checkmark"></span>
											<li>
											<li>
												<span>
													<label for="date" class="sr">Expiration Date<em>*</em> : </label>
																												<select id="expirationmonth" name="expirationmonth" tabindex="10">
																													<optgroup label="">
																														<option value="m" selected="selected">Select Month</option>
																													</optgroup>
																													<option value="01">Jan</option>
																													<option value="02">Feb</option>
																													<option value="03">Mar</option>
																													<option value="04">Apl</option>
																													<option value="05">May</option>
																													<option value="06">Jun</option>
																													<option value="07">Jul</option>
																													<option value="08">Aug</option>
																													<option value="09">Sep</option>
																													<option value="10">Oct</option>
																													<option value="11">Nov</option>
																													<option value="12">Dec</option>
																												</select>
																												<select id="expirationyear" name="expirationyear" tabindex="11">
																													<optgroup label="">
																														<option value="y" selected="selected">Select Year</option>
																													</optgroup>
																													<option value="2009">2009</option>
																													<option value="2010">2010</option>
																													<option value="2011">2011</option>
																													<option value="2012">2012</option>
																													<option value="2013">2013</option>
																													<option value="2014">2014</option>
																													<option value="2015">2015</option>
																													<option value="2016">2016</option>
																													<option value="2017">2017</option>
																													<option value="2018">2018</option>
																													<option value="2019">2019</option>
																													<option value="2020">2020</option>
																													<option value="2021">2021</option>
																													<option value="2022">2022</option>
																													<option value="2023">2023</option>
																													<option value="2024">2024</option>
																													<option value="2025">2025</option>
																													<option value="2026">2026</option>
																													<option value="2027">2027</option>
																													<option value="2028">2028</option>
																													<option value="2029">2029</option>
																													<option value="2030">2030</option>
																													<option value="2031">2031</option>
																													<option value="2031">2032</option>
																													<option value="2033">2033</option>
																												</select><span id="expirationdate-checkmark"></span>
												</span>
											</li>
										</ol>
									</fieldset>
									<fieldset>
										<legend>Donation Information</legend>
										<ol>
											<li><label for="donationamount">Donation amount \$<em>*</em> : </label> <input id="donationamount" name="donationamount" type="text" value="${donationamount}" class="field text" title="Donation Amount..." tabindex="12" /><span id="donationamount-checkmark" ></span></li>
											<li>
												<label for="organization">Organization<em>*</em> : </label>  <input id="organization" name="organization" type="text" value="${organization}" class="field text" title="Charity Organization Name..." tabindex="13" />	
												</a><span id="organization-checkmark"></span>
											</li>
                                            <li><label for="anonymouslisting">Be listed as anonymous donor : </label> <input id="anonymouslisting" name="anonymouslisting" type="checkbox" ${if (anonymouslisting) "checked"} tabindex="15" ></li>
										</ol>
									</fieldset>
									<p><input class="button" type="submit" id="makedonation" value="Make Donation" tabindex="16" /></p>
								</form>

				</div>
				<div id="terms-and-condition">

				</div>
				<div id="join-our-network">
					If you are with a charity organization or you know of a charity organization that would like to join our network of
					receiving organizations, please fill in the form below.  Our staff will contact the organization and update our database
					shortly.
                    <br>
                    ${suggestionerror}
                    <br>
					<form id="login" class="normalform" action="${url}makeDonation/suggestOrganization">
                      <fieldset>
					    <legend>Please fill in the form below :</legend>
                        <ol>
                           <li>
                            <label class="desc" id="organizationname-label" for="organizationname">
                               Organization Name<em>*</em> :
                            </label>
                            <input id="organizationname" name="organizationname" type="text" class="field text" title="Organization Name..." value="" size="25" tabindex="1"></input>
                            <span id="organizationname-checkmark" ></span>
                           </li>
                           <li>
                            <label class="desc" id="organizationemail-label" for="organizationemail">
                               Email<em>*</em> :
                             </label>
                             <input id="organizationemail" name="organizationemail" type="text" class="field text" title="Organization's email contact..." value="" size="25" tabindex="2"></input>
                             <span id="organizationemail-checkmark" ></span>
                           </li>
                           <li>
                            <label class="desc" id="contactperson-label" for="contactperson">
                               Contact Person :
                             </label>
                             <input id="contactperson" name="contactperson" type="text" class="field text" title="Person to contact..." value="" size="25" tabindex="3"></input>
                           </li>
                           <li>
                            <label class="desc" id="oganizationstate-label" for="oganizationstate">
                               State :
                             </label>
                             <input id="oganizationstate" name="oganizationstate" type="text" class="field text" title="State in which the organization is in..." value="" size="25" tabindex="4"></input>
                           </li>
                           <li>
                            <label class="desc" id="addtionalinfomation-label" for="addtionalinfomation">
                               Additonal Information :
                             </label>
                             <textarea cols="40" rows="5" id="addtionalinfomation" name="addtionalinfomation" type="textarea" class="field text" title="Please provide any additional information..." value="" size="25" tabindex="5"></textarea>
                           </li>
                         <p>
                           <input id="suggestorganization" class="button" type="submit" value="Submit your suggestion" tabindex="4"/>
                         </p>
                      </ol>
                      </fieldset>
                    </form>
			  </div>
            """)
        }
      }
    }
}