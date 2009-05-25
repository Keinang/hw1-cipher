package CipertextAttack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Util {
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private Character[] sortedLettersFreq_ = new Character[62];
	
	private Vector<String> wordsFromFile_ = new Vector<String>();
	private HashMap<String,Integer> freqAllWords = new HashMap<String,Integer>();
	private HashMap<String,Integer> freqWordsSize1 = new HashMap<String,Integer>();
	private HashMap<String,Integer> freqWordsSize2 = new HashMap<String,Integer>();
	private HashMap<String,Integer> freqWordsSize3 = new HashMap<String,Integer>();
	private HashMap<String,Integer> freqWordsSize4 = new HashMap<String,Integer>();
	private String[] sortedfreqWordsSize1 = new String[2000];
	private String[] sortedfreqWordsSize2 = new String[2000];
	private String[] sortedfreqWordsSize3 = new String[2000];
	private String[] sortedfreqWordsSize4 = new String[2000];
	private int sort1Size = 0;
	private int sort2Size = 0;
	private int sort3Size = 0;
	private int sort4Size = 0;
	private Vector<String> sortedVecSize1 = new Vector<String>();
	private Vector<String> sortedVecSize2 = new Vector<String>();
	private Vector<String> sortedVecSize3 = new Vector<String>();
	private Vector<String> sortedVecSize4 = new Vector<String>();
	private Vector<String> sortedAllVecSize1 = new Vector<String>();
	private Vector<String> sortedAllVecSize2 = new Vector<String>();
	private Vector<String> sortedAllVecSize3 = new Vector<String>();
	private Vector<String> sortedAllVecSize4 = new Vector<String>();
	
	public void initAllHashes(){
		getFreqOfAllWords();
		sortAllWords(freqWordsSize1,sortedfreqWordsSize1);
		sortAllWords(freqWordsSize2,sortedfreqWordsSize2);
		sortAllWords(freqWordsSize3,sortedfreqWordsSize3);
		sortAllWords(freqWordsSize4,sortedfreqWordsSize4);
		copyArrayToVec();
		copyOnly25();
	}
	
	private void copyOnly25() {
		int min1 = Math.min(sort1Size, 25);
		int min2 = Math.min(sort2Size, 25);
		int min3 = Math.min(sort3Size, 25);
		int min4 = Math.min(sort4Size, 25);
		
		for (int i=0;i<min1;i++){
			sortedAllVecSize1.add(sortedfreqWordsSize1[i]);
		}
		for (int i=0;i<min2;i++){
			sortedAllVecSize2.add(sortedfreqWordsSize2[i]);
		}
		for (int i=0;i<min3;i++){
			sortedAllVecSize3.add(sortedfreqWordsSize3[i]);
		}
		for (int i=0;i<min4;i++){
			sortedAllVecSize4.add(sortedfreqWordsSize4[i]);
		}
	}

	private void copyArrayToVec(){
		for (int i=0;i<sort1Size;i++){
			sortedAllVecSize1.add(sortedfreqWordsSize1[i]);
		}
		for (int i=0;i<sort2Size;i++){
			sortedAllVecSize2.add(sortedfreqWordsSize2[i]);
		}
		for (int i=0;i<sort3Size;i++){
			sortedAllVecSize3.add(sortedfreqWordsSize3[i]);
		}
		for (int i=0;i<sort4Size;i++){
			sortedAllVecSize4.add(sortedfreqWordsSize4[i]);
		}
	}

	private void sortAllWords(HashMap<String,Integer> hash,String[] sorted) {
		HashMap<String,Integer> tmpFreq = new HashMap<String,Integer>();
		copyHashMapString(tmpFreq,hash);
		List<String> mapKeys = new ArrayList<String>(((Map<String, Integer>) hash).keySet());
		List<Integer> mapValues = new ArrayList<Integer>(((Map<String, Integer>) hash).values());
		
		Collections.sort(mapKeys);
	    Collections.sort(mapValues);

	    Iterator<Integer> valueIt = mapValues.iterator();
	    int counter = 0;
	    while (valueIt.hasNext()) {
	        Integer val = valueIt.next();
	        Iterator<String> keyIt = mapKeys.iterator();
	        
	        while (keyIt.hasNext()) {
	        	String key = keyIt.next();
	            Integer comp1 =  hash.get(key);
	            
	            if (comp1 == val ){
	            	hash.remove(key);
	                mapKeys.remove(key);
	                sorted[counter]= key;
	                counter++;
	                break;
	            }
	        }
	    }
	    copyHashMapString(hash,tmpFreq);
	}
	private void inc(final int typeOfHash,final String word){
		if (typeOfHash == 1){
			Integer tmp = freqWordsSize1.get(word);
			if (tmp == null){
				freqWordsSize1.put(word, 1);
				sort1Size=1;
			}else{
				freqWordsSize1.put(word, tmp++);
				sort1Size++;
			}
		}else if (typeOfHash == 2){
			Integer tmp = freqWordsSize2.get(word);
			if (tmp == null){
				freqWordsSize2.put(word, 1);
				sort2Size=1;
			}else{
				freqWordsSize2.put(word, tmp++);
				sort2Size++;
			}
		}else if (typeOfHash == 3){
			Integer tmp = freqWordsSize3.get(word);
			if (tmp == null){
				freqWordsSize3.put(word, 1);
				sort3Size = 1;
			}else{
				freqWordsSize3.put(word, tmp++);
				sort3Size++;
			}
		}else if (typeOfHash == 4){
			Integer tmp = freqWordsSize4.get(word);
			if (tmp == null){
				freqWordsSize4.put(word, 1);
				sort4Size = 1;
			}else{
				freqWordsSize4.put(word, tmp++);
				sort4Size++;
			}
		}
	}
	
	void getFreqOfAllWords(){
		for (String str:this.getWordsFromFile_()){
			if (str.length() == 1){
				inc(1,str);
			}
			else if (str.length() == 2){
				inc(2,str);
			}
			else if (str.length() == 3){
				inc(3,str);
			}
			else if (str.length() == 4){
				inc(4,str);
			}
		}
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
	void sortFreq(){
		HashMap<Character, Integer> tmpLettersFreq_ = new HashMap<Character, Integer>();
		copyHashMapChar(tmpLettersFreq_,lettersFreq_);
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
	    copyHashMapChar(lettersFreq_,tmpLettersFreq_);
	}
	
    /**
     * calculating Frequency
     * @param str - a String from the file.
     */
	void calcFreq() {
		int[] tmpFreq = new int[123];
		for (String str : getWordsFromFile_()){
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
	private void copyHashMapChar(final HashMap<Character, Integer> hashTo,
			final HashMap<Character, Integer> hashFrom) {
		Iterator<Character> from = hashFrom.keySet().iterator();
		while(from.hasNext()){
			Character ch = (Character) from.next();
			Integer value = hashFrom.get(ch);
			hashTo.put(ch, value);
		}
	}
	private void copyHashMapString(final HashMap<String, Integer> hashTo,
			final HashMap<String, Integer> hashFrom) {
		Iterator<String> from = hashFrom.keySet().iterator();
		while(from.hasNext()){
			String ch = (String) from.next();
			Integer value = hashFrom.get(ch);
			hashTo.put(ch, value);
		}
	}
	/**
	 * //check for SSS-YYY Separation to 2 words 
	 * @return true if there was a '-' hyphen
	 */
	boolean hyphen(String str) {
		int tmpIndexOfchar = str.indexOf('-');
		if (tmpIndexOfchar > 0){
			this.getWordsFromFile_().add(str.substring(0,tmpIndexOfchar));
			this.getWordsFromFile_().add(str.substring(tmpIndexOfchar+1,str.length()));
			return true;
		}
		return false;
	}

	String removeSignsFromEndOfWord(char ch, String str) {
		int tmpIndexOfchar = str.indexOf(ch);
		if (tmpIndexOfchar > -1){
			return str.substring(0,tmpIndexOfchar);
		}
		return str;
	}
	String removeSignsFromBeginingOfWord(char ch, String str) {
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
			
	public void getWordsFromFile(String cipherText) {
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
	public Vector<String> getWordsFromFile_() {
		return wordsFromFile_;
	}
	public HashMap<String, Integer> getFreqWordsSize1() {
		return freqWordsSize1;
	}
	public HashMap<String, Integer> getFreqWordsSize2() {
		return freqWordsSize2;
	}
	public HashMap<String, Integer> getFreqWordsSize3() {
		return freqWordsSize3;
	}
	public HashMap<String, Integer> getFreqWordsSize4() {
		return freqWordsSize4;
	}
	public HashMap<String, Integer> getFreqAllWords() {
		return freqAllWords;
	}
	public String[] getSortedfreqWordsSize1() {
		return sortedfreqWordsSize1;
	}
	public String[] getSortedfreqWordsSize2() {
		return sortedfreqWordsSize2;
	}
	public String[] getSortedfreqWordsSize3() {
		return sortedfreqWordsSize3;
	}
	public String[] getSortedfreqWordsSize4() {
		return sortedfreqWordsSize4;
	}

	public Character[] getSortedLettersFreq_() {
		return sortedLettersFreq_;
	}

	public Vector<String> getSortedVecSize1() {
		return sortedVecSize1;
	}

	public Vector<String> getSortedVecSize2() {
		return sortedVecSize2;
	}

	public Vector<String> getSortedVecSize3() {
		return sortedVecSize3;
	}

	public Vector<String> getSortedVecSize4() {
		return sortedVecSize4;
	}


	
}
