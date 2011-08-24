package com.pdmaf.ui.gwt.client.json;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:14:51 PM
 */

public class FileUploadResponse extends JavaScriptObject {
	protected FileUploadResponse() {};
	/**
	 * Only non-null if upload was successful. In that case it contains the
	 * upload key for the next upload attempt.
	 * @return
	 */
	public final native String getNextUploadKey()/*-{ return this.nextUploadKey; }-*/;
	/**
	 * Only non-null if there was an error and the server is able to
	 * come up with a sensible message.
	 * @return
	 */
	public final native String errorMessage()/*-{ return this.errorMessage; }-*/;
}
