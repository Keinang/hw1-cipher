package CipertextAttack;

import junit.framework.TestCase;

public class CipherTextAttackTest extends TestCase {

	CipherTextAttack ct = new CipherTextAttack();
	Util util = new Util();
	
	protected void setUp() throws Exception {
		String cipherText = "text.txt";
		Encrypt encrypt = new Encrypt();
		encrypt.encryptWithKey(cipherText, "encryptedTxt.txt");
		cipherText = "encryptedTxt.txt";
		
	}

	public void testHyphen() {
		assertTrue(util.hyphen("abc-def"));
		assertEquals(util.getWordsFromFile_().get(0), "abc");
		assertEquals(util.getWordsFromFile_().get(1), "def");
	}

	public void testRemoveSignsFromEndOfWord() {
		assertEquals("abcd",util.removeSignsFromEndOfWord('.', "abcd."));
	}

	public void testRemoveSignsFromBeginingOfWord() {
		assertEquals("abcd",util.removeSignsFromBeginingOfWord('(', "(abcd"));
	}

}
