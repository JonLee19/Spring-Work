package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

public class Spreadsheet implements Grid {
	
	//declare fields
	private Cell[][] sheet;
	
	//constructor
	public Spreadsheet() {
		//initialize a 2D array of EmptyCell objects
		sheet = new Cell[20][12];
		clearAll();
	}
	
	//getters and setters
	@Override
	public int getRows(){	
		return sheet.length;
	}

	@Override
	public int getCols(){
		return sheet[0].length;
	}

	@Override
	public Cell getCell(Location loc){
		if (loc.getRow() > getRows()-1) {
			throw new IllegalArgumentException("The given row # is outside of the bounds of the spreadsheet");
		}
		if (loc.getCol() > getCols()-1) {
			throw new IllegalArgumentException("The given column # is outside of the bounds of the spreadsheet");
		}
		return sheet[loc.getRow()][loc.getCol()];
	}
	
	//alternate getter for utility
	public Cell getCell(int row, int col){
		if (row > getRows()-1) {
			throw new IllegalArgumentException("The given row # is outside of the bounds of the spreadsheet");
		}
		if (col > getCols()-1) {
			throw new IllegalArgumentException("The given column # is outside of the bounds of the spreadsheet");
		}
		return sheet[row][col];
	}

	public void setCell(Location loc, String value){
		//sets cell at a specific location to a given value
		sheet[loc.getRow()][loc.getCol()] = makeCell(value);
	}
	
	public double shareCell(SpreadsheetLocation loc) {
		RealCell c = (RealCell) getCell(loc);
		return c.getDoubleValue();
	}
	
	//toString
	
	
	//spreadsheet commands functionality
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
				command = command.substring(6);
				//removes the word "clear"
				//"command" becomes the cell#
			}
		value = "";
		//to clear, replace the cell(s) with an empty string
		}
		if (command.contains("=")) {
			//proves it is an assignment statement
			String[] components = command.split("=",2);
			//split on space to separate the cell#, equals sign, and value respectively
			command = components[0].trim();
			//set the cell number to the first component
			value = components[1].trim();
			//sets the value to be set in variable "value"
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
	
	public Cell makeCell(String value) {
		if (value.length()<1) {
			//if the value to input is an empty string, create an EmptyCell (different from just an empty TextCell)
			return new EmptyCell();
		}
		if (value.charAt(value.length()-1)=='%') {
			//if the string ends in %, make a PercentCell
			return new PercentCell(value);
		}
		if (isNumeric(value)){
			//if value is a real number
			return new ValueCell(value);
		}
		if (value.charAt(0)=='\"') {
			//if value has quotes it is text
			return new TextCell(value);
		}
		if (isFormula(value)) {
			//if value contains parentheses it is a formula cell
			return new FormulaCell(value, this);
		}
		else {
			throw new IllegalArgumentException("That is not a valid input.");
		}
		
	}
	
	public void clearAll() {
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[i].length; j++) {
				sheet[i][j] = new EmptyCell();
			}
		}
	}
	
	//utility methods
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
	
	public static boolean isFormula(String str) {
	    //if (!(str.toLowerCase().contains("sum")||str.toLowerCase().contains("avg")||str.contains("+")||str.contains("-")||str.contains("*")||str.contains("/"))) {
		 if (str.charAt(0)=='('||str.charAt(str.length()-1)==')') {    	
		//if the characters have parentheses, it's a formula
	        	return true;
	    }
	    return false;
	}
	
	//taken from online at stackoverflow and modified
	public static boolean isNumeric(String str){
	    for (char c : str.toCharArray()){
	        if (!Character.isDigit(c)&&(c!='.')&&(c!='-')) {
	        	//if the characters are not digits, decimal points, or negative signs, the string is not numeric
	        	return false;
	        }
	    }
	    return true;
	}
}
