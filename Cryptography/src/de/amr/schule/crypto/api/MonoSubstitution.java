package de.amr.schule.crypto.api;

import java.util.function.Function;

public interface MonoSubstitution extends Crypto {

	default String replace(String text, Function<Character, Character> substitution) {
		char[] symbols = text.toCharArray();
		char[] result = new char[symbols.length];
		for (int i = 0; i < symbols.length; ++i) {
			result[i] = substitution.apply(symbols[i]);
		}
		return new String(result);
	}

	default int index(char c, char[] array) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i] == c) {
				return i;
			}
		}
		return -1;
	}
}