package de.amr.schule.crypto.alg;

import static de.amr.schule.crypto.api.Permutation.perm;

import de.amr.schule.crypto.api.Crypto;
import de.amr.schule.crypto.api.Permutation;

public class Gartenzaun implements Crypto {

	private final int numRows;

	public Gartenzaun(int numRows) {
		this.numRows = numRows;
	}

	public Permutation permutation(int textLength) {
		int[] values = new int[textLength];
		int i = 0;
		for (int row = 0; row < numRows; ++row) {
			for (int pos = 0; pos < textLength; ++pos) {
				if (pos % numRows == row) {
					values[i] = pos;
					i += 1;
				}
			}
		}
		return perm(values).inv();
	}

	@Override
	public String encrypt(String text) {
		char[] symbols = text.toCharArray();
		int n = symbols.length;
		int numCols = n % numRows == 0 ? n / numRows : n / numRows + 1;
		char[][] grid = new char[numRows][numCols];
		for (int i = 0; i < n; i += 1) {
			int col = i / numRows; // Ganzzahliger Quotient
			int row = i % numRows; // Rest
			grid[row][col] = symbols[i];
		}
		char[] chiffre = new char[n];
		int i = 0;
		for (int row = 0; row < numRows; row += 1) {
			for (int col = 0; col < numCols; col += 1) {
				if (col * numRows + row < n) {
					chiffre[i++] = grid[row][col];
				}
			}
		}
		return new String(chiffre);
	}

	@Override
	public String decrypt(String chiffre) {
		char[] symbols = chiffre.toCharArray();
		int n = symbols.length;
		int numCols = n % numRows == 0 ? n / numRows : n / numRows + 1;
		char[][] grid = new char[numRows][numCols];
		int i = 0;
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (col * numRows + row < n) {
					grid[row][col] = symbols[i++];
				}
			}
		}
		i = 0;
		char[] text = new char[n];
		for (int col = 0; col < numCols; col++) {
			for (int row = 0; row < numRows; row++) {
				if (col * numRows + row < n) {
					text[i++] = grid[row][col];
				}
			}
		}
		return new String(text);
	}
}