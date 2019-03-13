package textExcel;
//This class contains client code that uses the spreadsheet class in a user interface program
//@author Jon Lee
//@version March 4, 2019
import java.io.FileNotFoundException;
import java.util.Scanner;

import textExcel.TestsALL.TestLocation;

public class TextExcel
{
	public static void main(String[] args) {
    	Scanner console = new Scanner(System.in);
    	Spreadsheet sheet = new Spreadsheet();
    	//set up variables
    	System.out.println("Pleace print a command to run on the spreadsheet");
    	//inform the user what to input
    	String input = console.nextLine();
    	//open scanner to take in an expression
    	while (!input.equalsIgnoreCase("quit")) {
    		//repeat the loop until the user enters quit
    		//input = "F3 = ( 1 + 3 + F2 + F1 - 3 - 1 )";
    		System.out.println(sheet.processCommand(input));
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine().trim();
    	}
    	console.close();
    }
}
