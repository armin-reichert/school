package de.amr.schule.crypto.alg;

import de.amr.schule.crypto.api.MonoSubstitution;

public class Caesar implements MonoSubstitution {

	private char[] alphabet;
	private char[] alphabetRight3;

	public Caesar() {
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .");
	}

	public Caesar(String alphabetAsString) {
		this.alphabet = alphabetAsString.toCharArray();
		alphabetRight3 = new char[alphabet.length];
		for (int i = 0, n = alphabet.length; i < n; ++i) {
			alphabetRight3[i] = alphabet[(i + 3) % n];
		}
	}

	@Override
	public String encrypt(String text) {
		return replace(text, c -> alphabetRight3[index(c, alphabet)]);
	}

	@Override
	public String decrypt(String text) {
		return replace(text, c -> alphabet[index(c, alphabetRight3)]);
	}
}