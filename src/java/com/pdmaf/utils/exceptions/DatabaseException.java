package com.pdmaf.utils.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 3, 2009
 * Time: 4:03:18 PM
 */
public class DatabaseException extends RuntimeException {

	/**
	 *
	 */
	public DatabaseException() {}

	/**
	 * @param arg0
	 */
	public DatabaseException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public DatabaseException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DatabaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
