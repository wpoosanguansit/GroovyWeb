package com.pdmaf.ui.gwt.client.rpc;

import com.pdmaf.ui.gwt.client.json.Call;
import com.pdmaf.ui.gwt.client.json.LoginResponse;
import com.pdmaf.ui.gwt.client.enums.LoginResponseType;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;
import com.pdmaf.ui.gwt.client.utils.CookieHelper;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 12:06:56 PM
 */

public class SiteRPC extends RPC {

	private static SiteRPC instance;

	public static SiteRPC get() {
		if (instance == null)
			instance = new SiteRPC();
		return instance;
	}

	private SiteRPC() {}

	/**
	 * Used to extend session on server so long as page is open.
	 */
	public void heartbeat() {
		getCaller().makeCall(Call.create("heartbeat"), null);
	}

	protected String getServiceName() {
		return "siteRPC";
	}
}
