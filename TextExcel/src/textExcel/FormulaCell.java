package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

import java.util.*;

public class FormulaCell extends RealCell{
	private static Spreadsheet sheet;
	//takes in a copy of spreadsheet in order to access those cells for other use
	
	public FormulaCell(String input, Spreadsheet s) {
		super(input);
		sheet = s;
	}
	
	public double getDoubleValue() { //add order of ops parentheses
		String formula = super.fullCellText().substring(1,super.fullCellText().length()-1).trim(); //cuts off parentheses and whitespace
		if (formula.toLowerCase().contains("avg")||formula.toLowerCase().contains("sum")) { //sum or average operations
			String[] splitted = formula.split(" ", 2);
			return specialOperation(splitted[0].trim(), splitted[1].trim());
		}
		ArrayList<String> splitted = parseFormula(formula);
    	for (int i = 1; i < splitted.size(); i+=2) {
    		if (splitted.get(i).equals("*")||splitted.get(i).equals("/")) { //perform multiplication/division and return the result to the arraylist
    			splitted.set(i-1, ""+doMath(parseOperand(splitted.get(i-1)), splitted.get(i), parseOperand(splitted.get(i+1))));
    			splitted.remove(i+1);
    			splitted.remove(i);
    			i-=2;
    		}
    	}
		double answer = parseOperand(splitted.get(0));
		for (int i = 2; i<splitted.size(); i+=2) { //repeat math operations until end of formula string
    		answer = doMath(answer, (splitted.get(i-1)), parseOperand(splitted.get(i)));
    	}
    	return answer;
	}
	
	//performs elementary functions
	public double doMath(double op1, String operator, double op2) {
		if (operator.equals("/")||operator.equals("*")) {
			if (operator.equals("/")) {
				op2=1/op2;
				//division is multipliction by the reciprocal
			}
			return (op1*op2);
		}
		if (operator.equals("+")||operator.equals("-")) {
			if (operator.equals("-")) {
				op2 = -1*op2;
    			//subtraction is just adding by the negative of the second operand
    		}
			return (op1+op2);
		}
		else {
			throw new ErrorException("ERROR: Input is in an invalid format.");
		}
	}
	
	public double specialOperation(String operator, String argument) {
		String[] range = argument.split("-");
		SpreadsheetLocation loc1 = new SpreadsheetLocation(range[0]);
		SpreadsheetLocation loc2 = new SpreadsheetLocation(range[1]);
		if (operator.equalsIgnoreCase("sum")) {
			return sum(loc1, loc2);
		}
		else if (operator.equalsIgnoreCase("avg")) {
			int count = (loc2.getRow()-loc1.getRow()+1)*(loc2.getCol()-loc1.getCol()+1);
			//how many cells are added up
			return sum(loc1, loc2)/count;
		}
		else {
			throw new ErrorException("ERROR: The operation you are attempting to use is unsupported");
		}
	}
	
	//utility methods
	public static double sum(SpreadsheetLocation loc1, SpreadsheetLocation loc2) {
		double answer= 0;
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol(); j++) {
				Cell cell = sheet.getCell(i, j);
				if (cell instanceof FormulaCell ) {//&& !(cell == this)
					// to make sure these are not the same cell
					answer += ((FormulaCell) sheet.getCell(i, j)).getDoubleValue();
				} 
				else if (cell instanceof RealCell) {
					answer += ((RealCell) sheet.getCell(i, j)).getDoubleValue();
				} 
				else {
					throw new ErrorException(
							"One or more cells you are trying to sum is not a real (numeric) cell");
				}
			}
		}
		return answer;
	}
	
	public double parseOperand(String op) {
		if (Spreadsheet.isNumeric(op)) {
			return Double.parseDouble(op);
		}
		else { //a cell#
			try {
				SpreadsheetLocation loc = new SpreadsheetLocation(op);
				if (!(sheet.getCell(loc) instanceof RealCell)) {
					//if its not a type of real cell
					throw new ErrorException("ERROR: The cell you refer to has no numeric value, so the formula cannot be calculated");
				}
				RealCell c = (RealCell) sheet.getCell(loc);
				return c.getDoubleValue();
			}
			catch (NumberFormatException n){
				throw new ErrorException("ERROR: The given value may not be stored in a cell");
			}
		}	
	}
	
	public double parseOperand(double op) {
		return op;
	}
	
	public double parseOperand(int op) {
		return op;
	}
	
	//separate operands and operators in order into an arraylist
	public static ArrayList<String> parseFormula (String s) {
		ArrayList<String> answer = new ArrayList<String>();
		int lastindex = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)=='+') {
				answer.add(s.substring(lastindex, i).trim());
				answer.add("+");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='*') {
				answer.add(s.substring(lastindex, i).trim());
				answer.add("*");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='/') {
				answer.add(s.substring(lastindex, i).trim());
				answer.add("/");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='%') {
				answer.add(s.substring(lastindex, i).trim());
				answer.add("%");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='-') { //the dash is a minus, not a negative sign, as long as the following character is not a number
				if (!(Character.isDigit(s.charAt(i+1)))) {	
					answer.add(s.substring(lastindex, i).trim());
					answer.add("-");
					lastindex = i+1;
				}
			}
		}
		if (lastindex != s.length()) { //the last operand has not been added yet
			answer.add(s.substring(lastindex, s.length()).trim());
		}
		return answer;
	}
	
	//toString
	public String toString() {
    	return ("Formula Cell: "+fullCellText());
    }
}