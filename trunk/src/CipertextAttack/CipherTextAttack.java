package CipertextAttack;

import java.util.HashMap;

/**
 *  
 * @author GK
 *
 */
public class CipherTextAttack {
	private Util util = new Util();
	private Key keyBuilder = new Key();
	private HashMap<Character,Character> key;
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
//		make encrypt file to work with
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
		key = this.keyBuilder.getKey();
		util.getWordsFromFile(cipherText);
		util.calcFreq();
		util.sortFreq();
		util.initAllHashes();
		
		search_the();     //getting e,t,h
		search_that();    //getting a

		if (!search_s()){ //getting s 
			search_this_is();
		}
		if (!search_re()){  //getting r
			search_are();	
		}
		search_too();     //getting o ;to
		search_for();     //getting f
		search_this();    //getting i
		//search_on();	  //getting n
		search_into();    //getting n ; try "one"
		search_anything();//getting y,g
		search_use();     //getting u
		search_about();	  //getting b
		search_each();    //getting c ;try which
		search_people();  //getting p,l; try part
		search_said();    //getting d
		search_with();    //getting w
		search_look();    //getting k ; try like
		search_from();    //getting m
		search_question();//getting q //try quite
		search_give();    //getting v
		search_expect();  //getting x
		search_subject(); //getting j
		search_dozen();   //getting z ; try realize,organize;
		
		search_S();		  //getting S
		search_You();	  //getting Y
		
		//print for testing
		util.printTempDecryptFile(this.key,"encryptedTxt.txt");
		
