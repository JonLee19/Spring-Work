package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019

public class ValueCell extends RealCell{
	
	public ValueCell(String input) {
		super(input);
	}
	
	@Override
	public double getDoubleValue() {
		return Double.parseDouble(super.fullCellText());
		//for real cells, parses directly
	}
	
	//toString
	public String toString() {
	    return ("Value Cell: "+fullCellText());
	}
}
