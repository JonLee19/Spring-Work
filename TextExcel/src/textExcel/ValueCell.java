package textExcel;

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
