package CipertextAttack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CipherTextAttack {
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private DestKey destKey_ = new DestKey();
	
	
	public CipherTextAttack(){
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
		String cipherText = args[0];
		if (cipherText == null){
			System.out.println("There is no Text file as input");
			return;
		}
		CipherTextAttack ct = new CipherTextAttack();
		ct.initLettersFreq();
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//make encrypt to work with
		Encrypt encrypt = new Encrypt(cipherText, "encryptedTxt.txt");
		encrypt.on();
		cipherText = "encryptedTxt.txt";
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		ct.cipher(cipherText);
	}
	
	/**
	 * Main program 
	 * @param ciperText
	 * @return the destination key
	 */
	private void cipher(String cipherText) {
    	BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(cipherText));
			String str;
		    while ((str = in.readLine()) != null) {
		    	//Doing Operations on File :
		    	//calculating Frequency
		       calcFreq(str);
		    }
		    in.close();
		    
		    //Done Calculating with File
		    printFreq();
		} catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
		//writing to file the Result key :
		int tmpIndex = cipherText.indexOf('.');
		String tmpFilename = cipherText.substring(0,tmpIndex);
		destKey_.tostring(tmpFilename+"_key.txt");
	}
	
    /**
     * calculating Frequency
     * @param str - a String from the file.
     */
	private void calcFreq(String str) {
		char[] charsInString = new char[str.length()];
		str.getChars(0, str.length(), charsInString, 0);
		for (Character ch:charsInString){
			if (Integer.valueOf(ch) >=48 && Integer.valueOf(ch) <=57 ||
				Integer.valueOf(ch) >=65 && Integer.valueOf(ch) <=90 ||	
				Integer.valueOf(ch) >=97 && Integer.valueOf(ch) <=122){
				int tmpValue = lettersFreq_.get(ch);
				lettersFreq_.put(ch, ++tmpValue);
			} 
		}
	}
	private void printFreq(){
		System.out.println("Frequently of text :");
		for (char ch = 'a'; ch <= 'z' ; ch++){
			System.out.print(ch +"="+lettersFreq_.get(ch)+" ");
		}
		System.out.println();
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			System.out.print(ch +"="+lettersFreq_.get(ch)+" ");
		}
		System.out.println();
		for (char ch = '0'; ch <= '9' ; ch++){
			System.out.print(ch +"="+lettersFreq_.get(ch)+" ");
		}
	}
}



