package CipertextAttack;

import java.util.HashMap;

import Util.Key;

/**
 *  
 * @author GK
 *
 */
public class CipherTextAttack {
	Key keyBuilder = new Key();
	HashMap<Character,Character> key;
	HashMap<Character,Character> reverseKey;
	
	public CipherTextAttack(){
		key = keyBuilder.getKey();
		reverseKey = new HashMap<Character,Character>();
	}
	
	/**
	 * @param args - cipher text
	 */
	public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
		//The cipher text:
		String cipherText = args[0];
		if (cipherText == null){
			System.out.println("Please Enter a cipher Text file as input");
			return;
		}
		
		CipherTextAttack ct = new CipherTextAttack();
		Decrypt dec = new Decrypt(ct.getKey(),ct.getReverseKey(),cipherText);
		dec.decrypt();
		System.out.println((System.currentTimeMillis()-startTime)/1000F + " Seconds");
		
	}
	public HashMap<Character, Character> getKey() {
		return key;
	}
	public HashMap<Character, Character> getReverseKey() {
		return reverseKey;
	}
}

