package CipertextAttack;

import java.util.HashMap;
/**
 * The Class Key contain the Key structure.
 * @author GK
 */
public class Key {
	private HashMap<Character, Character> key;
	
	public Key() {
		key = new HashMap<Character, Character>();
		for (char ch = 'a'; ch <= 'z' ; ch++){
			key.put(ch, '?');
		}
		for (char ch = 'A'; ch <= 'Z' ; ch++){
			key.put(ch, '?');
		}
		for (char ch = '0'; ch <= '9' ; ch++){
			key.put(ch, '?');
		}
	}

	public HashMap<Character, Character> getKey() {
		return key;
	}
	
}
