package com.pdmaf.ui.gwt.client.utils;

import com.google.gwt.user.client.Command;
import com.pdmaf.ui.gwt.client.json.UserProfile;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 7:05:19 PM
 * 
 */
public class PageJSInterface {
	/**
	 * May return -1 if undefined.
	 * @return
	 */

	public static native String getPageName()/*-{
		return $wnd.pageName;
	}-*/;
	public static native void setCurrentUser(String currentUser)/*-{
		$wnd.currentUser = currentUser;
	}-*/;
	public static native String getCurrentUser()/*-{
		return $wnd.currentUser;
	}-*/;
    public static native void setUserProfile(UserProfile userProfile)/*-{
		$wnd.userProfile = userProfile;
	}-*/;
	public static native UserProfile getUserProfile()/*-{
		return $wnd.userProfile;
	}-*/;
	/**
	 * Only useful in the profile and signup pages.
	 * @return
	 */
	public static native String getUploadKey()/*-{
		return $wnd.uploadKey;
	}-*/;
	/**
	 * The base url for the RPC services.
	 * @return
	 */
	public static native String SERVICEBASEURL()/*-{
		return $wnd.url;
	}-*/;

    public static native String SERVICEBASESECUREURL()/*-{
		return $wnd.secureUrl;
	}-*/;

	public static native String COOKIEDOMAIN()/*-{
		return $wnd.COOKIEDOMAIN ? $wnd.COOKIEDOMAIN : null;
	}-*/;
	/**
	 * Adds a NO-ARG javascript function to the page
	 * @param functionName
	 * @param command command to execute when function is called
	 */
	public static native void addJSFunction(String functionName, Command command)/*-{
		$wnd[functionName] = command.@com.google.gwt.user.client.Command::execute();
	}-*/;

    //redirect the browser to the given url
    //An example call : redirect("http://www.emgarten.com/");
    public static native void redirect(String url)/*-{
          $wnd.location = url;
    }-*/;

    //refresh the page
    public static native void pageReload() /*-{
        $wnd.location.reload();
    }-*/;
        
}
