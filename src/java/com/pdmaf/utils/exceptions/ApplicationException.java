package com.pdmaf.utils.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 2, 2009
 * Time: 3:52:10 PM
 */
public class ApplicationException extends RuntimeException {

	/**
	 *
	 */
	public ApplicationException() {}

	/**
	 * @param arg0
	 */
	public ApplicationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ApplicationException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ApplicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
