package com.srinivas.systemutils;

/**
 * Class that defines a command object.
 * 
 * @author Srinivas
 *
 */
public class Command {
	/**
	 * The string that contains the command to execute.
	 */
	private String m_cmd = null;
	/**
	 * The string that contains output stream of the executed command.
	 */
	private String[] m_outputContents = null;
	/**
	 * The string that contains the error stream contents of the executed
	 * command.
	 */
	private String[] m_errorContents = null;
	/**
	 * The executed command's status code.
	 */
	private ProcessCode statusCode = null;
	
	/* ******************************************************** */
	
	public Command() {
		setStatus(ProcessCode.UNKNOWN);
	}

	/* ******************************************************** */
	
	public Command(String command) {
		setCommand(command);
		setStatus(ProcessCode.UNKNOWN);
	}
	
	/* ******************************************************** */

	/**
	 * Returns the status code.
	 */
	public ProcessCode getStatus() {
		return statusCode;
	}
	
	/* ******************************************************** */

	/**
	 * Set's the status code.
	 */
	public void setStatus(final ProcessCode p) {
		statusCode = p;
	}
	
	/* ******************************************************** */

	/**
	 * Returns the command (to be) executed.
	 */
	public String getCommand() {
		return m_cmd;
	}

	/* ******************************************************** */

	/**
	 * Set's the command to be executed.
	 */
	public void setCommand(final String command) {
		m_cmd = command;
	}

	/* ******************************************************** */

	/**
	 * Returns the output stream contents as an array of Strings
	 */
	public String[] getOutput() {
		return m_outputContents;
	}

	/* ******************************************************** */

	/**
	 * Set's the output stream contents as an array of Strings
	 */
	public void setOutput(final String[] contents) {
		m_outputContents = contents;
	}

	/* ******************************************************** */

	/**
	 * Returns the error stream contents as an array of Strings
	 */
	public String[] getErrorContents() {
		return m_errorContents;
	}

	/* ******************************************************** */

	/**
	 * Set's the output stream contents as an array of Strings
	 */
	public void setErrorContents(final String[] contents) {
		m_errorContents = contents;
	}
}
