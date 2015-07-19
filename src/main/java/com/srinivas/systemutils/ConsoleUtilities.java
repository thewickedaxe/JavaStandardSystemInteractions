package com.srinivas.systemutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.srinivas.systemutils.exceptions.SystemInteractionException;

/**
 * Class that executes console commands and gets and puts input and output.
 * 
 * @author Srinivas
 *    v
 */
public class ConsoleUtilities {
<<<<<<< HEAD
    private static ProcessBuilder s_builder = new ProcessBuilder();
    private static final String LINE_SEPARATOR = "\n";
    private static final File ORIGIN_DIR = new File(
            System.getProperty("user.dir"));
    
    /* ******************************************************** */    
    /**
     * Checks correctness of String parameters.
     * @param value the String that must be verified
     */
    private static void checkParam(final String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
    }

    /* ******************************************************** */    
    /**
     * Sets the working directory to execute commands in.
     * Makes the directory if it doesn't exist.
     * @param path the path to the directory
     */
    public static void setWorkingDirectory(final String path) {
        checkParam(path);
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
     * @param process process whose output must be caught
     * @throws IOException when the stream could not be read
     * @return result an Array of strings separated by the newline character
     * containing the process output
     */
    private static String[] gobbleOutputStream(final Process process)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
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
     * @param process process whose output must be caught
     * @throws IOException
     * @return result an Array of strings separated by the newline character
     * containing the process error stream
     */
    private static String[] gobbleErrorStream(final Process process)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getErrorStream()));
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
        checkParam(command.getCommand());
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
        checkParam(command.getCommand());
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
        checkParam(command.getCommand());
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
        checkParam(command.getCommand());
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
        checkParam(message);
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
        checkParam(message);
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
        checkParam(message);
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
=======
	private static ProcessBuilder s_builder = new ProcessBuilder();
	private static final String LINE_SEPARATOR = "\n";
	private static final File ORIGIN_DIR = new File(
			System.getProperty("user.dir"));

	/* ******************************************************** */
	/**
	 * Checks correctness of String parameters.
	 * 
	 * @param value
	 *            the String that must be verified
	 */
	private static void checkParam(final String value) {
		if (value == null || value.equals("")) {
			throw new IllegalArgumentException("Value cannot be null or empty");
		}
	}

	/* ******************************************************** */
	/**
	 * Sets the working directory to execute commands in. Makes the directory if
	 * it doesn't exist.
	 * 
	 * @param path
	 *            the path to the directory
	 */
	public static void setWorkingDirectory(final String path) {
		checkParam(path);
		File executionPath = new File(path);
		executionPath.mkdirs();
		s_builder.directory(executionPath);
	}

	/* ******************************************************** */
	/**
	 * Reset working dir Makes the directory if it doesn't exist.
	 */
	public static void resetWorkingDirectory() {
		s_builder.directory(ORIGIN_DIR);
	}

	/* ******************************************************** */
	/**
	 * Capture a Process output.
	 * 
	 * @param process
	 *            process whose output must be caught
	 * @throws IOException
	 *             when the stream could not be read
	 * @return result an Array of strings separated by the newline character
	 *         containing the process output
	 */
	private static String[] gobbleOutputStream(final Process process)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
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
	 * @param process
	 *            process whose output must be caught
	 * @throws IOException
	 * @return result an Array of strings separated by the newline character
	 *         containing the process error stream
	 */
	private static String[] gobbleErrorStream(final Process process)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
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
	 * @param command
	 *            A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException
	 *             when the process builder cannot execute the command
	 * @throws SystemInteractionException
	 */
	public static Command exec(final Command command)
			throws SystemInteractionException {
		checkParam(command.getCommand());
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		try {
			Process process = s_builder.start();
			process.waitFor();
			command.setOutput(gobbleOutputStream(process));
			command.setErrorContents(gobbleErrorStream(process));
		} catch (IOException | InterruptedException e) {
			throw new SystemInteractionException("Error executing command");
		}
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
	 * @param command
	 *            A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException
	 *             when the process builder cannot execute the command
	 * @throws SystemInteractionException
	 */
	public static Command execDisregardingErrorStream(final Command command)
			throws SystemInteractionException {
		checkParam(command.getCommand());
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		try {
			Process process = s_builder.start();
			process.waitFor();
			command.setOutput(gobbleOutputStream(process));
			command.setErrorContents(gobbleErrorStream(process));
		} catch (IOException | InterruptedException e) {
			throw new SystemInteractionException("Error executing command");
		}
		return command;
	}

	/* ******************************************************** */

	/**
	 * Method will execute a system command. Reads streams before process
	 * finishes. May result in race conditions.
	 * 
	 * @param command
	 *            A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws IOException
	 *             when the process builder cannot execute the command
	 * @throws SystemInteractionException
	 */
	public static Command execUnSafe(final Command command)
			throws SystemInteractionException {
		checkParam(command.getCommand());
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		try {
			Process process = s_builder.start();
			command.setOutput(gobbleOutputStream(process));
			command.setErrorContents(gobbleErrorStream(process));
		} catch (IOException e) {
			throw new SystemInteractionException("Error executing command");
		}
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
	 * @param command
	 *            A command object to execute
	 * @return command the command with it's execution details set.
	 * @throws SystemInteractionException
	 * @throws IOException
	 *             when the process builder cannot execute the command
	 */
	public static Command execUnSafeDisregardingErrorStream(
			final Command command) throws SystemInteractionException {
		checkParam(command.getCommand());
		String cmdParts[] = command.getCommand().split(" ");
		s_builder.command(cmdParts);
		try {
			Process process = s_builder.start();
			command.setOutput(gobbleOutputStream(process));
			command.setErrorContents(gobbleErrorStream(process));
		} catch (IOException e) {
			throw new SystemInteractionException("Error executing command");
		}
		return command;
	}

	/* ******************************************************** */

	/**
	 * Write to standard output.
	 * 
	 * @param message
	 *            the string to output
	 */
	// TODO: add verification
	public static void sOut(final String message) {
		checkParam(message);
		System.out.println(message);
	}

	/* ******************************************************** */

	/**
	 * Write to standard output. No newline character.
	 * 
	 * @param message
	 *            the string to output
	 */
	// TODO: add verification
	public static void sDump(final String message) {
		checkParam(message);
		System.out.print(message);
	}

	/* ******************************************************** */

	/**
	 * Write to standard error.
	 * 
	 * @param message
	 *            the string to output
	 */
	// TODO: add verification
	public static void sErr(final String message) {
		checkParam(message);
		System.err.println(message);
	}

	/* ******************************************************** */

	/**
	 * Reads some input. It's up to the user to cast it to the write type. This
	 * provides more flexibility.
	 * 
	 * @return inputVal a string containing the values the user input
	 * @throws IOException
	 *             when the input stream could not be read.
	 */
	public static String sIn() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputVal = br.readLine();
		return inputVal;
	}
>>>>>>> 6a2e8bfcd7ece38dcf3df156e616f911e2c8e39a
}
