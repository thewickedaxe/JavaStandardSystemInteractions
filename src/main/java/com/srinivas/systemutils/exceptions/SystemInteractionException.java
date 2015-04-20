package com.srinivas.systemutils.exceptions;

/**
 * An exception thrown when System Interaction fails in some form or manner. A
 * generic interaction class for you to throw and catch when a command you want
 * to execute returns a failure code.
 * 
 * @author Srinivas
 *
 */
public class SystemInteractionException extends Exception {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Generic Exception type to log.
	 */
	public SystemInteractionException(final String message) {
		super(message);
	}

}
