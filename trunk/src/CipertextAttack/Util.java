package CipertextAttack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Util {
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
	 void printWordsFromFile(final Vector<String> words) {
		for (String tmpString : words){
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
	public boolean hyphen(Vector<String> words , String str) {
		int tmpIndexOfchar = str.indexOf('-');
		if (tmpIndexOfchar > 0){
			words.add(str.substring(0,tmpIndexOfchar));
			words.add(str.substring(tmpIndexOfchar+1,str.length()));
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
	private void addStrToVector(Vector<String> words,String str) {
		while (str.length() > 0){
			int indexOfSpace = str.indexOf(" ");
			
			if (indexOfSpace == -1){ //Checking if it's the last word:
				addWord(words,str);
				return;
			}
			addWord(words,str.substring(0, indexOfSpace));
			str = str.substring(indexOfSpace+1,str.length());
		}
	}
	void getWordsFromFile(Vector<String> words,String cipherText) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(cipherText));
			String str;
		    while ((str = in.readLine()) != null) {
		    	//Getting Words into HashMap
		    	addStrToVector(words,str);
		    }
		    in.close();
		    
		} catch (FileNotFoundException e) {
			System.out.println("File is not found");
			System.exit(0);
		} catch (IOException e) {
		}
	}
	/**
	 * Removing ( ) , . from the word and Adding to Vector
	 * @param tmpWord
	 */
	private void addWord(Vector<String> words ,String str) {
		str = removeSignsFromEndOfWord('.',str);
		str = removeSignsFromEndOfWord(')',str);
		str = removeSignsFromEndOfWord(':',str);
		str = removeSignsFromBeginingOfWord('(',str);
		str = removeSignsFromEndOfWord(',',str);
		if (hyphen(words,str)){ 
			return;
		}
		words.add(str);
	}
	public void printToText(Key key_, String string) {
		
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
					Iterator<Character> it = CipherTextAttack.key_.getKey().keySet().iterator();
					while (it.hasNext()){
						Character chKey = (Character) it.next();
						Character chValue = CipherTextAttack.key_.getKey().get(chKey);
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
}