package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Caesar;

public class CaesarTest {

	@Test
	public void test() {
		System.out.println("Caesar");
		Caesar caesar = new Caesar();
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		System.out.println(text);
		String chiffre = caesar.encrypt(text);
		System.out.println(chiffre);
		String decrypted = caesar.decrypt(chiffre);
		System.out.println(new String(decrypted));
		Assert.assertEquals(text, decrypted);
	}
}
