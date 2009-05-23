package CipertextAttack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
/**
 *  If there is a word with XXXX'z ==> z = S 
 *  
 * @author GK
 *
 */
public class CipherTextAttack {
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private Key key_ = new Key();
	private Vector<String> wordsFromFile_ = new Vector<String>();
	
	/**
	 * @param args - cipher text
	 */
	public static void main(String[] args) {
		//The cipher text:
		String cipherText = args[0];
		if (cipherText == null){
			System.out.println("Please Enter a cipher Text file as input");
			return;
		}
		
		//~~~~~~~~~~~~~~~~~~~~~ Encrypt File (Only for test) ~~~~~~~~~~~~~~~
		//make encrypt file to work with
		Encrypt encrypt = new Encrypt();
		encrypt.encryptWithKey(cipherText, "encryptedTxt.txt");
		cipherText = "encryptedTxt.txt";
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		CipherTextAttack ct = new CipherTextAttack();
		ct.decrypt(cipherText);
	}
	
	/**
	 * Main program 
	 * @param cipherText - a Simple cipher text
	 * @return the destination key
	 */
	public void decrypt(String cipherText) {
		this.initLettersFreq();
		this.getWordsFromFile(cipherText);
		//Doing Statistics on words :
		this.printWordsFromFile();
//        calcFreq(str);
	    //Done Calculating with File
//	    printFreq();
		
    	this.printResult(cipherText);
	}
	

	/**
	 * writing to file the Result key :
	 */
	private void printResult(String cipherText) {
		int tmpIndex = cipherText.indexOf('.');
		String tmpFilename = cipherText.substring(0,tmpIndex);
		key_.tostring(tmpFilename+"_key.txt");
	}

	private void getWordsFromFile(String cipherText) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(cipherText));
			String str;
		    while ((str = in.readLine()) != null) {
		    	//Getting Words into HashMap
		    	addStrToVector(str);
		    }
		    in.close();
		    
		} catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
	}
	/**
	 * 
	 * @param str - a String from a file to insert the HashMap<String,Integer>
	 */
	private void addStrToVector(String str) {
		while (str.length() > 0){
			int indexOfSpace = str.indexOf(" ");
			
			if (indexOfSpace == -1){ //Checking if it's the last word:
				addWord(str);
				return;
			}
			addWord(str.substring(0, indexOfSpace));
			str = str.substring(indexOfSpace+1,str.length());
		}
	}
	/**
	 * Removing ( ) , . from the word and Adding to Vector
	 * @param tmpWord
	 */
	private void addWord(String str) {
		str = removeSignsFromEndOfWord('.',str);
		str = removeSignsFromEndOfWord(')',str);
		str = removeSignsFromEndOfWord(':',str);
		str = removeSignsFromBeginingOfWord('(',str);
		str = removeSignsFromEndOfWord(',',str);
		if (hyphen(str)){ 
			return;
		}
		wordsFromFile_.add(str);
	}
	/**
	 * //check for XXX-YYY Separation to 2 words 
	 * @return true if there was a '-' hyphen
	 */
	public boolean hyphen(String str) {
		int tmpIndexOfchar = str.indexOf('-');
		if (tmpIndexOfchar > 0){
			wordsFromFile_.add(str.substring(0,tmpIndexOfchar));
			wordsFromFile_.add(str.substring(tmpIndexOfchar+1,str.length()));
			return true;
		}
		return false;
	}

	public String removeSignsFromEndOfWord(char ch, String str) {
		int tmpIndexOfchar = str.indexOf(ch);
		if (tmpIndexOfchar > -1){
			return str.substring(0,tmpIndexOfchar);
		}
		return str;
	}
	public String removeSignsFromBeginingOfWord(char ch, String str) {
		int tmpIndexOfchar = str.indexOf(ch);
		if (tmpIndexOfchar > -1){
			return str.substring(tmpIndexOfchar+1,str.length());
		}
		return str;
	}
	
	private void printWordsFromFile() {
		for (String tmpString : wordsFromFile_){
			System.out.println(tmpString);
		}
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
	/**
	 * Printing the Frequently table to SYSO
	 */
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

	public Vector<String> getWordsFromFile() {
		return wordsFromFile_;
	}
}

