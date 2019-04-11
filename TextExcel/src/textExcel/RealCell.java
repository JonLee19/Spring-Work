package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

public abstract class RealCell implements Cell, Comparable<Cell> {
	private String input;
	
	public RealCell(String i) {
		input = i;
	}
	
	// text for spreadsheet cell display, must be exactly length 10
	public String abbreviatedCellText() {
		String output = ""+getDoubleValue();
		if (output.length() < 10) {
			return output+Spreadsheet.repeatchars(' ',10-output.length());
			//makes the result 10 spaces long
		}
		return output.substring(0,10); 
	}
	
	// text for individual cell inspection, not truncated or padded
	public String fullCellText() {
		return input;
	}
	
	public abstract double getDoubleValue();

	//@Override
	//compares to another cell
	public int compareTo(Cell c) {
		//compares to another cell and returns -1 if this is less than that object, 0 if they're equals, and +1 if its greater than that object 
		if (c instanceof RealCell) {
			RealCell r = (RealCell) c;
			//cast to a real cell to get the value
			if (this.getDoubleValue() < r.getDoubleValue()) {
				return -1;
				//returns -1 if this is less than the given cell
			}
			else if (this.getDoubleValue()==r.getDoubleValue()) {
				return 0;
				//returns 0 if this is less than the given cell
			}
			//compare the values of both real cells to determine which is greater
		}
		return 1;
		//real cell is greater than every other type of cell
	}
	
	//toString
    public String toString() {
    	return (getClass()+": text: "+fullCellText());
    }
	
    //equals method
    public boolean equals(Object o) {
    	if(o instanceof RealCell) {
    		RealCell obj = (RealCell) o;
    		if (this.getDoubleValue()==obj.getDoubleValue()) {
    			return true;
    		}
    	}
    	return false;
    }
}
