package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:12:15 PM
 */
public class JSON {
	public static native String serialize(JavaScriptObject object)/*-{
		return "[" + $wnd.JSON.encode(object) + "]";
	}-*/;
	public static native JavaScriptObject deserialize(String json)/*-{
		return eval('(' + json + ')');
	}-*/;
}
