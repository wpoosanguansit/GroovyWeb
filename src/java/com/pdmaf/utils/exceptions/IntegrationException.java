/**
 * 
 */
package com.pdmaf.utils.exceptions;

/**
 * This is to capture all of the exception that comes from the lower 
 * layer of the stack - db, ldap, etc.
 * 
 * @author watt
 *
 */
public class IntegrationException extends RuntimeException {

	/**
	 * 
	 */
	public IntegrationException() {}

	/**
	 * @param arg0
	 */
	public IntegrationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IntegrationException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IntegrationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
