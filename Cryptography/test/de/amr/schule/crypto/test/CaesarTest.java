package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Caesar;

public class CaesarTest {

	@Test
	public void test() {
		Caesar caesar = new Caesar();
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		String chiffre = caesar.encrypt(text);
		String decrypted = caesar.decrypt(chiffre);
		Assert.assertEquals(text, decrypted);

		System.out.println("Caesar");
		System.out.println(text);
		System.out.println(chiffre);
		System.out.println(new String(decrypted));
	}
}
