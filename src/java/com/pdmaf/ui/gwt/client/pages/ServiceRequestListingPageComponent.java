package com.pdmaf.ui.gwt.client.pages;

import com.pdmaf.ui.gwt.client.components.ServiceRequestSearchResultTable;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 26, 2009
 * Time: 11:22:32 AM
 */
public class ServiceRequestListingPageComponent {

    public ServiceRequestListingPageComponent() {
        ServiceRequestSearchResultTable grid = new ServiceRequestSearchResultTable();
        RootPanel.get("search-result").add(grid);
    }
}
