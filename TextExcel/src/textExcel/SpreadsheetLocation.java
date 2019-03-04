package textExcel;
//This class contains methods that determine the value of cells at a given location
//@author Jon Lee
//@version March 4, 2019
//Update this file with your own code.

public class SpreadsheetLocation implements Location {
	//not used, but for later functionality, declare fields
		private int rows;
		private int cols;
	
	//constructor
	public SpreadsheetLocation(String cellName){
        rows = cellName.charAt(0)-65;
        cols = Integer.parseInt(cellName.substring(1, cellName.length()))-1;
    }
	
	@Override
    public int getRow(){
        return rows;
    }

    @Override
    public int getCol(){
        return cols;
    }
    
}
