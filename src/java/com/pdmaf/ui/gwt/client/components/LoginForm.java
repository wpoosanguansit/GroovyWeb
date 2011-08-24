package com.pdmaf.ui.gwt.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.pdmaf.ui.gwt.client.components.eventhandlers.LoginFormEventHandler;
import com.pdmaf.ui.gwt.client.utils.PageJSInterface;
import com.pdmaf.ui.gwt.client.utils.RegexValidator;
import com.pdmaf.ui.gwt.client.utils.LengthValidator;
import com.pdmaf.ui.gwt.client.utils.CookieHelper;
import com.pdmaf.ui.gwt.client.rpc.JsonpRequestBuilder;
import com.pdmaf.ui.gwt.client.json.LoginResponse;


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 13, 2009
 * Time: 10:06:13 AM
 * To use this component the page must contain this elements
 * <span id="login-text">
 *  <a href="${url}auth/login?targetUri=/application/profile">login</a>
 * </span>
 * <div id="${pageName}_loginform" style="width: 100%;">
 *                           <form name="login-form" method="post" action="${url}auth/signIn?targetUri=/application/profile" onsubmit="login_submit_${pageName}(); return false;">
 *		                        <div>
 *                                   <span id="${pageName}_real" style="display: none">
 *                                       <input type="text" autocomplete="off" name="username" class="login" id="${pageName}_1"/>&nbsp;
 *                                       <input type="password" autocomplete="off" name="password" class="password" id="${pageName}_2"/>
 *                                       <input class="button" type="submit" value="Login" id="${pageName}_6" />
 *                                       <input class="button" type="submit" value="X" id="${pageName}_7" onclick="login_form_close_${pageName}(); return false;" />
 *                                   </span>
 *                                   <span id="${pageName}_dummy">
 *                                       <input class="instructional login" name="dummyUsername" type="text" value="username/email" id="${pageName}_3"/>&nbsp;
 *                                       <input class="instructional password" name="dummyPassword" type="text" value="password" id="${pageName}_4"/>
 *                                       <input class="buttonDisabled" disabled="true" type="submit" value="Login" id="${pageName}_8" />
 *                                       <input class="button" type="submit" value="X" id="${pageName}_9" onclick="login_form_close_${pageName}(); return false;" />
 *                                   </span>
 *		                        </div>
 *		                        <div>
 *			                        <input type="checkbox" checked="checked" id="${pageName}_5"/><span class="label">remember me</span>
 *                                  <span style="visibility: hidden;" id="${pageName}_10"></span>
 *		                        </div>
 *                           </form>
 *                       </div>
 */

public class LoginForm {

	private LoginFormEventHandler eventHandler;
	private Element loginStatus;
	private TextBoxBaseWrapper usernameTextBox, passwordTextBox;
	private TextBox dummyUsername, dummyPassword;
	private InputElement rememberMe;
	private SpanElement real;
	private SpanElement dummy;
    private RegexValidator usernameValidator;
    private LengthValidator passwordValidator;

    public LoginForm(String idprefix, LoginFormEventHandler callback) {

        eventHandler = callback;
		addSubmitCall(this, "login_submit_" + idprefix);
		real = SpanElement.as(DOM.getElementById(idprefix + "_real"));
		dummy = SpanElement.as(DOM.getElementById(idprefix + "_dummy"));
		loginStatus = formElement(idprefix, 10);
		usernameTextBox = TextBoxBaseWrapper.wrapTextBox(formElement(idprefix, 1));
		passwordTextBox = TextBoxBaseWrapper.wrapPasswordTextBox(formElement(idprefix, 2));
		dummyUsername = TextBox.wrap(formElement(idprefix, 3));
		dummyPassword = TextBox.wrap(formElement(idprefix, 4));
		rememberMe = InputElement.as(formElement(idprefix, 5));
		addCloseCall(this, "login_form_close_" + idprefix);
		FocusHandler dummyFocusHandler = new FocusHandler() {
			public void onFocus(FocusEvent event) {
				showRealInputs();
			}
		};
		dummyUsername.addFocusHandler(dummyFocusHandler);
		dummyPassword.addFocusHandler(dummyFocusHandler);
        usernameTextBox.setValidator(RegexValidator.createCustom("Username has to be in the format of your" +
                        " email", RegexValidator.EMAILREGEX));
        passwordTextBox.setValidator(new LengthValidator("Password", 6, 25));

	}

