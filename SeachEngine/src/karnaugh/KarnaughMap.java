package karnaugh;

import java.util.ArrayList;

public class KarnaughMap {
	
	private String expression;

	private int matrix[][];
	private String values[];
	private int row;
	private int col;
	private int numEle;
	
	
	public KarnaughMap(String expression){
		this.expression = expression;
	}
	
	public void solve(){
		//determine there is how much different keys
		//remove all unneccesary chars and remain just element
		String helper = ((expression.replace("\'", "")).replace(".", " ")).replace("+", " "); 
		String arr[] = helper.split(" ");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			if (!list.contains(arr[i])) {
				list.add(arr[i]);
			}
		}
		//
		//adding element names to an array
		values = new String[4];
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			values[index] = list.get(i);
			index += 2;
			if (index == 4) {
				index = 1;
			}
		}
		//--Saving number of keywords and initialize matrix
		numEle = list.size();

		row = numEle > 2 ? 4 : 2;
		col = numEle < 4 ? 2 : 4;
		//
		matrix = new int[row][col];
		//
		
		//Turning sub expression to expression objects
		String[] subEx = expression.split("\\+");
		Expression[] expressions = new Expression[subEx.length];

		//--
		for (int i = 0; i < expressions.length; i++) {
			expressions[i] = new Expression(subEx[i]);
		}
		//--
		//System.out.println("Generated Expressions");
		//for (int i = 0; i < expressions.length; i++) {
		//	System.out.println(expressions[i]);
		//}
		//System.out.println("--------------------");
		//--
		
		//convert expressions to maps and printing all
		//System.out.println("Generated Maps");
		Map[] maps = new Map[expressions.length];
		for (int i = 0; i < maps.length; i++) {
			// convert expressions to maps
			maps[i] = expressions[i].toMap();
			// writing map to karnaugh map
			maps[i].writeMap();
			
			//System.out.println(maps[i]);
		}
		//System.out.println("-------------------");
		// --printing karnaugh map
		//printKarnaughMap();
		// creating captured maps
		ArrayList<Map> finalMaps = new ArrayList<Map>();

		// crate all possible maps for karnaugh map
		ArrayList<Map> generatedMaps = getMaps();

		//System.out.println("Availabe maps");
		//for (int i = 0; i < generatedMaps.size(); i++) {
			//System.out.println(generatedMaps.get(i));
		//}
		//System.out.println("--------------");
		//getting all available maps in Karnaugh...
		for (int i = 0; i < generatedMaps.size(); i++) {

			Map m = generatedMaps.get(i);

			int length_r = row - m.height == 0 ? 1 : row;
			int length_c = col - m.width == 0 ? 1 : col;

			for (int f_r = 0; f_r < length_r; f_r++) {
				for (int f_c = 0; f_c < length_c; f_c++) {

					Map temp = new Map(f_r, f_c, m.width, m.height);
					
					if(canContain(temp)){
						
						finalMaps.add(temp);
						
						addionToMap(temp);
					}
				}
			}
		}
		
		//System.out.println("Captured Maps");
		//for (int i = 0; i < finalMaps.size(); i++) {
			//System.out.println(finalMaps.get(i));
		//}
		//System.out.println("--------------");
		
		//converting last maps to expression objects
		ArrayList<Expression> lastExps = new ArrayList<Expression>();
		
		//converting all maps to expression and adding to list
		for (int i = 0; i < finalMaps.size(); i++) {
			lastExps.add(finalMaps.get(i).toExpression());
		}
		
		//System.out.println("Converted Expressions");
		//for (int i = 0; i < lastExps.size(); i++) {
			//System.out.println(lastExps.get(i));
		//}
		//System.out.println("-------------------");
		
		//returned query
		String result = "";
		
		for (int i = 0; i < lastExps.size(); i++) {
			result += lastExps.get(i).getExpression();
			if(i != lastExps.size() - 1){
				result += "+";
			}
		}
		
		System.out.println("Result is waiting us : " + result);
	}
	
	private void addionToMap(Map temp) {
		// TODO Auto-generated method stub
		for (int i = 0; i < temp.width; i++) {
			for (int j = 0; j < temp.height; j++) {
				matrix[(temp.row + j) % KarnaughMap.this.row][(temp.column + i) % KarnaughMap.this.col]++;
			}
		}
	}

	private boolean canContain(Map temp) {
		// TODO Auto-generated method stub
		boolean one = false;
		
		for (int i = 0; i < temp.width; i++) {
			for (int j = 0; j < temp.height; j++) {
				if(matrix[(temp.row + j) % KarnaughMap.this.row][(temp.column + i) % KarnaughMap.this.col] == 0){
					return false;
				}
				
				if(matrix[(temp.row + j) % KarnaughMap.this.row][(temp.column + i) % KarnaughMap.this.col] == 1){
					one = true;
				}
			}
		}
		return one;
	}

	private ArrayList<Map> getMaps() {
		ArrayList<Map> map = new ArrayList<Map>();

		int mapSize[] = {// width,heigth
				4, 4,// 16
				4, 2,// 8
				2, 4,// 8
				2, 2,// 4
				1, 4,// 4
				4, 1,// 4
				1, 2,// 2
				2, 1,// 2
				1, 1 // 1
		};

		int idealLength = mapSize.length / 2;

		for (int i = 0; i < idealLength; i++) {

			Map m = new Map();
			if(mapSize[i * 2] > KarnaughMap.this.row || mapSize[i * 2 + 1] > KarnaughMap.this.col){
				continue;
			}
			m.width = mapSize[i * 2 + 1];
			m.height = mapSize[i * 2];
			map.add(m);
		}

		return map;
	}
	
	private class Element{
		//--for states
		public static final int _0 = 0;
		public static final int _1 = 1;
		public static final int _NONE = -1;
		public static final int _INVALID = -2;
		
		public int state;
		public int index;
		
		public Element(int index){
			this.index = index;
			this.state = _NONE;
		}
		
		//expression visual'.tool
		public void setUp(String expression){
			
			if(state == _INVALID){
				return;
			}
			
			if(expression.contains(KarnaughMap.this.values[index])){	
				String helper[] = expression.split("\\.");
				for (int i = 0; i < helper.length; i++) {
					//
					if(helper[i].contains(KarnaughMap.this.values[index])){
						//setting value
						if(helper[i].contains("'")){
							state = _0;
						}
						else{
							state = _1;
						}
					}
				}
			}
		}
	}
	
	private class Expression{
		
		public Element A;//--as index 0		
		public Element B;//--as index 1		
		public Element C;//--as index 2
		public Element D;//--as index 3
		
		public Expression(){
			A = new Element(0);
			B = new Element(1);
			C = new Element(2);
			D = new Element(3);
		}
		
		public Expression(String expression){
			this();
			
			//--
			if(KarnaughMap.this.numEle == 2){
				B.state = Element._INVALID;
				D.state = Element._INVALID;
			}
			else if(KarnaughMap.this.numEle == 3){
				D.state = Element._INVALID;
			}
			//--
			A.setUp(expression);
			B.setUp(expression);
			C.setUp(expression);
			D.setUp(expression);
		}
		
		public Map toMap(){
			
			Map m = new Map();
			//--detech row
			if(B.state != Element._INVALID){
				//--
				if(A.state == Element._NONE && B.state == Element._NONE){
					m.row = 1;
					m.height = 4;
				}
				else if(A.state == Element._NONE){
					//Check B
					m.row = B.state == Element._0 ? 3 : 1;
					m.height = 2;
				}
				else if(B.state == Element._NONE){
					//Check A
					m.row = A.state == Element._0 ? 0 : 2;
					m.height = 2;
				}
				else{
					m.row = A.state == Element._0 ? 0 : 2;
					if(A.state != B.state){
						m.row++;
					}
					m.height = 1;
				}
			}
			else{
				//When there is no B component
				if(A.state == Element._NONE){
					m.row = 0;
					m.height = 2;
				}
				else{
					m.row = A.state == Element._0 ? 0 : 1;
					m.height = 1;
				}
			}
			//Detech column
			//--
			if(D.state != Element._INVALID){
				//--
				if(C.state == Element._NONE && D.state == Element._NONE){
					m.column = 1;
					m.width = 4;
				}
				else if(C.state == Element._NONE){
					//Check B
					m.column = D.state == Element._0 ? 3 : 1;
					m.width = 2;
				}
				else if(D.state == Element._NONE){
					//Check A
					m.column = C.state == Element._0 ? 0 : 2;
					m.width = 2;
				}
				else{
					m.column = C.state == Element._0 ? 0 : 2;
					if(C.state != D.state){
						m.column++;
					}
					m.width = 1;
				}
				//--
			}
			else{
				//When there is no D component
				if(C.state == Element._NONE){
					m.column = 0;
					m.width = 2;
				}
				else{
					m.column = C.state == Element._0 ? 0 : 1;
					m.width = 1;
				}
			}
			return m;
		}


		public String getExpression() {
			//Printed expression
			ArrayList<String> list = new ArrayList<String>();
			String ex = "";
			
			if(A.state == Element._0){
				list.add(KarnaughMap.this.values[A.index] + "'");
			}
			else if(A.state == Element._1){
				list.add(KarnaughMap.this.values[A.index]);
			}
			
			if(B.state == Element._0){
				list.add(KarnaughMap.this.values[B.index] + "'");
			}
			else if(B.state == Element._1){
				list.add(KarnaughMap.this.values[B.index]);
			}
			
			if(C.state == Element._0){
				list.add(KarnaughMap.this.values[C.index] + "'");
			}
			else if(C.state == Element._1){
				list.add(KarnaughMap.this.values[C.index]);
			}
			
			if(D.state == Element._0){
				list.add(KarnaughMap.this.values[D.index] + "'");
			}
			else if(D.state == Element._1){
				list.add(KarnaughMap.this.values[D.index]);
			}
			
			for (int i = 0; i < list.size(); i++) {
				ex += list.get(i);
				if(i != list.size() - 1){
					ex += ".";
				}
			}
			return ex;
		}
	}
	
	private class Map{
		
		public int row;
		public int column;
		public int width;
		public int height;
		
		public Map(){			
		}
		
		public Map(int row, int column, int width, int height) {
			this.row = row;
			this.column = column;
			this.width = width;
			this.height = height;
		}
		
		public Expression toExpression(){
			
			//all states is none as default
			Expression ex = new Expression();
			
			//
			if(2 < KarnaughMap.this.numEle){
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
			
			if(3 < KarnaughMap.this.numEle){
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
					KarnaughMap.this.matrix[(row + j) % KarnaughMap.this.row][(column + i) % KarnaughMap.this.col ] = 1;
				}
			}
		}
	}
	
	public static class KarnaughMapException extends Exception{
		
	}
	/*
	public static int matrix[][];
	// --0 as A, 1 as B, 2 as C, 3 as D
	public static String values[];
	public static int row;
	public static int col;
	public static int numEle;

	public static String solve(final String query) {
		
		//calculate how many different key word are there ?
		String helper = ((query.replace("\'", "")).replace(".", " ")).replace(
				"+", " ");

		String arr[] = helper.split(" ");
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < arr.length; i++) {
			if (!list.contains(arr[i])) {
				list.add(arr[i]);
			}
		}
		//--
		
		//--Saving all keywords
		values = new String[4];

		int index = 0;
		for (int i = 0; i < list.size(); i++) {

			values[index] = list.get(i);
			index += 2;
			if (index == 4) {
				index = 1;
			}
		}
		//
		
		//--Saving number of keywords and initialize matrix
		numEle = list.size();

		row = numEle > 2 ? 4 : 2;
		col = numEle < 4 ? 2 : 4;
		
		 // if(numEle == 4){ row = 4; col = 4; }else if(numEle == 3){ row = 4;
		 // col = 2; } else if(numEle == 2){ row = 2; col = 2; } else{ row = 2;
		 // col = 0; }
		 
		matrix = new int[row][col];
		//
		
		//Turning sub expression to expression objects
		String[] subEx = query.split("\\+");
		Expression[] expressions = new Expression[subEx.length];

		for (int i = 0; i < expressions.length; i++) {
			expressions[i] = new Expression(subEx[i]);
		}
		//--
		
		System.out.println("Generated Expressions");
		for (int i = 0; i < expressions.length; i++) {
			System.out.println(expressions[i]);
		}
		System.out.println("--------------------");
		//--
		
		//convert expressions to maps and printing all
		System.out.println("Generated Maps");
		Map[] maps = new Map[expressions.length];
		for (int i = 0; i < maps.length; i++) {
			// convert expressions to maps
			maps[i] = expressions[i].toMap();
			// writing map to karnaugh map
			maps[i].writeMap();
			
			System.out.println(maps[i]);
		}
		System.out.println("-------------------");
		// --printing karnaugh map
		printKarnaughMap();
		// creating captured maps
		ArrayList<Map> finalMaps = new ArrayList<Map>();

		// crate all possible maps for karnaugh map
		ArrayList<Map> generatedMaps = getMaps();

		System.out.println("Availabe maps");
		for (int i = 0; i < generatedMaps.size(); i++) {
			System.out.println(generatedMaps.get(i));
		}
		System.out.println("--------------");
		//getting all available maps in Karnaugh...
		for (int i = 0; i < generatedMaps.size(); i++) {

			Map m = generatedMaps.get(i);

			int length_r = row - m.height == 0 ? 1 : row;
			int length_c = col - m.width == 0 ? 1 : col;

			for (int f_r = 0; f_r < length_r; f_r++) {
				for (int f_c = 0; f_c < length_c; f_c++) {

					Map temp = new Map(f_r, f_c, m.width, m.height);
					
					if(canContain(temp)){
						
						finalMaps.add(temp);
						
						addionToMap(temp);
					}
				}
			}
		}
		
		System.out.println("Captured Maps");
		for (int i = 0; i < finalMaps.size(); i++) {
			System.out.println(finalMaps.get(i));
		}
		System.out.println("--------------");
		
		//converting last maps to expression objects
		ArrayList<Expression> lastExps = new ArrayList<Expression>();
		
		//converting all maps to expression and adding to list
		for (int i = 0; i < finalMaps.size(); i++) {
			lastExps.add(finalMaps.get(i).toExpression());
		}
		
		System.out.println("Converted Expressions");
		for (int i = 0; i < lastExps.size(); i++) {
			System.out.println(lastExps.get(i));
		}
		System.out.println("-------------------");
		
		//returned query
		String result = "";
		
		for (int i = 0; i < lastExps.size(); i++) {
			result += lastExps.get(i).getExpression();
			if(i != lastExps.size() - 1){
				result += "+";
			}
		}
		
		return result;
	}

	private static void addionToMap(Map temp) {
		// TODO Auto-generated method stub
		for (int i = 0; i < temp.width; i++) {
			for (int j = 0; j < temp.height; j++) {
				matrix[(temp.row + j) % KarnaughMap.row][(temp.column + i) % KarnaughMap.col]++;
			}
		}
	}

	private static boolean canContain(Map temp) {
		// TODO Auto-generated method stub
		boolean one = false;
		
		for (int i = 0; i < temp.width; i++) {
			for (int j = 0; j < temp.height; j++) {
				if(matrix[(temp.row + j) % KarnaughMap.row][(temp.column + i) % KarnaughMap.col] == 0){
					return false;
				}
				
				if(matrix[(temp.row + j) % KarnaughMap.row][(temp.column + i) % KarnaughMap.col] == 1){
					one = true;
				}
			}
		}
		
		return one;
	}

	private static ArrayList<Map> getMaps() {

		ArrayList<Map> map = new ArrayList<Map>();

		int mapSize[] = {// width,heigth
		4, 4,// 16
				4, 2,// 8
				2, 4,// 8
				2, 2,// 4
				1, 4,// 4
				4, 1,// 4
				1, 2,// 2
				2, 1,// 2
				1, 1 // 1
		};

		int idealLength = mapSize.length / 2;

		for (int i = 0; i < idealLength; i++) {

			Map m = new Map();
			if(mapSize[i * 2] > KarnaughMap.row || mapSize[i * 2 + 1] > KarnaughMap.col){
				continue;
			}
			m.width = mapSize[i * 2 + 1];
			m.height = mapSize[i * 2];
			map.add(m);
		}

		return map;
	}

	public static void printKarnaughMap() {

		System.out.println("Kanaugh Map");
		System.out.println("AB\\CD");
		
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print("    " + matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println("------------");
	}
	*/
}
