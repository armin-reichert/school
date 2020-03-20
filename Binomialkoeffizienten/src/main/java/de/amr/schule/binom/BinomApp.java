package de.amr.schule.binom;

public class BinomApp {

	static final int N = 25;
	static final int[][] B = new int[N + 1][N + 1];

	static {
		for (int n = 0; n <= N; ++n) {
			B[n][0] = B[n][n] = 1;
			for (int k = 1; k <= n - 1; ++k) {
				B[n][k] = B[n - 1][k - 1] + B[n - 1][k];
			}
		}
	}

	public static void main(String[] args) {
		for (int n = 0; n <= N; ++n) {
			for (int k = 0; k <= n; ++k) {
				System.out.print(over(n, k) + " ");
			}
			System.out.println();
		}
	}

	static int over(int n, int k) {
		if (0 <= n && n <= N && 0 <= k && k <= n) {
			return B[n][k];
		}
		throw new IllegalArgumentException();
	}
}
