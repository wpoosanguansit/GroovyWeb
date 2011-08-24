package com.pdmaf.ui.gwt.client.rpc;

import com.pdmaf.ui.gwt.client.json.Call;
import com.pdmaf.ui.gwt.client.json.ServiceRequest;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 28, 2009
 * Time: 11:50:11 AM
 */
public class ServiceRequestRPC extends RPC {
    private static ServiceRequestRPC instance;
    public static ServiceRequestRPC get() {
        if (instance == null)
            instance = new ServiceRequestRPC();
        return instance;
    }
    private ServiceRequestRPC() {}
    public void simpleSearchServiceRequest(String searchTerm,
            AsyncCallback<ServiceRequest> callback) {
        getCaller().makeCall(Call.create("simpleSearch").addStringArgument(searchTerm), callback);
    }

    public void advancedSearchServiceRequest(String allthewords, String exactphrase, String atleastoneofthesewords,
                                             String noneofthesewords, AsyncCallback<ServiceRequest> callback) {
        getCaller().makeCall(Call.create("advancedSearch").addStringArgument(allthewords).
                addStringArgument(exactphrase).addStringArgument(atleastoneofthesewords).
                addStringArgument(noneofthesewords).addStringArgument(atleastoneofthesewords), callback);
    }

    @Override
    protected String getServiceName() {
        return "serviceRequestRPC";
    }
}
