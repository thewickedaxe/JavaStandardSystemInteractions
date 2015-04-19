package com.srinivas.systemutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;

/**
 * Class to easily write to and read from files.
 * 
 * @author Srinivas
 *
 */
public class FileUtilities {

	private static PrintWriter s_writer;

	/* ******************************************************** */
	/**
	 * Writes to a file.
	 * 
	 * @param path path to file to write to.
	 * @param message content to write to file
	 * @throws FileNotFoundException when the required file doesn't exist.
	 */
	public static void writeToFile(final String path, final String message)
			throws FileNotFoundException {
		s_writer = new PrintWriter(path);
		s_writer.println(message);
		s_writer.close();
	}

	/* ******************************************************** */
	/**
	 * Appends to a file.
	 * 
	 * @param path path to file to write to.
	 * @param message content to write to file
	 * @throws FileNotFoundException when the required file doesn't exist.
	 */
	public static void appendToFile(final String path, final String message)
			throws FileNotFoundException {
		s_writer = new PrintWriter(new FileOutputStream(path, true));
		s_writer.println(message);
		s_writer.close();
	}

	/* ******************************************************** */
	/**
	 * Read from a file.
	 * 
	 * @param path path to file to write to.
	 * @param message content to write to file
	 * @throws IOException when the file culd not be read
	 * @return an Array of newline separated strings with the file contents
	 */
	public static String[] readFromFile(final String path)
			throws IOException {
		String fileContentsDump = null;
		FileInputStream inputStream = new FileInputStream(path);
		try {
			fileContentsDump = IOUtils.toString(inputStream);
		} finally {
			inputStream.close();
		}
		String contents[] = fileContentsDump.split("\n");
		return contents;
	}
}
