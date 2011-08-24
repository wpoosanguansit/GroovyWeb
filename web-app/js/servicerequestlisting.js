        jQuery(document).ready(function(){

				jQuery("#service-listing-tabs").tabs();

				//simple search form
				var defaultSearchSpan = jQuery('span#default-search-span').clone();
				var advancedSearchSpan = jQuery('span#advanced-search-span').clone();
				jQuery('span#search-container').html(defaultSearchSpan.show());
				//set up the hint
				jQuery('input:text').hint();
				var toggle = 0;
				jQuery('a#searchlink').bind('click',  function() {
					if ((toggle % 2) == 0) {
						jQuery('a#searchlink').text('Simple Search');
						jQuery('span#search-container').html('');
						jQuery('span#search-container').html(advancedSearchSpan.show());
						//reset the hint
						jQuery('input:text').hint();
					}	else {
						jQuery('a#searchlink').text('Advanced Search');
						jQuery('span#search-container').html('');
						jQuery('span#search-container').html(defaultSearchSpan.show());
						//reset the hint
						jQuery('input:text').hint();
					}
					toggle++;
				});

				//set up the side menu bar pages
				jQuery('a#registered-users').click(function(){
					//hover has to change the img and when click as well
					jQuery('div#service-request-by-registered-users').show();
					jQuery('div#service-request-by-non-registered-users').hide();
				});

				jQuery('a#non-registered-users').click(function(){
					//hover has to change the img and when click as well
					jQuery('div#service-request-by-registered-users').hide();
					jQuery('div#service-request-by-non-registered-users').show();
				});

				//fixing the form
				if( document.addEventListener )
					document.addEventListener( 'DOMContentLoaded', normalform, false);

				function normalform(){
					  // Hide forms
					  jQuery( 'form.normalform' ).hide().end();

					  // Processing
					  jQuery( 'form.normalform' ).find( 'li/label' ).not( '.nonormalform' )
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

		}