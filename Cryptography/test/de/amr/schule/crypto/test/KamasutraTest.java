package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Kamasutra;

public class KamasutraTest {

	static void print(char[][] subs) {
		for (int i = 0; i < subs.length; ++i) {
			System.out.println(String.format("'%c' <-> '%c'", subs[i][0], subs[i][1]));
		}
	}

	@Test
	public void test() {
		System.out.println("Kamasutra");

		Kamasutra kamasutra = new Kamasutra();
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		String chiffre = kamasutra.encrypt(text);
		String decrypted = kamasutra.decrypt(chiffre);
		Assert.assertEquals(text, decrypted);

		print(kamasutra.getSubstitutions());
		System.out.println(text);
		System.out.println(chiffre);
		System.out.println(decrypted);
	}
}