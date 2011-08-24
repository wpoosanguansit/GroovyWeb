package com.pdmaf.ui.gwt.client.rpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pdmaf.ui.gwt.client.json.Call;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:09:20 PM
 */

public interface ServiceCaller {
	<T extends JavaScriptObject> void makeCall(Call call, AsyncCallback<T> callback);
}
