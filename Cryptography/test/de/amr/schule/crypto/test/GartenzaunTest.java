package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Gartenzaun;

public class GartenzaunTest {

	@Test
	public void test() {
		int numRows = 3;
		Gartenzaun gartenzaun = new Gartenzaun(numRows);
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		String chiffre = gartenzaun.encrypt(text);
		String decrypted = gartenzaun.decrypt(chiffre);
		Assert.assertEquals(text, decrypted);

		System.out.println(String.format("Gartenzaun, %d rows", numRows));
		System.out.println(text);
		System.out.println(chiffre);
		System.out.println(decrypted);
	}
}