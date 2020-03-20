package de.amr.schule.binom;

public class BinomApp {

	public static void main(String[] args) {
		BinomApp app = new BinomApp();
		app.printAll();
	}

	static final int MAX_N = 20;
	static final int[][] B = new int[MAX_N + 1][];

	BinomApp() {
		for (int n = 0; n <= MAX_N; ++n) {
			B[n] = new int[n + 1];
			B[n][0] = B[n][n] = 1;
			for (int k = 1; k <= n - 1; ++k) {
				B[n][k] = B[n - 1][k - 1] + B[n - 1][k];
			}
		}
	}

	int over(int n, int k) {
		if (0 <= n && n <= MAX_N && 0 <= k && k <= n) {
			return B[n][k];
		}
		throw new IllegalArgumentException();
	}

	void printAll() {
		for (int n = 0; n <= MAX_N; ++n) {
			for (int k = 0; k <= n; ++k) {
				System.out.print(over(n, k) + " ");
			}
			System.out.println();
		}
	}
}