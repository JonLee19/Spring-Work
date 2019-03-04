package textExcel;
//This class contains methods that represent a spreadsheet's functionality
//@author Jon Lee
//@version March 4, 2019
// Update this file with your own code.

public class Spreadsheet implements Grid {
	
	//constructor
	public Spreadsheet() {
		//initialize a 2D array of EmptyCell objects
		Cell[][] sheet= new Cell[getRows()][getCols()];
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[i].length; j++) {
				sheet[i][j] = new EmptyCell();
			}
		}
	}
	
	@Override
	public String processCommand(String command){
		return "";
	}

	@Override
	public int getRows(){	
		return 20;
	}

	@Override
	public int getCols(){
		return 12;
	}

	@Override
	public Cell getCell(Location loc){
		return null;
	}

	@Override
	public String getGridText(){
		return null;
	}

}
