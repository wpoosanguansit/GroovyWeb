package com.pdmaf.ui.gwt.client.enums;

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 14, 2009
 * Time: 9:23:18 PM
 */
public enum SignupResponseType {
	USERNAMETAKEN(0),
	SUCCESS(1),
	EMAILTAKEN(2);

	private int id;

	private SignupResponseType(int id) {
		this.id = id;
	}

	public int getID(){
		return id;
	}
}
