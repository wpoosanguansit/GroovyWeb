package com.pdmaf.ui.gwt.client.pages;

import com.pdmaf.ui.gwt.client.components.eventhandlers.LoginFormEventHandler;
import com.pdmaf.ui.gwt.client.components.LoginForm;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;
import com.pdmaf.ui.gwt.client.rpc.SiteRPC;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 9:48:50 AM
 * This will be the base for inheritance in pararell to the MainPage on the groovy page.
 * This component will include components like login box, etc.
 */
public class MainPageComponent {

    private Element loginFormContainer;
    private Element navigationContainer;
    private MainPageComponentController controller;


    public MainPageComponent() {
        
        //initialize all the compoenents that will go into mainpage
        setupLoginContainers();
        if (PageJSInterface.getCurrentUser() == null ||
                PageJSInterface.getCurrentUser().isEmpty())
			setupLoginForm();

        controller = new MainPageComponentController(this);

    }

    private void setupLoginContainers() {
        loginFormContainer = DOM.getElementById("nav-panel-login-form");
        navigationContainer = DOM.getElementById("nav-panel");
    }

    private void setupLoginForm() {
        DOM.getElementById("login-text").setInnerHTML("");
        RootPanel login = RootPanel.get("login-text");
        login.clear();
        Anchor anchor = new Anchor("login");
        RootPanel.get("login-text").add(anchor);
        anchor.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                displayLoginPanel(true);
                new LoginForm(PageJSInterface.getPageName(),
                    new LoginFormEventHandler(){
                        public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                            //after hte login is complete redirect to profile page as default
                            PageJSInterface.redirect(PageJSInterface.SERVICEBASEURL() + "application/profile");
                        }
                        public void onWindowClosing(Window.ClosingEvent event) {
                            displayLoginPanel(false);
                        }
                    });

            }
        });
    }
    
    private void displayLoginPanel(boolean display) {
        UIObject.setVisible(navigationContainer, !display);
		UIObject.setVisible(loginFormContainer, display);
	}

    private class MainPageComponentController {
        private MainPageComponent component;

        /**
         * Constructor to be used for non-content components, like listing components.
         * @param component
         */
        public MainPageComponentController(MainPageComponent component) {
            this.component = component;
            Timer heartbeatTimer = new Timer() {
                @Override
                public void run() {
                    SiteRPC.get().heartbeat();
                }};
            // once every ten minutes.
            heartbeatTimer.scheduleRepeating(1000 * 60 * 10);
        }

    }
}