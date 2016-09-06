import java.util.Scanner;

import karnaugh.KarnaughMap;



public class Test {
	
	public static final String file = "C:\\Users\\Exper1\\Desktop\\document.txt";
	public static final String stopWords = "C:\\Users\\Exper1\\Desktop\\stopwords.txt";
	public static final String marks = "C:\\Users\\Exper1\\Desktop\\marks.txt";
	public static final String directory = "C:\\Users\\Exper1\\Desktop\\projectCorpus";
	
	public static void main(String [] args){
		
		String expression = "visual.java'+tool.linux+visual.java'.linux'";
		
		System.out.println("Expression: " + expression);
		
		KarnaughMap km = new KarnaughMap(expression);
		
		System.exit(0);
		
		//System.out.println("Solved" + KarnaughMap.solve(expression));
		
		System.out.println();
		System.out.println("Press enter to proccess files");
		
		new Scanner(System.in).nextLine();
		
		long time = System.currentTimeMillis();
		
		Parser p = new Parser(stopWords, marks);
		
		p.readDocument(file);
		
		//p.readDirectory(directory);
		
		time = System.currentTimeMillis() - time;
		System.out.println();
		System.out.println("Total time : " + time);
	}
	
}
