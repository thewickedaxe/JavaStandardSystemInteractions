package com.srinivas.systemutils;

/**
 * An exception thrown when System Interaction fails in some form or manner.
 * @author Srinivas
 *
 */
public class SystemInteractionException extends Exception{

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Generic Exception type to log.
	 */
	public SystemInteractionException(String message) {
		super(message);
	}

}
