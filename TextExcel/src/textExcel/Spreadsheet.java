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
		if (row > getRows()-1) {
			errorExit("ERROR: The given row # is outside of the bounds of the spreadsheet");
		}
		if (col > getCols()-1) {
			errorExit("ERROR: The given column # is outside of the bounds of the spreadsheet");
		}
		//check if that location is within the bounds of this spreadsheet
		return sheet[row][col];
	}
	
	@Override
	public Cell getCell(Location loc){
		return getCell(loc.getRow(), loc.getCol());
	}

	public void setCell(int row, int col, Cell c){
		//sets a specific location to the given cell
		if (row > getRows()-1) {
			errorExit("ERROR: The given row # is outside of the bounds of the spreadsheet");
		}
		if (col > getCols()-1) {
			errorExit("ERROR: The given column # is outside of the bounds of the spreadsheet");
		}
		//check if that location is within the bounds of this spreadsheet
		sheet[row][col] = c;
	}
	
	public void setCell(int row, int col, String value){
		//sets a specific location to a given value
		setCell(row, col, makeCell(value));
	}
	
	public void setCell(Location loc, String value){
		//sets a specific location to a given value
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
	public String processCommand(String command){
		//converts input into specific commands, calls those commands, and returns the result
		if (command.length()<2) {
			return command;
		}
		if (command.length()<=3) {
			//proves it's an inspect-only command
			SpreadsheetLocation loc = new SpreadsheetLocation(command);
			return getCell(loc).fullCellText();
		}
		if (command.substring(0,4).equalsIgnoreCase("sort")) {
			if (command.toLowerCase().charAt(4)=='a') {
				return sorta(command.substring(5).trim());
				//send in the command with "sorta" cut off
			}
			else if (command.toLowerCase().charAt(4)=='d') {
				return sortd(command.substring(5).trim());
				//send in the command with "sortd" cut off
			}
			else {
				errorExit("ERROR: the sorting method you called is not a valid type of sorting");
			}
		}
		String value = "";
		//value to input into the cell
		if (command.substring(0,5).toLowerCase().contains("clear")) {
			if (command.equalsIgnoreCase("clear")) {
				//clears all cells in the spreadsheet
				clearAll();
				return getGridText();
			}
			else {
				//clears a specific cell
				command = command.substring(5).trim();
				//removes the word "clear" and "command" becomes the cell#
			}
		value = "";
		//to clear, replace the cell(s) with an empty string
		}
		if (command.contains("=")) { //assignment statement
			String[] components = command.split("=",2);
			command = components[0].trim(); //the cell#
			value = components[1].trim();
		}
		SpreadsheetLocation loc = new SpreadsheetLocation(command);
		//convert "command," or the cell number, to a location
		setCell(loc, value);
		//replace the cell at the given location with the value provided
		return getGridText();
		//returns the entire spreadsheet as a string
	}

	@Override
	public String getGridText(){
		//prints header row with letters
		String answer = repeatchars(' ',3)+"|";
		for (char i = 'A'; i < 'M'; i++) {
			answer += i+repeatchars(' ',9)+"|";
		}
		answer += "\n";
		int o = 0;
		//prints the row numbers
		for (int i = 0; i < 20; i++) {
			//offsets the second digit of numbers from 10 to 20
			if (i == 9) {
				o = 1;
			}
			answer += (i+1)+repeatchars(' ',2-o)+"|";
			//prints the spreadsheet body
			for (int j = 0; j < 12; j++) {
				answer += sheet[i][j].abbreviatedCellText()+"|";
			}
			answer+="\n";
		}
		return answer;
	}
	
	public void clearAll() {
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[i].length; j++) {
				sheet[i][j] = new EmptyCell();
			}
		}
	}
	
	public Cell makeCell(String value) {
		if (value.length()<1) {
			System.out.println("making empty cell: "+value);
			//if the value to input is an empty string, create an EmptyCell (different from just an empty TextCell)
			return new EmptyCell();
		}
		if (value.charAt(value.length()-1)=='%') {
			System.out.println("making percent cell "+value);
			//if the string ends in %, make a PercentCell
			return new PercentCell(value);
		}
		if (isNumeric(value) && !value.contains("..")){
			System.out.println("making number cell "+value);
			//if value is a real number
			return new ValueCell(value);
		}
		if (value.charAt(0)=='\"' && value.charAt(value.length()-1)=='\"') {
			System.out.println("making text cell "+value);
			//if value has quotes it is text
			return new TextCell(value);
		}
		if (value.charAt(0)=='(' && value.charAt(value.length()-1)==')') {
			System.out.println("making formula cell "+value);
			//if value contains parentheses at both ends, it is a formula cell
			return new FormulaCell(value, this);
		}
		else {
			errorExit("ERROR: "+value+" is not a valid input to store");
			throw new IllegalArgumentException(); //backup exit if errorExit fails to stop execution
		}
	}
	
	//sorting
	public String sorta(String range) {
		//sorts a range of cells in ascending order
		return sortRange(range, false);
	}
		
	public String sortd(String range) {
		//sorts a range of cells in descending order
		return sortRange(range, true);
	}
	
	public String sortRange(String range, boolean descending) {
		//sorts a range of cells in ascending order
		ArrayList<Cell> storecell = new ArrayList<Cell>();
		String[] splitted = range.split("-");
		SpreadsheetLocation loc1 = new SpreadsheetLocation(splitted[0].trim());
		SpreadsheetLocation loc2 = new SpreadsheetLocation(splitted[1].trim());	
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol(); j++) {
				if (getCell(i, j) instanceof FormulaCell) {
					//doesn't work with formula cells for now
					errorExit("ERROR: One of the cells you are trying to sort contains a formula, which cannot be sorted");	
				}
				storecell.add(getCell(i, j));
			}
		}
		ArrayList<Cell> sortedcells = sortArray(storecell);
		if (descending == true) {
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
		return getGridText();
	}

	//utility methods
	public static ArrayList<Cell> sortArray(ArrayList<Cell> arr) {
		//returns a sorted copy of the array passed in
		ArrayList<Cell> arrsorted = new ArrayList<Cell>();
		//int initiallength = arr.size();
		for (int i = 0; i < arr.size(); i = i) {
			//because a cell will be removed each time i does not need to iterate
			arrsorted.add(min(arr));
			arr.remove(min(arr));
			//add the earliest cell from the inputed array to the answer array, then remove it from the former
		}
		return arrsorted;
	}
	
	public static Cell min(ArrayList<Cell> cell) {
		Cell min = cell.get(0);
		for (int i = 1; i < cell.size(); i++) {
			//if the current cell is less than min, reset min to that cell
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
	
	public static String repeatchars(char s, int count) {
		String answer = "";
		for (int i = 0; i<count; i++) {
			answer+=s;
		}
		return answer;
	}
	
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
	    if (dotcount > 2 || dashcount > 2) {
	    	return false;
	    }
	    return true;
	}
	
	public static void errorExit(String errorstatement) {
		System.out.println(errorstatement);
		System.exit(1);
	}
	
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
