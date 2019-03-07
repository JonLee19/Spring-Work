package textExcel;
//This class contains methods that determine the value of cells at a given location
//@author Jon Lee
//@version March 4, 2019

public class SpreadsheetLocation implements Location {
	//declare fields
	private int row;
	private int col;
	
	//constructor
	public SpreadsheetLocation(String cellName){
        row = Integer.parseInt(cellName.substring(1, cellName.length()))-1;
        col = cellName.toUpperCase().charAt(0)-65;
    }
	
	@Override
    public int getRow(){
        return row;
    }

    @Override
    public int getCol(){
        return col;
    }
    
}
