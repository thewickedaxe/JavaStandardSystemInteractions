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
	
	/**
	 * Default Constructor.
	 */
	public Command() {
		setStatus(ProcessCode.UNKNOWN);
	}

	/* ******************************************************** */
	
	/**
	 * Constructor whic allows command instantiation.
	 * @param command the command to execute
	 */
	public Command(String command) {
		setCommand(command);
		setStatus(ProcessCode.UNKNOWN);
	}
	
	/* ******************************************************** */

	/**
	 * Returns the status code.
	 * @return Process' status code
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
	 * @return the command to be executed or executed
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
	 * @return  an array of newline separated strings which is the process' output
	 */
	public String[] getOutput() {
		return m_outputContents;
	}

	/* ******************************************************** */

	/**
	 * Set's the output stream contents as an array of Strings (not advisable but convenient for unit tests)
	 */
	public void setOutput(final String[] contents) {
		m_outputContents = contents;
	}

	/* ******************************************************** */

	/**
	 * Returns the error stream contents as an array of Strings
	 * @return  an array of newline separated strings which is the process' error stream
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
