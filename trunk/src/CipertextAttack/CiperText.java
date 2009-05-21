package CipertextAttack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CiperText {
	/**
	 * @param args - cipher text
	 */
	public static void main(String[] args) {
		//The cipher text:
		String ciperText = args[0];
		if (ciperText == null){
			System.out.println("There is now Text file as input");
			return;
		}
		DestKey key = cipher(ciperText);
		key.tostring(ciperText+"_key.txt");
	}

	private static DestKey cipher(String ciperText) {
		readFromFile(ciperText);
		return new DestKey(new Vector<Character>());
	}
	
    private static void readFromFile(String ciperText) {
    	BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(ciperText));
			String str;
		    while ((str = in.readLine()) != null) {
		       //calcFreq(str);
		    }
		    in.close();	
		} catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
       	
	}
}



