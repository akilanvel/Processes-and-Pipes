/*	CS 4348.003 Project 01
 * 	Instructor: Dr. Elmer Salazar
 * 	Student/Author: Akilan Gnanavel
 * 	NET ID: AXG180113
 * 	Student ID: 2021471298
*/

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws Exception {
		try {
			// Create process to execute logger program
			// Create output stream to provide input from driver to logger
			Process logger = Runtime.getRuntime().exec("java Logger");
			OutputStream loggerOutStream = logger.getOutputStream();
			PrintStream toLogger = new PrintStream(loggerOutStream);

			// Create process to execute encryptor program
			// Create output stream to provide input from driver to logger
			// Create input stream to provide output from logger to driver
			Process encrypt = Runtime.getRuntime().exec("java Encryptor");
			InputStream encryptInStream = encrypt.getInputStream();
			OutputStream encryptOutStream = encrypt.getOutputStream();
			Scanner fromEncrypt = new Scanner(encryptInStream);
			PrintStream toEncrypt = new PrintStream(encryptOutStream);

			// Create input stream to read from the console
			InputStream inputStream = System.in;
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			Scanner inputScanner = new Scanner(inputStreamReader);

			// Print credits to console
			System.out.println("CS 4348.003 Project 01");
			System.out.println("Instructor: Dr. Elmer Salazar");
			System.out.println("Student/Author: Akilan Gnanavel");
			System.out.println("NET ID: AXG180113");
			System.out.println("Student ID: 2021471298\n");
			System.out.println(
					"Note: there cannot be random leading/trailing whitespace characters when entering commands\n");

			// Prompt for the name of the log file and feed it to the logger program
			System.out.print("Enter the name of the log file: ");
			String logFile = inputScanner.nextLine();
			toLogger.println(logFile);

			// Print out main menu of commands to the console
			// Prompt the user for the next command
			System.out.println("\nList of Commands:");
			System.out.println("password: ");
			System.out.println("encrypt: ");
			System.out.println("decrypt: ");
			System.out.println("history: ");
			System.out.println("quit: \n");
			System.out.print("Enter your command: ");
			String command = inputScanner.nextLine();
			toEncrypt.flush();

			// The ArrayList to store strings in history
			ArrayList<String> history = new ArrayList<String>();

			// Driver program execution loop (executes until the quit command is issued)
			while (!command.equalsIgnoreCase("quit")) {
				boolean alr = false;
				boolean goBack = false; // This labels whether the user chose to go back after choosing history
				if (isValid(command)) { // Execute the command only if it is a valid command
					boolean done = false; // This labels whether the user entered an invalid history option
					if (command.equalsIgnoreCase("encrypt") || command.equalsIgnoreCase("decrypt")) {
						// Determine whether the user wants to encrypt/decrypt with history
						System.out.println("Would you like to use a string from the history? (Y/N)");
						String decision = inputScanner.nextLine();

						if (decision.equalsIgnoreCase("Y") || decision.equalsIgnoreCase("yes")) {
							// Since user wants to use history, show the history and prompt for selection
							showHistory(history);
							System.out.println("Enter the number of the string you would like to use in the history: ");
							System.out.println("OR enter -1 if you want to go back");
							try {
								int index = inputScanner.nextInt();
								inputScanner.nextLine();
								alr = true;
								if (index == -1) {
									// Re-execute driver program loop with same command if user goes back
									System.out.println("You have been sent back, the command has not executed");
									done = true;
									goBack = true;
								} else {
									// Feed command to the encryptor program if the user chooses a history string
									toEncrypt.println(command + " " + history.get(index));
									toEncrypt.flush();
									toLogger.println(command + " " + history.get(index));
									toLogger.flush();
								}
							} catch (Exception e) { // Reach this if user entered an invalid number or not a number
								if (!alr) {
									inputScanner.nextLine();
								}
								System.out.println("Invalid number. You have been sent back to the main menu.");
								done = true;
							}
							if (!done) {
								// If the user chose a history string, get the output string
								String output = fromEncrypt.nextLine();
								System.out.println(output);
								toLogger.println(output);
								toLogger.flush();
								// Add the string to the history if it isn't already
								if (!history.contains(output.substring(output.indexOf(" ") + 1))
										&& !output.equals("ERROR passkey not set")) {
									history.add(output.substring(output.indexOf(" ") + 1).toUpperCase());
								}
							}
						} else if (decision.equalsIgnoreCase("N") || decision.equalsIgnoreCase("no")) {
							// Since user doens't want to user history, prompt to enter a string
							System.out.print("Enter the string: ");
							String string = inputScanner.nextLine();
							if (hasWeirdChars(string)) {
								System.out.println("That string has invalid characters.");
								System.out.println("Only alphabetical characters are allowed");
								System.out.println("You have been returned to the main menu.");
							} else {
								toEncrypt.println(command + " " + string);
								toEncrypt.flush();

								// If the history doesn't already contain it, add it
								if (!history.contains(string)) {
									history.add(string.toUpperCase());
								}

								// Feed the command to the encryptor program
								toLogger.println(command + " " + string);
								toLogger.flush(); // may have to remove

								// Get the output string and add it to the history if not already
								String output = fromEncrypt.nextLine();
								System.out.println(output);
								toLogger.println(output);
								toLogger.flush();
								if (!history.contains(output.substring(output.indexOf(" ") + 1))
										&& !output.equals("ERROR passkey not set")) {
									history.add(output.substring(output.indexOf(" ") + 1).toUpperCase());
								}
							}
						} else {
							System.out.println("Invalid decision. Try again from the beginning");
						}
					} else if (command.equalsIgnoreCase("password")) {
						// Determine whether the user wants to use a string from history
						System.out.println("Would you like to use a string from the history? (Y/N)");
						String decision = inputScanner.nextLine();

						if (decision.equalsIgnoreCase("Y") || decision.equalsIgnoreCase("yes")) {
							// Since user wants to use history, show the history and prompt for selection
							showHistory(history);
							System.out.println("Enter the number of the string you would like to use in the history:");
							System.out.println("OR enter -1 if you want to go back:");
							try {
								int index = inputScanner.nextInt();
								inputScanner.nextLine();
								alr = true;
								// Re-execute driver program loop with same command if user goes back
								if (index == -1) {
									System.out.println("You have been sent back, the command has not executed");
									done = true;
									goBack = true;
								} else {
									// Feed command to the encryptor program if the user chooses a history string
									toEncrypt.println("passkey " + history.get(index));
									toEncrypt.flush();
									toLogger.println(command + " " + history.get(index));
									toLogger.flush(); // may have to remove
								}
							} catch (Exception e) { // Reach this if user entered an invalid number or not a number
								if (!alr) {
									inputScanner.nextLine();
								}
								System.out.println("Invalid number. You have been sent back to the main menu.");
								done = true;
							}
							if (!done) {
								// If the user chose a history string, get the output string
								String output = fromEncrypt.nextLine();
								System.out.println(output);
							}
						} else if (decision.equalsIgnoreCase("N") || decision.equalsIgnoreCase("no")) {
							// Since user doens't want to user history, prompt to enter a string
							System.out.print("Enter the string: ");
							String string = inputScanner.nextLine();
							if (hasWeirdChars(string)) {
								System.out.println("That string has invalid characters.");
								System.out.println("Only alphabetical characters are allowed");
								System.out.println("You have been returned to the main menu.");
							} else {
								// Feed the command to the encryptor program
								toEncrypt.println("passkey " + string);
								toEncrypt.flush();
								toLogger.println(command + " " + string);
								toLogger.flush();

								// Get the output string
								String output = fromEncrypt.nextLine();
								System.out.println(output);
							}
						} else {
							System.out.println("Invalid decision. Try again");
						}
					} else if (command.equalsIgnoreCase("history")) {
						// If user entered history, show the history
						showHistory(history);
						toLogger.println(command);
						toLogger.flush();
					}

					// If the user didn't choose the go back option from history, continue loop
					if (!goBack) {
						// Print out main menu of commands to the console
						// Prompt the user for the next command
						System.out.println("\nList of Commands:");
						System.out.println("password: ");
						System.out.println("encrypt: ");
						System.out.println("decrypt: ");
						System.out.println("history: ");
						System.out.println("quit: ");
						System.out.print("Enter your command: ");
						command = inputScanner.nextLine();
					}
				} else {
					// Since the command wasn't valid, inform the user
					System.out.println("Invalid command");
					System.out.println("Please try again");

					// Print out main menu of commands to the console
					// Prompt the user for the next command
					System.out.println("\nList of Commands:");
					System.out.println("password: ");
					System.out.println("encrypt: ");
					System.out.println("decrypt: ");
					System.out.println("history: ");
					System.out.println("quit: ");
					System.out.print("Enter your command: ");
					command = inputScanner.nextLine();
				}
			}

			// Notify user of program termination
			System.out.println("\n\nTHE PROGRAM HAS TERMINATED");

			// Close associated processes and pipes
			toEncrypt.println("quit");
			toLogger.println("quit");
			toEncrypt.close();
			toLogger.close();
			fromEncrypt.close();
			toEncrypt.flush();
			toLogger.flush();
			inputScanner.close();

		} catch (IOException e) {
			System.out.println("Unable to run");
		}
	}

	// This method determines whether an entered command follows the guidelines
	public static boolean isValid(String command) {
		if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("history")
				|| command.equalsIgnoreCase("password") || command.equalsIgnoreCase("encrypt")
				|| command.equalsIgnoreCase("decrypt")) {
			return true;
		}
		return false;
	}

	// This method prints out the history in a readable format
	public static void showHistory(ArrayList<String> history) {
		System.out.println("History:\n");
		for (int i = 0; i < history.size(); i++) {
			System.out.println(i + " - " + history.get(i));
		}
		System.out.println();
	}

	// This method returns whether the entered string has a numerical value in it
	public static boolean hasNumbers(String string) {
		for (int i = 0; i < string.length(); i++) {
			try {
				// If converting the String to an Integer does not throw an error, it's a number
				Integer.parseInt(string.substring(i, i + 1));
				return true;
			} catch (Exception e) {

			}
		}
		return false;
	}

	// This method returns whether the entered string has non-alphabet characters
	public static boolean hasWeirdChars(String string) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			if ((int) string.charAt(i) < 65 || (int) string.charAt(i) > 90) {
				if (!string.substring(i, i + 1).equals(" ")) {
					return true;
				}
			}
		}
		return false;
	}
}
