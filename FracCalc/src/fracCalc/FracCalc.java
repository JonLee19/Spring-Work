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
    
    public static String produceAnswer(String input) { 
    	String[] splitted = input.split(" ");
    	//split on a space to separate the operands and operator
    	String answer = splitted[0];
    	for (int i = 2; i<splitted.length; i+=2) {
    		Fraction op1 = new Fraction(answer);
    		String operator = splitted[i-1];
    		Fraction op2 = new Fraction(splitted[i]);
    		if (!(operator.equals("+")||operator.equals("-")||operator.equals("*")||operator.equals("/"))) {
    			return ("ERROR: Input is in an invalid format.");
	    	//check for incorrect operators
    		}
    		answer = op1.doMath(operator, op2).toString();
    	}
    	return answer;
    }
}