//This class contains methods that perform various math operations
//@author Jon Lee
//@version February 19, 2019
package fracCalc;

public class Fraction {
	private int wholenumber = 0;
	private int numerator = 0;
	private int denominator = 1;
	private String sign = "+";
	
	public Fraction() {
		//sets a generic fraction
	}
	public Fraction(int wholenumber, int numerator, int denominator) {
		this.wholenumber = wholenumber;
		this.numerator = numerator;
		this.denominator = denominator;
	}
	public Fraction(String input) {
		//parses input into int's for each component of the fraction	
		String[] fraction = input.split("/");
    	if (fraction.length>1) {
    		//if there are >1 components after splitting on "/", that means there was a fraction part
    		denominator = Integer.parseInt(fraction[1]);
    		String[] components = fraction[0].split("_");
    		numerator = Integer.parseInt(components[0]);
    		if (components.length > 1) {
    			//after splitting further on "_", assign each value to their respective parts
    			wholenumber = Integer.parseInt(components[0]);
    			numerator = Integer.parseInt(components[1]);
    			if (wholenumber < 0) {
    				numerator *= -1;
    			//for math purposes, if wholenumber is negative, numerator is therefore also negative
    			}
	    	}
    	}
    	else {
    		wholenumber= Integer.parseInt(fraction[0]);
    		//if splitting on / produces only one component, it is a whole number
    	}
	}
	public int getWholeNumber() {
		return wholenumber;
	}
	public int getNumerator() {
		return numerator;
	}
	public int getDenominator() {
		return denominator;
	}
	public String getSign() {
		return sign;
	}
	public void setWholeNumber(int num) {
		wholenumber = num;
	}
	public void setNumerator(int num) {
		numerator = num;
	}
	public void setDenominator(int den) {
		denominator = den;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String toString() {
		//converts an improper fraction to mixed number using math rules
		simplify();
		//error checkers
		if (numerator==0 && wholenumber==0) {
			//if no whole number, returns the fraction, and 0 if the fraction is 0
			return ("0");
		}
		if (numerator==0) {		
			//if no whole number, returns the fraction, and 0 if the fraction is 0
			return (""+wholenumber);
		}
		if (denominator==0) {
    		return("ERROR: Cannot divide by zero.");
    	}
		if (wholenumber==0) {
			//if no numerator, return the whole number by itself			
			return (numerator+"/"+denominator);
		}
		return (wholenumber+"_"+absValue(numerator)+"/"+denominator);
		//because numerators are always written as positive if there's a whole number
	}
    public Fraction doMath(String operator, Fraction operand2) {
    	this.toImproperFrac();
    	operand2.toImproperFrac();
    	//convert both fractions to improper form for calculations
    	Fraction result = new Fraction();
    	if (operator.equals("-")||operator.equals("+")) {
    		if (operator.equals("-")) {
    			operand2.setNumerator(-1*operand2.getNumerator());
    			//subtraction is just adding by the negative of the second operand
    		}
    		result.setNumerator(this.numerator*operand2.getDenominator()+operand2.getNumerator()*this.denominator);
    		//cross multiply the numerators by the other operand's denominator, then add to get your result numerator
    		result.setDenominator(this.denominator*operand2.getDenominator());
    		//final denominator is product of the individual denominators
    	}
    	else {
    		if (operator.equals("/")) {
    			int temp=operand2.getDenominator();
    			operand2.setDenominator(operand2.getNumerator());
    			operand2.setNumerator(temp);
    			//division is the same as multiplication by the reciprocal of the second operand
    		}
    		result.setNumerator(this.numerator*operand2.getNumerator());
    		result.setDenominator(this.denominator*operand2.getDenominator());
    		//multiply numerators and denominators for fraction multiplication
    	}
    	return result;
    }
    public void simplify() {
    	if (denominator<0) {
			numerator *= -1;
			denominator *= -1;
			//switches the negative to the numerator, because denominators can't have negative signs
		}
		int gcf = gcf();
    	numerator /= gcf;
    	denominator /= gcf;
    	//reduce the fraction by dividing both components by their greatest common factor
		if (denominator!=0) {
	    	wholenumber += numerator/denominator;
			numerator = numerator%denominator;
			//converts from an improper fraction to a mixed number
		}
	}
    public void toImproperFrac() {
		//converts mixed number to improper fraction using math rules
		if (denominator == 0) {
			throw new IllegalArgumentException("The denominator is 0, please give the right input.");
		}
		numerator += wholenumber*denominator;
		wholenumber = 0;
	}
    public int gcf() {
		//finds the largest factor shared by the numerator and denominator of this fraction
		for (int i = absValue(numerator); i > 1; i--) {
			//starting from the denominator, count down while testing factors to see if both numerator and denominator are divisible by them
			if (isDivisibleBy(numerator,i)==true && isDivisibleBy(denominator,i)==true) {
					return i;
			}
		}
		return 1;
	}
    //extra functionality for the fraction class
    public int gcf(Fraction frac2) {
		//finds the largest factor shared by the denominators of this fraction and the inputted fraction
		for (int i = absValue(denominator); i > 1; i--) {
			//starting from the denominator of this fraction, count down while testing factors to see if both denominators are divisible by them
			if (isDivisibleBy(denominator,i)==true && isDivisibleBy(frac2.getDenominator(),i)==true) {
					return i;
			}
		}
		return 1;
	}
	public boolean isDivisibleBy(Fraction divisor) {
		//true if this fraction is divisible by the inputted fraction, returns false if not
		if (divisor.getNumerator()==0&&divisor.getWholeNumber()==0) {
			throw new IllegalArgumentException("The given divisor is 0, please give the right input.");
			//return error message if incorrect input is given
		}
		else {
			if ((doMath("/",divisor)).getNumerator() == 0) {
				//if after division the resulting fraction's numerator is 0, then the two fractions produced a whole number quotient, so they're divisible
				return true;
			}
		}
		return false;
	}
	public void absValue() {
		if (wholenumber <= 0) {
			wholenumber*=-1;
			//if number is negative, changes it to positive
		}
	}
	//static methods used for calculations
    public String toImproperFrac(int wholenumber, int numerator, int denominator) {
		//converts mixed number to improper fraction using math rules
		if (denominator == 0) {
			throw new IllegalArgumentException("The denominator is 0, please give the right input.");
		}
		int answer = wholenumber*denominator+numerator;
		return (answer+"/"+denominator);
	}
	public static int gcf(int num1, int num2) {
		//finds the largest factor shared by both given values
		for (int i = absValue(num1); i >= 1; i--) {
			//starting from the 1st input (either works), count down while testing factors to see if both num1 and num2 are divisible by them
			if (isDivisibleBy(num1,i)==true && isDivisibleBy(num2,i)==true) {
					return i;
			}
		}
		return 1;
	}
	public static boolean isDivisibleBy(int dividend, int divisor) {
		//if dividend divided by divisor has no remainder, it is divisible, so returns true, if not, returns false
		if (divisor == 0) {
			throw new IllegalArgumentException("The given divisor is 0, please give the right input.");
			//return error message if incorrect input is given
		}
		else {
			if (dividend%divisor == 0) {
				return true;
			}
		}
		return false;
	}
	public static int absValue(int number) {
		if (number <= 0) {
			return -1*number;
			//if number is negative, returns the opposite;
		}
		else {
			return number;
			//if number is positive, returns number
		}
		//either result is the positive value of number
	}
}