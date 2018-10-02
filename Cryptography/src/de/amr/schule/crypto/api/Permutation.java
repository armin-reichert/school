package de.amr.schule.crypto.api;

import java.util.Arrays;

public class Permutation {

	private final int[] values;

	public static Permutation identity(int n) {
		Permutation identity = new Permutation(new int[n]);
		for (int i = 0; i < n; ++i) {
			identity.values[i] = i;
		}
		return identity;
	}
	
	public static Permutation perm(int...values) {
		return new Permutation(values);
	}

	private Permutation(int... values) {
		this.values = new int[values.length];
		System.arraycopy(values, 0, this.values, 0, values.length);
	}

	public int length() {
		return values.length;
	}

	public int value(int i) {
		return values[i];
	}

	public Permutation inv() {
		Permutation inv = new Permutation(values);
		for (int i = 0; i < length(); ++i) {
			int j = values[i];
			inv.values[j] = i;
		}
		return inv;
	}

	public Permutation before(Permutation p) {
		int[] result = new int[length()];
		for (int i = 0; i < result.length; i++) {
			result[i] = p.value(value(i));
		}
		return new Permutation(result);
	}

	public Permutation after(Permutation p) {
		int[] result = new int[length()];
		for (int i = 0; i < result.length; i++) {
			result[i] = value(p.value(i));
		}
		return new Permutation(result);
	}
	
	public String apply(String text) {
		if (text.length() != length()) {
			throw new IllegalArgumentException("Text length does not match permutation");
		}
		char[] result = new char[text.length()];
		for (int i = 0; i < text.length(); ++i) {
			result[values[i]] = text.charAt(i);
		}
		return new String(result);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length(); ++i) {
			s.append(values[i]);
			if (i < length() - 1) {
				s.append(" ");
			}
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permutation other = (Permutation) obj;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}
}
