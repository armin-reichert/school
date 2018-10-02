package de.amr.schule.crypto.alg;

import de.amr.schule.crypto.api.MonoSubstitution;

public class Caesar implements MonoSubstitution {

	private char[] alphabet;
	private char[] alphabetRight3;
	private char[] alphabetLeft3;

	public Caesar() {
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZ .");
	}

	public Caesar(String alphabetString) {
		this.alphabet = alphabetString.toCharArray();
		alphabetRight3 = new char[alphabet.length];
		alphabetLeft3 = new char[alphabet.length];
		for (int i = 0, n = alphabet.length; i < n; ++i) {
			alphabetLeft3[i] = alphabet[(i - 3 + n) % n];
			alphabetRight3[i] = alphabet[(i + 3) % n];
		}
	}

	@Override
	public String encrypt(String text) {
		return replace(text, c -> alphabetRight3[find(c, alphabet)]);
	}

	@Override
	public String decrypt(String text) {
		return replace(text, c -> alphabetLeft3[find(c, alphabet)]);
	}
}