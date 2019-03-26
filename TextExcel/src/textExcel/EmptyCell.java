package textExcel;
//This class contains methods that perform the function of an empty spreadsheet cell
//@author Jon Lee
//@version March 4, 2019

public class EmptyCell implements Cell, Comparable<Cell> {
	
	//constructor
	public EmptyCell() {
	}
	
	// text for spreadsheet cell display, must be exactly length 10
	public String abbreviatedCellText() {
		return "          ";
	}
	// text for individual cell inspection, not truncated or padded
	public String fullCellText() {
		return "";
	}
	
	//@Override
	//compares to another cell
	public int compareTo(Cell c) {
		//compares to another cell and returns -1 if this is less than that object, 0 if they're equals, and +1 if its greater than that object 
		if (c instanceof EmptyCell) {
			return 0;
			//if the object to compare to is also an empty cell, return 0 to show they are equal
		}
		return -1;
		//empty cell is less than every other type of cell
	}
	
	//toString
	public String toString() {
	    return ("Empty Cell: "+fullCellText());
	}
	
	//equals method
    public boolean equals(Object o) {
    	if(o instanceof EmptyCell) {
    			return true;
    	}
    	return false;
    }
}
