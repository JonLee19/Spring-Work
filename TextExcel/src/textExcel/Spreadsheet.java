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
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[i].length; j++) {
				sheet[i][j] = new EmptyCell();
			}
		}
	}
	
	@Override
	public String processCommand(String command){
		if (command.length()<=3) {
			//proves it's an inspect-only command
			SpreadsheetLocation loc = new SpreadsheetLocation(command);
			return sheet[loc.getRow()][loc.getCol()].fullCellText();
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
		//COME BACK TO later in order to change to an empty cell
		}
		if (command.contains("=")) {
			//proves it is an assignment statement
			String[] components = command.split(" ",3);
			//split on space to separate the cell#, equals sign, and value respectively
			command = components[0];
			//set the cell number to the first component
			value = components[2];
			//sets the value to be set in variable "value"
		}
		SpreadsheetLocation loc = new SpreadsheetLocation(command);
		//convert "command," or the cell number, to a location
		sheet[loc.getRow()][loc.getCol()] = new TextCell(value);
		//replace the cell at the given location with the value determined
		//COME BACK TO make this replacement a method to take formulas as well as text
		return getGridText();
	}

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
		return sheet[loc.getRow()][loc.getCol()];
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
}
