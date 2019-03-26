package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

public class PercentCell extends RealCell {
	
	public PercentCell(String input) {
		super(input);
	}
	
	@Override
	// text for spreadsheet cell display, must be exactly length 10
	public String abbreviatedCellText() {
		String output = ((int) (100*getDoubleValue()))+"%";
		//truncates on the decimal point and adds percent sign
		if (output.length() < 10) {
			return output+Spreadsheet.repeatchars(' ',10-output.length());
			//makes the result 9 spaces long, so it's 10 when the percent sign is added
		}
		return output.substring(0,9)+"%"; 
	}
	
	@Override
	// text for spreadsheet cell display, must be exactly length 10
	public String fullCellText() {
		return ""+getDoubleValue();
	}
	
	@Override
	public double getDoubleValue() {
		return (Double.parseDouble(super.fullCellText().substring(0,super.fullCellText().length()-1)))/100.0;
			//parses after removing the percent sign, keeps it in percent format (2 decimal points over)
	}
	
	//toString
	public String toString() {
    	return ("Percent Cell: "+super.fullCellText());
    }
}
