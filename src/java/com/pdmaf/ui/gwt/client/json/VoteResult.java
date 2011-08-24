package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:05:58 PM
 */

public class VoteResult extends JavaScriptObject {
	protected VoteResult() {}
	public final native double getAverage()/*-{ return this.average; }-*/;
	public final native int getCount()/*-{ return this.count; }-*/;
}
