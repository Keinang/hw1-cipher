package CipertextAttack;

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
	Vector<Character> mostFreqLet  = new Vector<Character>();
	private Util util = new Util();
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private HashMap<Character, Integer> zugLettersFreq_ = new HashMap<Character, Integer>();
	private Character[] sortedLettersFreq_ = new Character[62];
	private Character[] sortedZugLettersFreq_ = new Character[62];
	private Key key_ = new Key();
	private Key keyOpposite_ = new Key();
	private Vector<String> wordsFromFile_ = new Vector<String>();
	private Dict dict = new Dict();
	
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
		
		util.getWordsFromFile(this.getWordsFromFile(),cipherText);
		calcFreq();
		sortFreq();
		calcZug();
		sortZug();
		
		search_the();
		search_to();
		search_that();
		search_s();
		//Start of Checking permutation
		//findNine();
		
		//print for testing
		util.printTempDecryptFile(this.key_,"encryptedTxt.txt");
		
		//printing the Result key to the output file :
    	util.printResult(this.key_,cipherText);
	}
	
	private void findNine() {
		Character[] mostFreq = {'a','o','n','i','s','r'}; 
		for (int i = 0; i < mostFreq.length; i++) {
			mostFreqLet.add(mostFreq[i]);
		}
		Vector<String> mostFreqLettersWords = wordsWithMostFreqLetters();
		double percent = 0.0;
		int numOfPerms = 0;
		
		for (int i=0;i<mostFreq.length;i++){
			substitute(sortedLettersFreq_[61-i],mostFreq[i]);
		}
		numOfPerms++;
		percent = checkCorrectWords(mostFreqLettersWords);
		System.out.println(percent);
		
		while (percent < 80){
			for (int i=0;i<mostFreq.length;i++){
				substitute(sortedLettersFreq_[61-i],mostFreq[i]);
			}
			numOfPerms++;
			percent = checkCorrectWords(mostFreqLettersWords);
			System.out.println(percent);
		}
		System.out.println("Found 9");
	}

	private Vector<String> wordsWithMostFreqLetters(){
		Vector<String> tmpVec = new Vector<String>();
		boolean flag = true;
		
		for(String str:this.getWordsFromFile()){
			flag = true;
			char[] tmpCharArray = str.toCharArray();
			for (Character ch:tmpCharArray){
				if (!mostFreqLet.contains(ch)){
					flag=false;
					break;
				}
			}
			if (flag){
				tmpVec.add(str);
			}
		}
		return tmpVec;
	}
	private double checkCorrectWords(Vector<String> correctWords) {
		double totalCorrectWords = correctWords.size();
		double counter = 0;
		for (String str:correctWords){
			if (dict.getSpecialWords().contains(str)){
				counter++;
			}
		}
		return (counter / totalCorrectWords ) * 100; 
	}
	private void search_s() {
		for (String str : this.getWordsFromFile()){
			if (str.length()> 1 && str.charAt(str.length()-2) == '\'' ){
				substitute(str.charAt(str.length()-1), 's');
				break;
			}
		}
	}
	private void search_that() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='t'&& str.charAt(3) =='t' 
				&& str.charAt(1) == 'h'  ){
				substitute(str.charAt(2), 'a');
				break;
			}
		}
	}
	private void search_to() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==2 && str.charAt(0) =='t'){
				substitute(str.charAt(1), 'o');
				break;
			}
		}
	}
	private void search_the() {
		substitute(this.sortedLettersFreq_[61],'t');
		substitute(this.sortedLettersFreq_[60],'e');
		int[] appear = new int[123];
		for (String str : this.getWordsFromFile()){
			if (str.length()==3 && str.charAt(0) =='t' && str.charAt(2) =='e'){
				appear[str.charAt(1)]++;
			}
		}
		int maxIndex = findMaxInArray(appear);
		int maxChar = appear[maxIndex];
		
		int[] appear2 = new int[123];
		substitute('e',this.sortedLettersFreq_[60]);
		substitute('t',this.sortedLettersFreq_[61]);
		substitute(this.sortedLettersFreq_[61],'e');
		substitute(this.sortedLettersFreq_[60],'t');
		
		for (int i=0;i<this.getWordsFromFile().size();i++){
			String str =this.getWordsFromFile().get(i);
			if (str.length() == 3 && str.charAt(0) =='t' && str.charAt(2) =='e'){
				appear2[str.charAt(1)]++;
			}
		}
		int maxIndex2 = findMaxInArray(appear2);
		int maxChar2 = appear2[maxIndex2];
		if (maxChar > maxChar2){
			substitute('t',this.sortedLettersFreq_[60]);
			substitute('e',this.sortedLettersFreq_[61]);
			substitute(this.sortedLettersFreq_[61],'t');
			substitute(this.sortedLettersFreq_[60],'e');
			substitute((char) maxIndex , 'h');
		}
		else{
			substitute((char) maxIndex2 , 'h');
		}
	}

	private int findMaxInArray(int[] appear) {
		int max = 0;
		int maxIndex=0;
		for(int i=0; i < appear.length;i++){
			if(appear[i] > max ){
				max = appear[i];
				maxIndex= i;
			}
		}
		return maxIndex;
	}

	/**
	 * Substitute between the Chars 
	 * @param oldChar - The encrypt char
	 * @param newChar - The origin char
	 */
	private void substitute(Character oldChar,Character newChar){
		//System.out.println("sub :"+oldChar +" with = "+newChar);
		Character pointToNewChar = this.keyOpposite_.getKey().get(newChar);
		Character pointFromOldChar = this.key_.getKey().get(oldChar);

		for (int i=0;i<this.getWordsFromFile().size();i++){
			String str = this.getWordsFromFile().elementAt(i);
			char[] chars = str.toCharArray();
			for(int j=0;j<chars.length;j++){
				if (chars[j] == oldChar){
					chars[j] = newChar;
				}
				else if (chars[j] == pointToNewChar){
					chars[j] = pointFromOldChar;
				}
			}
			this.getWordsFromFile().set(i,String.valueOf(chars) );
		}
		this.key_.getKey().put(newChar, oldChar);
		this.keyOpposite_.getKey().put(oldChar, newChar);
		this.key_.getKey().put(pointFromOldChar, pointToNewChar);
		this.keyOpposite_.getKey().put(pointToNewChar, pointFromOldChar);
	}

	private void sortFreq(){
		HashMap<Character, Integer> tmpLettersFreq_ = new HashMap<Character, Integer>();
		util.copyHashMap(tmpLettersFreq_,lettersFreq_);
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
	    util.copyHashMap(lettersFreq_,tmpLettersFreq_);
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
	 * Calculate frequency of Zug Letters like 'LL' , 'OO'
	 */
	private void calcZug() {
		int[] tmpFreq = new int[123];
		for (String str : this.wordsFromFile_){
			char[] charsInString = new char[str.length()];
			str.getChars(0, str.length(), charsInString, 0);
			
			for (int i=0;i<charsInString.length-1;i++){
				int tmpValue = Integer.valueOf(charsInString[i]);
				if (tmpValue>=48 && tmpValue <=57 ||tmpValue >=65 && tmpValue <=90 ||	
						tmpValue >=97 && tmpValue <=122){
					int tmpValue2 = Integer.valueOf(charsInString[i+1]);
					if (tmpValue == tmpValue2){
						tmpFreq[tmpValue]++;
					}
				}
			} 
			for (char ch = 'a'; ch <= 'z' ; ch++){
				zugLettersFreq_.put(ch, tmpFreq[ch]);
			}
			for (char ch = 'A'; ch <= 'Z' ; ch++){
				zugLettersFreq_.put(ch, tmpFreq[ch]);
			}
			for (char ch = '0'; ch <= '9' ; ch++){
				zugLettersFreq_.put(ch, tmpFreq[ch]);
			}
		}
	}
	private void sortZug(){
		HashMap<Character, Integer> tmpZugLettersFreq_ = new HashMap<Character, Integer>();
		util.copyHashMap(tmpZugLettersFreq_,zugLettersFreq_);
		List<Character> mapKeys = new ArrayList<Character>(((Map<Character, Integer>) this.zugLettersFreq_).keySet());
		List<Integer> mapValues = new ArrayList<Integer>(((Map<Character, Integer>) this.zugLettersFreq_).values());
		
		Collections.sort(mapKeys);
	    Collections.sort(mapValues);

	    Iterator<Integer> valueIt = mapValues.iterator();
	    int counter = 0;
	    while (valueIt.hasNext()) {
	        Integer val = valueIt.next();
	        Iterator<Character> keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	            Character key = keyIt.next();
	            Integer comp1 =  this.zugLettersFreq_.get(key);
	            
	            if (comp1 == val ){
	            	this.zugLettersFreq_.remove(key);
	                mapKeys.remove(key);
	                sortedZugLettersFreq_[counter]= key;
	                counter++;
	                break;
	            }
	        }
	    }
	    util.copyHashMap(zugLettersFreq_,tmpZugLettersFreq_);
	}
	public Vector<String> getWordsFromFile() {
		return wordsFromFile_;
	}
}

