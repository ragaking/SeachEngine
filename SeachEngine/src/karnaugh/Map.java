package karnaugh;

public class Map {
	
	public int row;
	public int column;
	public int width;
	public int height;
	
	public Map(int row, int column, int width, int height) {
		this.row = row;
		this.column = column;
		this.width = width;
		this.height = height;
	}
	
	public Map(){
		
	}
	
	public Expression toExpression(){
		
		//all states is none as default
		Expression ex = new Expression();
		
		//
		if(2 < KarnaughMap.numEle){
			//B isn't invalid
			
			ex.A.state = row < 2 ? Element._0 : Element._1;
			ex.B.state = ((row < 2)&&(3 < row)) ? Element._0 : Element._1; 
			
			if(height == 2){
				ex.A.state = (row % 2 == 0) ? ex.A.state : Element._NONE;
				ex.B.state = (row % 2 == 0) ? Element._NONE : ex.B.state;
			}
			else if(height == 1){
				ex.A.state = Element._NONE;
				ex.B.state = Element._NONE;
			}
			
		}
		else{
			//B is inlavid
			ex.B.state = Element._INVALID;
			
			if(height == 1){
				ex.A.state = row == 0 ? Element._0 : Element._1;
			}
		}
		
		if(3 < KarnaughMap.numEle){
			//D isn't invalid
			ex.C.state = column < 2 ? Element._0 : Element._1;
			ex.D.state = ((column < 2)&&(3 < column)) ? Element._0 : Element._1; 
			
			if(width == 2){
				ex.C.state = (column % 2 == 0) ? ex.C.state : Element._NONE;
				ex.D.state = (column % 2 == 0) ? Element._NONE : ex.D.state;
			}
			else if(width == 1){
				ex.C.state = Element._NONE;
				ex.D.state = Element._NONE;
			}
			
		}
		else{
			//D is invalid
			ex.D.state = Element._INVALID;
			
			if(width == 1){
				ex.C.state = column == 0 ? Element._0 : Element._1;
			}
		}
		
		return ex;
	}
	
	public void writeMap(){
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				
				KarnaughMap.matrix[(row + j) % KarnaughMap.row][(column + i) % KarnaughMap.col ] = 1;
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "row: " + row + " height:" + height + " column:" + column + " width:" + width ;
	}
	
	
	
}
