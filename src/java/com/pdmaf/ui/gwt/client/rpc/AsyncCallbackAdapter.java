package com.pdmaf.ui.gwt.client.rpc;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 17, 2009
 * Time: 9:17:34 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class AsyncCallbackAdapter<T> implements AsyncCallback<T> {

	public AsyncCallbackAdapter() {}

	public final void onFailure(Throwable caught) {
		Window.alert("There was an error with the server, please try again later. :code RPC error");
	}
	public final void onSuccess(T result) {
		onSuccessImpl(result);
	}
	protected abstract void onSuccessImpl(T result);
}