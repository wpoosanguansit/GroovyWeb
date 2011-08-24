package com.pdmaf.ui.gwt.client;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.core.client.GWT;
import com.pdmaf.ui.gwt.client.components.ProfileServiceProviderForm;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 7, 2009
 * Time: 9:16:44 AM
 */
public interface PDMAFWebMessages extends Messages, ProfileServiceProviderForm.ProfileServiceProviderFormMessages {

    public final static PDMAFWebMessages instance = (PDMAFWebMessages) GWT.create(PDMAFWebMessages.class);
}