package CipertextAttack;

import junit.framework.TestCase;

public class CipherTextAttackTest extends TestCase {

	CipherTextAttack ct = new CipherTextAttack();
	
	protected void setUp() throws Exception {
		String cipherText = "text.txt";
		Encrypt encrypt = new Encrypt();
		encrypt.encryptWithKey(cipherText, "encryptedTxt.txt");
		cipherText = "encryptedTxt.txt";
		
	}

	public void testHyphen() {
		assertTrue(ct.hyphen("abc-def"));
		assertEquals(ct.getWordsFromFile().get(0), "abc");
		assertEquals(ct.getWordsFromFile().get(1), "def");
	}

	public void testRemoveSignsFromEndOfWord() {
		assertEquals("abcd",ct.removeSignsFromEndOfWord('.', "abcd."));
	}

	public void testRemoveSignsFromBeginingOfWord() {
		assertEquals("abcd",ct.removeSignsFromBeginingOfWord('(', "(abcd"));
	}

}
