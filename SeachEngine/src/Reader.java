import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	public static BufferedReader getBufferedReader(String fileName){

		BufferedReader br = null;

		try {

			File f = new File(fileName);
			FileReader fr = new FileReader(f);
			br = new BufferedReader(fr);

		} catch (IOException ex) {
			
			ex.printStackTrace();
		}

		return br;
	}
}
