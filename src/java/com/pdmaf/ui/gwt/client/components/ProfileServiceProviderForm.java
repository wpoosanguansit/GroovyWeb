package com.pdmaf.ui.gwt.client.components;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.pdmaf.ui.gwt.client.rpc.ServiceProviderRPC;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;
import com.pdmaf.ui.gwt.client.json.ServiceProvider;
import com.pdmaf.ui.gwt.client.json.UserProfile;
import com.pdmaf.ui.gwt.client.PDMAFWebMessages;


/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 21, 2009
 * Time: 8:22:01 AM
 *
 * this component expects this fragment to be on the page
 *
 * <div id=service-provider-form"></div>
 */

public class ProfileServiceProviderForm {
    private ServiceProvider serviceProvider;
    private RootPanel profileServiceProviderForm;
    private SignupFormStatus formStatus;
    
    private static final ProfileServiceProviderFormMessages messages =  PDMAFWebMessages.instance;

    public static interface ProfileServiceProviderFormMessages extends Messages {
        String introduction();
        String serviceProviderSignupForm();
    }

    public enum SignupFormStatus { DISPLAY, EDIT, CONFIRMSAVE }

    public ProfileServiceProviderForm() {
        setupProfileServiceProviderForm();
        ServiceProviderForm serviceProviderForm;
//        ServiceProviderRPC.get().retrieveServiceProviderByUserid(PageJSInterface.getCurrentUser(),
//        new AsyncCallback<ServiceProvider>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(ServiceProvider serviceProvider) {
//                ProfileServiceProviderForm.this.serviceProvider = serviceProvider;
//            }
//        });
        UserProfile userProfile = PageJSInterface.getUserProfile();
        if (userProfile.getServiceProviderProfileID() != null) {
            serviceProviderForm = new ServiceProviderForm(serviceProvider);
        } else {
            serviceProviderForm = new ServiceProviderForm();
        }

        profileServiceProviderForm.add(serviceProviderForm);
    }

    private void setupProfileServiceProviderForm() {
        profileServiceProviderForm = RootPanel.get("service-provider-form");
    }

    private class ServiceProviderForm extends Composite {

        public ServiceProviderForm() {
            DockPanel mainPanel = new DockPanel();
            mainPanel.add(new HTML(messages.introduction()), DockPanel.NORTH);
            DisclosurePanel signupFormDisclosure = new DisclosurePanel(messages.serviceProviderSignupForm());
            signupFormDisclosure.setAnimationEnabled(true);
            Grid grid = new Grid(5, 2);
            TextBoxBaseWrapper skillCategoryTextBox = new TextBoxBaseWrapper(new TextBox());
            TextBoxBaseWrapper skillDescriptionTextArea = new TextBoxBaseWrapper(new TextArea());
            TextBoxBaseWrapper noteTextArea = new TextBoxBaseWrapper(new TextArea());
            TextBoxBaseWrapper referenceTextArea = new TextBoxBaseWrapper(new TextArea());
            grid.setWidget(0, 0, new HTML("Skill Description : "));
            grid.setWidget(0, 1, skillDescriptionTextArea.getTextBoxBase());
            grid.setWidget(1, 0, new HTML("Skill Category : "));
            grid.setWidget(1, 1, skillCategoryTextBox.getTextBoxBase());
            grid.setWidget(2, 0, new HTML("Note : "));
            grid.setWidget(2, 1, noteTextArea.getTextBoxBase());
            grid.setWidget(3, 0, new HTML("Reference : "));
            grid.setWidget(3, 1, referenceTextArea.getTextBoxBase());
            Button submit = null;
            grid.setWidget(4, 1, submit = new Button("Submit"));
            submit.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent clickEvent) {
                    
                }
            });
            signupFormDisclosure.setContent(grid);
            mainPanel.add(signupFormDisclosure, DockPanel.SOUTH);
            this.initWidget(mainPanel);
        }

        public ServiceProviderForm(ServiceProvider serviceProvider) {

            switch(formStatus) {
                case DISPLAY        :   break;
                case EDIT           :   break;
                case CONFIRMSAVE    :   break;

            }
        }
    }

}
