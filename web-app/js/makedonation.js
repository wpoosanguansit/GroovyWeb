/**
 * for tab with blueprintCSS and jquery
 */
		jQuery(document).ready(function(){
            
				loader();
            
				jQuery('input:text').hint();
                jQuery('textarea').hint();
				jQuery('#makedonation-tabs').tabs();
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
                jQuery('#oganizationstate').autocomplete(states,{
					multiple: false,
					mustMatch: true,
					autoFill: true
				});
                jQuery('#organization').autocomplete(charityOrganizations,{
					multiple: false,
					mustMatch: false,
					autoFill: false
				});
				if( document.addEventListener )
					document.addEventListener( 'DOMContentLoaded', normalform, false);

				function normalform(){
					  // Hide forms
					  jQuery( 'form.normalform' ).hide().end();

					  // Processing
					  jQuery( 'form.normalform' ).find( 'li/label' ).not( '.nocmx' )
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
			  var fullnameB = extractValueB('fullname');
			  var addressB = extractValueB('address');
			  var cityB = extractValueB('city');
			  var stateB = extractValueB('state');
			  var countryB = extractValueB('country', 'value');
			  var zipcodeB = extractValueB('zipcode');
			  var cardnumberB = extractValueB('cardnumber');
			  var cardtypeB = extractValueB('cardtype');
			  var expirationmonthB = extractValueB('expirationmonth');
			  var expirationyearB = extractValueB('expirationyear');
			  var donationamountB = extractValueB('donationamount');
			  var organizationB = extractValueB('organization');

			  var validLenP = function (str, len) { return ( (str != undefined && str.length >= len) );};
			  var fullnameValidB = liftB(validLenP, fullnameB, 6);
			  var addressValidB = liftB(validLenP, addressB, 5);
			  var cityValidB = liftB(validLenP, cityB, 3);
			  var stateValidB = liftB(validLenP, stateB, 3);
			  var countryValidB = liftB(validLenP, countryB, 3);
			  var zipcodeValidLengthB = liftB(validLenP, zipcodeB, 5);
              var zipcodeValidNumberB = liftB(isNumber, zipcodeB);
              var zipcodeValidB = andB(zipcodeValidLengthB, zipcodeValidNumberB);
			  var cardtypeValidB = liftB(validLenP, cardtypeB, 2);
			  var expirationyearValidB = liftB(validLenP, expirationyearB, 2);
			  var expirationmonthValidB = liftB(validLenP, expirationmonthB, 2);
			  var donationamountValidB = liftB(isDouble, donationamountB);
			  var cardnumberBValidB = liftB(validateCreditcard, cardnumberB, expirationyearB, expirationmonthB);
			  var organizationValidB = liftB(validLenP, organizationB, 5);

              if (document.getElementById('fullname-checkmark') != null)
			    insertDomB(ifB(fullnameValidB, IMG({src: '../images/checkmark.gif'}), ''), 'fullname-checkmark');
			  if (document.getElementById('address-checkmark') != null)
                insertDomB(ifB(addressValidB, IMG({src: '../images/checkmark.gif'}), ''), 'address-checkmark');
			  if (document.getElementById('country-checkmark') != null)
                insertDomB(ifB(countryValidB, IMG({src: '../images/checkmark.gif'}), ''), 'country-checkmark');
			  if (document.getElementById('zipcode-checkmark') != null)
                insertDomB(ifB(zipcodeValidB, IMG({src: '../images/checkmark.gif'}), ''), 'zipcode-checkmark');
			  if (document.getElementById('city-checkmark') != null)
                insertDomB(ifB(cityValidB, IMG({src: '../images/checkmark.gif'}), ''), 'city-checkmark');
			  if (document.getElementById('cardtype-checkmark') != null)
                insertDomB(ifB(cardtypeValidB, IMG({src: '../images/checkmark.gif'}), ''), 'cardtype-checkmark');
			  var validexpirationdateB = andB(expirationyearValidB, expirationmonthValidB);
			  if (document.getElementById('expirationdate-checkmark') != null)
                insertDomB(ifB(validexpirationdateB, IMG({src: '../images/checkmark.gif'}), ''), 'expirationdate-checkmark');
			  if (document.getElementById('cardnumber-checkmark') != null)
                insertDomB(ifB(cardnumberBValidB, IMG({src: '../images/checkmark.gif'}), ''), 'cardnumber-checkmark');
			  if (document.getElementById('state-checkmark') != null)
                insertDomB(ifB(stateValidB, IMG({src: '../images/checkmark.gif'}), ''), 'state-checkmark');
			  if (document.getElementById('donationamount-checkmark') != null)
                insertDomB(ifB(donationamountValidB, IMG({src: '../images/checkmark.gif'}), ''), 'donationamount-checkmark');
			  if (document.getElementById('organization-checkmark') != null)
                insertDomB(ifB(organizationValidB, IMG({src: '../images/checkmark.gif'}), ''), 'organization-checkmark');

			  var makedonationReadyForSubmissionB = andB(fullnameValidB, addressValidB, cityValidB, stateValidB,
														 countryValidB, zipcodeValidB, cardtypeValidB,expirationyearValidB,
														 expirationmonthValidB, donationamountValidB, organizationValidB);
              if ((document.getElementById('makedonation') != undefined)) {
			    insertValueB(notB(makedonationReadyForSubmissionB), 'makedonation', 'disabled');
                insertValueB(ifB(makedonationReadyForSubmissionB, 'button', 'buttonDisabled'), 'makedonation', 'className');
              }
			  var organizationnameB = extractValueB('organizationname');
			  var organizationemailB = extractValueB('organizationemail');
			  var organizationnameValidB = liftB(validLenP, organizationnameB, 3);
			  var organizationemailValidB = liftB(validateEmailFormat, organizationemailB);
              if (document.getElementById('organizationname-checkmark') != undefined)
			    insertDomB(ifB(organizationnameValidB, IMG({src: '../images/checkmark.gif'}), ''), 'organizationname-checkmark');
			  if (document.getElementById('organizationemail-checkmark') != undefined)
                insertDomB(ifB(organizationemailValidB, IMG({src: '../images/checkmark.gif'}), ''), 'organizationemail-checkmark');

			  var suggestorganizationReadyForSubmissionB = andB(organizationnameValidB, organizationemailValidB);
              if (document.getElementById('suggestorganization') != undefined) {
			    insertValueB(notB(suggestorganizationReadyForSubmissionB), 'suggestorganization', 'disabled');
                insertValueB(ifB(suggestorganizationReadyForSubmissionB, 'button', 'buttonDisabled'), 'suggestorganization', 'className');
              }
		}

		function validateCreditcard(cardnumber, expirationyear, expirationmonth) {
			var Cards = new Array(8);
			Cards[0] = new CardType("mastercard", "51,52,53,54,55", "16");
			var mastercard = Cards[0];
			Cards[1] = new CardType("visacard", "4", "13,16");
			var visacard = Cards[1];
			Cards[2] = new CardType("americanexpress", "34,37", "15");
			var americanexpress = Cards[2];
			Cards[3] = new CardType("dinersclub", "30,36,38", "14");
			var dinersclub = Cards[3];
			Cards[4] = new CardType("discovercard", "6011", "16");
			var discovercard = Cards[4];
			Cards[5] = new CardType("enroute", "2014,2149", "15");
			var enroute = Cards[5];
			Cards[6] = new CardType("jcb", "3088,3096,3112,3158,3337,3528", "16");
			var jcb = Cards[6];
			var LuhnCheckSum = Cards[7] = new CardType();

			for (var n = 0; n < Cards.length; n++) {
				if (Cards[n].checkCardNumber(cardnumber, expirationyear, expirationmonth)) {
					return true;
					break;
				}
			}

			return false;

		}

		/*************************************************************************\
		Object CardType([String cardtype, String rules, String len, int year,
												int month])
		cardtype    : type of card, eg: MasterCard, Visa, etc.
		rules       : rules of the cardnumber, eg: "4", "6011", "34,37".
		len         : valid length of cardnumber, eg: "16,19", "13,16".
		year        : year of expiry date.
		month       : month of expiry date.
		eg:
		var VisaCard = new CardType("Visa", "4", "16");
		var AmExCard = new CardType("AmEx", "34,37", "15");
		\*************************************************************************/
		function CardType() {
			var n;
			var argv = CardType.arguments;
			var argc = CardType.arguments.length;

			this.objname = "object CardType";

			var tmpcardtype = (argc > 0) ? argv[0] : "CardObject";
			var tmprules = (argc > 1) ? argv[1] : "0,1,2,3,4,5,6,7,8,9";
			var tmplen = (argc > 2) ? argv[2] : "13,14,15,16,19";

			this.setCardNumber = setCardNumber;  // set CardNumber method.
			this.setCardType = setCardType;  // setCardType method.
			this.setLength = setLength;  // setLength method.
			this.setRules = setRules;  // setRules method.
			this.setExpiryDate = setExpiryDate;  // setExpiryDate method.

			this.setCardType(tmpcardtype);
			this.setLength(tmplen);
			this.setRules(tmprules);
			if (argc > 4)
			this.setExpiryDate(argv[3], argv[4]);

			this.checkCardNumber = checkCardNumber;  // checkCardNumber method.
			this.getExpiryDate = getExpiryDate;  // getExpiryDate method.
			this.getCardType = getCardType;  // getCardType method.
			this.isCardNumber = isCardNumber;  // isCardNumber method.
			this.isExpiryDate = isExpiryDate;  // isExpiryDate method.
			this.luhnCheck = luhnCheck;// luhnCheck method.
			return this;
		}

		/*************************************************************************\
		boolean checkCardNumber([String cardnumber, int year, int month])
		return true if cardnumber pass the luhncheck and the expiry date is
		valid, else return false.
		\*************************************************************************/
		function checkCardNumber() {
			var argv = checkCardNumber.arguments;
			var argc = checkCardNumber.arguments.length;
			var cardnumber = (argc > 0) ? argv[0] : this.cardnumber;
			var year = (argc > 1) ? argv[1] : this.year;
			var month = (argc > 2) ? argv[2] : this.month;

			this.setCardNumber(cardnumber);
			this.setExpiryDate(year, month);

			if (!this.isCardNumber())
				return false;
			if (!this.isExpiryDate())
				return false;

			return true;
		}
		/*************************************************************************\
		String getCardType()
		return the cardtype.
		\*************************************************************************/
		function getCardType() {
			return this.cardtype;
		}
		/*************************************************************************\
		String getExpiryDate()
		return the expiry date.
		\*************************************************************************/
		function getExpiryDate() {
			return this.month + "/" + this.year;
		}
		/*************************************************************************\
		boolean isCardNumber([String cardnumber])
		return true if cardnumber pass the luhncheck and the rules, else return
		false.
		\*************************************************************************/
		function isCardNumber() {
			var argv = isCardNumber.arguments;
			var argc = isCardNumber.arguments.length;
			var cardnumber = (argc > 0) ? argv[0] : this.cardnumber;
			if (!this.luhnCheck())
				return false;

			for (var n = 0; n < this.len.length; n++)
				if (cardnumber.toString().length == this.len[n]) {
					for (var m = 0; m < this.rules.length; m++) {
						var headdigit = cardnumber.substring(0, this.rules[m].toString().length);
						if (headdigit == this.rules[m])
							return true;
				}
				return false;
			}

			return false;
		}

		/*************************************************************************\
		boolean isExpiryDate([int year, int month])
		return true if the date is a valid expiry date,
		else return false.
		\*************************************************************************/
		function isExpiryDate() {
			var argv = isExpiryDate.arguments;
			var argc = isExpiryDate.arguments.length;

			year = argc > 0 ? argv[0] : this.year;
			month = argc > 1 ? argv[1] : this.month;

			if (!isNumber(year+""))
				return false;
			if (!isNumber(month+""))
				return false;
			today = new Date();
			expiry = new Date(year, month, 1);
			if (today.getTime() > expiry.getTime())
				return false;
			else
				return true;
		}

		/*************************************************************************\
		boolean isNumber(String argvalue)
		return true if argvalue contains only numeric characters,
		else return false.
		\*************************************************************************/
		function isNumber(argvalue) {
            if (argvalue == undefined) {
                return false;
            }

			argvalue = argvalue.toString();

			if (argvalue.length == 0)
				return false;

			for (var n = 0; n < argvalue.length; n++)
				if (argvalue.substring(n, n+1) < "0" || argvalue.substring(n, n+1) > "9")
					return false;

			return true;
		}

		function isDouble(argvalue) {
            if (argvalue == undefined) {
                return false;
            }
            
            argvalue = argvalue.toString();

            if (argvalue.length == 0)
				return false;
            
			var pattern = /^\d+.?\d*$/;

			if ( argvalue.match(pattern)==null )
				return false;

			return true;

		}

		/*************************************************************************\
		boolean luhnCheck([String CardNumber])
		return true if CardNumber pass the luhn check else return false.
		Reference: http://www.ling.nwu.edu/~sburke/pub/luhn_lib.pl
		\*************************************************************************/
		function luhnCheck() {
			var argv = luhnCheck.arguments;
			var argc = luhnCheck.arguments.length;

			var CardNumber = argc > 0 ? argv[0] : this.cardnumber;

			if (! isNumber(CardNumber)) {
				return false;
		  }

			var no_digit = CardNumber.length;
			var oddoeven = no_digit & 1;
			var sum = 0;

			for (var count = 0; count < no_digit; count++) {
				var digit = parseInt(CardNumber.charAt(count));
				if (!((count & 1) ^ oddoeven)) {
					digit *= 2;
				if (digit > 9)
					digit -= 9;
				}
				sum += digit;
			}

			if (sum % 10 == 0)
				return true;
			else
				return false;
		}
		function CardType() {
			var n;
			var argv = CardType.arguments;
			var argc = CardType.arguments.length;

			this.objname = "object CardType";

			var tmpcardtype = (argc > 0) ? argv[0] : "CardObject";
			var tmprules = (argc > 1) ? argv[1] : "0,1,2,3,4,5,6,7,8,9";
			var tmplen = (argc > 2) ? argv[2] : "13,14,15,16,19";

			this.setCardNumber = setCardNumber;  // set CardNumber method.
			this.setCardType = setCardType;  // setCardType method.
			this.setLength = setLength;  // setLength method.
			this.setRules = setRules;  // setRules method.
			this.setExpiryDate = setExpiryDate;  // setExpiryDate method.

			this.setCardType(tmpcardtype);
			this.setLength(tmplen);
			this.setRules(tmprules);
			if (argc > 4)
				this.setExpiryDate(argv[3], argv[4]);

			this.checkCardNumber = checkCardNumber;  // checkCardNumber method.
			this.getExpiryDate = getExpiryDate;  // getExpiryDate method.
			this.getCardType = getCardType;  // getCardType method.
			this.isCardNumber = isCardNumber;  // isCardNumber method.
			this.isExpiryDate = isExpiryDate;  // isExpiryDate method.
			this.luhnCheck = luhnCheck;// luhnCheck method.
			return this;
		}
		/*************************************************************************\
		CardType setCardNumber(cardnumber)
		return the CardType object.
		\*************************************************************************/
		function setCardNumber(cardnumber) {
			this.cardnumber = cardnumber;
			return this;
		}
		/*************************************************************************\
		CardType setCardType(cardtype)
		return the CardType object.
		\*************************************************************************/
		function setCardType(cardtype) {
			this.cardtype = cardtype;
			return this;
		}

		/*************************************************************************\
		CardType setExpiryDate(year, month)
		return the CardType object.
		\*************************************************************************/
		function setExpiryDate(year, month) {
			this.year = year;
			this.month = month;
			return this;
		}

		/*************************************************************************\
		CardType setLength(len)
		return the CardType object.
		\*************************************************************************/
		function setLength(len) {
			// Create the len array.
			if (len.length == 0 || len == null)
				len = "13,14,15,16,19";

			var tmplen = len;
			n = 1;
			while (tmplen.indexOf(",") != -1) {
				tmplen = tmplen.substring(tmplen.indexOf(",") + 1, tmplen.length);
				n++;
			}
			this.len = new Array(n);
			n = 0;
			while (len.indexOf(",") != -1) {
				var tmpstr = len.substring(0, len.indexOf(","));
				this.len[n] = tmpstr;
				len = len.substring(len.indexOf(",") + 1, len.length);
				n++;
			}
			this.len[n] = len;
			return this;
		}
		/*************************************************************************\
		CardType setRules()
		return the CardType object.
		\*************************************************************************/
		function setRules(rules) {
			// Create the rules array.
			if (rules.length == 0 || rules == null)
				rules = "0,1,2,3,4,5,6,7,8,9";

			var tmprules = rules;
			n = 1;
			while (tmprules.indexOf(",") != -1) {
				tmprules = tmprules.substring(tmprules.indexOf(",") + 1, tmprules.length);
				n++;
			}
			this.rules = new Array(n);
			n = 0;
			while (rules.indexOf(",") != -1) {
				var tmpstr = rules.substring(0, rules.indexOf(","));
				this.rules[n] = tmpstr;
				rules = rules.substring(rules.indexOf(",") + 1, rules.length);
				n++;
			}
			this.rules[n] = rules;
			return this;
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