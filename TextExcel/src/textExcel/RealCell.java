package textExcel;

public abstract class RealCell implements Cell {
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
    
	//easier version
	/*public double getDoubleValue() {
		if (input.contains("%")) {
			return Double.parseDouble(input.substring(0,input.length()-1));
			//parses after removing the percent sign
		}
		return Double.parseDouble(input);
		//for real cells, parses directly
	}
	*/
}
