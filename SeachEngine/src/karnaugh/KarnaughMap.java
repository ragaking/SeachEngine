import java.util.ArrayList;

public class KarnaughMap {

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
		/*
		 * if(numEle == 4){ row = 4; col = 4; }else if(numEle == 3){ row = 4;
		 * col = 2; } else if(numEle == 2){ row = 2; col = 2; } else{ row = 2;
		 * col = 0; }
		 */
		matrix = new int[row][col];
		//
		
		//Turning sub expression to expression objects
		String[] subEx = query.split("\\+");
		Expression[] expressions = new Expression[subEx.length];

		for (int i = 0; i < expressions.length; i++) {
			expressions[i] = new Expression(subEx[i]);
		}
		//--
		
		/**
		 * info about generated expressions
		 */
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
			/**
			 * Ýnfo
			 */
			System.out.println(maps[i]);
		}
		System.out.println("-------------------");
		// --printing karnaugh map
		printKarnaughMap();
		// creating captured maps
		ArrayList<Map> finalMaps = new ArrayList<Map>();

		// crate all possible maps for karnaugh map
		ArrayList<Map> generatedMaps = getMaps();

		/**
		 * info
		 */
		System.out.println("Availabe maps");
		for (int i = 0; i < generatedMaps.size(); i++) {
			System.out.println(generatedMaps.get(i));
		}
		System.out.println("--------------");
		/*
		 */
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
		
		/**
		 * info
		 */
		System.out.println("Captured Maps");
		for (int i = 0; i < finalMaps.size(); i++) {
			System.out.println(finalMaps.get(i));
		}
		System.out.println("--------------");
		/**
		 * output expressions
		 */
		//converting last maps to expression objects
		ArrayList<Expression> lastExps = new ArrayList<Expression>();
		
		//converting all maps to expression and adding to list
		for (int i = 0; i < finalMaps.size(); i++) {
			lastExps.add(finalMaps.get(i).toExpression());
		}
		
		/**
		 * info
		 */
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

}
