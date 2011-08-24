package com.pdmaf.ui.gwt.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pdmaf.ui.gwt.client.json.Call;
import com.pdmaf.ui.gwt.client.json.ServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 21, 2009
 * Time: 9:29:29 AM
 */
public class ServiceProviderRPC extends RPC {
    private static ServiceProviderRPC instance;
    public static ServiceProviderRPC get() {
        if (instance == null)
            instance = new ServiceProviderRPC();
        return instance;
    }
    private ServiceProviderRPC() {}

    public void retrieveServiceProviderByUserid(String userid,
            AsyncCallback<ServiceProvider> callback) {
        getCaller().makeCall(Call.create("retrieveServiceProviderByUserid").addStringArgument(userid), callback);
    }
    @Override
    protected String getServiceName() {
        return "serviceProviderRPC"; 
    }
}
