package CipertextAttack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import Util.Key;

public class PlainTextAttack {
	Key keyBuilder = new Key();
	HashMap<Character,Character> key;
	HashMap<Character,Character> reverseKey;
	
	public PlainTextAttack(String plainTxt,String textCrypted){
		key = keyBuilder.getKey();
		reverseKey = new HashMap<Character,Character>();
		initKeys(plainTxt,textCrypted);
	}
	/**
	 * @param args - cipher text
	 */
	public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
		String plainText = args[0]; 		  //a simple text
		String plainTextCrypted = args[1];  //crypted text
		String cipherText = args[2];		  //crypted text of plain text
		if (cipherText == null || plainText==null || plainTextCrypted==null){
			System.out.println("Please Enter correct inputs ");
			return;
		}
		PlainTextAttack pt = new PlainTextAttack(plainText,plainTextCrypted);
		Decrypt dec = new Decrypt(pt.getKey(),pt.getReverseKey(),cipherText);
		dec.decrypt();
		
		System.out.println((System.currentTimeMillis()-startTime)/1000F + " Seconds");
		
	}

	private void initKeys(String plainText, String plainTextDecrypted) {
		RandomAccessFile rac1;
		RandomAccessFile rac2;
		String str1 = null;
		String str2 = null;
		try {
			rac1 = new RandomAccessFile(plainText, "r");
			rac2 = new RandomAccessFile(plainTextDecrypted, "r");
			int length1 = (int)rac1.length();
			int length2 = (int)rac2.length();
			byte[] data1= new byte[length1];
			byte[] data2 = new byte[length2];
			rac1.read(data1, 0, length1);
			rac2.read(data2, 0, length2);
			str1 = new String(data1);
			str2 = new String(data2);
			rac1.close();
			rac2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		char[] chars1 = str1.toCharArray();
		char[] chars2 = str2.toCharArray();
		for (int i=0 ; i<chars1.length;i++){
			if ((Integer.valueOf(chars1[i]) >=48 && Integer.valueOf(chars1[i]) <=57) ||
				(Integer.valueOf(chars1[i]) >=65 && Integer.valueOf(chars1[i]) <=90) ||	
				(Integer.valueOf(chars1[i]) >=97 && Integer.valueOf(chars1[i]) <=122)){
				key.put(chars2[i], chars1[i]);
				reverseKey.put(chars1[i], chars2[i]);
			}
		}
	}
	public HashMap<Character, Character> getKey() {
		return key;
	}
	public HashMap<Character, Character> getReverseKey() {
		return reverseKey;
	}
}
