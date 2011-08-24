package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.pdmaf.ui.gwt.client.enums.SignupResponseType;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:22:12 PM
 */


public class SignupResponse extends JavaScriptObject {
	protected SignupResponse() {}
	/**
	 * Will return null if response type is anything other than success.
	 * @return
	 */
	public final native UserProfile getUserProfile()/*-{ return this.userProfile }-*/;
	private native String getSignupResponseString()/*-{ return this.signupResponseString; }-*/;
	/**
	 * Will be null if "remember me" wasn't checked. If non-null, the client
	 * must take extra steps to set this cookie. Yeah!
	 * @return
	 */
	public final native String getRememberMeCookie()/*-{ return this.rememberMeCookie; }-*/;
	public final SignupResponseType getResponseType() {
		return SignupResponseType.valueOf(getSignupResponseString());
	}
}
