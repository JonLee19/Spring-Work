package textExcel;
//This class contains methods that perform the function of an empty spreadsheet cell
//@author Jon Lee
//@version March 4, 2019
public class EmptyCell implements Cell {
	
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
}
