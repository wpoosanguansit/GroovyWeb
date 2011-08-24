package com.pdmaf.ui.gwt.client.rpc;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:08:48 PM
 */
public abstract class RPC {
	private ServiceCaller caller = null;

	protected ServiceCaller getCaller() {
		if (caller == null)
			caller = new XHRServiceCaller(getServiceName());
		return caller;
	}
	protected abstract String getServiceName();
}