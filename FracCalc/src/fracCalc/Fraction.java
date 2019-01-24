package fracCalc;

public class Fraction {
	private int numerator = 0;
	private int denominator = 1;
	private String sign = "+";
	
	public Fraction() {
		//sets a generic fraction
	}
	public Fraction(String input) {
		//convert operands to improper fractions where the components make up an array
    	String[] mixednumcomponents = input.split("_");
    	if (mixednumcomponents.length>1) {
    		//if there are 2 or more components after splitting on _, that means there was
    		//a whole number and a fractional component
	    	String[] fraction = mixednumcomponents[1].split("/");
	    	//after splitting further on /, assign each value to their respective parts
	    	this.denominator = Integer.parseInt(fraction[1]);
	    	this.numerator = toImproperNumerator(Integer.parseInt(mixednumcomponents[0]),Integer.parseInt(fraction[0]),denominator);
	    	//combines whole number and numerator components into an improper numerator
    	}
    	else {
    		//if splitting on _ produces only one component, there is no whole number component
	    	String[] fraction = input.split("/");
	    	if (fraction.length>1) {
	    		//if splitting on / produces more than one component, assign each 
	    		//value to its respective part of the fraction
	    		this.numerator = Integer.parseInt(fraction[0]);
	    		this.denominator = Integer.parseInt(fraction[1]);
	    	}
	    	else {
	    		//if splitting on _ and / both produce only one component, that
	    		//value must be a whole number with no fractional components
	    		//so set that to numerator and leave denominator as 1
	    		this.numerator = Integer.parseInt(fraction[0]);
	    	}
    	}
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
		if (numerator==0) {
			return ("0");
		}
		//if numerator is 0, that means the fraction is equal to 0
		int wholenumber = numerator/denominator;
		if (wholenumber==0) {
			if (denominator<0) {
				numerator=-numerator;
				denominator=-denominator;
				//switches the negative to the numerator, because denominators can't have negative signs
			}
			return (numerator+"/"+denominator);
			//if no whole number, returns original fraction
		}
		int remainder = absValue(numerator%denominator);
		if (remainder==0) {
			return (""+wholenumber);
		//if no remainder, then the numerator divides evenly and there is no fractional component
		}
		
		return (wholenumber+"_"+remainder+"/"+absValue(denominator));
		//because denominators of fractions are always written as positives
	}
    public Fraction doMath(String operator, Fraction operand2) {
    	Fraction result = new Fraction();
    	if (operator.equals("-")||operator.equals("+")) {
    		if (operator.equals("-")) {
    			operand2.setNumerator(-1*operand2.getNumerator());
    		}
    		//subtraction is just adding by the negative of the second operand
    		result.setNumerator(this.numerator*operand2.getDenominator()+operand2.getNumerator()*this.denominator);
    		//cross multiply the numerators by the other operand's denominator
    		//and then add to get your final answer's numerator
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
    		//multiply numerators and denominators in fraction multiplication
    	}
    	int gcf = gcf(result.getNumerator(), result.getDenominator());
    	result.setNumerator(result.getNumerator()/gcf);
    	result.setDenominator(result.getDenominator()/gcf);
    	return result;
    	//find gcf of numerator and denominator of answer, reduce the fraction by dividing 
    	//both components by that gcf, and then return as a mixed number
    }
    public static int toImproperNumerator(int wholenumber, int numerator, int denominator) {
		//converts mixed number to improper fraction using math rules
		if (wholenumber<0) {
			numerator=-numerator;
		}
		//if the fraction is negative, numerator needs to be negative to "add" it to whole number
		//when converting to an improper fraction
		return wholenumber*denominator+numerator;
		//convert whole number to the appropriate fraction and then add to numerator to produce the improper numerator
	}
	public static int gcf(int num1, int num2) {
		//finds the largest factor shared by both given values
		int answer = 1;
		for (int i = absValue(num1); i > 1; i--) {
			/*starting from the 1st input (either works), count down while testing factors
			 * to see if both num1 and num2 are divisible by them
			 */
			if (isDivisibleBy(num1,i)==true && isDivisibleBy(num2,i)==true) {
					answer = i;
					i = 1;
					/*once a factor is found, assign the value to answer, then
					 * set the control variable/counter to 1 to end the loop
					 *  (so that "i" doesn't satisfy the test condition anymore)
					 */
			}
		}
		return answer;
	}
	public static boolean isDivisibleBy(int dividend, int divisor) {
		//if dividend divided by divisor has no remainder, it is divisible 
		//so returns true, if not, returns false
		if (divisor == 0) {
			throw new IllegalArgumentException("The given divisor is 0, please give the right input.");
			//return error message if incorrect input is given
		}
		else {
			if (dividend%divisor == 0) {
				return true;
			}
			else {
				return false;
			}
		}
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