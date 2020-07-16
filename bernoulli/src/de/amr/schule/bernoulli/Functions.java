package de.amr.schule.bernoulli;

public class Functions {

	public static double binomial(int n, int k) {
		double binomial = 1;
		for (int i = 0; i < k; ++i) {
			binomial *= (double) (n - i) / (k - i);
		}
		return binomial;
	}

	public static double pow(double p, double k) {
		double pow = 1.0;
		while (k-- > 0) {
			pow *= p;
		}
		return pow;
	}

	public static double b(int n, double p, int k) {
		return binomial(n, k) * pow(p, k) * pow(1 - p, n - k);
	}

	public static double b_leq(int n, double p, int k) {
		double b = 0;
		for (int i = 0; i <= k; ++i) {
			b += b(n, p, i);
		}
		return b;
	}

	public static double b_geq(int n, double p, int k) {
		double b = 0;
		for (int i = k; i <= n; ++i) {
			b += b(n, p, i);
		}
		return b;
	}

	public static double b_less(int n, double p, int k) {
		return 1.0 - b_geq(n, p, k);
	}

	public static double b_greater(int n, double p, int k) {
		return 1.0 - b_leq(n, p, k);
	}
}