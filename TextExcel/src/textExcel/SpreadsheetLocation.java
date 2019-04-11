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
        //if (row < 0 || col < 0) {
        	//throw new IllegalArgumentException("ERROR: either your row or column is negative, which is not possible");
        //}
    }
	
	@Override
    public int getRow(){
        return row;
    }

    @Override
    public int getCol(){
        return col;
    }
    
    //toString
    public String toString() {
    	return ("Spreadsheet Location: row: "+row+", col: "+col);
    }
    
    //equals method
    public boolean equals(Object o) {
    	if(o instanceof SpreadsheetLocation) {
    		SpreadsheetLocation obj = (SpreadsheetLocation) o;
    		if (this.getRow()==obj.getRow()&&this.getCol()==obj.getCol()) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
