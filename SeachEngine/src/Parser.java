import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	public ArrayList<String> stopWords;
	public ArrayList<String> puncMarks;
	public ArrayList<String> particalWords;

	public Parser(String stopWordsFile, String marksFile) {
		
		//init arraylists
		stopWords = new ArrayList<String>();
		puncMarks = new ArrayList<String>();
		particalWords = new ArrayList<String>();

		try {
			// Reading stop words
			BufferedReader reader = Reader.getBufferedReader(stopWordsFile);
			while (reader.ready()) {
				stopWords.add(reader.readLine());
			}
			reader.close();
			//--
			// Reading punctuation marks
			reader = Reader.getBufferedReader(marksFile);
			while (reader.ready()) {
				puncMarks.add(reader.readLine());
			}
			reader.close();
			// --
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void readDirectory(String directoryName){
		
		String [] files = (new File(directoryName)).list();
		
		for (int i = 0; i < files.length; i++) {
			readDocument(directoryName + "\\" +files[i]);
		}
	}
	
	public void readDocument(String fileName) {

		//
		HashMap map = new HashMap(100);
		
		System.out.println("File proccess started:" + fileName);
		
		BufferedReader reader = null;

		try {
			// Read main file
			reader = Reader.getBufferedReader(fileName);

			String mainstr = "";
			String helperstr = "";
			String particalstr = "";

			while (reader.ready()) {

				mainstr = reader.readLine();

				// reset particalstr
				particalstr = "";

				// remove punctuation marks
				for (int j = 0; j < puncMarks.size(); j++) {

					mainstr = mainstr.replace(puncMarks.get(j), "");
				}

				// Getting each words to remove stop words
				int firstIndex = 0;
				int secondIndex = 0;

				do {
					// getting next blank's index
					secondIndex = mainstr.indexOf(' ', firstIndex + 1);

					// getting word
					if (secondIndex == -1) {
						helperstr = mainstr.substring(firstIndex);
					} else {
						helperstr = mainstr.substring(firstIndex, secondIndex);
					}

					// for reset the starting blank
					firstIndex = secondIndex;

					// Salt word , removes extra blanks
					helperstr = helperstr.trim();

					// Ýf this word is an stop word reset the helper so it does
					// not print out
					/**
					for (int i = 0; i < stopWords.size(); i++) {
						if (helperstr.equals(stopWords.get(i))) {
							helperstr = null;
							break;
						}
					}
					*/
					//temp
					for (int i = 0; i < stopWords.size(); i++) {
						if (helperstr.toLowerCase().equals(stopWords.get(i).toLowerCase())) {
							helperstr = null;
							break;
						}
					}
					//--
					
					if (helperstr != null){
						particalstr += helperstr + " ";
						System.out.println(helperstr);
						map.add(helperstr);
					}
						
				} while (secondIndex != -1);

				//System.out.println(particalstr);
			}

			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("File proccess finished:" + fileName);
		
		map.print();
		
	}

}
