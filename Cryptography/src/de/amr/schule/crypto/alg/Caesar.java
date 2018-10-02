package de.amr.schule.crypto.alg;

import de.amr.schule.crypto.api.MonoSubstitution;

public class Caesar implements MonoSubstitution {

	private char[] alphabet;
	private char[] alphabetRightShift3;
	private char[] alphabetLeftShift3;

	public Caesar() {
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZ .");
	}
	
	public Caesar(String alphabetString) {
		this.alphabet = alphabetString.toCharArray();
		alphabetRightShift3 = alphabetString.toCharArray();
		alphabetLeftShift3 = alphabetString.toCharArray();
		for (int i = 0, n = alphabet.length; i < n; ++i) {
			alphabetLeftShift3[i] = alphabet[(i - 3 + n) % n];
			alphabetRightShift3[i] = alphabet[(i + 3) % n];
		}
	}

	public String encrypt(String text) {
		return replace(text, c -> alphabetRightShift3[find(c, alphabet)]);
	}

	public String decrypt(String text) {
		return replace(text, c -> alphabetLeftShift3[find(c, alphabet)]);
	}
}