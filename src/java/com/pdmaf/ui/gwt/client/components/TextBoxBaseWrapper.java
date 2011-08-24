package com.pdmaf.ui.gwt.client.components;

import com.pdmaf.ui.gwt.client.utils.CustomTextArea;
import com.pdmaf.ui.gwt.client.utils.TooltipFactory;
import com.pdmaf.ui.gwt.client.utils.Validator;
import com.pdmaf.ui.gwt.client.utils.Tooltip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.*;


/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 1:46:37 PM
 * 
 */

public class TextBoxBaseWrapper implements ValidatingTextInput, FocusHandler,
        KeyPressHandler, BlurHandler {
	private static final String INSTRUCTIONAL = "instructional";

	private TextBoxBase textBoxBase;
	private String instructionalMessage = null;
	private boolean inInstructionalState = false;
	private String onFocusMessage = null;
	private Validator[] validators = null;
	private Tooltip onFocusTooltip = null;
	private Tooltip validationTooltip = null;
	private FocusHandler onFocusMessageHandler = null;
    private BlurHandler onBlurMessageHandler = null;
	private static TooltipFactory tooltipFactory;

	public TextBoxBaseWrapper(TextBoxBase textBoxBase) {
		this.textBoxBase = textBoxBase;
		this.textBoxBase.addFocusHandler(this);
        this.textBoxBase.addBlurHandler(this);
        this.textBoxBase.addKeyPressHandler(this);
	}
	public static TextBoxBaseWrapper wrapTextBox(String elementID) {
		return wrapTextBox(DOM.getElementById(elementID));
	}
	public static TextBoxBaseWrapper wrapTextBox(Element element) {
		return new TextBoxBaseWrapper(TextBox.wrap(element));
	}
	public static TextBoxBaseWrapper wrapPasswordTextBox(String elementID) {
		return wrapPasswordTextBox(DOM.getElementById(elementID));
	}
	public static TextBoxBaseWrapper wrapPasswordTextBox(Element element) {
		return new TextBoxBaseWrapper(PasswordTextBox.wrap(element));
	}
	public static TextBoxBaseWrapper wrapTextArea(String elementID) {
		return new TextBoxBaseWrapper(CustomTextArea.wrap(DOM.getElementById(elementID)));
	}
	public static void setTooltipFactory(TooltipFactory tooltipFactory) {
		TextBoxBaseWrapper.tooltipFactory = tooltipFactory;
	}
	public TextBoxBase getTextBoxBase() {
		return textBoxBase;
	}
	public String getText() {
		return inInstructionalState ? "" : textBoxBase.getText();
	}
	public void setText(String text) {
		textBoxBase.setText(text);
	}
    

    public void onFocus(FocusEvent event) {
		if (inInstructionalState)
			setInstructionalState(false);
		if (onFocusMessage != null)
			showOnFocusMessage(onFocusMessage);
	}


    public void onKeyPress(KeyPressEvent event) {
        hideValidationMessage();
    }

   
    public void onBlur(BlurEvent event) {
       hideOnFocusMessage();
		if (textBoxBase.getText().trim().length() == 0 && instructionalMessage != null)
			setInstructionalState(true);
		if (!inInstructionalState && textBoxBase.getText().trim().length() > 0)
			validateNow();
    }
	public void setInstructionalMessage(String message) {
		this.instructionalMessage = message;
		if (textBoxBase.getText().trim().length() == 0)
			setInstructionalState(true);
	}

	/**
	 * The message will be displayed when the TextBoxBase has focus,
	 * and then hidden when it doesn't have focus. Useful for Location.
	 * @param message
	 */
	public void setOnFocusMessage(String message, FocusHandler focusHandler) {
		this.onFocusMessage = message;
		this.onFocusMessageHandler  = focusHandler;
	}

    public void setOnBlurMessage(BlurHandler blurHandler) {
		this.onBlurMessageHandler  = blurHandler;
	}

	public void setValidators(Validator[] validators) {
		this.validators = validators;
	}
	public void setValidator(Validator validator) {
		setValidators(new Validator[] { validator });
	}

    public void showMessage(String message, boolean isHTML) {
		showValidationMessage(message, isHTML);
	}
	/**
	 *
	 * @return true if valid, false if not.
	 */
	public boolean validateNow() {
		clearMessages();
		String message = null;
		if (validators != null) {
			for (Validator v : validators)
				if ((message = v.validationMessage(getText())) != null) {
					showValidationMessage(message, false);
					return false;
				}
			return true;
		}
		else
			return true;
	}
	private void setInstructionalState(boolean inState) {
		textBoxBase.setText(inState ? instructionalMessage : "");
		inInstructionalState = inState;
		if (inState)
			textBoxBase.addStyleName(INSTRUCTIONAL);
		else
			textBoxBase.removeStyleName(INSTRUCTIONAL);
	}
	/**
	 * Clears all messages. Useful for when a popup is hidden.
	 */
	public void clearMessages() {
		hideOnFocusMessage();
		hideValidationMessage();
	}
	private void showOnFocusMessage(String message) {
		// TODO: think about what we should do with existing messages.
		// right now we just hide them.
		clearMessages();
		if (onFocusMessageHandler != null) {
            onFocusMessageHandler.onFocus(null);
        }
		onFocusTooltip = tooltipFactory.show(textBoxBase, message, false);
	}
	private void showValidationMessage(String message, boolean isHTML) {
		// TODO: think about what we should do with existing messages.
		// right now we just hide them.
		clearMessages();
		validationTooltip = tooltipFactory.show(textBoxBase, message, isHTML);
	}
	private void hideOnFocusMessage() {
		if (onFocusTooltip != null) {
			onFocusTooltip.hide();
			if (onBlurMessageHandler != null)
				onBlurMessageHandler.onBlur(null);
		}
		onFocusTooltip = null;
	}
	private void hideValidationMessage() {
		if (validationTooltip != null)
			validationTooltip.hide();
		validationTooltip = null;
	}
	public int getAbsoluteTop() {
		return textBoxBase.getAbsoluteTop();
	}
	public void setReadOnly(boolean readOnly) {
		textBoxBase.setReadOnly(readOnly);
	}
}

