package CipertextAttack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Util {
	//private HashMap<Character, Integer> zugLettersFreq_ = new HashMap<Character, Integer>();
	//private Character[] sortedZugLettersFreq_ = new Character[62];
	//private Dict dict = new Dict();
	
	private Vector<String> wordsFromFile_ = new Vector<String>();
	public HashMap<String,Integer> freqAllWords = new HashMap<String,Integer>();
	public HashMap<String,Integer> freqWordsSize1 = new HashMap<String,Integer>();
	public HashMap<String,Integer> freqWordsSize2 = new HashMap<String,Integer>();
	public HashMap<String,Integer> freqWordsSize3 = new HashMap<String,Integer>();
	public HashMap<String,Integer> freqWordsSize4 = new HashMap<String,Integer>();
	
	void getFreqOfAllWords(){
		//for (String str:)
	}
	/**
	 * Printing the Frequently table to SYSO
	 */
	void printFreq(final HashMap<Character, Integer> letters){
		System.out.println("Frequently of text :");
		for (char ch = 'a'; ch <= 'z' ; ch++){
			System.out.print(ch +"="+letters.get(ch)+" ");
		}
		System.out.println();
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			System.out.print(ch +"="+letters.get(ch)+" ");
		}
		System.out.println();
		for (char ch = '0'; ch <= '9' ; ch++){
			System.out.print(ch +"="+letters.get(ch)+" ");
		}
	}
	/**
	 * Printing the words from the file :
	 */
	 void printWordsFromFile() {
		for (String tmpString : this.getWordsFromFile_()){
			System.out.println(tmpString);
		}
	}

	/**
	 * writing to file the Result key :
	 */
	void printResult(final Key key , final String cipherText) {
		int tmpIndex = cipherText.indexOf('.');
		String tmpFilename = cipherText.substring(0,tmpIndex);
		key.tostring(tmpFilename+"_key.txt");
	}
	/**
	 * Printing the sorted Frequency Letters :
	 */
	public void printSortedFreqLetters(Character[] sortList){
		for (Character ch:sortList){
			System.out.print(ch);
		}
		System.out.println();
	}
	void copyHashMap(final HashMap<Character, Integer> hashTo,
			final HashMap<Character, Integer> hashFrom) {
		Iterator<Character> from = hashFrom.keySet().iterator();
		while(from.hasNext()){
			Character ch = (Character) from.next();
			Integer value = hashFrom.get(ch);
			hashTo.put(ch, value);
		}
	}
	/**
	 * //check for SSS-YYY Separation to 2 words 
	 * @return true if there was a '-' hyphen
	 */
	public boolean hyphen(String str) {
		int tmpIndexOfchar = str.indexOf('-');
		if (tmpIndexOfchar > 0){
			this.getWordsFromFile_().add(str.substring(0,tmpIndexOfchar));
			this.getWordsFromFile_().add(str.substring(tmpIndexOfchar+1,str.length()));
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
	/**
	 * 
	 * @param str - a String from a file to insert the HashMap<String,Integer>
	 */
	private void addStrToVector(String str) {
		StringBuilder sb = new StringBuilder(30);
		char[] tmpChars = str.toCharArray();
		int tmpLength = 0;
		for (char cha:tmpChars){
			if ((Integer.valueOf(cha) >=48 && Integer.valueOf(cha) <=57) ||
				(Integer.valueOf(cha) >=65 && Integer.valueOf(cha) <=90) ||	
				(Integer.valueOf(cha) >=97 && Integer.valueOf(cha) <=122)||
				Integer.valueOf(cha) == 39){
				sb.append(cha);
				tmpLength++;
			}
		}
		if (tmpLength > 0){
			sb.setLength(tmpLength);
			addWord(sb.toString());
		}
	}
			
	void getWordsFromFile(String cipherText) {
		RandomAccessFile rac;
		try {
			rac = new RandomAccessFile(cipherText, "r");
			int length = (int)rac.length();
			byte[] data = new byte[length];
			rac.read(data, 0, length);
			String str = new String(data);
			rac.close();
			String[] allWords = str.split(" ");
			for (String tmpStr :allWords){
				addStrToVector(tmpStr.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		str = removeSignsFromEndOfWord(';',str);
		str = removeSignsFromEndOfWord('"',str);
		str = removeSignsFromEndOfWord('?',str);
		str = removeSignsFromEndOfWord('!',str);
		str = removeSignsFromBeginingOfWord('(',str);
		str = removeSignsFromBeginingOfWord('"',str);
		str = removeSignsFromEndOfWord(',',str);
		if (hyphen(str)){ 
			return;
		}
		this.getWordsFromFile_().add(str);
	}

	public void printTempDecryptFile(Key key_, String file) {
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new FileReader(file));
			out = new BufferedWriter(new FileWriter("de"+file));
			String str;
			boolean found = false;
			//Getting String and substitute it with the key
			while ((str = in.readLine()) != null) {
		    	char[] charsInString = new char[str.length()];
				str.getChars(0, str.length(), charsInString, 0);
				
				for (Character cha:charsInString){
					found = false;
					Iterator<Character> it = key_.getKey().keySet().iterator();
					while (it.hasNext()){
						Character chKey = (Character) it.next();
						Character chValue = key_.getKey().get(chKey);
						if (chValue != '?'){
							if (chValue == cha){
								out.write(chKey);
								found = true;
								break;
							}
						}
					}
					if (!found){
						out.write(cha);
					}
				}
			out.write("\n");
	    }
	    out.close();
	    in.close();
	    } catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
	}
//	Character[] mostFreq = {'n'}; 
//	for (int i = 0; i < mostFreq.length; i++) {
//		mostFreqLet.add(mostFreq[i]);
//	}
//	Vector<String> mostFreqLettersWords = wordsWithMostFreqLetters();
//	double percent = 0.0;
//	int numOfPerms = 0;
//	
//	for (int i=0;i<mostFreq.length;i++){
//		substitute(sortedLettersFreq_[61-i],mostFreq[i]);
//	}
//	numOfPerms++;
//	percent = checkCorrectWords(mostFreqLettersWords);
//	System.out.println(percent);
//	
//	while (percent < 80){
//		for (int i=0;i<mostFreq.length;i++){
//			substitute(sortedLettersFreq_[61-i],mostFreq[i]);
//		}
//		numOfPerms++;
//		percent = checkCorrectWords(mostFreqLettersWords);
//		System.out.println(percent);
//	}
//	System.out.println("Found 9");
//}
	public Vector<String> getWordsFromFile_() {
		return wordsFromFile_;
	}
	public void setWordsFromFile_(Vector<String> wordsFromFile_) {
		this.wordsFromFile_ = wordsFromFile_;
	}
	public HashMap<String, Integer> getFreqWordsSize1() {
		return freqWordsSize1;
	}
	public void setFreqWordsSize1(HashMap<String, Integer> freqWordsSize1) {
		this.freqWordsSize1 = freqWordsSize1;
	}
	public HashMap<String, Integer> getFreqWordsSize2() {
		return freqWordsSize2;
	}
	public void setFreqWordsSize2(HashMap<String, Integer> freqWordsSize2) {
		this.freqWordsSize2 = freqWordsSize2;
	}
	public HashMap<String, Integer> getFreqWordsSize3() {
		return freqWordsSize3;
	}
	public void setFreqWordsSize3(HashMap<String, Integer> freqWordsSize3) {
		this.freqWordsSize3 = freqWordsSize3;
	}
	public HashMap<String, Integer> getFreqWordsSize4() {
		return freqWordsSize4;
	}
	public void setFreqWordsSize4(HashMap<String, Integer> freqWordsSize4) {
		this.freqWordsSize4 = freqWordsSize4;
	}


//private Vector<String> wordsWithMostFreqLetters(final Vector<String> words,final Vector<Character> mostFreqLet){
//	Vector<String> tmpVec = new Vector<String>();
//	boolean flag = true;
//	
//	for(String str:words){
//		flag = true;
//		char[] tmpCharArray = str.toCharArray();
//		for (Character ch:tmpCharArray){
//			if (!mostFreqLet.contains(ch)){
//				flag=false;
//				break;
//			}
//		}
//		if (flag){
//			tmpVec.add(str);
//		}
//	}
//	return tmpVec;
//}
//private double checkCorrectWords(Vector<String> correctWords,Dict dict) {
//	double totalCorrectWords = correctWords.size();
//	double counter = 0;
//	for (String str:correctWords){
//		if (dict.getSpecialWords().contains(str)){
//			counter++;
//		}
//	}
//	return (counter / totalCorrectWords ) * 100; 
//}
//	/**
//	 * Calculate frequency of Zug Letters like 'LL' , 'OO'
//	 */
//	private void calcZug(Vector<String> words) {
//		int[] tmpFreq = new int[123];
//		for (String str : words){
//			char[] charsInString = new char[str.length()];
//			str.getChars(0, str.length(), charsInString, 0);
//			
//			for (int i=0;i<charsInString.length-1;i++){
//				int tmpValue = Integer.valueOf(charsInString[i]);
//				if (tmpValue>=48 && tmpValue <=57 ||tmpValue >=65 && tmpValue <=90 ||	
//						tmpValue >=97 && tmpValue <=122){
//					int tmpValue2 = Integer.valueOf(charsInString[i+1]);
//					if (tmpValue == tmpValue2){
//						tmpFreq[tmpValue]++;
//					}
//				}
//			} 
//			for (char ch = 'a'; ch <= 'z' ; ch++){
//				zugLettersFreq_.put(ch, tmpFreq[ch]);
//			}
//			for (char ch = 'A'; ch <= 'Z' ; ch++){
//				zugLettersFreq_.put(ch, tmpFreq[ch]);
//			}
//			for (char ch = '0'; ch <= '9' ; ch++){
//				zugLettersFreq_.put(ch, tmpFreq[ch]);
//			}
//		}
//	}
//	private void sortZug(){
//		HashMap<Character, Integer> tmpZugLettersFreq_ = new HashMap<Character, Integer>();
//		copyHashMap(tmpZugLettersFreq_,zugLettersFreq_);
//		List<Character> mapKeys = new ArrayList<Character>(((Map<Character, Integer>) this.zugLettersFreq_).keySet());
//		List<Integer> mapValues = new ArrayList<Integer>(((Map<Character, Integer>) this.zugLettersFreq_).values());
//		
//		Collections.sort(mapKeys);
//	    Collections.sort(mapValues);
//
//	    Iterator<Integer> valueIt = mapValues.iterator();
//	    int counter = 0;
//	    while (valueIt.hasNext()) {
//	        Integer val = valueIt.next();
//	        Iterator<Character> keyIt = mapKeys.iterator();
//	        
//	        while (keyIt.hasNext()) {
//	            Character key = keyIt.next();
//	            Integer comp1 =  this.zugLettersFreq_.get(key);
//	            
//	            if (comp1 == val ){
//	            	this.zugLettersFreq_.remove(key);
//	                mapKeys.remove(key);
//	                sortedZugLettersFreq_[counter]= key;
//	                counter++;
//	                break;
//	            }
//	        }
//	    }
//	    copyHashMap(zugLettersFreq_,tmpZugLettersFreq_);
//	}
	
}
