package textExcel;

public class FormulaErrorException extends ErrorException {
	public FormulaErrorException(String errorMessage) {
		super(errorMessage);
	}
	
	public String abbreviatedCellText() {
		return "#ERROR    ";
	}
}
