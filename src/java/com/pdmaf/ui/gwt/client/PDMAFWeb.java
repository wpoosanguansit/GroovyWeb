package com.pdmaf.ui.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

import com.pdmaf.ui.gwt.client.pages.*;
import com.pdmaf.ui.gwt.client.components.TextBoxBaseWrapper;
import com.pdmaf.ui.gwt.client.components.ValidatingToolTip;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;

//import com.smartgwt.client.util.SC;
//import com.smartgwt.client.util.KeyCallback;
//import com.smartgwt.client.util.Page;
//import com.smartgwt.client.core.KeyIdentifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PDMAFWeb implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        //debug smartclient with console do control D in the browser
//        if (!GWT.isScript()) {
//            KeyIdentifier debugKey = new KeyIdentifier();
//            debugKey.setCtrlKey(true);
//            debugKey.setKeyName("D");
//
//        Page.registerKey(debugKey, new KeyCallback() {
//            public void execute(String keyName) {
//                SC.showConsole();
//            }});
//        }

        TextBoxBaseWrapper.setTooltipFactory(ValidatingToolTip.FACTORY);
        PageName page = PageName.valueOf(PageJSInterface.getPageName());
        switch (page) {
            case PDMAFWeb                   :   new ProfileServiceProviderPageComponent();
                                                break;
            case LoginPage                  :   break;
            case MainPage                   :   new MainPageComponent();
                                                break;
            case MakeDonationPage           :   new MakeDonationPageComponent();
                                                break;
            case ProfileServiceProviderPage :   new ProfileServiceProviderPageComponent();
                                                break;
            case ProfilePage                :   new MainPageComponent();
                                                break;
            default                         :   new MainPageComponent();
                                                break;
        }
    }
}
