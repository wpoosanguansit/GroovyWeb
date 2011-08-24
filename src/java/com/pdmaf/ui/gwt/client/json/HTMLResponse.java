package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:15:27 PM
 */

public class HTMLResponse extends JavaScriptObject {
	protected HTMLResponse() {}
	public final native String getHTML()/*-{return this.htmlResponse;}-*/;
}
