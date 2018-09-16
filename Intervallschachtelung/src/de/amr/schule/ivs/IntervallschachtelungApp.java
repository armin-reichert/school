package de.amr.schule.ivs;

public class IntervallschachtelungApp {

	public static void main(String[] args) {
		double x = Math.pow(4, wurzel(2));
		System.out.println("4 hoch wurzel(2)=" + x);

		double a = 0;
		long time = System.currentTimeMillis();
		while (a < 300_000) {
			double wurzel = wurzel(a);
			System.out.println(String.format("Wurzel(%g) ist ungefähr %.10g", a, wurzel));
			a += 10;
		}
		long used = System.currentTimeMillis() - time;
		System.out.println("Time[sec] needed: " + used / 1000);
	}

	static double[] schachtelung(double a, double b, double p, double q) {
		double delta = (q - p) / 10;
		double pi, qi;
		pi = p;
		qi = p + delta;
		for (int i = 0; i < 10; i += 1) {
			// System.out.println(String.format("Interval#%d [%.20g;%.20g[", i, pi, qi));
			if (pi * pi <= a && a < qi * qi) {
				return new double[] { pi, qi };
			}
			pi = qi;
			qi = qi + delta;
		}
		throw new IllegalStateException("Kein Teilintervall enthält gesuchte Zahl"); // cannot happen?
	}

	static double wurzel(double a) {
		if (a < 0) {
			throw new IllegalArgumentException("Radikant muss größer als 0 sein");
		}
		if (a == 0) {
			return 0;
		}
		// 1. Finde größte Quadratzahl p*p < a, nimm p als Untergrenze
		double p = 0;
		while (p * p < a) {
			p += 1;
		}
		p = p - 1;
		// Nimm a als Obergrenze
		double q = a;
		double[] interval = new double[] { p, q };
		for (int i = 1; i <= 10; i += 1) {
			interval = schachtelung(a, 1, interval[0], interval[1]);
		}
		return interval[0];
	}
}