package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 7:58:27 AM
 */

public class Location extends JavaScriptObject {

	protected Location() {}
	public final native String getDescription()/*-{ return this.description; }-*/;
	public final native double getLatitude()/*-{ return this.latitude; }-*/;
	public final native double getLongtitude()/*-{ return this.longtitude; }-*/;
	public static native Location create(String description, double latitude, double longitude)/*-{
		return { description: description, latitude: latitude, longitude: longtitude };
	}-*/;
}