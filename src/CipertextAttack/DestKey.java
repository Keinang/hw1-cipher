package CipertextAttack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class DestKey {
	
	public DestKey() {
		destKey_ = new HashMap<Character, Character>();
		
		
	}

	private HashMap<Character, Character> destKey_;
	
	public void tostring(String filename){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			out.write("{");
			for (Iterator<Character> iterator = destKey_.keySet().iterator(); iterator.hasNext();) {
				Character keyChar = (Character) iterator.next();
				out.write(destKey_.get(keyChar)+",");
			}
			out.write("}");
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDestKey_(HashMap<Character,Character>  destKey_) {
		this.destKey_ = destKey_;
	}

	public HashMap<Character,Character> getDestKey_() {
		return destKey_;
	}
	
}
