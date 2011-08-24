package com.pdmaf.ui.gwt.client.utils;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 6, 2009
 * Time: 1:44:52 PM
 * 
 */
public interface Validator {
	/**
	 * Returns null if valid, non-null otherwise.
	 * @param name
	 * @param value
	 * @return
	 */
	String validationMessage(String value);
}