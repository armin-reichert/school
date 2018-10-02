package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Gartenzaun;

public class GartenzaunTest {

	@Test
	public void test() {
		System.out.println("Gartenzaun");
		Gartenzaun gartenzaun = new Gartenzaun(3);
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		System.out.println(text);
		String chiffre = gartenzaun.encrypt(text);
		System.out.println(chiffre);
		String decrypted = gartenzaun.decrypt(chiffre);
		System.out.println(decrypted);
		Assert.assertEquals(text, decrypted);
	}
}