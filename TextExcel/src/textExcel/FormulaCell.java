package textExcel;

public class FormulaCell extends RealCell{
	
	public FormulaCell(String input) {
		super(input);
	}
	
	public double getDoubleValue() {
		return Double.parseDouble(super.fullCellText());
	}
}
