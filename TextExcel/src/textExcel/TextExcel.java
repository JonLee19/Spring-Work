package textExcel;
//This class contains client code that uses the spreadsheet class in a user interface program
//@author Jon Lee
//@version March 4, 2019

import java.util.Scanner;

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
    		sheet.processCommand("A1 = \"hello\"");
            sheet.processCommand("A37 = 5");
            sheet.processCommand("M1 = 3");
            sheet.processCommand("A-5 = 2");
            sheet.processCommand("A0 = 17");
            System.out.println(sheet.processCommand("lesnerize"));
            System.out.println(sheet.getGridText());
    		//input = "A1 = 3 ";
            //System.out.println(sheet.getGridText());
    		//System.out.println(sheet.processCommand(input));
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine().trim();
    	}                                          
    	console.close();
    }
}
