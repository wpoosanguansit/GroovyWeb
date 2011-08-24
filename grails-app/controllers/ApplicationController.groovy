
import com.pdmaf.ui.groovy.ForgetYourPasswordPage
import com.pdmaf.ui.groovy.HowItWorksPage
import com.pdmaf.ui.groovy.MainPage
import com.pdmaf.ui.groovy.PointSystemPage
import com.pdmaf.ui.groovy.RegisterPage
import com.pdmaf.ui.groovy.MakeDonationPage
import com.pdmaf.ui.groovy.SuggestOranizationPage
import com.pdmaf.ui.groovy.ServiceRequestListingPage
import com.pdmaf.ui.groovy.PostServiceRequestPage
import com.pdmaf.ui.groovy.ErrorPage
import com.pdmaf.ui.groovy.ProfilePage

class ApplicationController {

    //to pass messageSource to view pages for internationalization
    def messageSource

    def index = {
        final MainPage mainPage = new MainPage(messageSource: messageSource, locale: request.locale)
//        //checking if it is IE browser
//        def browser = request.getHeader("user-agent")
//        def isMSIE = ( browser != null && browser.indexOf("MSIE") != -1 );
    	render(text: mainPage.render(), contentType: "text/html")
	}
    
    def register = {
        final RegisterPage registerPage = new RegisterPage()
		render(text: registerPage.render(), contentType: "text/html")
    }

    def forgetPassword = {
      final ForgetYourPasswordPage forgetPasswordPage = new ForgetYourPasswordPage()
      render(text: forgetPasswordPage.render(), contentType: "text/html")
    }

    def howItWorks = {
	  final HowItWorksPage howItWorksPage = new HowItWorksPage()	
      render(text: howItWorksPage.render(), contentType: "text/html")
    }

    def pointSystem = {
      final PointSystemPage pointSystemPage = new PointSystemPage()
      render(text: pointSystemPage.render(), contentType: "text/html")
    }

    def makeDonation = {
      final MakeDonationPage donationPage = new MakeDonationPage()
      render(text: donationPage.render(), contentType: "text/html")
    }

    def listing = {
      final ServiceRequestListingPage serviceRequestListingPage = new ServiceRequestListingPage()
      render(text: serviceRequestListingPage.render(), contentType: "text/html")
    }
  
    def profile = {
      final ProfilePage profilePage = new ProfilePage()
      render(text: profilePage.render(), contentType: "text/html")
    }

    def suggestOrganization = {
      final SuggestOranizationPage suggestOrganizationPage = new SuggestOranizationPage()
      render(text: suggestOrganizationPage.render(), contentType: "text/html")
    }

    def postServiceRequest = {
      final PostServiceRequestPage postServiceRequestPage = new PostServiceRequestPage()
      render(text: postServiceRequestPage.render(), contentType: "text/html")
    }

    def showError = {
      final ErrorPage errorPage = new ErrorPage(errorMessage: params.errorMessage, messageSource: messageSource, locale: request.locale)
      render(text: errorPage.render(), contentType: "text/html")
    }
}
