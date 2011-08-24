package com.pdmaf.utils.exceptions;

/**
 * This is to capture the exception that comes from the directory
 * specifically on bad credential - wrong username or password LDAP
 * error code 49.
 *
 * @author watt
 *
 */
public class LoginException extends RuntimeException {

	/**
	 *
	 */
	public LoginException() {}

	/**
	 * @param arg0
	 */
	public LoginException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public LoginException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public LoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
