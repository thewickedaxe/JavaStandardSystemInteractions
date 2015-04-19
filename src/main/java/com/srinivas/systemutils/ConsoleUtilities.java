package com.srinivas.systemutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class that executes console commands and gets and puts input and output.
 * 
 * @author Srinivas
 *
 */
public class ConsoleUtilities {
	private static ProcessBuilder s_builder = new ProcessBuilder();
	private static final String LINE_SEPARATOR = "\n";
	private static final File ORIGIN_DIR = new File(
			System.getProperty("user.dir"));

	/* ******************************************************** */
	/**
	 * Sets the working directory to execute commands in.
	 * Makes the directory if it doesn't exist.
	 * @param path the path to the directory
	 */
	public static void setWorkingDirectory(final String path) {
		File executionPath = new File(path);
		executionPath.mkdirs();
		s_builder.directory(executionPath);
	}
	
	/* ******************************************************** */
	/**
	 * Reset working dir
	 * Makes the directory if it doesn't exist.
	 */
	public static void resetWorkingDirectory() {
		s_builder.directory(ORIGIN_DIR);
	}
	
	/* ******************************************************** */
	/**
	 * Capture a Process output.
	 * 
	 * @param p process whose output must be caught
	 * @throws IOException when the stream could not be read
	 * @return result an Array of strings separated by the newline character
	 * containing the process output
	 */
	private static String[] gobbleOutputStream(final Process p)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			builder.append(System.getProperty("line.separator"));
		}
		String intermediateResult = builder.toString();
		String result[] = intermediateResult.split(LINE_SEPARATOR);
		return result;
	}

	/* ******************************************************** */

	/**
	 * Capture a Process error stream.
	 * 
	 * @param p process whose output must be caught
	 * @throws IOException
	 * @return result an Array of strings separated by the newline character
	 * containing the process error stream
	 */
	private static String[] gobbleErrorStream(final Process p)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			builder.append(System.getProperty("line.separator"));
		}
		String intermediateResult = builder.toString();
		String result[] = intermediateResult.split(LINE_SEPARATOR);
		return result;
	}

	/* ******************************************************** */

	/**
	 * Method will execute a system command. Fails when command writes to error
	 * stream.
	 * 
	 * @param command A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException when the process builder cannot execute the command
	 */
	public static Command exec(final Command command) throws IOException,
			InterruptedException {
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		Process process = s_builder.start();
		process.waitFor();
		command.setOutput(gobbleOutputStream(process));
		command.setErrorContents(gobbleErrorStream(process));
		if (command.getErrorContents().length > 1) {
			command.setStatus(ProcessCode.FAILURE);
		} else {
			command.setStatus(ProcessCode.SUCCESS);
		}
		return command;
	}

	/* ******************************************************** */

	/**
	 * Method will execute a system command. Error stream is not a failure
	 * criterion.
	 * 
	 * @param command A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException when the process builder cannot execute the command
	 */
	public static Command execDisregardingErrorStream(final Command command)
			throws IOException, InterruptedException {
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		Process process = s_builder.start();
		process.waitFor();
		command.setOutput(gobbleOutputStream(process));
		command.setErrorContents(gobbleErrorStream(process));
		return command;
	}

	/* ******************************************************** */

	/**
	 * Method will execute a system command. Reads streams before process
	 * finishes. May result in race conditions.
	 * 
	 * @param command A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException when the process builder cannot execute the command
	 */
	public static Command execUnSafe(final Command command) throws IOException,
			InterruptedException {
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		Process process = s_builder.start();
		command.setOutput(gobbleOutputStream(process));
		command.setErrorContents(gobbleErrorStream(process));
		if (command.getErrorContents().length > 0) {
			command.setStatus(ProcessCode.FAILURE);
		} else {
			command.setStatus(ProcessCode.SUCCESS);
		}
		return command;
	}

	/* ******************************************************** */

	/**
	 * Method will execute a system command. Reads streams before process
	 * finishes. May result in race conditions. Disregards Error stream as a
	 * criterion.
	 * 
	 * @param command A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException when the process builder cannot execute the command
	 */
	public static Command execUnSafeDisregardingErrorStream(
			final Command command) throws IOException, InterruptedException {
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		Process process = s_builder.start();
		command.setOutput(gobbleOutputStream(process));
		command.setErrorContents(gobbleErrorStream(process));
		return command;
	}

	/* ******************************************************** */

	/**
	 * Write to standard output.
	 * 
	 * @param message the string to output
	 */
	// TODO: add verification
	public static void sOut(final String message) {
		System.out.println(message);
	}
	
	/* ******************************************************** */

	/**
	 * Write to standard output. No newline character.
	 * 
	 * @param message the string to output
	 */
	// TODO: add verification
	public static void sDump(final String message) {
		System.out.print(message);
	}

	/* ******************************************************** */

	/**
	 * Write to standard error.
	 * 
	 * @param message the string to output
	 */
	// TODO: add verification
	public static void sErr(final String message) {
		System.err.println(message);
	}

	/* ******************************************************** */

	/**
	 * Reads some input. It's up to the user to cast it to the write type. This
	 * provides more flexibility.
	 * 
	 * @return inputVal a string containing the values the user input
	 * @throws IOException when the input stream could not be read.
	 */
	public static String sIn() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputVal = br.readLine();
		return inputVal;
	}
}
