package com.pdmaf.ui.gwt.client.utils;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 2:01:26 PM
 *
 */
public class LengthValidator implements Validator {
	private String fieldName;
	private int minLength;
	private int maxLength;

	public LengthValidator(String fieldName, int minLength, int maxLength) {
		this.fieldName = fieldName;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public String validationMessage(String value) {
		value = value.trim();
		if (value.length() < minLength || value.length() > maxLength)
			return fieldName + " must be between " + minLength +
				" and " + maxLength + " characters.";
		else
			return null;
	}
}