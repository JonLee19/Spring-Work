package textExcel;
//This class contains client code that uses the spreadsheet class in a user interface program
//@author Jon Lee
//@version March 4, 2019

import java.util.Scanner;

public class TextExcel
{
	public static void main(String[] args) {
		//set up variables
    	Scanner console = new Scanner(System.in);
    	Spreadsheet sheet = new Spreadsheet();
    	//inform the user what to input
    	System.out.println("Pleace print a command to run on the spreadsheet");
    	//open scanner to take in an expression
    	String input = console.nextLine();
    	//repeat the loop until the user enters quit
    	while (!input.equalsIgnoreCase("quit")) {
    		System.out.println(sheet.processCommand(input));
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine().trim();
    	}                                          
    	console.close();
    }
}
