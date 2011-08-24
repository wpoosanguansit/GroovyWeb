package com.pdmaf.ui.gwt.client.rpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pdmaf.ui.gwt.client.json.Call;
import com.pdmaf.ui.gwt.client.json.JSON;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:10:15 PM
 */

public class XHRServiceCaller implements ServiceCaller {
	private String serviceName;

	public XHRServiceCaller(String serviceName) {
		this.serviceName = serviceName;
	}
	public <T extends JavaScriptObject> void makeCall(Call call,
			final AsyncCallback<T> callback) {
		RequestBuilder rb = new RequestBuilder(
			RequestBuilder.POST,
			PageJSInterface.SERVICEBASEURL() +
			serviceName + "/" + call.getMethod());
		rb.setCallback(new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				if (callback != null)
					callback.onFailure(exception);
			}
			@SuppressWarnings("unchecked")
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == 200) {
					if (callback == null)
                        return;
						callback.onSuccess((T) JSON.deserialize(response.getText()));
				}
				else {
					if (callback != null)
						callback.onFailure(new Exception("Received status code of " +
							response.getStatusCode()));
				}
			}});
		rb.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		rb.setRequestData("args=" + URL.encodeComponent(JSON.serialize(call.getArguments())));
		try {
			rb.send();
		}
		catch (RequestException rex) {
			if (callback != null)
				callback.onFailure(rex);
		}
	}

}
