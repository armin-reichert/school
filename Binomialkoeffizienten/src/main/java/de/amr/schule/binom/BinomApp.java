package de.amr.schule.binom;

public class BinomApp {

	static final int MAX = 20;
	static final int[][] BK = new int[MAX + 1][MAX + 1];
	static {
		for (int i = 0; i <= MAX; ++i) {
			BK[i][0] = 1;
			BK[i][i] = 1;
		}
		for (int i = 2; i <= MAX; ++i) {
			for (int j = 1; j <= MAX - 1; ++j) {
				BK[i][j] = BK[i - 1][j - 1] + BK[i - 1][j];
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
			return BK[n][k];
		}
		throw new IllegalArgumentException();
	}
}