	private Element formElement(String prefix, int num) {
		return DOM.getElementById(prefix + "_" + num);
	}

	private native void addSubmitCall(LoginForm loginForm, String functionName)/*-{
		$wnd[functionName] = function() { loginForm.@com.pdmaf.ui.gwt.client.components.LoginForm::onSubmit()(); };
	}-*/;

	private native void addCloseCall(LoginForm loginForm, String functionName)/*-{
		$wnd[functionName] = function() { loginForm.@com.pdmaf.ui.gwt.client.components.LoginForm::closeClicked()(); };
	}-*/;

	public ValidatingTextInput getUsernameTextBox() {
		return this.usernameTextBox;
	}
	public ValidatingTextInput getPasswordTextBox() {
		return this.passwordTextBox;
	}
	public boolean isRememberMeChecked() {
		return rememberMe.isChecked();
	}
	public void clearMessages() {
		getUsernameTextBox().clearMessages();
		getPasswordTextBox().clearMessages();
	}

    public void resetContent() {
        getUsernameTextBox().setText("");
        getPasswordTextBox().setText("");
    }
    
	@SuppressWarnings("unused")
	private void onSubmit() {
        
        showWaitingStatus();
        if (!usernameTextBox.validateNow()) {
            clearStatus();
            usernameTextBox.showMessage("Username has to be in the format of your valid email address", true);
            return;
        }

        if (!passwordTextBox.validateNow()) {
            clearStatus();
            passwordTextBox.showMessage("Password has to be at least 6 charater", true);
            return;
        }

        JsonpRequestBuilder requestBuilder = new JsonpRequestBuilder();
        requestBuilder.requestObject(PageJSInterface.SERVICEBASESECUREURL() + "userRPC/signIn?username="
                + usernameTextBox.getText() + "&password=" + passwordTextBox.getText() + "&rememberMe="
                + rememberMe.isChecked(),
                new AsyncCallback<LoginResponse>() {
                    public void onFailure(Throwable caught) {
                        clearStatus();
                        GenericDialog.showOKDialog(null,
                            "There is an error with our server!" ,
                            "We are sorry for your inconvenience.  We will direct you to another " +
                            "authentication server. Please try logging in again.",
                            new GenericDialog.ButtonClickListener() {
                                public void buttonClicked(int buttonNo) {
                                      PageJSInterface.redirect(PageJSInterface.SERVICEBASEURL() + "auth/login?username="
                                      + usernameTextBox.getText() + "&rememberMe=" + rememberMe.isChecked());
                                }
                        });

                    }

                    
                    public void onSuccess(LoginResponse result) {
                        clearStatus();
                        switch (result.getResponseType()) {
                            case BADUSERNAMEORBADPASSWORD : showLoginStatus("Invalid username or password");
                                                            break;
                            case SUCCESS                  : if (result.getRememberMe() != null)
                                                                CookieHelper.setRememberMeCookie(result.getRememberMe());
                                                            PageJSInterface.setUserProfile(result.getUserProfile());
                                                            eventHandler.onSubmitComplete(null);
                                                            break;
                        }
                    }
                });
	}
    
	@SuppressWarnings("unused")
	private void closeClicked() {
        resetContent();
        clearMessages();
        clearStatus();
		if (eventHandler != null)
			eventHandler.onWindowClosing(null);
	}
	private void showRealInputs() {
		UIObject.setVisible(dummy, false);
		UIObject.setVisible(real, true);
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				usernameTextBox.getTextBoxBase().setFocus(true);
			}});
	}

    private void showWaitingStatus() {
        loginStatus.setInnerHTML("<img src=\"" + PageJSInterface.SERVICEBASEURL() +
                "images/loadingAnimation.gif\" alt=\"loading....\"/>");
        loginStatus.setAttribute("style", "visibility: true;");
    }

    private void showLoginStatus(String message) {
        loginStatus.setInnerHTML(message);
        loginStatus.setAttribute("style", "visibility: true; background-color: gray; color: white;");
    }

    private void clearStatus() {
        loginStatus.setInnerHTML("");
        loginStatus.setAttribute("style", "visibility: hidden;");
    }
}
