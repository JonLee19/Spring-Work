package fracCalc;

import java.util.Scanner;

public class FracCalc {

    public static void main(String[] args) {
    	Scanner console = new Scanner(System.in);
    	boolean done = false;
    	System.out.println("Pleace print an expression with two operands.");
    	String input = console.nextLine();
    	//open scanner to take in an expression
    	while (done!=true) {
    		System.out.println(produceAnswer(input));
    		System.out.println("Type \"quit\" to end or new values to try again.");
    		input = console.nextLine();
    		if (input.equals("quit")) {
    			done=true;
    		}
    	}
    	console.close();
    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input) { 
    	String[] splitted = input.split(" ");
    	Fraction op1 = new Fraction(splitted[0]);
    	String operator = splitted[1];
    	Fraction op2 = new Fraction(splitted[2]);
    	//split on a space to separate the operands and operators
    	if (!(operator.equals("+")||operator.equals("-")||operator.equals("*")||operator.equals("/"))) {
	    		return ("ERROR: Input is in an invalid format.");
	    	}
    	return (op1.doMath(operator, op2).toString());
    	/*int[] multipliedanswer = {1, 1};
    	int[] addedanswer = {0, 1};
    	if (splitted.length<4) {
    		return toMixedNum(doMath(stringToImproperFrac(splitted[0]), splitted[1], stringToImproperFrac(splitted[2])));
    	}
    	for (int i = 2; i<splitted.length; i+=2) {
    		String operator = splitted[i-1];
    		//System.out.println(operator);
    		
    		int[] operand2 = stringToImproperFrac(splitted[i]);
    		//update operand2 to the next operand, which is every other array element
    		if (operator.equals("/")||operator.equals("*")) {
    				int temp = i;
		    		multipliedanswer = stringToImproperFrac(splitted[i-2]);
		    		while (operator.equals("*")||operator.equals("/")) {
		    			multipliedanswer = doMath(multipliedanswer, operator, operand2);
		    			//System.out.println("hi");
		    			if (i<splitted.length-2) {
		    				//System.out.println("inside if");
			    			i+=2;
			    			operator = splitted[i-1];
			    			operand2 = stringToImproperFrac(splitted[i]);
			    			//System.out.println("operator is:"+ operator+"; multipliedanswer is"+ Arrays.toString(multipliedanswer));
		    			}
		    			else {
		    				operator = "+";
		    				//exit the while loop
		    				//System.out.println("outside if");
		    			}
		    		}
		    		//System.out.println("left the while loop");
		    		if (i<splitted.length-1) {
		    			i-=2;
		    		}
	    			String preoperator;
	    			if (temp>2) {
	    				preoperator = splitted[temp-3];
	    			}
	    			else {
	    				preoperator = "+";
	    			}
	    			addedanswer = doMath(addedanswer, preoperator, multipliedanswer);
	    			//once a series of multiplied numbers has been computed, add it back to added answer
	    			multipliedanswer[0] = 1;
	    			multipliedanswer[1] = 1;
	    			//reset multipliedanswer to 1, because multiplying or dividing by one makes no difference
    		}
		    else{  
		    	//means (operator.equals("-")||operator.equals("+")) 
		    	if (i==2) {
		    		addedanswer = doMath(addedanswer, operator, stringToImproperFrac(splitted[0]));
		    	}
		    	else {
		    		addedanswer = doMath(addedanswer, operator, operand2);
		    	}
		    }
		   //System.out.println(Arrays.toString(multipliedanswer)+", "+Arrays.toString(addedanswer));
    	}
		if (addedanswer[1]==0) {
    		return("ERROR: Cannot divide by zero.");
    	}
		/*if one operand's denominator is 0, the final fraction's denominator will also be 0
	     *because the only math performed on the denominator is multiplication
		 *and dividing by zero is not allowed for this calculator, so return error
		 */
    }
}
