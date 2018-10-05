package de.amr.schule.crypto.alg;

import de.amr.schule.crypto.api.MonoSubstitution;

public class Caesar implements MonoSubstitution {

	private char[] alphabet;
	private char[] chiffre;

	public Caesar() {
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .");
	}

	public Caesar(String alphabetAsString) {
		this(alphabetAsString, 3);
	}

	public Caesar(String alphabetAsString, int delta) {
		this.alphabet = alphabetAsString.toCharArray();
		chiffre = new char[alphabet.length];
		for (int i = 0, n = alphabet.length; i < n; ++i) {
			chiffre[i] = alphabet[(i + delta) % n];
		}
	}

	@Override
	public String encrypt(String text) {
		return replace(text, c -> chiffre[index(c, alphabet)]);
	}

	@Override
	public String decrypt(String text) {
		return replace(text, c -> alphabet[index(c, chiffre)]);
	}
}