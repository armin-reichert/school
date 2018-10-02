package de.amr.schule.crypto.alg;

import java.util.Random;

import de.amr.schule.crypto.api.MonoSubstitution;

public class Kamasutra implements MonoSubstitution {

	public static char[][] randomSubstitutions() {
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .".toCharArray();
		char[][] result = new char[alphabet.length / 2][2];
		shuffle(alphabet);
		int j = 0;
		for (int i = 0; i < alphabet.length / 2; ++i) {
			result[i][0] = alphabet[j++];
			result[i][1] = alphabet[j++];
		}
		return result;
	}

	public static void shuffle(char[] array) { // mix-up the array
		Random rand = new Random();
		for (int i = array.length - 1; i > 0; --i) {
			int j = rand.nextInt(i + 1);
			char temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	private final char[][] substitutions;

	public Kamasutra(char[][] substitutions) {
		this.substitutions = substitutions;
	}

	public Kamasutra() {
		this(randomSubstitutions());
	}

	public char[][] getSubstitutions() {
		return substitutions;
	}

	private char replace(char c) {
		for (int i = 0; i < substitutions.length; ++i) {
			if (c == substitutions[i][0]) {
				return substitutions[i][1];
			}
			if (c == substitutions[i][1]) {
				return substitutions[i][0];
			}
		}
		return c;
	}

	@Override
	public String encrypt(String text) {
		return replace(text, this::replace);
	}

	@Override
	public String decrypt(String text) {
		return encrypt(text);
	}
}