		//printing the Result key to the output file :
    	util.printResult(this.key,cipherText);
	}

	
	private void search_You() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() ==  3 && checkKey(str.charAt(1),'o')&&checkKey(str.charAt(2),'u')){
				substitute(str.charAt(0), 'Y');
				break;
			}
		}
	}

	private boolean search_re() {
		boolean flag = false;
		for (String str : util.getWordsFromFile_()){
			if (str.length() > 4 && checkKey(str.charAt(str.length() - 1),'e')
				&& checkKey(str.charAt(str.length() - 3),'\'')){
				substitute(str.charAt(str.length()- 2), 'r');
				return true;
			}
		}
		return flag;
	}

	private void search_dozen() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==5 && checkKey(str.charAt(0),'d')&& checkKey(str.charAt(1),'o')
				&& checkKey(str.charAt(3),'e')&& checkKey(str.charAt(4),'n')){
				substitute(str.charAt(2), 'z');
				break;
			}
		}
	}

	private void search_subject() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==7 && checkKey(str.charAt(0),'s')&& checkKey(str.charAt(1),'u')
				&& checkKey(str.charAt(2),'b')&& checkKey(str.charAt(4),'e')&& checkKey(str.charAt(5),'c')
				&& checkKey(str.charAt(6),'t')){
				substitute(str.charAt(3), 'j');
				break;
			}
		}
	}

	private void search_expect() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==6 && checkKey(str.charAt(0),'e')&& checkKey(str.charAt(2),'p')
				&& checkKey(str.charAt(3),'e')&& checkKey(str.charAt(4),'c')&& checkKey(str.charAt(5),'t')){
				substitute(str.charAt(1), 'x');
				break;
			}
		}			
	}

	private void search_give() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'g')&& checkKey(str.charAt(1),'i')
				&&checkKey(str.charAt(3),'e')){
				substitute(str.charAt(2), 'v');
				break;
			}
		}			
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
	private void search_from() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()== 4 && checkKey(str.charAt(0),'f')&& checkKey(str.charAt(1),'r')
				&&checkKey(str.charAt(2),'o')){
				substitute(str.charAt(3), 'm');
				break;
			}
		}			
	}

	private void search_look() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'l')&& checkKey(str.charAt(1),'o')
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
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(1),'i')&& checkKey(str.charAt(2),'t')
				&& checkKey(str.charAt(3),'h')){
				substitute(str.charAt(0), 'w');
				break;
			}
		}			
	}

	private void search_each() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'e')&& checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(3),'h')){
				substitute(str.charAt(2), 'c');
				break;
			}
		}			
	}

	private void search_use() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==3 && checkKey(str.charAt(1),'s')&& checkKey(str.charAt(2),'e')){
				substitute(str.charAt(0), 'u');
				break;
			}
		}	
	}

	private void search_anything() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==8 && checkKey(str.charAt(0),'a')&& checkKey(str.charAt(1),'n')
				&&checkKey(str.charAt(3),'t')&&checkKey(str.charAt(4),'h')&&checkKey(str.charAt(5),'i')
				&&checkKey(str.charAt(6),'n')){
				substitute(str.charAt(2), 'y');
				substitute(str.charAt(7), 'g');
				break;
			}
		}			
	}

	private void search_for() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==3 && checkKey(str.charAt(1),'o')&& checkKey(str.charAt(2),'r')){
				substitute(str.charAt(0), 'f');
				break;
			}
		}	
	}
	private void search_said() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'s')&& checkKey(str.charAt(1),'a')
				&& checkKey(str.charAt(2),'i')&& !checkKey(str.charAt(3),'l')){
				substitute(str.charAt(3), 'd');
				break;
			}
		}	
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

	private boolean search_into() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'i')&& checkKey(str.charAt(2),'t')
				&& checkKey(str.charAt(3),'o')){
				substitute(str.charAt(1), 'n');
				return true;
			}
		}	
		return false;
	}
	private void search_this() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'t')&& checkKey(str.charAt(1),'h')
				&& checkKey(str.charAt(3),'s')){
				substitute(str.charAt(2), 'i');
				break;
			}
		}
	}
	private void search_are() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==3 && checkKey(str.charAt(0),'a')&& checkKey(str.charAt(2),'e')){
				substitute(str.charAt(1), 'r');
				break;
			}
		}
	}
	private boolean search_this_is() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'t')&&checkKey(str.charAt(1),'h')
				&& this.util.getSortedVecSize4().contains(str)&& 
				this.util.getSortedVecSize2().contains(str.substring(2))){
				substitute(str.charAt(3), 's');
				substitute(str.charAt(2), 'i');
				return true;
			}
		}
		return false;
	}
	private boolean search_s() {
		int[] charsRep = new int[123];
		for (String str : util.getWordsFromFile_()){
			if (str.length()>= 2 && checkKey(str.charAt(str.length()-2),'\'') 
				&&!checkKey(str.charAt(str.length()-1),'t')){
				charsRep[str.charAt(str.length()-1)]+=1;
				if(charsRep[str.charAt(str.length()-1)]> 20){
					substitute(str.charAt(str.length()-1), 's');
					return true;
				}
			}
		}
		return false;
	}
	private boolean search_S() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()>= 2 && checkKey(str.charAt(str.length()-2),'\'') 
				&& !checkKey(str.charAt(str.length()-1),'t')
				&& !checkKey(str.charAt(str.length()-1),'s')){
					substitute(str.charAt(str.length()-1), 'S');
					return true;
				}
		}
		return false;
	}
	private boolean search_that() {
		for (String str : util.getWordsFromFile_()){
			if (str.length()==4 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(3),'t') 
				&& checkKey(str.charAt(1),'h') ){
				substitute(str.charAt(2), 'a');
				return true;
			}
		}
		return false;
	}
	private void search_too() {
		for (String str : util.getWordsFromFile_()){
			if (str.length() == 3 && checkKey(str.charAt(0),'t') && str.charAt(1)==str.charAt(2)){
				substitute(str.charAt(1), 'o');
				break;
			}
		}
	}
	private void search_the() {
		substitute(util.getSortedLettersFreq_()[61],'t');
		substitute(util.getSortedLettersFreq_()[60],'e');
		int[] appear = new int[123];
		for (String str : util.getWordsFromFile_()){
			if (str.length()==3 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(2),'e')){
				appear[str.charAt(1)]++;
			}
		}
		int maxIndex = findMaxInArray(appear);
		int maxChar = appear[maxIndex];
		
		int[] appear2 = new int[123];
		this.key.remove(util.getSortedLettersFreq_()[61]);
		this.key.remove(util.getSortedLettersFreq_()[60]);
		substitute(util.getSortedLettersFreq_()[61],'e');
		substitute(util.getSortedLettersFreq_()[60],'t');
		
		for (int i=0;i<util.getWordsFromFile_().size();i++){
			String str =util.getWordsFromFile_().get(i);
			if (str.length() == 3 && checkKey(str.charAt(0),'t') && checkKey(str.charAt(2),'e')){
				appear2[str.charAt(1)]++;
			}
		}
		int maxIndex2 = findMaxInArray(appear2);
		int maxChar2 = appear2[maxIndex2];
		if (maxChar > maxChar2){
			this.key.remove(util.getSortedLettersFreq_()[61]);
			this.key.remove(util.getSortedLettersFreq_()[60]);
			substitute(util.getSortedLettersFreq_()[61],'t');
			substitute(util.getSortedLettersFreq_()[60],'e');
			substitute((char) maxIndex , 'h');
		}
		else{
			substitute((char) maxIndex2 ,'h');
		}
	}

	private boolean checkKey(char charAt, char ch) {
		Character tmp =this.key.get(charAt);
		if (tmp == null){
			return false;
		}
		else {
			return (tmp == ch);
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
		this.key.put(oldChar, newChar);
	}
}

