/*
 * Swetal Bhatt
 * Partner: Peng Gao
 * 
 * Resource: http://www.java-tips.org/java-se-tips/java.util/from-runtime.exec-to-processbuilder.html
 * Resource: http://stackoverflow.com/questions/4884681/how-to-use-cd-command-using-java-runtime
 * 		^why the cd command won't work.
 */

import java.io.*;
import java.util.Scanner;

import java.lang.Process;
import java.lang.ProcessBuilder;
import java.lang.Runtime;


public class MyShell {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Scanner s = new Scanner(System.in);
		String mysh = "mysh> ";
		String cmd;
		
		// start shell output
		System.out.print(mysh);
		
		while (s.hasNextLine()) {	
			
			// store the user provided input 
			cmd = s.nextLine();
			
			// for exiting the shell break the loop
			if (cmd.equals("exit")) break;
			
			// if user provides empty input continue shell
			else if (cmd.equals("")) {
				System.out.print(mysh);
				continue;
			}
			
			else {
				
				Runtime r = Runtime.getRuntime();
				
				// try and make sure that cmd is valid, if not print the error message
				try {
					// create the child process
					Process p = r.exec(cmd);
					
					// get the output from process p and put it into BufferedReader br
					InputStream is = p.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					
					// read process p's output from br and print
					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
					
					//System.out.println(p.exitValue());
					
					// wait for p which should have terminated on its own
					p.waitFor();
				} 
				catch (Exception e) {
					// error message as mentioned above
					System.out.println("Can't execute " + cmd + ".");
				}
				
				// continue shell output
				System.out.print(mysh);
			}
		}
		
		// only reached when the shell has been exited
		System.out.println("myshell exited.");
	}
}
