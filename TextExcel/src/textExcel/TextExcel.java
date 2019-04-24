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
    		sheet.processCommand("A1 = 1");
            String before = sheet.processCommand("A2 = 2");
            String error1 = sheet.processCommand("A3 = 5 + 2");
            String error2 = sheet.processCommand("A4 = ( avs A1-A2 )");
            String error3 = sheet.processCommand("A5 = ( sum A0-A2 )");
            String error4 = sheet.processCommand("A6 = ( 1 + 2");
            String error5 = sheet.processCommand("A7 = ( avg A1-B )");
            String error6 = sheet.processCommand("A8 = M80");
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
