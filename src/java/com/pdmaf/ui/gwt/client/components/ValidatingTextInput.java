package com.pdmaf.ui.gwt.client.components;

import com.pdmaf.ui.gwt.client.utils.Validator;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.event.dom.client.FocusHandler;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 1:49:36 PM
 * 
 */
public interface ValidatingTextInput extends HasText {
	void setOnFocusMessage(String message, FocusHandler listener);
	void showMessage(String message, boolean isHTML);
	void setValidators(Validator[] validators);
	/**
	 * Convenience method for setting just one validator.
	 * @param validator
	 */
	void setValidator(Validator validator);
	boolean validateNow();
	void clearMessages();
	void setReadOnly(boolean readOnly);
	int getAbsoluteTop();
}