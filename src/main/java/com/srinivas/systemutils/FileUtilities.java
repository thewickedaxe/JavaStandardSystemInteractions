package com.srinivas.systemutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;

import com.srinivas.systemutils.exceptions.SystemInteractionException;

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
	 * @param path
	 *            path to file to write to.
	 * @param message
	 *            content to write to file
	 * @throws SystemInteractionException
	 *             when the required file does not exist
	 */
	public static void writeToFile(final String path, final String message)
			throws SystemInteractionException {
		try {
			s_writer = new PrintWriter(path);
			s_writer.println(message);
			s_writer.close();
		} catch (FileNotFoundException e) {
			throw new SystemInteractionException(
					"Could not find the file to write to");
		}
	}

	/* ******************************************************** */
	/**
	 * Appends to a file.
	 * 
	 * @param path
	 *            path to file to write to.
	 * @param message
	 *            content to write to file
	 * @throws SystemInteractionException
	 *             when the required file does not exist
	 */
	public static void appendToFile(final String path, final String message)
			throws SystemInteractionException {
		try {
			s_writer = new PrintWriter(new FileOutputStream(path, true));
			s_writer.println(message);
			s_writer.close();
		} catch (FileNotFoundException e) {
			throw new SystemInteractionException(
					"Could not find file to append to");
		}
	}

	/* ******************************************************** */
	/**
	 * Read from a file.
	 * 
	 * @param path
	 *            path to file to write to.
	 * @throws SystemInteractionException
	 *             when the file could not be read
	 * @return an Array of newline separated strings with the file contents
	 */
	public static String[] readFromFile(final String path)
			throws SystemInteractionException {
		String fileContentsDump = null;
		try {
			FileInputStream inputStream = new FileInputStream(path);
			try {
				fileContentsDump = IOUtils.toString(inputStream);
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			throw new SystemInteractionException(
					"Problem reading fromthis file, it might not exist");
		}
		String contents[] = fileContentsDump.split("\n");
		return contents;
	}
}
