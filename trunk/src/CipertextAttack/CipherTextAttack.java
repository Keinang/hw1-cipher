package CipertextAttack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
/**
 *  
 * @author GK
 *
 */
public class CipherTextAttack {
	Vector<Character> mostFreqLet  = new Vector<Character>();
	private Util util = new Util();
	private HashMap<Character, Integer> lettersFreq_ = new HashMap<Character, Integer>();
	
	private Character[] sortedLettersFreq_ = new Character[62];
	
	private Key key_ = new Key();
	private Key keyOpposite_ = new Key();
	private Vector<String> wordsFromFile_ = new Vector<String>();
	
	
	/**
	 * @param args - cipher text
	 */
	public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

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
		System.out.println((System.currentTimeMillis()-startTime)/1000F + " Seconds");
		
	}
	
	/**
	 * Main program 
	 * @param cipherText - a Simple cipher text
	 * @return the destination key
	 */
	public void decrypt(String cipherText) {
		/**
		 * 1. Dates dd/mm/yyyy
		 */
		
		util.getWordsFromFile(this.getWordsFromFile(),cipherText);
		calcFreq();
		sortFreq();
		
		search_the();     //getting e,t,h
		search_to();      //getting o
		search_that();    //getting a
		
		if (!search_s()){ //getting s 
			search_such();
		}
		if (!search_re()){  //getting r
			search_are();	
		}
		
		search_this();    //getting i
		search_into();    //getting n ; try "one"
		search_for();     //getting f
		search_anything();//getting y,g
		search_use();     //getting u
		search_about();	  //getting b
		search_each();    //getting c ;try which
		search_said();    //getting d
		search_with();    //getting w
		search_people();  //getting p,l; try part
		search_look();    //getting k ; try like
		search_from();    //getting m
		search_question();//getting q //try quite
		search_give();    //getting v
		search_expect();  //getting x
		search_subject(); //getting j
		search_dozen();   //getting z ; try realize,organize;
		
		search_I();		  //getting I
		
		//print for testing
		util.printTempDecryptFile(this.key_,"encryptedTxt.txt");
		
		//printing the Result key to the output file :
    	util.printResult(this.key_,cipherText);
	}

	private boolean search_re() {
		boolean flag = false;
		for (String str : this.getWordsFromFile()){
			if (str.length() > 2 && str.charAt(str.length()-1) =='e'&& str.charAt(str.length()-3) =='\''){
				substitute(str.charAt(str.length()-2), 'r');
				flag = true;
				break;
			}
		}
		return flag;
	}

	private void search_I() {
		
	}

	private void search_dozen() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==5 && str.charAt(0) =='d'&& str.charAt(1) =='o'
				&& str.charAt(3) =='e'&& str.charAt(4) =='n'){
				substitute(str.charAt(2), 'z');
				break;
			}
		}
	}

	private void search_subject() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==7 && str.charAt(0) =='s'&& str.charAt(1) =='u'
				&& str.charAt(2) =='b'&& str.charAt(4) =='e'&& str.charAt(5) =='c'
					&& str.charAt(6) =='t'){
				substitute(str.charAt(3), 'j');
				break;
			}
		}
	}

	private void search_expect() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==6 && str.charAt(0) =='e'&& str.charAt(2) =='p'
				&& str.charAt(3) =='e'&& str.charAt(4) =='c'&& str.charAt(5) =='t'){
				substitute(str.charAt(1), 'x');
				break;
			}
		}			
	}

	private void search_give() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='g'&& str.charAt(1) =='i'
				&& str.charAt(3) =='e'){
				substitute(str.charAt(2), 'v');
				break;
			}
		}			
	}
	private void search_question() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==8 && str.charAt(1) =='u'&& str.charAt(2) =='e'
				&& str.charAt(3) =='s'&& str.charAt(4) =='t'&& str.charAt(5) =='i'
					&& str.charAt(6) =='o'&& str.charAt(7) =='n'){
				substitute(str.charAt(0), 'q');
				break;
			}
		}			
	}
	private void search_from() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='f'&& str.charAt(1) =='r'
				&& str.charAt(2) =='o'){
				substitute(str.charAt(3), 'm');
				break;
			}
		}			
	}

	private void search_look() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='l'&& str.charAt(1) =='o'
				&& str.charAt(2) =='o'){
				substitute(str.charAt(3), 'k');
				break;
			}
		}			
	}

	private void search_people() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==6 && str.charAt(1) =='e'&& str.charAt(2) =='o'
				&& str.charAt(5) =='e'&& str.charAt(0) == str.charAt(3)){
				substitute(str.charAt(0), 'p');
				substitute(str.charAt(4), 'l');
				break;
			}
		}	
	}

	private void search_with() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(1) =='i'&& str.charAt(2) =='t'
				&& str.charAt(3) =='h'){
				substitute(str.charAt(0), 'w');
				break;
			}
		}			
	}

	private void search_each() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='e'&& str.charAt(1) =='a'
				&& str.charAt(3) =='h'){
				substitute(str.charAt(2), 'c');
				break;
			}
		}			
	}

	private void search_use() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==3 && str.charAt(1) =='s'&& str.charAt(2) =='e'){
				substitute(str.charAt(0), 'u');
				break;
			}
		}	
	}

	private void search_anything() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==8 && str.charAt(0) =='a'&& str.charAt(1) =='n'
				&& str.charAt(3) =='t'&& str.charAt(4) =='h'&& str.charAt(5) =='i'
					&& str.charAt(6) =='n'){
				substitute(str.charAt(2), 'y');
				substitute(str.charAt(7), 'g');
				break;
			}
		}			
	}

	private void search_for() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==3 && str.charAt(1) =='o'&& str.charAt(2) =='r'){
				substitute(str.charAt(0), 'f');
				break;
			}
		}	
	}
	private void search_said() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='s'&& str.charAt(1) =='a'
				&& str.charAt(2) =='i'){
				substitute(str.charAt(3), 'd');
				break;
			}
		}	
	}

	private void search_about() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==5 && str.charAt(0) =='a'&& str.charAt(2) =='o'
				&& str.charAt(3) =='u'&& str.charAt(4) =='t'){
				substitute(str.charAt(1), 'b');
				break;
			}
		}			
	}

	private void search_into() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='i'&& str.charAt(2) =='t'
				&& str.charAt(3) =='o'){
				substitute(str.charAt(1), 'n');
				break;
			}
		}		
	}
	private void search_this() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(0) =='t'&& str.charAt(1) =='h'
				&& str.charAt(3) =='s'){
				substitute(str.charAt(2), 'i');
				break;
			}
		}
	}
	private void search_are() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==3 && str.charAt(0) =='a'&& str.charAt(2) =='e'){
				substitute(str.charAt(1), 'r');
				break;
			}
		}
	}
	private void search_such() {
		for (String str : this.getWordsFromFile()){
			if (str.length()==4 && str.charAt(1) =='u'&& str.charAt(2) =='c'
				&& str.charAt(3) =='h'){
				substitute(str.charAt(0), 's');
				break;
			}
		}
	}
	private boolean search_s() {
		boolean flag_s = false;
		for (String str : this.getWordsFromFile()){
			if (str.length()> 1 && str.charAt(str.length()-2)== '\''){
				substitute(str.charAt(str.length()-1), 's');
				flag_s = true;
				break;
			}
		}
		return flag_s;
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
	public Vector<String> getWordsFromFile() {
		return wordsFromFile_;
	}
}

