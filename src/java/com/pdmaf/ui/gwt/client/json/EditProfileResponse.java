package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:13:57 PM
 */

public class EditProfileResponse extends JavaScriptObject {
	protected EditProfileResponse() {}
	/**
	 * If false, then edit profile was successful.
	 * @return
	 */
	public final native boolean changedEmailAddressTaken()/*-{ return this.changedEmailAddressTaken; }-*/;
}
