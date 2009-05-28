package CipertextAttack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Vector;

import Util.Util;

public class Decrypt {
	private static HashMap<Character,Character> key;
	private static HashMap<Character,Character> reverseKey;
	private Vector<String> dictionaryVec ;
	private String dictFile = "dict.txt";
	private String cipherText ;
	private int numOfSubs = 0;
	private Util util = new Util();
	public Decrypt(){
		
	}
	public Decrypt(HashMap<Character,Character> key2,HashMap<Character,Character> revKey
			,String cipherText2){
		key = key2;
		reverseKey = revKey;
		cipherText= cipherText2;
	}
	/**
	 * Main program 
	 * @param cipherText - a Simple cipher text
	 * @return the destination key
	 */
	public void decrypt() {
		initDict();
		util.getWordsFromFile(cipherText);
		util.calcFreq();
		util.sortFreq();
		util.initAllHashes();
		search_the();     //getting e,t,h
		search_that();    //getting a
		if (!search_this_is()){ //getting s ,i
			search_s();  	//getting s  
			search_it();	 //getting i
		}
		
		if (!search_re()){  //getting r
			if (!search_are()){
				search_their();
			}
		}
		if (!search_to()){  //getting o 
			search_too();
		}
		search_use();     //getting u	
		search_people();  //getting p,l; will
		if (!search_from()){//getting f , m
			search_for(); //getting f
			if (!search_much()){ //getting m
					if(!search_small()){
						search_him();
					}
				}
		}
		search_into_in(); //getting n 
		
		if (!search_had()){   //getting d
			if(!search_add()){
				search_said();
			}
		}
		
		search_with();    //getting w
		
		if (!search_anything()){//getting y,g
			search_any(); //y
			search_good();//g
		}
		
		search_about();	  //getting b
		search_each();    //getting c ;try which
		
		
		search_look();    //getting k ; try like
		search_question();//getting q //try quite
		if (!search_give()){    //getting v
			serach_have();
		}
		if (!search_expect()){  //getting x
			if (!search_next()){
				search_example();
			}
		}
		if(!search_subject()){//getting j
			search_just();
		}
		if (!search_dozen()){   //getting z ; try realize,organize;
			if(!search_size()){
				if(!search_hazard()){
					search_gaze();
				}
			}
		}
		System.out.println("Dict Starts");
		//Getting Big Letters :
		Vector<String> allWordsWithoutFirstBig = getWordsWithoutBig(this.util.getWordsFromFile_());
		
		//check if the word is unique
		for (String str : allWordsWithoutFirstBig){
			if (key.get(str.charAt(0)) != '?'){
				continue;
			}
			int counter = 0;
			Character goodChar = '?';
			for (char ch = 'a'; ch <= 'z' ; ch++){
				if (reverseKey.get(Character.toUpperCase(ch)) != null){
					continue;
				}
				String newStr = new String(ch+translate(str.substring(1)));
				if (this.dictionaryVec.contains(newStr)){
					counter++;
					goodChar = ch; 
					if (counter == 2 ){
						break;
					}
				}
			}
			if (counter == 1){//found ch
				substitute(str.charAt(0), Character.toUpperCase(goodChar));
			}
		}
		System.out.println("Dict finish");
		//Search_And(); // getting A
		
		search_You();	  //getting Y

		if (!search_But()){
			if(!search_By()){
				if(!search_Book()){
				//	search_B();
				}
			}
		}
		if(! Search_I()){
			if(!Search_INTO()){
				Search_IT();	  //getting I
			}
		}
		Search_T();
		if(!Search_F()){
			Search_For();
		}
		Search_W();
		if(!Search_H()){
			Search_Has();
		}
		if(!Search_OTHER()){
			Search_ONE();
		}
		if(!Search_USE()){
			if(!Search_UP()){
				Search_UNDER();
			}
		}
		if(!Search_EACH()){
			Search_EVERY();
		}
		if(!Search_NUMBER()){
			Search_NOW();
		}
		if(!Search_COULD()){
			Search_CAN();
		}
		if(!Search_DID()){
			Search_DAY();
		}
		if(!Search_PART()){
			Search_PEOPLE();
		}
		if(!Search_SHOULD()){
			Search_SINCE();
		}
		System.out.println("Number of subs : "+numOfSubs);
		//print for testing
		util.printTempDecryptFile(Decrypt.key,cipherText);
		
		//printing the Result key to the output file :
    	util.printResult(reverseKey,cipherText);
	}

