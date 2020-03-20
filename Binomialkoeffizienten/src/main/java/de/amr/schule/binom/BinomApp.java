package de.amr.schule.binom;

public class BinomApp {

	static final int MAX = 20;
	static final int[][] B = new int[MAX + 1][MAX + 1];
	static {
		for (int n = 0; n <= MAX; ++n) {
			B[n][0] = 1;
			B[n][n] = 1;
		}
		for (int n = 2; n <= MAX; ++n) {
			for (int k = 1; k <= n - 1; ++k) {
				B[n][k] = B[n - 1][k - 1] + B[n - 1][k];
			}
		}
	}

	public static void main(String[] args) {
		for (int n = 0; n <= MAX; ++n) {
			for (int k = 0; k <= n; ++k) {
				System.out.print(over(n, k) + " ");
			}
			System.out.println();
		}
	}

	static int over(int n, int k) {
		if (0 <= n && n <= MAX && 0 <= k && k <= n) {
			return B[n][k];
		}
		throw new IllegalArgumentException();
	}
}
