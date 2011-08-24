package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.pdmaf.ui.gwt.client.enums.LoginResponseType;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:17:42 PM
 */

public class LoginResponse extends JavaScriptObject {
	protected LoginResponse() {}
	/**
	 * Will return null if response type is anything other than success.
	 * @return
	 */
	public final native UserProfile getUserProfile()/*-{ return this.userProfile; }-*/;
	private native String getLoginResponse()/*-{ return this.loginResponse; }-*/;
	/**
	 * Will be null if "remember me" wasn't checked. If non-null, the client
	 * must take extra steps to set this cookie. Yeah!
	 * @return
	 */
	public final native String getRememberMe()/*-{ return this.rememberMe; }-*/;
	public final LoginResponseType getResponseType() {
		return LoginResponseType.valueOf(getLoginResponse());
	}
}
