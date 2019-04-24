package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Spreadsheet implements Grid {
	
	//declare fields
	private Cell[][] sheet;
	private ArrayList<String> history;
	
	//constructor
	public Spreadsheet() {
		//initialize a 2D array of EmptyCell objects
		sheet = new Cell[20][12];
		clearAll();
	}
	
	//getters and setters (with alternates for utility)
	@Override
	public int getRows(){	
		return sheet.length;
	}

	@Override
	public int getCols(){
		return sheet[0].length;
	}

	public Cell getCell(int row, int col){
		//check if the location is within the bounds of this spreadsheet
		if (row > getRows()-1) {
			throw new ErrorException("ERROR: The given row # is outside of the bounds of the spreadsheet");
		}
		if (col > getCols()-1) {
			throw new ErrorException("ERROR: The given column # is outside of the bounds of the spreadsheet");
		}
		return sheet[row][col];
	}
	
	@Override
	public Cell getCell(Location loc){
		return getCell(loc.getRow(), loc.getCol());
	}
	
	//sets a specific location to the given cell
	public void setCell(int row, int col, Cell c){
		//check if that location is within the bounds of this spreadsheet
		if (row > getRows()-1) {
			throw new ErrorException("ERROR: The given row # is outside of the bounds of the spreadsheet");
		}
		if (col > getCols()-1) {
			throw new ErrorException("ERROR: The given column # is outside of the bounds of the spreadsheet");
		}
		sheet[row][col] = c;
	}
	
	public void setCell(Location loc, Cell c) {
		setCell(loc.getRow(), loc.getCol(), c);
	}
	
	//sets a specific location to a given value
	public void setCell(int row, int col, String value){
		setCell(row, col, makeCell(value));
	}
	
	public void setCell(Location loc, String value){
		setCell(loc.getRow(), loc.getCol(), makeCell(value));
	}
	
	public Cell[][] getSheet() {
		return sheet;
	}
	
	//equals method
    public boolean equals(Object o) {
    	if(o instanceof Spreadsheet) {
    		Spreadsheet obj = (Spreadsheet) o;
    		if (Arrays.deepEquals(sheet, obj.getSheet())) {
    			return true;
    		}
    	}
    	return false;
    }
	
	//Methods to ADD ROWS AND COLUMNS??
    
    
    
    
	//spreadsheet functions
	@Override
	//converts input into specific commands, calls those commands, and returns the result
	public String processCommand(String command){
		try {
			if (command.equals("")) { //return nothing if empty string is inputted
				return command;
			}
			if (command.length()<=3) { //proves it's an inspect-only command
				SpreadsheetLocation loc = new SpreadsheetLocation(command);
				return getCell(loc).fullCellText();
			}
			if (command.substring(0,4).equalsIgnoreCase("sort")) {
				if (command.toLowerCase().charAt(4)=='a') { //send in the command with "sorta" cut off
					return sorta(command.substring(5).trim());	
				}
				else if (command.toLowerCase().charAt(4)=='d') { //send in the command with "sortd" cut off
					return sortd(command.substring(5).trim());
				}
				else {
					throw new ErrorException("ERROR: the sorting method you called is not a valid type of sorting");
				}
			}
			String value = ""; //value to input into the cell
			if (command.substring(0,5).toLowerCase().contains("clear")) { //to clear, replace the cell(s) with an empty cell
				if (command.equalsIgnoreCase("clear")) { //clears all cells in the spreadsheet
					clearAll();
					return getGridText();
				}
				else { //clears a specific cell
					command = command.substring(5).trim();
					//removes the word "clear", "command" becomes the cell# and leave value as an empty string
				}
			}
			if (command.contains("=")) { //assignment statement
				String[] components = command.split("=",2);
				command = components[0].trim(); //the cell#
				value = components[1].trim();
			}
			SpreadsheetLocation loc = new SpreadsheetLocation(command); //convert "command," or the cell#, to a location
			setCell(loc, value);
			return getGridTextWithErrors(); 
		}
		catch (ErrorException e) {
			return e.getErrorMessage();
		}
		catch (NumberFormatException n) { //when the cell requested is outside the bounds of the spreadsheet
			return ("ERROR: The requested cell is outside the bounds of this spreadsheet");
		}
	}

	@Override
	//returns the entire spreadsheet as a string
	public String getGridText(){
		String answer = repeatchars(' ',3)+"|";
		for (char i = 'A'; i < 'M'; i++) { //prints header row with letters
			answer += i+repeatchars(' ',9)+"|";
		}
		answer += "\n";
		int digits = 1; 
		for (int i = 0; i < 20; i++) { //prints the row numbers
			if (i+1 == 10) { 
				digits = 2; //indicates a two-digit row number
			}
			answer += (i+1)+repeatchars(' ',3-digits)+"|"; //prints out 1 fewer space to offset two-digit numbers
			for (int j = 0; j < 12; j++) { //prints the spreadsheet body
				boolean error = false;
				try {
					answer += sheet[i][j].abbreviatedCellText()+"|";
				}
				catch (ErrorException e) {
					error = true;
				}
				catch (NumberFormatException n) { //when the command was invalid
					error = true;
				}
				if (error == true) {
				answer += (new EmptyCell()).abbreviatedCellText()+"|";
				}
				
			}
			answer+="\n";
		}
		return answer;
	}
	
	//returns the entire spreadsheet as a string
		public String getGridTextWithErrors(){
			String answer = repeatchars(' ',3)+"|";
			for (char i = 'A'; i < 'M'; i++) { //prints header row with letters
				answer += i+repeatchars(' ',9)+"|";
			}
			answer += "\n";
			int digits = 1; 
			for (int i = 0; i < 20; i++) { //prints the row numbers
				if (i+1 == 10) { 
					digits = 2; //indicates a two-digit row number
				}
				answer += (i+1)+repeatchars(' ',3-digits)+"|"; //prints out 1 fewer space to offset two-digit numbers
				for (int j = 0; j < 12; j++) { //prints the spreadsheet body
						answer += sheet[i][j].abbreviatedCellText()+"|";
				}
				answer+="\n";
			}
			return answer;
		}
	
	//replaces all cells in the spreadsheet with empty cells
	public void clearAll() { 
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[i].length; j++) {
				sheet[i][j] = new EmptyCell();
			}
		}
	}
	
	//creates the appropriate type of cell for each input
	public Cell makeCell(String value){
		if (value.length()<1) {
			//if the value to input is an empty string, create an EmptyCell (different from just an empty TextCell)
			return new EmptyCell();
		}
		if (value.charAt(value.length()-1)=='%') {
			//if the string ends in %, make a PercentCell
			return new PercentCell(value);
		}
		if (isNumeric(value) && !value.contains("..")){
			//if value is a real number
			return new ValueCell(value);
		}
		if (value.charAt(0)=='\"' && value.charAt(value.length()-1)=='\"') {
			//if value has quotes it is text
			return new TextCell(value);
		}
		if (value.charAt(0)=='(' && value.charAt(value.length()-1)==')') {
			//if value contains parentheses at both ends, it is a formula cell
			return new FormulaCell(value, this);
		}
		else {
			throw new ErrorException("ERROR: "+value+" is not a valid input to store");
		}
	}
	
	//sorts a range of cells in ascending order
	public String sorta(String range) {
		return sortRange(range, false);
	}
	
	//sorts a range of cells in descending order
	public String sortd(String range) {
		return sortRange(range, true);
	}
	
	public String sortRange(String range, boolean descending){
		//sorts a range of cells in ascending order
		ArrayList<Cell> storecell = new ArrayList<Cell>();
		String[] splitted = range.split("-");
		SpreadsheetLocation loc1 = new SpreadsheetLocation(splitted[0].trim());
		SpreadsheetLocation loc2 = new SpreadsheetLocation(splitted[1].trim());	
		//add each cell in the range to a list
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol(); j++) {
				if (getCell(i, j) instanceof FormulaCell) {
					//doesn't work with formula cells for now
					throw new ErrorException("ERROR: One of the cells you are trying to sort contains a formula, which cannot be sorted");	
				}
				storecell.add(getCell(i, j));
			}
		}
		ArrayList<Cell> sortedcells = sortArray(storecell);
		if (descending == true) { //if sortd was called, reverse the order of the list
			Collections.reverse(sortedcells);
		}
		//insert the sorted cells back into the spreadsheet
		int count = 0;
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol(); j++) {
				setCell(i, j, sortedcells.get(count));
				count++;
			}
		}
		return getGridText(); //prints out the spreadsheet
	}

	//utility methods
	//returns a sorted copy of the array passed in
	public static ArrayList<Cell> sortArray(ArrayList<Cell> arr) {
		ArrayList<Cell> arrsorted = new ArrayList<Cell>();
		for (int i = 0; i < arr.size(); i = i) { //because a cell will be removed each time, i doesn't need to iterate
			arrsorted.add(min(arr));
			arr.remove(min(arr));
			//add the earliest (by order) cell from the inputed array to the answer array, then remove it from the former
		}
		return arrsorted;
	}
	
	public static Cell min(ArrayList<Cell> cell) {
		Cell min = cell.get(0);
		for (int i = 1; i < cell.size(); i++) { //if the current cell is less than min, reset min to that cell
			if (cell.get(i) instanceof RealCell) {
				if (((RealCell) cell.get(i)).compareTo(min)==-1) {
					min = cell.get(i);
				}
			}
			else if (cell.get(i) instanceof TextCell) {
				if (((TextCell) cell.get(i)).compareTo(min)==-1) {
					min = cell.get(i);
				}
			}
			else { //the cell must then be an empty cell
				if (((EmptyCell) cell.get(i)).compareTo(min)==-1) {
					min = cell.get(i);
				}
			}
		}
		return min;
	}
	
	//returns a String of chars repeated a given # of times
	public static String repeatchars(char s, int count) {
		String answer = "";
		for (int i = 0; i<count; i++) {
			answer+=s;
		}
		return answer;
	}
	
	//returns a String repeated a given # of times
	public static String repeatString(String s, int count) {
		String answer = "";
		for (int i = 0; i<count; i++) {
			answer+=s;
		}
		return answer;
	}
	
	//taken from online at stackoverflow and modified
	public static boolean isNumeric(String str){
		int dotcount = 0;
		int dashcount = 0;
	    for (char c : str.toCharArray()){
	        if (!Character.isDigit(c)&&(c!='.')&&(c!='-')) {
	        	//if the characters are not digits, decimal points, or negative signs, the string is not numeric
	        	return false;
	        }
	        else if (c=='.') {
	        	dotcount++;
	        }
	        else if (c=='-') {
	        	dashcount++;
	        }
	    }
	    if (dotcount > 2 || dashcount > 2) { //not a real number if there are multiple dots or dashes in it
	    	return false;
	    }
	    return true;
	}
	
	//doesn't work
	/*public static void errorExit(String errorstatement) { //used to exit the program if some input is incorrect
		System.out.println(errorstatement);
		break testError;
		//System.exit(1); //figure out a way to limit this to just exiting processCommand
	}
	*/
	
	//not needed but could be useful elsewhere
	public static boolean isFormula(String str) {
	    //if (!(str.toLowerCase().contains("sum")||str.toLowerCase().contains("avg")||str.contains("+")||str.contains("-")||str.contains("*")||str.contains("/"))) {
		 if (str.charAt(0)=='('||str.charAt(str.length()-1)==')') {    	
		//if the characters have parentheses, it's a formula
	        	return true;
	    }
	    return false;
	}
}
