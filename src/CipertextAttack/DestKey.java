package CipertextAttack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class DestKey {
	
	public DestKey(Vector<Character> destKey_) {
		this.destKey_ = destKey_;
	}

	private Vector<Character> destKey_;
	
	public void tostring(String filename){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			out.write("{");
			for (Character ch : destKey_ ){
				out.write(ch+",");
			}
			out.write("}");
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDestKey_(Vector<Character> destKey_) {
		this.destKey_ = destKey_;
	}

	public Vector<Character> getDestKey_() {
		return destKey_;
	}
	
}
