        /**
         * This is for the post service request page.
         * */

		jQuery(document).ready(function(){
				loader();
				jQuery('input:text').hint();
				jQuery('textarea').hint();
                //for autocomplete
                jQuery('#city').autocomplete(cities, {
					multiple: false,
					mustMatch: true,
					autoFill: true
				});
                jQuery('#state').autocomplete(states,{
					multiple: false,
					mustMatch: true,
					autoFill: true
				});
				//show and hide compensation box
				jQuery("input[name='compensation']").change(function(){
					if (jQuery("input[name='compensation']:checked").val() == 'pointcompensation') {
						jQuery('div#compensationdescription').hide();
                        if (jQuery("textarea[name='othercompensation']").val().length > 0) {
                            jQuery("textarea[name='othercompensation']").val("");
                        }
						jQuery('div#pointsoffered').show();
					} else if (jQuery("input[name='compensation']:checked").val() == 'othercompensation') {
                        jQuery('div#pointsoffered').hide();
                        if (jQuery("input[name='pointsoffered']").val().length > 0) {
                            jQuery("input[name='pointsoffered']").val("");
                        }
						jQuery('div#compensationdescription').show();
					} else {
						jQuery('div#pointsoffered').hide();
						jQuery('div#compensationdescription').hide();
                        if (jQuery("textarea[name='othercompensation']").val().length > 0) {
                            jQuery("textarea[name='othercompensation']").val("");
                        }
                        if (jQuery("input[name='pointsoffered']").val().length > 0) {
                            jQuery("input[name='pointsoffered']").val("");
                        }
					}
				});


				//fixing the form
				if( document.addEventListener )
					document.addEventListener( 'DOMContentLoaded', normalform, false);

				function normalform(){
					  // Hide forms
					jQuery( 'form.normalform' ).hide().end();

					  // Processing
					jQuery( 'form.normalform' ).find( 'li/label' ).not( '.normalform' )
					.each( function( i ){
						var labelContent = this.innerHTML;
						var labelWidth = document.defaultView.
					getComputedStyle( this, '' ).getPropertyValue( 'width' );
						var labelSpan = document.createElement( 'span' );
							labelSpan.style.display = 'block';
							labelSpan.style.width = labelWidth;
							labelSpan.innerHTML = labelContent;
						this.style.display = '-moz-inline-box';
						this.innerHTML = null;
						this.appendChild( labelSpan );
					  } ).end();

					  // Show forms
					  jQuery( 'form.normalform' ).show().end();
				}


		});

		function loader() {
			  var postingtitleB = extractValueB('postingtitle');
			  var locationB = extractValueB('location');
			  var cityB = extractValueB('city');
			  var stateB = extractValueB('state');
			  var countryB = extractValueB('country', 'value');
			  var email1B = extractValueB('email1');
			  var email2B = extractValueB('email2');
			  var showemailB = extractValueB('showemail');
			  var categoryB = extractValueB('category');
			  var descriptionB = extractValueB('description');
			  var compensationB = extractValueB('compensation');

			  var validLenP = function (str, len) { return ( (str != undefined && str.length >= len) );};
			  var validSameValueP = function (val1, val2) { return ( (val1 !== undefined && val2 != undefined && val1 == val2) );};
			  var postingtitleValidB = liftB(validLenP, postingtitleB, 10);
			  var locationValidB = liftB(validLenP, locationB, 3);
			  var cityValidB = liftB(validLenP, cityB, 3);
			  var stateValidB = liftB(validLenP, stateB, 3);
			  var countryValidB = liftB(validLenP, countryB, 3);
			  var email1ValidB = liftB(validateEmailFormat, email1B);
			  var email2ValidB = liftB(validateEmailFormat, email2B);
			  var sameEmailValidB = liftB(validSameValueP, email1B, email2B)
			  var bothEmailValidB = andB(email1ValidB, email2ValidB, sameEmailValidB);
			  var categoryValidB = liftB(validLenP, categoryB, 3);
			  var descriptionValidB = liftB(validLenP, descriptionB, 20);

			  if (document.getElementById('postingtitle-checkmark') != null)
				insertDomB(ifB(postingtitleValidB, IMG({src: '../images/checkmark.gif'}), ''), 'postingtitle-checkmark');
			  if (document.getElementById('location-checkmark') != null)
				insertDomB(ifB(locationValidB, IMG({src: '../images/checkmark.gif'}), ''), 'location-checkmark');
			  if (document.getElementById('city-checkmark') != null)
                insertDomB(ifB(cityValidB, IMG({src: '../images/checkmark.gif'}), ''), 'city-checkmark');
			  if (document.getElementById('state-checkmark') != null)
                insertDomB(ifB(stateValidB, IMG({src: '../images/checkmark.gif'}), ''), 'state-checkmark');
			  if (document.getElementById('country-checkmark') != null)
                insertDomB(ifB(countryValidB, IMG({src: '../images/checkmark.gif'}), ''), 'country-checkmark');
			  if (document.getElementById('email-checkmark') != null)
                insertDomB(ifB(bothEmailValidB, IMG({src: '../images/checkmark.gif'}), ''), 'email-checkmark');
			  if (document.getElementById('category-checkmark') != null)
                insertDomB(ifB(categoryValidB, IMG({src: '../images/checkmark.gif'}), ''), 'category-checkmark');
			  if (document.getElementById('description-checkmark') != null)
                insertDomB(ifB(descriptionValidB, IMG({src: '../images/checkmark.gif'}), ''), 'description-checkmark');

			  var submitserviceRequestReadyForSubmissionB = andB(postingtitleValidB, locationValidB, cityValidB, stateValidB,
																	countryValidB, bothEmailValidB, categoryValidB, descriptionValidB);
			  insertValueB(notB(submitserviceRequestReadyForSubmissionB), 'submitservicerequest', 'disabled');
              insertValueB(ifB(submitserviceRequestReadyForSubmissionB, 'button', 'buttonDisabled'), 'submitservicerequest', 'className');
		}

		function validateEmailFormat (emailStr) {
            if (emailStr == undefined) {
                return false;
            }
                        /* The following variable tells the rest of the function whether or not
                        to verify that the address ends in a two-letter country or well-known
                        TLD.  1 means check it, 0 means don't. */
            var checkTLD=1;

                        /* The following is the list of known TLDs that an e-mail address must end with. */
            var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;

                        /* The following pattern is used to check if the entered e-mail address
                        fits the user@domain format.  It also is used to separate the username
                        from the domain. */
            var emailPat=/^(.+)@(.+)$/;

                        /* The following string represents the pattern for matching all special
                        characters.  We don't want to allow special characters in the address.
                        These characters include ( ) < > @ , ; : \ " . [ ] */
            var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";

                        /* The following string represents the range of characters allowed in a
                        username or domainname.  It really states which chars aren't allowed.*/
            var validChars="\[^\\s" + specialChars + "\]";

                        /* The following pattern applies if the "user" is a quoted string (in
                        which case, there are no rules about which characters are allowed
                        and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
                        is a legal e-mail address. */
            var quotedUser="(\"[^\"]*\")";

                        /* The following pattern applies for domains that are IP addresses,
                        rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
                        e-mail address. NOTE: The square brackets are required. */
            var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;

                        /* The following string represents an atom (basically a series of non-special characters.) */
            var atom=validChars + '+';

                        /* The following string represents one word in the typical username.
                        For example, in john.doe@somewhere.com, john and doe are words.
                        Basically, a word is either an atom or quoted string. */
            var word="(" + atom + "|" + quotedUser + ")";

                        // The following pattern describes the structure of the user
            var userPat=new RegExp("^" + word + "(\\." + word + ")*$");

                        /* The following pattern describes the structure of a normal symbolic
                        domain, as opposed to ipDomainPat, shown above. */
            var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");

                        /* Finally, let's start trying to figure out if the supplied address is valid. */
                        /* Begin with the coarse pattern to simply break up user@domain into
                        different pieces that are easy to analyze. */
            var matchArray=emailStr.match(emailPat);

            if (matchArray==null) {

                        /* Too many/few @'s or something; basically, this address doesn't
                        even fit the general mould of a valid e-mail address. */
                return false;
            }

            var user=matchArray[1];
            var domain=matchArray[2];

                        // Start by checking that only basic ASCII characters are in the strings (0-127).


            for (i=0; i<user.length; i++) {
                if (user.charCodeAt(i)>127) {
                    return false;
                }
            }

            for (i=0; i<domain.length; i++) {
                if (domain.charCodeAt(i)>127) {
                    return false;
                }
            }

                            // See if "user" is valid


            if (user.match(userPat)==null) {
                return false;
            }

                        /* if the e-mail address is at an IP address (as opposed to a symbolic
                        host name) make sure the IP address is valid. */
            var IPArray=domain.match(ipDomainPat);
            if (IPArray!=null) {

                for (var i=1;i<=4;i++) {

                    if (IPArray[i]>255) {
                        return false;
                    }
                }
                return true;
            }

                        // Domain is symbolic name.  Check if it's valid.


            var atomPat=new RegExp("^" + atom + "$");


            var domArr=domain.split(".");

            var len=domArr.length;

            for (i=0;i<len;i++) {

                if (domArr[i].search(atomPat)==-1) {
                    return false;
                }

            }

                        /* domain name seems valid, but now make sure that it ends in a
                        known top-level domain (like com, edu, gov) or a two-letter word,
                        representing country (uk, nl), and that there's a hostname preceding
                        the domain or country. */


            if (checkTLD && domArr[domArr.length-1].length!=2 &&
                domArr[domArr.length-1].search(knownDomsPat)==-1) {

                return false;
                        // Make sure there's a host name preceding the domain.
            }


            if (len<2) {
                return false;
            }
                        // If we've gotten this far, everything's valid!
            return true;
        }