package com.pdmaf.ui.gwt.client.utils;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 2:05:44 PM
 * 
 */
public class RegexValidator implements Validator {
	public static final String EMAILREGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
	public static final String URLREGEXNOBEGEND = "http(s?)://[a-zA-Z0-9@:%_+*~#?&=.,/;-]*[a-zA-Z0-9@:%_+*~#?&=/-]";
	public static final String URLREGEX = "^" + URLREGEXNOBEGEND + "$";

	private String fieldName;
	private String regex;
	private String customMessage = null;

	private RegexValidator() {}

	public static RegexValidator createGeneric(String fieldName, String regex) {
		RegexValidator v = new RegexValidator();
		v.fieldName = fieldName;
		v.regex = regex;
		return v;
	}

	public static RegexValidator createCustom(String customMessage, String regex) {
		RegexValidator v = new RegexValidator();
		v.customMessage = customMessage;
		v.regex = regex;
		return v;
	}

	public String validationMessage(String value) {
		if (!value.matches(regex)) {
			if (customMessage != null)
				return customMessage;
			else
				return fieldName + " contains invalid characters.";
		}
		else
			return null;
	}
}