	private Vector<String> getWordsWithoutBig(Vector<String> wordsFromFile_) {
		Vector<String> tmp = new Vector<String>();
		boolean flag;
		for (String str:wordsFromFile_){
			flag = true;
			Character tmpValueFirst = key.get(str.charAt(0));
			if (tmpValueFirst == null){//\'
				continue;
			}
			if (tmpValueFirst != '?'){//Big letters beacause we didn't discover it.
				continue;
			}
			char[] tmpChars = str.toCharArray();
			for (int i=1; i<tmpChars.length;i++){ //passing on all the chars from 1
				Character tmpValueOthers = key.get(tmpChars[i]);
				if (tmpValueOthers == null){//\'
					flag = false;
					break;
				}
				if (tmpValueOthers == '?'){//didn't discover -> not little char
					flag = false;
					break;
				}
			}
			if (flag){
				tmp.add(str);
			}
		}
		return tmp;
	}
	
	private void search_gaze() {
		for (String str : util.getSortedAllVecSize4()){
			if (checkKey(str.charAt(0),'g') && checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(3),'e')){
				substitute(str.charAt(2), 'z');
				break;
			}
		}		
	}
	private boolean Search_UNDER() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 5 &&  checkKey(str.charAt(1),'n') && checkKey(str.charAt(2), 'd')
					&& checkKey(str.charAt(3),'e')&&  checkKey(str.charAt(4),'r')   
					&& checkKeynotValue(str.charAt(0), 'u')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'U');
			
			}
		}
		return false;
	}
	 private void search_You() {
			for (String str : util.getSortedAllVecSize3()){
				if ( checkKey(str.charAt(1),'o')&&checkKey(str.charAt(2),'u')&& checkKeynotValue(str.charAt(0),'y')){
					substitute(str.charAt(0) , 'Y');
					break;
				}
			}
		}
	private boolean Search_SINCE() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 5 &&  checkKey(str.charAt(1),'i') && checkKey(str.charAt(2), 'n')
					&& checkKey(str.charAt(3),'c')&&  checkKey(str.charAt(4),'e')   
					&& checkKeynotValue(str.charAt(0), 's')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'S');
			
			}
		}
		return false;
	}

	private boolean Search_SHOULD() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 6 &&  checkKey(str.charAt(1),'h') && checkKey(str.charAt(2), 'o')
					&& checkKey(str.charAt(3),'u')&&  checkKey(str.charAt(4),'l')  &&  checkKey(str.charAt(5),'d')  
					&& checkKeynotValue(str.charAt(0), 's')&& checkKeynotValue(str.charAt(0), 'C')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'S');
			
			}
		}
		return false;
	}
		
	

	private boolean Search_PEOPLE() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 6 &&  checkKey(str.charAt(1),'e') && checkKey(str.charAt(2), 'o')
					&& checkKey(str.charAt(3),'p')&&  checkKey(str.charAt(4),'l')  &&  checkKey(str.charAt(5),'e')  
					&& checkKeynotValue(str.charAt(0), 'p')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'P');
			
			}
		}
		return false;
	}

	private boolean Search_PART() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 6 &&  checkKey(str.charAt(1),'a') && checkKey(str.charAt(2), 'r')
					&& checkKey(str.charAt(3),'t')&&  checkKeynotValue(str.charAt(0), 'c')&& checkKeynotValue(str.charAt(0), 'C') 
					&& checkKeynotValue(str.charAt(0), 'p')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'P');
			
			}
		}
		return false;
	}

	private boolean Search_DAY() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'a') && checkKey(str.charAt(2), 'y')
					&& checkKeynotValue(str.charAt(0),'d')&& checkKeynotValue(str.charAt(0), 'm')
					&&checkKeynotValue(str.charAt(0), 'M') &&checkKeynotValue(str.charAt(0), 'b') 
					&&checkKeynotValue(str.charAt(0), 'r')&&checkKeynotValue(str.charAt(0), 'd')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'D');
			
			}
		}
		return false;
		
	}

	private boolean Search_DID() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'i') && checkKey(str.charAt(2), 'd')
					&& checkKeynotValue(str.charAt(0),'h')&& checkKeynotValue(str.charAt(0), 'H')
					&&checkKeynotValue(str.charAt(0), 'b') &&checkKeynotValue(str.charAt(0), 'B') 
					&&checkKeynotValue(str.charAt(0), 'r')&&checkKeynotValue(str.charAt(0), 'd')
					&&checkKeynotValue(str.charAt(0), 'a')&&checkKeynotValue(str.charAt(0), 'A')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'D');
			
			}
		}
		return false;
	}

	private boolean Search_COULD() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 5 &&  checkKey(str.charAt(1),'o') && checkKey(str.charAt(2), 'u')
					&& checkKey(str.charAt(3),'l')&&  checkKey(str.charAt(4),'d')  
					&& checkKeynotValue(str.charAt(0), 'c')&& checkKeynotValue(str.charAt(0), 'w')
					&& checkKeynotValue(str.charAt(0), 'W')&& checkKeynotValue(str.charAt(0), 's')
					){
				//System.out.println(str);
				return substitute(str.charAt(0), 'C');
			
			}
		}
		return false;
	}
	private boolean Search_CAN() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'a') && checkKey(str.charAt(2), 'n')
					&& checkKeynotValue(str.charAt(0),'c')&& checkKeynotValue(str.charAt(0), 'B')
					&&checkKeynotValue(str.charAt(0), 'b') &&checkKeynotValue(str.charAt(0), 'r') 
					&&checkKeynotValue(str.charAt(0), 't')&&checkKeynotValue(str.charAt(0), 'm')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'C');
			
			}
		}
		return false;
	}
	private boolean Search_NUMBER() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 6 &&  checkKey(str.charAt(1),'u') && checkKey(str.charAt(2), 'm')
					&& checkKey(str.charAt(3),'b')&&  checkKey(str.charAt(4),'e') && checkKey(str.charAt(5), 'r')
					&& checkKeynotValue(str.charAt(0), 'n')&& checkKeynotValue(str.charAt(0), 'l')
					){
				//System.out.println(str);
				return substitute(str.charAt(0), 'N');
			
			}
		}
		return false;
	}
	private boolean Search_NOW() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'o') && checkKey(str.charAt(2), 'w')
					&& checkKeynotValue(str.charAt(0),'n')&& checkKeynotValue(str.charAt(0), 'B')
					&&checkKeynotValue(str.charAt(0), 'b') &&checkKeynotValue(str.charAt(0), 'r') 
					&&checkKeynotValue(str.charAt(0), 't')&&checkKeynotValue(str.charAt(0), 'h')
					&&checkKeynotValue(str.charAt(0), 'H')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'N');
			
			}
		}
		return false;
	}
	private boolean Search_EACH() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&  checkKey(str.charAt(1),'a') && checkKey(str.charAt(2), 'c')
					&& checkKey(str.charAt(3),'h')&& checkKeynotValue(str.charAt(0), 'e')
					){
				//System.out.println(str);
				return substitute(str.charAt(0), 'E');
			
			}
		}
		return false;
	}
	private boolean Search_EVERY() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&  checkKey(str.charAt(1),'v') && checkKey(str.charAt(2), 'e')
					&& checkKey(str.charAt(3),'r')&&  checkKey(str.charAt(3),'y')
					&&checkKeynotValue(str.charAt(0), 'e')
					){
				//System.out.println(str);
				return substitute(str.charAt(0), 'E');
			
			}
		}
		return false;
	}

	private boolean Search_USE() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'s') && checkKey(str.charAt(2), 'e')
					&& checkKeynotValue(str.charAt(0),'b')&& checkKeynotValue(str.charAt(0), 'B')
					&&checkKeynotValue(str.charAt(0), 'o') &&checkKeynotValue(str.charAt(0), 'p') 
					&&checkKeynotValue(str.charAt(0), 'u')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'U');
			
			}
		}
		return false;
	}
	private boolean Search_UP() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 2 &&  checkKey(str.charAt(1),'p') 
					&& checkKeynotValue(str.charAt(0),'b')&& checkKeynotValue(str.charAt(0), 'B')
					&&checkKeynotValue(str.charAt(0), 'o') &&checkKeynotValue(str.charAt(0), 'p') 
					&&checkKeynotValue(str.charAt(0), 'u')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'U');
			
			}
		}
		return false;
	}

	private boolean Search_ONE() {
		
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'n') && checkKey(str.charAt(2), 'e')
					&& checkKeynotValue(str.charAt(0),'b')&& checkKeynotValue(str.charAt(0), 'B')
					&&checkKeynotValue(str.charAt(0), 'o') &&checkKeynotValue(str.charAt(0), 'p') 
					&&checkKeynotValue(str.charAt(0), 'c')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'O');
			
			}
		}
		return false;
		
	}

	private boolean Search_OTHER() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 5 &&  checkKey(str.charAt(1),'t') && checkKey(str.charAt(2), 'h')
					&& checkKey(str.charAt(3),'e')&& checkKey(str.charAt(4), 'r')&&checkKeynotValue(str.charAt(0), 'o') ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'O');
			
			}
		}
		return false;
		
	}

	private boolean Search_For() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'o') && checkKeynotValue(str.charAt(0), 'n')
					&& checkKey(str.charAt(2),'r')&& checkKeynotValue(str.charAt(0), 'f') ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'F');
			
			}
		}
		return false;
		
	}

	private boolean Search_Has() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'a') && checkKeynotValue(str.charAt(0), 'h')&& str.charAt(0)!= '\''
					&& checkKey(str.charAt(2),'s')&& checkKeynotValue(str.charAt(0), 'W')&& checkKeynotValue(str.charAt(0), 'w') ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'H');
			
			}
		}
		return false;
		
	}

	private boolean Search_H() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'i') && checkKeynotValue(str.charAt(0), 'h')
					&& checkKey(str.charAt(2),'s')&& str.charAt(0)!= '\'' ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'H');
			
			}
		}
		return false;
		
	}

		private boolean search_Book() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&checkKey(str.charAt(1), 'o')&&checkKey(str.charAt(2), 'o')
				&&checkKey(str.charAt(3), 'k')
				&& checkKeynotValue(str.charAt(0), 'h')
				&& checkKeynotValue(str.charAt(0), 'b')){
				return substitute(str.charAt(0), 'B');
			}
		}
		return false;
	}

	private boolean search_By() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 2 &&checkKey(str.charAt(1), 'y')
				&& checkKeynotValue(str.charAt(0), 'b')
				&& checkKeynotValue(str.charAt(1), 'Y')){
				return substitute(str.charAt(0), 'B');
			}
		}
		return false;
	}

	private boolean search_But() { //need to be before O
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&checkKey(str.charAt(1), 'u')&&checkKey(str.charAt(2), 't')
				&& checkKeynotValue(str.charAt(0), 'b')&& checkKeynotValue(str.charAt(0), 'p')
				&& checkKeynotValue(str.charAt(0), 'o')
				&& checkKeynotValue(str.charAt(0), 'h')){
				if(!substitute(str.charAt(0), 'B')){
					continue;
				}
				else{
					return true;
				}
			}
		}
		return false;
	}
	private boolean Search_W() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&  checkKey(str.charAt(1),'i') && checkKeynotValue(str.charAt(0), 'w')
					&& checkKey(str.charAt(2),'t')&&checkKey(str.charAt(3),'h') ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'W');
			
			}
		}
		return false;
		
	}

	private boolean Search_F() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&  checkKey(str.charAt(1),'r') && checkKeynotValue(str.charAt(0), 'f')
					&& checkKey(str.charAt(2),'o')&&checkKey(str.charAt(3),'m') ){
				//System.out.println(str);
				return substitute(str.charAt(0), 'F');
			
			}
		}
		return false;
		
		
	}

	private boolean Search_T() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 &&  checkKey(str.charAt(1),'h') && checkKeynotValue(str.charAt(0), 't')
					&& checkKey(str.charAt(2),'e')&& checkKeynotValue(str.charAt(0), 's') && checkKeynotValue(str.charAt(0), 'S')){
				//System.out.println(str);
				return substitute(str.charAt(0), 'T');
			
			}
		}
		return false;
		
	}
		
	

	private boolean Search_INTO() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 &&  checkKey(str.charAt(1),'n') && checkKeynotValue(str.charAt(0), 'i')
					&& checkKey(str.charAt(2),'t')&& checkKey(str.charAt(3),'o')){
				return substitute(str.charAt(0), 'I');
			
			}
		}
		return false;
		
	}
		
	

	private boolean Search_IT() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 2 &&  checkKey(str.charAt(1),'t') && checkKeynotValue(str.charAt(0), 'A')
					&& checkKeynotValue(str.charAt(0), 'i')&& checkKeynotValue(str.charAt(0), 't')
					&& checkKeynotValue(str.charAt(0), 'a')){
				return substitute(str.charAt(0), 'I');
			
			}
		}
		return false;
		
	}

	private boolean Search_I(){
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 && str.charAt(1) == '\''&& checkKey(str.charAt(2),'m')
					&& checkKeynotValue(str.charAt(0), 'i')){
				return substitute(str.charAt(0), 'I');
			
			}
		}
		return false;
	}
