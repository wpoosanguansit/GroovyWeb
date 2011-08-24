package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:01:30 PM
 */

public class Call extends JavaScriptObject {
	protected Call() {}
	public final native String getMethod() /*-{ return this.method; }-*/;
	public final native JavaScriptObject getArguments() /*-{ return this.args; }-*/;
	public final native int getNumberOfArguments() /*-{ return this.args.length; }-*/;
	public final native boolean getBooleanArgument(int index) /*-{ return this.args[index]; }-*/;
	public final native Call addBooleanArgument(boolean value) /*-{ this.args.push(value); return this; }-*/;
	public final native double getDoubleArgument(int index) /*-{ return this.args[index]; }-*/;
	public final native Call addDoubleArgument(double value) /*-{ this.args.push(value); return this; }-*/;
	public final native int getIntArgument(int index) /*-{ return this.args[index]; }-*/;
	public final native Call addIntArgument(int value) /*-{ this.args.push(value); return this; }-*/;
	public final native String getStringArgument(int index) /*-{ return this.args[index]; }-*/;
	public final native Call addStringArgument(String value) /*-{ this.args.push(value); return this; }-*/;
	public final native JavaScriptObject getObjectArgument(int index) /*-{ return this.args[index]; }-*/;
	public final native Call addObjectArgument(JavaScriptObject value) /*-{ this.args.push(value); return this; }-*/;
	public static native Call create(String method)/*-{
		return { method: method, args: new Array() };
	}-*/;
}