package CipertextAttack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DestKey {
	
	public DestKey() {
		destKey_ = new HashMap<Character, Character>();
		for (char ch = 'a'; ch <= 'z' ; ch++){
			destKey_.put(ch, '?');
		}
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			destKey_.put(ch, '?');
		}
		for (char ch = '0'; ch <= '9' ; ch++){
			destKey_.put(ch, '?');
		}
	}

	private HashMap<Character, Character> destKey_;
	
	public void tostring(String filename){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			for (char ch = 'a'; ch <= 'z' ; ch++){
				out.write(ch +"="+destKey_.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = 'A'; ch <= 'Z' ; ch++){
				out.write(ch +"="+destKey_.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = '0'; ch <= '9' ; ch++){
				out.write(ch +"="+destKey_.get(ch)+" ");
			}
			
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