//	private boolean Search_And(){
//		boolean flag = false;
//		for (String str : util.getWordsFromFile_()){
//			if (str.length() == 3 &&  checkKey(str.charAt(1), 'n')&&checkKey(str.charAt(2),'d')
//				&& checkKeynotValue(str.charAt(0), 'a')&&!flag){
//				flag =substitute(str.charAt(0), 'A');
//			}
//		}
//		return flag;
//	}
	private boolean search_hazard() {
		for (String str : util.getWordsFromFile_()){
			if ( str.length() == 6 && checkKey(str.charAt(0),'h') && checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(3),'a') && checkKey(str.charAt(4),'r') && checkKey(str.charAt(5),'d')){
				return substitute(str.charAt(2), 'z');
			}
		}
		return false;
	}

	private boolean search_much() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(1),'u') && checkKey(str.charAt(2),'c')
				&& checkKey(str.charAt(3),'h')
				&& checkKeynotValue(str.charAt(0),'o')
				&& checkKeynotValue(str.charAt(0),'s')){
				return substitute(str.charAt(0), 'm');
			}
		}
		return false;
	}

	private boolean search_small() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() ==  5 && checkKey(str.charAt(0),'s') && checkKey(str.charAt(2),'a')
				&& checkKey(str.charAt(3),'l')&& checkKey(str.charAt(4),'l')
				&& checkKeynotValue(str.charAt(1),'h')
				&& checkKeynotValue(str.charAt(1),'t')){
				return substitute(str.charAt(1), 'm');
			}
		}	
		return false;
	}

	private void serach_have() {
		for (String str : util.getSortedAllVecSize4()){
			if (checkKey(str.charAt(0),'h') && checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(3),'e')
				&& checkKeynotValue(str.charAt(2),'r')
				&& this.util.getSortedVecSize4().contains(str)){	
				substitute(str.charAt(2), 'v');
				break;
			}
		}			
	}

	private void search_their() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() ==  5 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(1),'h')
				&& checkKey(str.charAt(2),'e')&& checkKey(str.charAt(3),'i')){	
				substitute(str.charAt(4), 'r');
				break;
			}
		}				
	}

	private void search_good() {
		for (String str :util.getSortedAllVecSize4()){
			if (checkKey(str.charAt(1),'o') && checkKey(str.charAt(2),'o')
				&& checkKey(str.charAt(3),'d')	
				&& checkKeynotValue(str.charAt(0),'w')
				&& checkKeynotValue(str.charAt(0),'h')
				&& checkKeynotValue(str.charAt(0),'f')
				&& checkKeynotValue(str.charAt(0),'m')){
				substitute(str.charAt(0), 'g');
				break;
			}
		}			
	}

	private void search_any() {
		for (String str : util.getSortedAllVecSize3()){
			if (checkKey(str.charAt(0),'a') && checkKey(str.charAt(1),'n')
				&& checkKeynotValue(str.charAt(2),'d')
				&& checkKeynotValue(str.charAt(2),'t')
				&& checkKeynotValue(str.charAt(2),'n')){
				substitute(str.charAt(2), 'y');
				break;
			}
		}	
	}

	private void search_example() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() ==  7 && checkKey(str.charAt(0),'e') && checkKey(str.charAt(6),'e')
				&& checkKey(str.charAt(2),'a')&& checkKey(str.charAt(3),'m')&& checkKey(str.charAt(4),'p')
				&& checkKey(str.charAt(5),'l')){
				substitute(str.charAt(2), 'x');
				break;
			}
		}			
	}

	private boolean search_next() {
		for (String str : util.getSortedAllVecSize4()){
			if (checkKey(str.charAt(0),'n') && checkKey(str.charAt(1),'e')
				&& checkKey(str.charAt(3),'t')
				&& checkKeynotValue(str.charAt(2),'s')){
				return substitute(str.charAt(2), 'x');
			}
		}	
		return false;
	}

	private boolean search_add() {
		for (String str :util.getSortedAllVecSize3()){
			if (checkKey(str.charAt(0),'a') && str.charAt(1) ==str.charAt(2) 
				&& checkKeynotValue(str.charAt(2),'s')
				&& checkKeynotValue(str.charAt(2),'l')
				&& checkKeynotValue(str.charAt(2),'n')
				&& checkKeynotValue(str.charAt(2),'c')
				&& checkKeynotValue(str.charAt(2),'r')){
				return substitute(str.charAt(2), 'd');
			}
		}			
		return false;
	}

	private boolean search_had() {
		for (String str : util.getSortedAllVecSize3()){
			if (checkKey(str.charAt(0),'h') && checkKey(str.charAt(1),'a')
				&& checkKeynotValue(str.charAt(2),'s')
				&& checkKeynotValue(str.charAt(2),'m')
				&& util.getSortedVecSize3().contains(str)){
				return substitute(str.charAt(2), 'd');
			}
		}	
		return false;
	}

	private boolean search_it() {
		for (String str : util.getSortedAllVecSize2()){
			if ( checkKey(str.charAt(1),'t')
				&& checkKeynotValue(str.charAt(0),'a')
				&& checkKeynotValue(str.charAt(0),'t')
				&& checkKeynotValue(str.charAt(0),'h')
				//&& util.getSortedVecSize2().contains(str)){
				){
				return substitute(str.charAt(0), 'i');
			}
		}			
		return false;
	}

	private boolean search_size() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'s')&&checkKey(str.charAt(1),'i')&& checkKey(str.charAt(3),'e')
				&& checkKeynotValue(str.charAt(2),'d')
				&& checkKeynotValue(str.charAt(2),'k')
				&& checkKeynotValue(str.charAt(2),'t')){
				return substitute(str.charAt(2), 'z');
			}
		}
		return false;
	}

	private void search_just() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(1),'u')&&checkKey(str.charAt(2),'s')
				&& checkKey(str.charAt(3),'t')&& checkKeynotValue(str.charAt(0),'m')
				&& checkKeynotValue(str.charAt(0),'l')){
				substitute(str.charAt(0), 'Y');
				break;
			}
		}		
	}

	private boolean search_him() {
		for (String str : util.getSortedAllVecSize3()){
			if ( checkKey(str.charAt(0),'h')&&checkKey(str.charAt(1),'i')
				&&  checkKeynotValue(str.charAt(str.length()-1),'s')
				&&  checkKeynotValue(str.charAt(str.length()-1),'t')){
				return substitute(str.charAt(2), 'm');
			}
		}
		return false;
	}
	private boolean search_re() {
		boolean flag = false;
		for (String str : util.getWordsFromFile_()){
			if (str.length() > 4 && checkKey(str.charAt(str.length() - 1),'e')
				&& checkKey(str.charAt(str.length() - 3),'\'')){
				return substitute(str.charAt(str.length()- 2), 'r');
			}
		}
		return flag;
	}
	private boolean search_dozen() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==5 && checkKey(str.charAt(0),'d')&& checkKey(str.charAt(1),'o')
				&& checkKey(str.charAt(3),'e')&& checkKey(str.charAt(4),'n')
				&&checkKeynotValue(str.charAt(2), 'r')){
				return substitute(str.charAt(2), 'z');
			}
		}
		return false;
	}
	private boolean search_subject() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==7 && checkKey(str.charAt(0),'s')&& checkKey(str.charAt(1),'u')
				&& checkKey(str.charAt(2),'b')&& checkKey(str.charAt(4),'e')&& checkKey(str.charAt(5),'c')
				&& checkKey(str.charAt(6),'t')){
				return substitute(str.charAt(3), 'j');
			}
		}
		return false;
	}
	
	private boolean search_expect() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==6 && checkKey(str.charAt(0),'e')&& checkKey(str.charAt(2),'p')
				&& checkKey(str.charAt(3),'e')&& checkKey(str.charAt(4),'c')&& checkKey(str.charAt(5),'t')){
				return substitute(str.charAt(1), 'x');
			}
		}			
		return false;
	}
	private boolean search_give() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'g')&& checkKey(str.charAt(1),'i')
				&&checkKey(str.charAt(3),'e')){
				return substitute(str.charAt(2), 'v');
			}
		}			
		return false;
	}
	private void search_question() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==8 && checkKey(str.charAt(1),'u')&& checkKey(str.charAt(2),'e')
				&&checkKey(str.charAt(3),'s')&& checkKey(str.charAt(4),'t')&&checkKey(str.charAt(5),'i')
				&&checkKey(str.charAt(6),'o')&& checkKey(str.charAt(7),'n')){
				substitute(str.charAt(0), 'q');
				break;
			}
		}			
	}
	private boolean search_from() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(1),'r')	&&checkKey(str.charAt(2),'o')
				&& util.getSortedVecSize4().contains(str)){
				substitute(str.charAt(0), 'f');
				return substitute(str.charAt(3), 'm');
			}
		}			
		return false;
	}
	private void search_look() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'l')&& checkKey(str.charAt(1),'o')
				&& checkKey(str.charAt(2),'o')){
				substitute(str.charAt(3), 'k');
				break;
			}
		}			
	}
	private void search_people() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==6 && checkKey(str.charAt(1),'e')&& checkKey(str.charAt(2),'o')
				&&checkKey(str.charAt(5),'e')&& str.charAt(0) == str.charAt(3)){
				substitute(str.charAt(0),'p');
				substitute(str.charAt(4),'l');
				break;
			}
		}	
	}
	private void search_with() {
		for (String str : util.getSortedAllVecSize4()){
			if (checkKey(str.charAt(1),'i')&& checkKey(str.charAt(2),'t')
				&& checkKey(str.charAt(3),'h')){
				substitute(str.charAt(0), 'w');
				break;
			}
		}			
	}
	private void search_each() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'e')&& checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(3),'h')){
				substitute(str.charAt(2), 'c');
				break;
			}
		}			
	}
	private void search_use() {
		for (String str : util.getSortedAllVecSize3()){
			if (checkKey(str.charAt(1),'s')&& checkKey(str.charAt(2),'e')
				&&  checkKeynotValue(str.charAt(0),'o')){
				substitute(str.charAt(0), 'u');
				break;
			}
		}	
	}
	 private void search_said() {
			for (String str : util.getSortedAllVecSize4()){
				if (checkKey(str.charAt(0) ,'s')&& checkKey(str.charAt(1),'a')
					&& checkKey(str.charAt(2),'i')
					&& checkKeynotValue(str.charAt(3),'l')){
					substitute(str.charAt(3), 'd');
					break;
				}
			}	
		}
	private boolean search_anything() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()== 8 && checkKey(str.charAt(0),'a')&& checkKey(str.charAt(1),'n')
				&&checkKey(str.charAt(3),'t')&&checkKey(str.charAt(4),'h')&&checkKey(str.charAt(5),'i')
				&&checkKey(str.charAt(6),'n')){
				return substitute(str.charAt(2), 'y') && substitute(str.charAt(7), 'g');
			}
		}
		return false;
	}
	private boolean search_for() {
		for (String str : util.getSortedAllVecSize3()){
			if ( checkKey(str.charAt(1),'o')&& checkKey(str.charAt(2),'r')
				//&& util.getSortedVecSize3().contains(str)){
					){
				return substitute(str.charAt(0), 'f');
			}
		}
		return false;
	}

	private void search_about() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==5 && checkKey(str.charAt(0),'a')&& checkKey(str.charAt(2),'o')
				&& checkKey(str.charAt(3),'u')&& checkKey(str.charAt(4),'t')){
				substitute(str.charAt(1), 'b');
				break;
			}
		}			
	}
	private boolean search_into_in() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'i')&&checkKey(str.charAt(2),'t')
				&& checkKey(str.charAt(3),'o')){
				//?&& this.util.getSortedVecSize4().contains(str)
				//&& this.util.getSortedVecSize2().contains(str.substring(0, 2))){
				return substitute(str.charAt(1), 'n');
			}
		}
		return false;
	}
	private boolean search_are() { //age close enough
		for (String str : util.getSortedAllVecSize3()){
			if (checkKey(str.charAt(0),'a')&& checkKey(str.charAt(2),'e')
				&& util.getSortedVecSize3().contains(str)){
				return substitute(str.charAt(1), 'r');
			}
		}
		return false;
	}
	private boolean search_this_is() {
		for (String str : util.getSortedAllVecSize4()){
			if ( checkKey(str.charAt(0),'t')&&checkKey(str.charAt(1),'h')
				&& this.util.getSortedVecSize4().contains(str)  		    //this
				&& this.util.getSortedVecSize2().contains(str.substring(2)) //is
				&& this.util.getSortedVecSize3().contains(str.substring(1)) // his 
				&& checkKeynotValue(str.charAt(3),'t')){
				return substitute(str.charAt(3), 's') && substitute(str.charAt(2), 'i');
			}
		}
		return false;
	}
	private boolean search_s() { 
		for (String str : util.getWordsFromFile_()){
			if (str.length()> 2 && str.charAt(str.length()-2) == '\''
				&&  checkKeynotValue(str.charAt(str.length()-1),'t')){	
				return substitute(str.charAt(str.length()-1), 's');
			}
		}
		return false;
	}
	private boolean search_that() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 4 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(3),'t') 
				&& checkKey(str.charAt(1),'h')
				//& util.getSortedVecSize4().contains(str) ){
				){
				return substitute(str.charAt(2), 'a');
			}
		}
		return false;
	}
	private boolean search_to() {
		for (String str : util.getSortedAllVecSize2()){
			if ( checkKey(str.charAt(0),'t')
				&& util.getSortedVecSize2().contains(str)){
				return substitute(str.charAt(1), 'o');
			}
		}
		return false;
	}
	private boolean search_too() {
		for (String str : util.getSortedAllVecSize3()){
			if ( checkKey(str.charAt(0),'t') && str.charAt(1)==str.charAt(2)){
				return substitute(str.charAt(1), 'o');
			}
		}
		return false;
	}
	private void search_the() {
		substitute(util.getSortedLettersFreq_()[61],'t');
		substitute(util.getSortedLettersFreq_()[60],'e');
		int[] appear = new int[123];
		for (String str : util.getWordsFromFile_()){
			if (str.length()== 3 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(2),'e')){
				appear[str.charAt(1)]++;
			}
		}
		int maxIndex = findMaxInArray(appear);
		int maxChar = appear[maxIndex];
		
		int[] appear2 = new int[123];
		//this.key.remove(util.getSortedLettersFreq_()[61]);
		//this.key.remove(util.getSortedLettersFreq_()[60]);
		key.put(util.getSortedLettersFreq_()[61],'e');
		key.put(util.getSortedLettersFreq_()[60],'t');
		reverseKey.put('e',util.getSortedLettersFreq_()[61]);
		reverseKey.put('t',util.getSortedLettersFreq_()[60]);
		
		for (String str : util.getWordsFromFile_()){
			if (str.length()== 3 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(2),'e')){
				appear2[str.charAt(1)]++;
			}
		}
		int maxIndex2 = findMaxInArray(appear2);
		int maxChar2 = appear2[maxIndex2];
		if (maxChar > maxChar2){
			//this.key.remove(util.getSortedLettersFreq_()[61]);
			//this.key.remove(util.getSortedLettersFreq_()[60]);
			key.put(util.getSortedLettersFreq_()[61],'t');
			key.put(util.getSortedLettersFreq_()[60],'e');
			reverseKey.put('t',util.getSortedLettersFreq_()[61]);
			reverseKey.put('e',util.getSortedLettersFreq_()[60]);
			substitute((char) maxIndex , 'h');
		}
		else{
			substitute((char) maxIndex2 ,'h');
		}
	}

	
	private boolean checkKey(char charAt, char ch) {
		Character tmp = key.get(charAt);
		if (tmp == null){
			return false;
		}
		else {
			return (tmp == ch);
		}
	}
	private boolean checkKeynotValue(char charAt, char ch) {
		Character tmp = key.get(charAt);
		if (tmp == null){
			return true;
		}
		else {
			return (tmp != ch);
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
	private boolean substitute(Character oldChar,Character newChar){
		if (key.get(oldChar) != '?'){
			return false;
		}
		System.out.println("sub :"+oldChar +" with = "+newChar);
		key.put(oldChar, newChar);
		reverseKey.put(newChar, oldChar);
		numOfSubs++;
		return true;
	}
	
	public String translate(String str){
		char[] chars = str.toCharArray();
		for (int i=0 ; i<chars.length;i++){
			Character tmpValue = key.get(chars[i]);
			if (tmpValue == null){
				continue;
			}
			chars[i] = tmpValue;
		}
		return new String(chars);
	}
	private void initDict(){
		this.dictionaryVec = new Vector<String>();
		//initializing the dictionary :
		File dictFile = new File(this.dictFile);
		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(dictFile));
			String word;
			while((word = inputStream.readLine()) != null)
				this.dictionaryVec.add(word.toLowerCase());
		}
		catch(Exception e){
			System.out.println("dictionary file missing");
		}
	}

}
