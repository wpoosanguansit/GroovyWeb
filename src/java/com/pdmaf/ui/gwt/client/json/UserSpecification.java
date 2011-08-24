package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 7:11:01 PM
 * 
 */
public class UserSpecification extends JavaScriptObject {
	protected UserSpecification() {}

    public final native String getUsername()/*-{ return this.username; }-*/;
	public final native boolean getDefaultPhoto()/*-{ return this.hasDefaultPhoto; }-*/;
}
