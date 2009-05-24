package CipertextAttack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
/**
 *  If there is a word with XXXX'z ==> z = S 
 *  
 * @author GK
 *
 */
public class CipherTextAttack {
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private Character[] sortedLettersFreq_ = new Character[62];
	private Key key_ = new Key();
	private Vector<String> wordsFromFile_ = new Vector<String>();
	private int lettersFound_ = 0;
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
		int correctWords = 0;
		
		this.getWordsFromFile(cipherText);
		//this.printWordsFromFile();
		//System.out.println("Number of words : "+this.getWordsFromFile().size());
		calcFreq();
		sortFreq();//sorting the frequently table
		printSortedFreqLetters();
		printFreq();
		
		//we stop the program if we found at least 50% of the words 
		//while (correctWords < this.getWordsFromFile().size()/2){
			substitute(sortedLettersFreq_[61],'e');
			substitute(sortedLettersFreq_[60],'t');
			printWordsFromFile();
		//}
		
		//printing the Result key to the output file :
    	this.printResult(cipherText);
	}
	/**
	 * Substitute between the Chars 
	 * @param oldChar - The encrypt char
	 * @param newChar - The origin char
	 */
	private void substitute(Character oldChar,Character newChar){
		lettersFound_++;
		System.out.println();
		System.out.println("sub :"+oldChar +" with = "+newChar);
		for (int j = 0 ; j< this.wordsFromFile_.size(); j++){
			String str = this.wordsFromFile_.elementAt(j);
			char [] word  = str.toCharArray();
			for(int i =0 ;i<word.length;i++){
				if (str.charAt(i) == oldChar){
					word[i] = newChar;
				}else {
					if (str.charAt(i) == newChar){
						word[i] = oldChar;
					}
				}
				this.wordsFromFile_.set(j, word.toString());
			}
		}
		this.key_.getKey().put(newChar, oldChar);
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
	 * //check for SSS-YYY Separation to 2 words 
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

	private void sortFreq(){
		HashMap<Character, Integer> tmpLettersFreq_ = new HashMap<Character, Integer>();
		copyHashMap(tmpLettersFreq_,lettersFreq_);
		List<Character> mapKeys = new ArrayList<Character>(((Map<Character, Integer>) this.lettersFreq_).keySet());
		List<Integer> mapValues = new ArrayList<Integer>(((Map<Character, Integer>) this.lettersFreq_).values());
		
		Collections.sort(mapKeys);
	    Collections.sort(mapValues);

	    Iterator<Integer> valueIt = mapValues.iterator();
	    int counter = 0;
	    while (valueIt.hasNext()) {
	        Integer val = valueIt.next();
	        Iterator<Character> keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	            Character key = keyIt.next();
	            Integer comp1 =  this.lettersFreq_.get(key);
	            
	            if (comp1 == val ){
	            	this.lettersFreq_.remove(key);
	                mapKeys.remove(key);
	                sortedLettersFreq_[counter]= key;
	                counter++;
	                break;
	            }
	        }
	    }
	    copyHashMap(lettersFreq_,tmpLettersFreq_);
	}
	private void copyHashMap(HashMap<Character, Integer> hashTo,
			HashMap<Character, Integer> hashFrom) {
		Iterator<Character> from = hashFrom.keySet().iterator();
		while(from.hasNext()){
			Character ch = (Character) from.next();
			Integer value = hashFrom.get(ch);
			hashTo.put(ch, value);
		}
	}

	/**
	 * Printing the sorted Frequency Letters :
	 */
	private void printSortedFreqLetters(){
		for (Character ch:this.sortedLettersFreq_){
			System.out.print(ch);
		}
		System.out.println();
	}
	/**
	 * Printing the words from the file :
	 */
	private void printWordsFromFile() {
		for (String tmpString : wordsFromFile_){
			System.out.println(tmpString);
		}
	}
	
    /**
     * calculating Frequency
     * @param str - a String from the file.
     */
	private void calcFreq() {
		int[] tmpFreq = new int[123];
		for (String str : this.wordsFromFile_){
			char[] charsInString = new char[str.length()];
			str.getChars(0, str.length(), charsInString, 0);
			for (Character ch:charsInString){
				int tmpValue = Integer.valueOf(ch);
				if (tmpValue>=48 && tmpValue <=57 ||tmpValue >=65 && tmpValue <=90 ||	
						tmpValue >=97 && tmpValue <=122){
					tmpFreq[tmpValue]++;
				} 
			}
			for (char ch = 'a'; ch <= 'z' ; ch++){
				lettersFreq_.put(ch, tmpFreq[ch]);
			}
			for (char ch = 'A'; ch <= 'Z' ; ch++){
				lettersFreq_.put(ch, tmpFreq[ch]);
			}
			for (char ch = '0'; ch <= '9' ; ch++){
				lettersFreq_.put(ch, tmpFreq[ch]);
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

