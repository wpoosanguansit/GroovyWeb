package com.pdmaf.utils.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 3, 2009
 * Time: 4:03:30 PM
 */
public class DirectoryException extends RuntimeException {

	/**
	 *
	 */
	public DirectoryException() {}

	/**
	 * @param arg0
	 */
	public DirectoryException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public DirectoryException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DirectoryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
