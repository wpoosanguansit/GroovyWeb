package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:04:40 PM
 */


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:02:14 PM
 */

public class PasswordResetResponse extends JavaScriptObject {
	protected PasswordResetResponse() {}

    /**
	 * Was the password successfully reset?
	 * @return true if the password was reset, false otherwise
	 */
	public final native boolean getWasReset()/*-{ return this.wasReset; }-*/;
	public final native String getRememberMeCookie()/*-{ return this.rememberMeCookie; }-*/;
	public final native UserProfile getUserProfile()/*-{ return this.userProfile; }-*/;
}
