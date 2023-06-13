/*	CS 4348.003 Project 01
 * 	Instructor: Dr. Elmer Salazar
 * 	Student/Author: Akilan Gnanavel
 * 	NET ID: AXG180113
 * 	Student ID: 2021471298
*/

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Encryptor {
	static int[][] table = new int[26][26];

	public static void main(String[] args) throws IOException {
		// Open an input stream to read from console
		InputStream inputStream = System.in;
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		Scanner inputScanner = new Scanner(inputStreamReader);
		String passkey = null;

		// Create the vignere cipher table to read from
		int offset = 0;
		for (int i = 0; i < table.length; i++, offset++) {
			for (int x = 0; x < table[i].length; x++) {
				char letter = (char) (65 + offset + x);
				if ((int) letter > 90) {
					letter -= 26;
				}
				table[i][x] = letter;
			}
		}

		// Read the input and run the program execution loop until user chooses to quit
		String input = inputScanner.nextLine();
		while (!input.equalsIgnoreCase("quit")) {
			if (input.equalsIgnoreCase("quit")) {
				break;
			} else if (input.substring(0, input.indexOf(" ")).equalsIgnoreCase("encrypt")) {
				// Run encryption if there is a passkey set
				if (passkey == null) {
					System.out.println("ERROR passkey not set");
				} else {
					String ciphertext = encrypt(input.substring(input.indexOf(" ") + 1).toUpperCase(),
							passkey.toUpperCase());
					System.out.println("RESULT " + ciphertext);
				}
			} else if (input.substring(0, input.indexOf(" ")).equalsIgnoreCase("decrypt")) {
				// Run decryption if there is a passkey set
				if (passkey == null) {
					System.out.println("ERROR Password not set");
				} else {
					String plaintext = decrypt(input.substring(input.indexOf(" ") + 1).toUpperCase(),
							passkey.toUpperCase());
					System.out.println("RESULT " + plaintext);
				}
			} else if (input.substring(0, input.indexOf(" ")).equalsIgnoreCase("passkey")) {
				// Set a passkey
				passkey = input.substring(input.indexOf(" ") + 1).toUpperCase();
				System.out.println("RESULT");
			}
			input = inputScanner.nextLine();
		}

		// Close associated scanner
		inputScanner.close();
	}

	// This method encrypts a message using the given passkey using a Vignere cipher
	public static String encrypt(String message, String passkey) throws IOException {
		String ciphertext = "";
		// Stretch the passkey circularly to be long enough to encrypt the message
		passkey = stretchPasskey(message, passkey);

		// Encrypt each letter of the message one-by-one
		for (int i = 0, x = 0; i < message.length(); i++) {
			if (message.substring(i, i + 1).equals(" ")) { // Do not encrypt spaces
				ciphertext += " ";
			} else {
				int row = ((int) (passkey.substring(x, x + 1).toUpperCase().charAt(0))) - 65;
				int col = ((int) (message.substring(i, i + 1).toUpperCase().charAt(0))) - 65;
				ciphertext += (char) (table[row][col]) + "";
				x++;
			}
		}

		// Return the generated cipher text
		return ciphertext;
	}

	// This method decrypts a message using the given passkey using a Vignere cipher
	public static String decrypt(String message, String passkey) {
		String plaintext = "";
		// Stretch the passkey circularly to be long enough to decrypt the message
		passkey = stretchPasskey(message, passkey);

		// Decrypt each letter of the message one-by-one
		for (int i = 0, y = 0; i < message.length(); i++) {
			if (message.substring(i, i + 1).equals(" ")) { // Do not decrypt spaces
				plaintext += " ";
			} else {
				int row = ((int) (passkey.substring(y, y + 1).toUpperCase().charAt(0))) - 65;
				int col = -1;
				for (int x = 0; x < table[row].length; x++) {
					if (String.valueOf((char) (table[row][x])).equalsIgnoreCase(message.substring(i, i + 1))) {
						col = x;
						break;
					}
				}
				plaintext += (char) (col + 65) + "";
				y++;
			}
		}

		// Return the generated plain text
		return plaintext;
	}

	// This method circularly stretches passkey to reach length of message
	public static String stretchPasskey(String message, String passkey) {
		String newPasskey = "";

		// Remove spaces from the passkey
		for (int i = 0; i < passkey.split(" ").length; i++) {
			newPasskey += passkey.split(" ")[i];
		}
		int i = 0;

		// Circularly wrap the passkey until it is the lenght of the message
		while (newPasskey.length() < message.length()) {
			newPasskey += newPasskey.substring(i, i + 1);
			i++;
			if (i == passkey.length()) {
				i = 0;
			}
		}

		// Return the stretched passkey
		return newPasskey;
	}
}
