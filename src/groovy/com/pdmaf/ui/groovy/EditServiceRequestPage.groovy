package com.pdmaf.ui.groovy

import org.svenson.JSONParser

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 29, 2009
 * Time: 8:39:00 AM
 *
 */

public class EditServiceRequestPage extends MainPage {
  def serviceRequest
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
       div(class: 'span-17 prepend-1') {
           p {
               img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
           }
       }

   }


   def protected pageBodyMainContent = {

       private String description = ""
       private String title = ""
       private String replyEmail = ""
       private String showEmailPreference = ""
       private String specificLocation = ""
       private String city = ""
       private String state = ""
       private String country = ""
       private String poster = ""
       private String category = ""
       private String otherCompensation = ""
       private String pointsOffered = ""

       if(serviceRequest) {
          Map map = JSONParser.defaultJSONParser().parse(Map.class, serviceRequest.toJSON());
          title = (String)map.get("title") ?: ""
          description = (String)map.get("description") ?: ""
          category = (String)map.get("category") ?: ""
          replyEmail = (String)map.get("replyEmail") ?: ""
          showEmailPreference = (String)map.get("showEmailPreference") ?: ""
          specificLocation = (String)map.get("specificLocation") ?: ""
          city = (String)map.get("city") ?: ""
          state = (String)map.get("state") ?: ""
          country = (String)map.get("country") ?: ""
          pointsOffered = (String)map.get("pointsOffered") ?: ""
          otherCompensation = (String)map.get("otherCompensation") ?: ""
       }
       div(class: 'span-30') { mkp.yieldUnescaped '&nbsp' }
       div(class: 'span-2') { mkp.yieldUnescaped '&nbsp' }
       div(class: 'span-26') {
           mkp.yieldUnescaped("""
								<form action="${url}serviceRequest/confirmEditServiceRequest" method="POST" class="normalform" id="service-request-form">
									<input  type="hidden" value="${serviceRequest.id()}" name="serviceRequestID">
                                    <p>Please complete the form below. Mandatory fields marked <em>*</em></p>
									<fieldset>
										<legend>Service Request</legend>
										<ol>
											<li><label for="name">Posting Title : <em>*</em></label> <input id="postingtitle" name="title" class="field text"  value="${title}" title="Short description of service needed..." tabindex="1"/><span id="postingtitle-checkmark"></span></li>
											<li><label for="address">Specific Location : <em>*</em></label> <input id="location" name="location" class="field text" value="${specificLocation}" title="Neighborhood, anywhere, online, etc..." tabindex="2"/><span id="location-checkmark" ></span></li>
											<li><label for="city">City : <em>*</em></label> <input id="city" name="city" class="field text" value="${city}" title="City where you are located..." tabindex="3"/><span id="city-checkmark" ></span></li>
											<li><label for="state">State : <em>*</em></label> <input id="state" name="state" class="field text" value="${state}" title="State where you are located..." tabindex="4"/><span id="state-checkmark" ></span></li>
											<li><label for="county">Country : <em>*</em></label> <select id="country" name="country" tabindex="5"><optgroup label="">
																											<option value="c" selected="selected">Select Country</option>
																										</optgroup>
																										<optgroup label="common choices">
																											<option value="UnitedStates">United States</option>
																											<option value="UnitedKingdom">United Kingdom</option>
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
																											<option value="CapeVerde">Cape Verde</option>
																											<option value="CaymanIslands">Cayman Islands</option>
																											<option value="CentralAfricanRepublic">Central African Republic</option>
																											<option value="Chad">Chad</option>
																											<option value="Chile">Chile</option>
																											<option value="China">China</option>
																											<option value="ChristmasIsland">Christmas Island</option>
																											<option value="CocosIslands">Cocos (Keeling) Islands</option>
																											<option value="Colombia">Colombia</option>
																											<option value="Comoros">Comoros</option>
																											<option value="Congo">Congo</option>
																											<option value="CongoTheDemocraticRepublicofThe">Congo, The Democratic Republic of The</option>
																											<option value="CookIslands">Cook Islands</option>
																											<option value="CostaRica">Costa Rica</option>
																											<option value="CoteD'ivoire">Cote D'ivoire</option>
																											<option value="Croatia">Croatia</option>
																											<option value="Cuba">Cuba</option>
																											<option value="Cyprus">Cyprus</option>
																											<option value="CzechRepublic">Czech Republic</option>
																											<option value="Denmark">Denmark</option>
																											<option value="Djibouti">Djibouti</option>
																											<option value="Dominica">Dominica</option>
																											<option value="Dominican Republic">Dominican Republic</option>
																											<option value="Ecuador">Ecuador</option>
																											<option value="Egypt">Egypt</option>
																											<option value="ElSalvador">El Salvador</option>
																											<option value="Equatorial Guinea">Equatorial Guinea</option>
																											<option value="Eritrea">Eritrea</option>
																											<option value="Estonia">Estonia</option>
																											<option value="Ethiopia">Ethiopia</option>
																											<option value="Falkland Islands (Malvinas)">Falkland Islands (Malvinas)</option>
																											<option value="Faroe Islands">Faroe Islands</option>
																											<option value="Fiji">Fiji</option>
																											<option value="Finland">Finland</option>
																											<option value="France">France</option>
																											<option value="FrenchGuiana">French Guiana</option>
																											<option value="FrenchPolynesia">French Polynesia</option>
																											<option value="FrenchSouthernTerritories">French Southern Territories</option>
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
																											<option value="HeardIslandandMcdonaldIslands">Heard Island and Mcdonald Islands</option>
																											<option value="HolySee">Holy See (Vatican City State)</option>
																											<option value="Honduras">Honduras</option>
																											<option value="HongKong">Hong Kong</option>
																											<option value="Hungary">Hungary</option>
																											<option value="Iceland">Iceland</option>
																											<option value="India">India</option>
																											<option value="Indonesia">Indonesia</option>
																											<option value="Iran">Iran, Islamic Republic of</option>
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
																											<option value="NorthKorea">Korea, Democratic People's Republic of</option>
																											<option value="SouthKorea">Korea, Republic of</option>
																											<option value="Kuwait">Kuwait</option>
																											<option value="Kyrgyzstan">Kyrgyzstan</option>
																											<option value="Lao">Lao People's Democratic Republic</option>
																											<option value="Latvia">Latvia</option>
																											<option value="Lebanon">Lebanon</option>
																											<option value="Lesotho">Lesotho</option>
																											<option value="Liberia">Liberia</option>
																											<option value="LibyanArabJamahiriya">Libyan Arab Jamahiriya</option>
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
																											<option value="Micronesia">Micronesia, Federated States of</option>
																											<option value="Moldova">Moldova, Republic of</option>
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
																											<option value="NewCaledonia">New Caledonia</option>
																											<option value="NewZealand">New Zealand</option>
																											<option value="Nicaragua">Nicaragua</option>
																											<option value="Niger">Niger</option>
																											<option value="Nigeria">Nigeria</option>
																											<option value="Niue">Niue</option>
																											<option value="NorfolkIsland">Norfolk Island</option>
																											<option value="NorthernMarianaIslands">Northern Mariana Islands</option>
																											<option value="Norway">Norway</option>
																											<option value="Oman">Oman</option>
																											<option value="Pakistan">Pakistan</option>
																											<option value="Palau">Palau</option>
																											<option value="Palestinian Territory, Occupied">Palestinian Territory, Occupied</option>
																											<option value="Panama">Panama</option>
																											<option value="PapuaNewGuinea">Papua New Guinea</option>
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
																											<option value="SaintHelena">Saint Helena</option>
																											<option value="SaintKitts and Nevis">Saint Kitts and Nevis</option>
																											<option value="SaintLucia">Saint Lucia</option>
																											<option value="SaintPierre and Miquelon">Saint Pierre and Miquelon</option>
																											<option value="SaintVincent and The Grenadines">Saint Vincent and The Grenadines</option>
																											<option value="Samoa">Samoa</option>
																											<option value="SanMarino">San Marino</option>
																											<option value="SaoTomeAndPrincipe">Sao Tome and Principe</option>
																											<option value="SaudiArabia">Saudi Arabia</option>
																											<option value="Senegal">Senegal</option>
																											<option value="SerbiaAndMontenegro">Serbia and Montenegro</option>
																											<option value="Seychelles">Seychelles</option>
																											<option value="SierraLeone">Sierra Leone</option>
																											<option value="Singapore">Singapore</option>
																											<option value="Slovakia">Slovakia</option>
																											<option value="Slovenia">Slovenia</option>
																											<option value="SolomonIslands">Solomon Islands</option>
																											<option value="Somalia">Somalia</option>
																											<option value="SouthAfrica">South Africa</option>
																											<option value="SouthGeorgia and The South Sandwich Islands">South Georgia and The South Sandwich Islands</option>
																											<option value="Spain">Spain</option>
																											<option value="SriLanka">Sri Lanka</option>
																											<option value="Sudan">Sudan</option>
																											<option value="Suriname">Suriname</option>
																											<option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
																											<option value="Swaziland">Swaziland</option>
																											<option value="Sweden">Sweden</option>
																											<option value="Switzerland">Switzerland</option>
																											<option value="Syrian Arab Republic">Syrian Arab Republic</option>
																											<option value="Taiwan">Taiwan, Province of China</option>
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
																											<option value="UnitedArab Emirates">United Arab Emirates</option>
																											<option value="UnitedKingdom">United Kingdom</option>
																											<option value="UnitedStates">United States</option>
																											<option value="UnitedStatesMinorOutlyingIslands">United States Minor Outlying Islands</option>
																											<option value="Uruguay">Uruguay</option>
																											<option value="Uzbekistan">Uzbekistan</option>
																											<option value="Vanuatu">Vanuatu</option>
																											<option value="Venezuela">Venezuela</option>
																											<option value="Viet Nam">Viet Nam</option>
																											<option value="VirginIslandsBritish">Virgin Islands, British</option>
																											<option value="VirginIslandsU.S.">Virgin Islands, U.S.</option>
																											<option value="WallisAndFutuna">Wallis and Futuna</option>
																											<option value="WesternSahara">Western Sahara</option>
																											<option value="Yemen">Yemen</option>
																											<option value="Zambia">Zambia</option>
																											<option value="Zimbabwe">Zimbabwe</option>
																										</optgroup>
																									</select><span id="country-checkmark" tabindex="6"></span></li>
												<li>
													  <label class="desc" id="reply-to-label" for="replyto">
														  Reply To : <em>*</em>
													  </label>
													  <span>
														  <input id="email1" name="email1" type="text" class="field text" value="${replyEmail}" title="Valid email address..." value="" size="25" tabindex="7" />
													 </span>
                                                </li>
                                                <li>
                                                     <label class="desc">
														  &nbsp;
													  </label>
													  <span>
														  <input id="email2" name="email2" type="text" class="field text" value="${replyEmail}" title="Please confirm your email..." value="" size="25" tabindex="8" />
													  </span>
													  <span id="email-checkmark" ></span>
												</li>
												<li>
                                                    <div id="email-preference">
													<label title="your actual email address will appear in the posting">
														Show : <input type="radio" name="showemail" value="showemail" id="showemail" tabindex="9" />
													</label>
													<label title="No email address will appear in your posting - be sure to include other contact info!">
														Hide : <input type="radio" name="showemail" value="shownoemail" id="shownoemail" tabindex="10" />
													</label>

													<label title="Email will be anonymized">
														Anonymize : <input type="radio" name="showemail" value="anonymizedemail" id="anonymizedemail" checked tabindex="11" />
														i.e.<a href="service-request-xxxx@pdmaf.com">service-request-xxx@pdmaf.com</a>
													</label>
                                                    </div>
												</li>
												<li><label for="category">Category : <em>*</em></label> <input id="category" name="category" class="field text"  value="${category}" title="Suggested category..." tabindex="12"/><span id="category-checkmark"></span></li>
												<li><label for="description">Full Description : <em>*</em></label>
												<textarea id="description" name="description" class="field text" title="Full service request description.  It has to be at least 20 characters or more..." tabindex="13" >${description}</textarea>
												<span id="description-checkmark" ></span></li>
												<li>
                                                    <div id="compensation-preference">
													<label title="no-compensation">
														No compensation : <input type="radio" name="compensation" value="nocompensation" id="nocompensation" tabindex="14" checked/>
													</label>
													<label title="point-compensation">
														Pay by PDMAF points : <input type="radio" name="compensation" value="pointcompensation" id="pointcompensation" tabindex="15" />
													</label>
													<label title="other-compensation">
														Other compensation : <input type="radio" name="compensation" value="othercompensation" id="othercompensation" tabindex="16" />
													</label>
                                                    </div>
                                                 </li>
                                                 <li>
                                                    <div id="pointsoffered" style="display: none;">
                                                        <label class="desc">
                                                              &nbsp;
                                                        </label>
                                                        <span>
                                                          <input name="pointsoffered" type="text" class="field text" title="Points you would like to offer..." value="${pointsOffered}" size="25" tabindex="17" />
                                                        </span>
                                                    </div>
                                                    <div id="compensationdescription" style="display: none;">
                                                        <label class="desc">
                                                              &nbsp;
                                                        </label>
                                                        <span>
                                                          <textarea cols="40" rows="5" name="othercompensation" type="text" class="field text" value="${otherCompensation}" title="Please be specific how you would like to compensate the service provider..." value="" size="100" tabindex="17" ></textarea>
                                                        </span>
                                                    </div>
                                                </li>
										</ol>
									</fieldset>
									<p><input class="button" type="submit" id="submitservicerequest" name="submitservicerequest" value="Submit Service Request" tabindex="18"/>
								       <input class="button" type="submit" id="backtoservicerequestconfirmation" name="backtoservicerequestconfirmation" value="Back to Service Request Confirmation" tabindex="19"/>
                                    </p>
                                </form>
                             """)
                        }

   }
 }


