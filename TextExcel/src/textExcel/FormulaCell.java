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
	
	public double getDoubleValue() {
		double answer = 0;
		System.out.println("this is the formula: "+super.fullCellText());
		String formula = super.fullCellText().substring(1,super.fullCellText().length()-1).trim();
		System.out.println("take off the parentheses "+formula);
		//removing the parentheses and any spaces from the edges
		ArrayList<String> splitted = new ArrayList<String>(Arrays.asList(formula.split(" ")));
		//split on space and store as an array list for added flexibility (changes length)
		/*if (splitted.size()==1) {
			ArrayList<String> specialsplitted = splitOperator(splitted.get(0));
			for (int i = 2; i<specialsplitted.size(); i+=2) {
				answer = parseOperand(specialsplitted.get(0));
				answer = doMath(answer, (specialsplitted.get(i-1)), parseOperand(specialsplitted.get(i)));
	    		//repeat math operations until end of formula string
	    	}
			return answer;
		}
		*/
		if (splitted.size()==2) {
			//reorder the components so that sum or avg is the operator
			return specialOperation(splitted.get(0), splitted.get(1));
			
		}
		answer = parseOperand(splitted.get(0));
    	for (int i = 2; i<splitted.size(); i+=2) {
    		answer = doMath(answer, (splitted.get(i-1)), parseOperand(splitted.get(i)));
    		//repeat math operations until end of formula string
    	}
    	return answer;
	}
	
	public double doMath(double op1, String operator, double op2) {
		//elementary functions
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
			//final possible path
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
			throw new IllegalArgumentException(
					"the operation you are attempting to use is unsupported");
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
					throw new IllegalArgumentException(
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
			SpreadsheetLocation loc = new SpreadsheetLocation(op);
			if (!(sheet.getCell(loc) instanceof RealCell)) {
				//if its not a type of real cell
				throw new ErrorException("ERROR: The cell you refer to has no numeric value, so the formula cannot be calculated");
			}
			RealCell c = (RealCell) sheet.getCell(loc);
			return c.getDoubleValue();
		}	
	}
	
	//to separate operands when there are no spaces between them
	public static ArrayList<String> splitOperator(String s) {
		ArrayList<String> answer = new ArrayList<String>();
		int lastindex = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)=='+') {
				answer.add(s.substring(lastindex, i));
				answer.add("+");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='-') {
				answer.add(s.substring(lastindex, i));
				answer.add("-");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='*') {
				answer.add(s.substring(lastindex, i));
				answer.add("*");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='/') {
				answer.add(s.substring(lastindex, i));
				answer.add("/");
				lastindex = i+1;
			}
			else if (s.charAt(i)=='%') {
				answer.add(s.substring(lastindex, i));
				answer.add("%");
				lastindex = i+1;
			}
		}
		if (lastindex != s.length()) {
			//the last operand has not been added yet
			answer.add(s.substring(lastindex, s.length()));
		}
		return answer;
	}
	
	//toString
	public String toString() {
    	return ("Formula Cell: "+fullCellText());
    }
}