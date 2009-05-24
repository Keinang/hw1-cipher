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
	private Util util = new Util();
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	private HashMap<Character, Integer> zugLettersFreq_ = new HashMap<Character, Integer>();
	private Character[] sortedLettersFreq_ = new Character[62];
	private Character[] sortedZugLettersFreq_ = new Character[62];
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
		
		util.getWordsFromFile(this.getWordsFromFile(),cipherText);
		//this.printWordsFromFile();
		//System.out.println("Number of words : "+this.getWordsFromFile().size());
		calcFreq();
		sortFreq();//sorting the frequently table
		util.printSortedFreqLetters(sortedLettersFreq_);
		util.printFreq(this.lettersFreq_);
		calcZug();
		sortZug();
		System.out.println();
		System.out.println(sortedZugLettersFreq_[61]);
		System.out.println(sortedZugLettersFreq_[60]);
		System.out.println(sortedZugLettersFreq_[59]);
		
		
		//we stop the program if we found at least 50% of the words 
		//while (correctWords < this.getWordsFromFile().size()/2){
			substitute(sortedLettersFreq_[61],'e');
			substitute(sortedLettersFreq_[60],'t');
			
			//substitute();//search for LL
			//printWordsFromFile();
		//}
		
		//printing the Result key to the output file :
    	util.printResult(this.key_,cipherText);
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
				this.wordsFromFile_.set(j, String.valueOf(word));
			}
		}
		this.key_.getKey().put(newChar, oldChar);
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

