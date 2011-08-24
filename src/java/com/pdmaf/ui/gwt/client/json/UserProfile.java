package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.JavaScriptObject;


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 7:10:11 PM
 * 
 */
public class UserProfile extends JavaScriptObject {
	protected UserProfile() {}
	public final native int getID()/*-{return this.id;}-*/;
//	public final native Window.Location getLocation()/*-{ return this.location; }-*/;
	public final native boolean isAdministrator()/*-{return this.isAdministrator;}-*/;
    public final native String getUsername()/*-{ return this.username; }-*/;
	public final native boolean getDefaultPhoto()/*-{ return this.hasDefaultPhoto; }-*/;
    public final native String getServiceProviderProfileID()/*-{ return this.serviceProviderProfileID; }-*/;
}