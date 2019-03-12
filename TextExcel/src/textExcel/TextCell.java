package textExcel;

public class TextCell implements Cell{
	
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
}
