package CipertextAttack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CiperText {
	HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	static DestKey dk = new DestKey();
	public CiperText(){
		initLettersFreq();

	}
	/**
	 * Initializing the Letters Map Frequency
	 */
	private void initLettersFreq() {
		for (char ch = 'a'; ch <= 'z' ; ch++){
			lettersFreq_.put(ch, 0);
		}
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			lettersFreq_.put(ch, 0);
		}
		for (char ch = '0'; ch <= '9' ; ch++){
			lettersFreq_.put(ch, 0);
		}
	}
	
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
	
	/**
	 * Main program 
	 * @param ciperText
	 * @return the destination key
	 */
	private static DestKey cipher(String ciperText) {
    	BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(ciperText));
			String str;
		    while ((str = in.readLine()) != null) {
		    //Doing Operations on File :
		    	//calculating Frequency
		       calcFreq(str);
		    }
		    in.close();
		    
		    //Done Calculating with File
		    
		    //
		} catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
       	return dk;
	}
	
    /**
     * calculating Frequency
     * @param str - a String from the file.
     */
	private static void calcFreq(String str) {
		
		
	}
}



