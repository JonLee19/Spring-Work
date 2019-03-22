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
    		sheet.processCommand("A9 = -13.2");
            sheet.processCommand("A10 = 19.2");
            sheet.processCommand("A11 = 2.2");
            sheet.processCommand("A12 = 607.1");
            sheet.processCommand("A13 = 0.01");
            sheet.processCommand("B9 = 88.2");
            sheet.processCommand("B10 = -190.1");
            sheet.processCommand("B11 = 1.2");
            sheet.processCommand("B12 = 607.2");
            sheet.processCommand("B13 = -0.02");
         
            System.out.println(sheet.getGridText());
            System.out.println(sheet.processCommand("SoRTd A9-B13"));
    		//System.out.println(sheet.processCommand(input));
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine().trim();
    	}                                          
    	console.close();
    }
}
