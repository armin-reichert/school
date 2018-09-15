package de.amr.schule.ivs;

public class IntervallschachtelungApp {

	public static void main(String[] args) {
		double x = hochwurzel(4, 2);
		System.out.println("4 hoch wurzel(2)=" + x);

		double a = 2.0;
		long time = System.currentTimeMillis();
		while (a < 3_000) {
			double wurzel = wurzel(a);
			System.out.println(String.format("Wurzel(%g) ist ungefähr %.10g", a, wurzel));
			a += 1;
		}
		long used = System.currentTimeMillis() - time;
		System.out.println("Time[sec] needed: " + used / 1000);
	}

	static double[] schachtelung(double a, double b, double p, double q) {
		double delta = (q - p) / 10;
		double pi, qi;
		pi = p;
		qi = p + delta;
		for (int i = 0; i <= 9; i += 1) {
			// System.out.println(String.format("Interval#%d [%.20g;%.20g[", i, pi, qi));
			if (pi * pi <= a && a < qi * qi) {
				double[] interval = new double[2];
				interval[0] = pi;
				interval[1] = qi;
				return interval;
			}
			pi = qi;
			qi = qi + delta;
		}
		return null; // cannot happen?
	}

	static double wurzel(double a) {
		double[] interval = new double[2];
		// 1. Finde eine Quadratzahl p*p < a, nimm p als Untergrenze
		double p = 0;
		while (p * p < a) {
			p += 1;
		}
		p = p - 1;
		// Nimm a als Obergrenze
		double q = a;
		interval[0] = p;
		interval[1] = q;
		for (int i = 1; i <= 10; i += 1) {
			interval = schachtelung(a, 1, interval[0], interval[1]);
		}
		return interval[0];
	}

	static double hochwurzel(double b, double a) {
		// berechne b hoch wurzel(a)
		double[] interval = new double[2];

		// 1. Finde eine Quadratzahl p*p < a, nimm p als Untergrenze
		double p = 0;
		while (p * p < a) {
			p += 1;
		}
		p = p - 1;

		// Nimm a als Obergrenze
		double q = a;

		interval[0] = p;
		interval[1] = q;
		for (int i = 1; i <= 10; i += 1) {
			interval = schachtelung(a, b, interval[0], interval[1]);
		}
		return Math.pow(b, interval[0]);
	}
}