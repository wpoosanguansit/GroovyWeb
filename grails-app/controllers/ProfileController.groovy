import com.pdmaf.ui.groovy.ProfileServiceProviderPage

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 21, 2009
 * Time: 8:08:33 AM
 *  
 */

public class ProfileController {

  def serviceProvider = {
    final ProfileServiceProviderPage profileServiceProviderPage = new ProfileServiceProviderPage()
    render(text: profileServiceProviderPage.render(), contentType: "text/html")
  }

}