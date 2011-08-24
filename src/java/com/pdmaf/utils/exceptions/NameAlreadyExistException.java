package com.pdmaf.utils.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 7, 2009
 * Time: 7:21:51 AM
 */
public class NameAlreadyExistException extends RuntimeException {

	/**
	 *
	 */
	public NameAlreadyExistException() {}

	/**
	 * @param arg0
	 */
	public NameAlreadyExistException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public NameAlreadyExistException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NameAlreadyExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
