package CipertextAttack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
/**
 * The Class Key contain the Key structure.
 * @author GK
 */
public class Key {
	
	private HashMap<Character, Character> key;
	
	public Key() {
		key = new HashMap<Character, Character>();
		for (char ch = 'a'; ch <= 'z' ; ch++){
			key.put(ch, '?');
		}
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			key.put(ch, '?');
		}
		for (char ch = '0'; ch <= '9' ; ch++){
			key.put(ch, '?');
		}
	}

	public void tostring(String outputFile){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
			for (char ch = 'a'; ch <= 'z' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = 'A'; ch <= 'Z' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = '0'; ch <= '9' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
			
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<Character, Character> getKey() {
		return key;
	}
	
}
