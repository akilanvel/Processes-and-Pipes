/*	CS 4348.003 Project 01
 * 	Instructor: Dr. Elmer Salazar
 * 	Student/Author: Akilan Gnanavel
 * 	NET ID: AXG180113
 * 	Student ID: 2021471298
*/

import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	public static void main(String[] args) throws Exception {
		// Open the input stream to scan console input
		InputStream inputStream = System.in;
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		Scanner inputScanner = new Scanner(inputStreamReader);

		// Read in the input file name (which should come from Driver)
		String logFile = "";
		if (inputScanner.hasNextLine()) {
			logFile = inputScanner.nextLine();
		}

		// Create a FileWriter and start logging to the given file name
		FileWriter writer = new FileWriter(logFile, true);
		writer.write(logMessage("START Logging Started"));

		// Run the program loop and keep logging every command that is received
		String input = inputScanner.nextLine();
		while (!input.equalsIgnoreCase("quit")) {
			writer.write(logMessage(input));
			writer.flush();
			if (input.equalsIgnoreCase("quit")) {
				break;
			}
			input = inputScanner.nextLine();
		}

		// End the logging by logging the specified end message
		writer.write(logMessage("STOP Logging Stopped"));

		// Close the associated writers and scanners
		writer.close();
		inputScanner.close();
	}

	// This message converts a command from Driver program to apprpriate log format
	public static String logMessage(String command) {
		// Determine if command has arguments and handle it differently based on that
		boolean large = false;
		if (command.split(" ").length > 1) {
			large = true;
		}

		if (!large) { // If the command has an argument, log the argument too
			// Include date and time in the log message
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " [" + command.toUpperCase() + "]"
					+ "\n";
		} else { // If there is no argument, log just the command
			// Include data and time in the log message
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " ["
					+ command.split(" ")[0].toUpperCase() + "]" + command.substring(command.indexOf(" ")) + "\n";
		}
	}
}
