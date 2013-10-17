SIMPLE SHELL:
	The purpose of this assignment was to create a simple shell that will 
	execute a command by the user in a separate process.



* Resource: http://www.java-tips.org/java-se-tips/java.util/from-runtime.exec-to-processbuilder.html

Design Choice Outline:

Create a loop for shell and check for user input
Use Scanner to take in user input
if input is "exit", then program "myshell" exited.
if input is "", then go to the next line and await for next input.
if input is a command, then make a new child process with Runtime and Process class
	then InputStream, InputStreamReader, and BufferedReader objects are used to get process output
	and then we print out the string(s) to the console. This was surrounded with a try-catch in
	case the command provided was not valid.