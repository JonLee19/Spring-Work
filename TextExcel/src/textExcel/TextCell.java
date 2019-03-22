package textExcel;

//import java.lang.*;

public class TextCell implements Cell, Comparable<Cell> {
	
	//declare fields
	private String text;
	
	//constructor
	public TextCell(String input) {
		text = input;
	}
	
	// text for spreadsheet cell display, must be exactly length 10
	public String abbreviatedCellText() {
		String output = text;
		if (output.length()<2) {
			//proves it's an empty string with two double quotes
			return "          ";
		}
		output = output.substring(1, text.length()-1);
		//cuts off the first and last characters, which are double quotes
		if (output.length() < 10) {
			return output+Spreadsheet.repeatchars(' ',10-output.length());
			//makes the result 10 spaces long
		}
		return output.substring(0,10); 
	}
	
	// text for individual cell inspection, not truncated or padded
	public String fullCellText() {
		return text;
	}
	
	//@Override
	//compares to another cell
	public int compareTo(Cell c) {
		//compares to another cell and returns -1 if this is less than that object, 0 if they're equals, and +1 if its greater than that object 
		if (c instanceof TextCell) {
			TextCell t = (TextCell) c;
			//cast to a real cell to get the value
			return compareText(this.fullCellText(), t.fullCellText());
		}
		else if (c instanceof EmptyCell) {
			return 1;
			//the only cell that is before a text cell is an empty cell
		}
		return -1;
		//text cell comes before all the other types of cells
	}
	
	//toString
	public String toString() {
	    return ("Text Cell: "+fullCellText());
	}
	
	//equals method
    public boolean equals(Object o) {
    	if(o instanceof TextCell) {
    		TextCell obj = (TextCell) o;
    		if (this.fullCellText().equals(obj.fullCellText())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //utility methods
    public static int compareText(String s1, String s2) {
		s1.toUpperCase();
		s2.toUpperCase();
		if (s1.equals(s2)) {
			return 0;
		}
		for (int i = 0; i < shorter(s1, s2).length(); i++) {
		//parse through the strings comparing letters, returning whichever one comes first alphabetically
			if (s1.charAt(i) < s2.charAt(i)) {
				return -1;
			}
			else if (s1.charAt(i) > s2.charAt(i)) {
				return 1;
			}
			//if both tests fail, the first letters of the two words are the same, so repeat check for the next letter and so on
		}
		//if all characters compared are the same, the shorter string comes first
		if (s1.equals(shorter(s1,s2))) {
			return -1;
		}
		return 1;
		//if all letters up until the end of one word are the same, then the shorter word comes first
	}
		
	public static String shorter(String s1, String s2) {
		String shorter;
		if (s1.length()<=s2.length()) {
			shorter = s1;
		}
		else {
			shorter = s2;
		}
		return shorter;
	}
}
