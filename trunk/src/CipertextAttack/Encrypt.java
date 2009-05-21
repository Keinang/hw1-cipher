package CipertextAttack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class Encrypt {
	
	private String input;
	private String output;
	private DestKey randomKey ;
	private Random rand = new Random();
	HashSet<Integer> usedChars = new HashSet<Integer>();
	Character[] usedLocations = {48,49,50,51,52,53,54,55,56,57,
	65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
	97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122};
	
	
	public Encrypt(String input, String output) {
		this.input = input;
		this.output = output;
		this.randomKey = new DestKey();
	}
	
	private void randomizeKey() {
		int counter = 0;
		//randomize LowerCase
		int counterLower = 0;
		while (counterLower < 26){
			int tmpChar = rand.nextInt(123); 
			if (tmpChar >= 97 && !usedChars.contains(tmpChar)){
				usedChars.add(tmpChar);
				this.randomKey.getKey().put(usedLocations[counter],(char)tmpChar);
				counter++;
				counterLower++;
			}
		}
		//randomize UpperCase
		int counterUpper = 0;
		while (counterUpper < 26){
			int tmpChar = rand.nextInt(91); 
			if (tmpChar >= 65 && !usedChars.contains(tmpChar)){
				usedChars.add(tmpChar);
				this.randomKey.getKey().put(usedLocations[counter], (char)tmpChar);
				counter++;
				counterUpper++;
			}
		}
		//randomize Numbers
		int counterNumbers = 0;
		while (counterNumbers < 9){
			int tmpChar = rand.nextInt(58); 
			if (tmpChar >= 48 && !usedChars.contains(tmpChar)){
				usedChars.add(tmpChar);
				this.randomKey.getKey().put(usedLocations[counter], (char)tmpChar);
				counter++;
				counterNumbers++;
			}
		}
	}

	public void on() {
		randomizeKey();
		this.randomKey.tostring("randomKey.txt");	
		this.encryptWithKey();
	}
	private void encryptWithKey() {
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new FileReader(input));
			out = new BufferedWriter(new FileWriter(output));
			String str;
			
			//Getting String and substitute it with the key
		    while ((str = in.readLine()) != null) {
		    	char[] charsInString = new char[str.length()];
				str.getChars(0, str.length(), charsInString, 0);
				for (Character cha:charsInString){
					if (Integer.valueOf(cha) >=48 && Integer.valueOf(cha) <=57 ||
						Integer.valueOf(cha) >=65 && Integer.valueOf(cha) <=90 ||	
						Integer.valueOf(cha) >=97 && Integer.valueOf(cha) <=122){
						
						Character newChar = randomKey.getKey().get(cha);
						out.write(newChar);
					}
					else{
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

	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	
}
