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
	private int sort1Size = 0;//vectors size
	private int sort2Size = 0;
	private int sort3Size = 0;
	private int sort4Size = 0;
	private Vector<String> sortedVecSize1 = new Vector<String>(); //only top 25
	private Vector<String> sortedVecSize2 = new Vector<String>();
	private Vector<String> sortedVecSize3 = new Vector<String>();
	private Vector<String> sortedVecSize4 = new Vector<String>();
	private Vector<String> sortedAllVecSize1 = new Vector<String>();//all words
	private Vector<String> sortedAllVecSize2 = new Vector<String>();
	private Vector<String> sortedAllVecSize3 = new Vector<String>();
	private Vector<String> sortedAllVecSize4 = new Vector<String>();
	
	public void initAllHashes(){
		getFreqOfAllWords();
		
		byValueComparator bvc1 = new byValueComparator(freqWordsSize1);
		List<String> keys1 = new ArrayList<String>(freqWordsSize1.keySet());
		Collections.sort(keys1, bvc1);
		
		for (int i=0;i<keys1.size();i++){
			sortedAllVecSize1.add((String)keys1.get(i));
		}
		byValueComparator bvc2 =  new byValueComparator(freqWordsSize2);
		List<String> keys2 = new ArrayList<String>(freqWordsSize2.keySet());
		Collections.sort(keys2, bvc2);
		
		for (int i=0;i<keys2.size();i++){
			sortedAllVecSize2.add((String) keys2.get(i));
		}
		byValueComparator bvc3 =  new byValueComparator(freqWordsSize3);
		List<String> keys3 = new ArrayList<String>(freqWordsSize3.keySet());
		Collections.sort(keys3, bvc3);
		
		for (int i=0;i<keys3.size();i++){
			sortedAllVecSize3.add((String) keys3.get(i));
		}
		byValueComparator bvc4 =  new byValueComparator(freqWordsSize4);
		List<String> keys4 = new ArrayList<String>(freqWordsSize4.keySet());
		Collections.sort(keys4, bvc4);
		
		for (int i=0;i<keys4.size();i++){
			sortedAllVecSize4.add((String) keys4.get(i));
		}
		copy25();
	}
	
	private void copy25() {
		int min1 = Math.min(this.sort1Size, 25);
		for (int i=0;i<min1;i++){
			this.sortedVecSize1.add(this.sortedAllVecSize1.get(i));
		}
		int min2 = Math.min(this.sort2Size, 25);
		for (int i=0;i<min2;i++){
			this.sortedVecSize2.add(this.sortedAllVecSize2.get(i));
		}
		int min3 = Math.min(this.sort3Size, 25);
		for (int i=0;i<min3;i++){
			this.sortedVecSize3.add(this.sortedAllVecSize3.get(i));
		}
		int min4 = Math.min(this.sort4Size, 25);
		for (int i=0;i<min4;i++){
			this.sortedVecSize4.add(this.sortedAllVecSize4.get(i));
		}
	}

	private void inc(final int typeOfHash,final String word){
		if (typeOfHash == 1){
			Integer tmp = freqWordsSize1.get(word);
			if (tmp == null){
				Integer i = new Integer(1);
				freqWordsSize1.put(word, i);
				sort1Size++;
			}else{
				freqWordsSize1.put(word, ++tmp);
				sort1Size++;
			}
		}else if (typeOfHash == 2){
			Integer tmp = freqWordsSize2.get(word);
			if (tmp == null){
				Integer i = new Integer(1);
				freqWordsSize2.put(word, i);
				sort2Size++;
			}else{
				freqWordsSize2.put(word, ++tmp);
				sort2Size++;
			}
		}else if (typeOfHash == 3){
			Integer tmp = freqWordsSize3.get(word);
			if (tmp == null){
				Integer i = new Integer(1);
				freqWordsSize3.put(word, i);
				sort3Size++;
			}else{
				//int tmp2=tmp++;
				//System.out.println(word +","+tmp2);
				freqWordsSize3.put(word, ++tmp);
				sort3Size++;
			}
		}else if (typeOfHash == 4){
			Integer tmp = freqWordsSize4.get(word);
			if (tmp == null){
				Integer i = new Integer(1);
				freqWordsSize4.put(word, i);
				sort4Size++;
			}else{
				freqWordsSize4.put(word, ++tmp);
				sort4Size++;
			}
		}
	}
	
	void getFreqOfAllWords(){
		for (String str:this.getWordsFromFile_()){
			if (str.length() == 1){
				if(str.equals("'")){
					continue;
				}else{
					inc(1,str);	
				}
			}
			else if (str.length() == 2){
				if(str.charAt(0) == '\''){
					continue;
				}else{
					inc(2,str);	
				}
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
	void printResult(HashMap<Character,Character> key , final String cipherText) {
		int tmpIndex = cipherText.indexOf('.');
		String tmpFilename = cipherText.substring(0,tmpIndex);
		tostring(key,tmpFilename+"_key.txt");
	}
	
	public void tostring(HashMap<Character,Character> key,String outputFile){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(outputFile));
			for (char ch = 'a'; ch <= 'z' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = 'A'; ch <= 'Z' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
			out.write('\n');
			for (char ch = '0'; ch <= '9' ; ch++){
				out.write(ch +"="+key.get(ch)+" ");
			}
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			else if (tmpLength > 0){
				sb.setLength(tmpLength);
				addWord(sb.toString());
				sb = new StringBuilder(30);
				tmpLength=0;
			}
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
			addStrToVector(str);
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

	public void printTempDecryptFile(HashMap<Character,Character> key_, String file) {
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new FileReader(file));
			out = new BufferedWriter(new FileWriter("RESULT"));
			String str;
			boolean found = false;
			//Getting String and substitute it with the key
			while ((str = in.readLine()) != null) {
		    	char[] charsInString = new char[str.length()];
				str.getChars(0, str.length(), charsInString, 0);
				
				for (Character cha:charsInString){
					found = false;
					Iterator<Character> it = key_.keySet().iterator();
					while (it.hasNext()){
						Character chKey = (Character) it.next();
						Character chValue = key_.get(chKey);
						if (chValue != '?'){
							if (chKey == cha){
								out.write(chValue);
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
