package textExcel;

import java.util.Arrays;

//import fracCalc.Fraction;

public class FormulaCell extends RealCell{
	private static Spreadsheet sheet;
	//takes in a copy of spreadsheet in order to access those cells for other use
	
	public FormulaCell(String input, Spreadsheet s) {
		super(input);
		sheet = s;
	}
	
	public double getDoubleValue() {
		String formula = super.fullCellText().substring(1,super.fullCellText().length()-1).trim();
		//store the formula after removing the parentheses and spaces from the edges
		String[] splitted = formula.split(" ");
		String answer = splitted[0];
    	for (int i = 2; i<splitted.length; i+=2) {
    		answer = doMath(answer, splitted[i-1], splitted[i]);
    	}
    	return Double.parseDouble(answer);
	
	}
	
	public String doMath(String op1, String operator, String op2) {
		if (op1.equalsIgnoreCase("sum")) {
			//COME BACK TO SUM AND AVG
			return op1;
		}
		if (op1.equalsIgnoreCase("avg")) {
			return op1;
		}
		double op1val = parseOperand(op1);
		double op2val = parseOperand(op2);
		//elementary functions
		if (operator.equals("/")||operator.equals("*")) {
			if (operator.equals("/")) {
				op2val = 1/op2val;
				//division is multiplaction by the reciprical
			}
			return ""+(op1val*op2val);
		}
		if (operator.equals("+")||operator.equals("-")) {
			if (operator.equals("-")) {
				op2val = -1*op2val;
    			//subtraction is just adding by the negative of the second operand
    		}
			return ""+(op1val+op2val);
		}
		else {
			return ("ERROR: Input is in an invalid format.");
			//final possible path
		}
		//for PEMDAS
    	/*
		if (operator.equals("/")||operator.equals("*")) {
    			int temp = i;
		    	multipliedanswer = stringToImproperFrac(splitted[i-2]);
		    		while (operator.equals("*")||operator.equals("/")) {
		    			//if multiplied/divided, needs to multiply and divide all factors first, then add back
		    			multipliedanswer = doMath(multipliedanswer, operator, operand2);
		    			i+=2;
		    			if (i<splitted.length) {
		    				//System.out.println(i);
		    				operator = splitted[i-1];
		    				//System.out.println(operator);
		    				operand2 = stringToImproperFrac(splitted[i]);
		    				//update operator and operand to the next pair and repeat the math
		    			}
		    			else {
		    				operator = "+";
		    				//exit the while loop
		    			}
		    		}
		    		System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
		    		i-=2;
	    			String preoperator;
	    			if (temp>2) {
	    				preoperator = splitted[temp-3];
	    			}
	    			else {
	    				preoperator = "+";
	    			}
	    			System.out.println("preoperator is: "+preoperator);
	    			addedanswer = doMath(addedanswer, preoperator, multipliedanswer);
	    			System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
		    		if (temp > 2) {	
		    			if (preoperator.equals("+")) {
		    				addedanswer = doMath(addedanswer, "-", stringToImproperFrac(splitted[temp-2]));
		    			}
		    			else {
		    				addedanswer = doMath(addedanswer, "+", stringToImproperFrac(splitted[temp-2]));
		    			}
		    		}
	    			//undoing the extra operand that was added (the one to start the string of multiplied/divided operands, which was still added or subtracted while traversing the string)
	    			//System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
	    			//once a series of multiplied numbers has been computed, add it back to added answer
	    			multipliedanswer[0] = 1;
	    			multipliedanswer[1] = 1;
	    			//reset multipliedanswer to 1, because multiplying or dividing by one makes no difference
	    			//System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
    		}
		    else{  
		    	//means (operator.equals("-")||operator.equals("+")) 
		    	if ((i==2)&&(operator.equals("+")||operator.equals("-"))) {
		    		addedanswer = doMath(addedanswer, "+", stringToImproperFrac(splitted[0]));
		    		System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
		    	}
		    	System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
		    	addedanswer = doMath(addedanswer, operator, operand2);
		    	System.out.println("multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
		    }
		   System.out.println("end of loop: multiplied answer is "+Arrays.toString(multipliedanswer)+", added answer is "+Arrays.toString(addedanswer));
    	}
		if (addedanswer[1]==0) {
    		return("ERROR: Cannot divide by zero.");
    	}
    	*/
	}
	
	public double parseOperand(String op) {
		if (Spreadsheet.isNumeric(op)) {
			return Double.parseDouble(op);
		}
		else { //a cell#
			SpreadsheetLocation loc = new SpreadsheetLocation(op);
			RealCell c = (RealCell) sheet.getCell(loc);
			return c.getDoubleValue();
		}
		
	}
}
