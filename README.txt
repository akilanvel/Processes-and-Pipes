Name: Akilan Gnanavel
NETID: AXG180113
CS 4348.003 Project 01
Professor: Dr. Elmer Salazar


Files contained and their purpose:

-Driver.java is the Java file for the Driver program
-Encryptor.java is the Java file for the Encryptor program
-Logger.java is the Java file for the Logger program
-Project01 Writeup.pdf is the PDF file containing my writeup


How to compile and run the project:

First, if necessary, modify the Driver.java program so that when you initialize the processes, the exec() function has the paths to the Logger.java and Encryptor.java files as arguments. (They are defaulted to java Logger and java Encryptor. The 'encrypt' process needs to execute Encryptor.java and the 'logger' process needs to execute Logger.java)
Second, run the Driver.java program. It will first print out credits and the prompt for the name of the file to write logs to. You can enter something like "logs.txt".
Third, you will see the first iteration of the program loop. You can enter a command follow the associated prompts to complete the commands.
Note: If you choose to use a string from history, but change your mind, you can enter -1 to go back (as displayed on the screen).
Finally, when you are at the main menu where it shows a list of commands, you can enter quit to end the program.

In the UTD CS machine on giant.utdallas.edu, after inserting the files into the same directory, I performed these commands in order to get the program running successfully:
1. javac Driver.java
2. javac Logger.java
3. javac Encryptor.java
4. java Driver


Notes:

When you choose to use a string in the history but change your mind, entering '-1' will take you back to the screen where it asks if you want to use a string from history.
	This will be clear and obvious when you run the program.
As instructed by the professor, only encrypt/decrypt input strings and their results are saved in the history. Previously used passwords are not saved in the history.
As instructed by the professor, only valid commands entered are logged into the log file. 
As instructed by the professor, the only output that is logged is the output of the encrypt and decrypt commands.
As instructed by the professor, all encryption/decryption strings, passwords, and result strings will be considered in uppercase.
	However, when logging, the Logger may log the string as it was entered (without forcing it into uppercase).
	Even if an encryption, decryption, or password string was logged in lowercase, it was still considered in uppercase.
The Driver program sends the password and encrypt/decrypt strings to the Logger program without modification. So, if it was typed in lowercase, it'll be logged in lowercase.
	Everything else will be logged in uppercase.
The program will not read a command as valid if there are leading or trailing whitespaces.
If there are leading/trailing whitespaces in the encryption, decryption, or password string, those whitespaces will be considered part of the string.
When you are prompted whether you want to use the history, you can enter 'Y' for yes and 'N' for no.
When entering a string to encrypt, decrypt, or password, the string cannot have numerical values. The program will check for this.
For encryption string, decryption strings, and password strings, only alphabetical (A-Z) characters are allowed.


Thank you very much for your time and have a wonderful day!