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
    		/*input = "A1 = -9";
    		System.out.println(sheet.processCommand(input));
    		input = "A2 = ( 14 - 7 + -4 - 3 + 3 * A1 )";
    		System.out.println(sheet.processCommand(input));
    		input = "B1 = ( avG A2-a2 )";
    		System.out.println(sheet.processCommand(input));
    		*/
    		 sheet.processCommand("F8 = 3");
             sheet.processCommand("G8 = ( 5 )");
             System.out.println(sheet.getGridText());
             sheet.processCommand("H8 = ( 2 * -3 + 4 - -2 + -1 * F8 + G8 )"); // 2
             System.out.println(sheet.getGridText());
             sheet.processCommand("I8 = ( sum F8-H8 )"); // 10
             System.out.println(sheet.getGridText());
             sheet.processCommand("J8 = ( AVG F8-I8 )"); // 5
             System.out.println(sheet.getGridText());
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine().trim();
    	}                                          
    	console.close();
    }
}
