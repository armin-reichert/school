package de.amr.schule.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.crypto.alg.Kamasutra;

public class KamasutraTest {

	static void printSubstitutions(char[][] substitutions) {
		for (int i = 0; i < substitutions.length; ++i) {
			System.out.println(substitutions[i][0] + " - " + substitutions[i][1]);
		}
	}

	@Test
	public void test() {
		System.out.println("Kamasutra");
		Kamasutra kamasutra = new Kamasutra();
		printSubstitutions(kamasutra.getSubstitutions());
		String text = "PETERCHEN RAFFT MAL WIDA NIX";
		System.out.println(text);
		String chiffre = kamasutra.encrypt(text);
		System.out.println(chiffre);
		String decrypted = kamasutra.decrypt(chiffre);
		System.out.println(decrypted);
		Assert.assertEquals(text, decrypted);
	}
}