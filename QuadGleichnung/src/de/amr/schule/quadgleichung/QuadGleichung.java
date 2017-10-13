package de.amr.schule.quadgleichung;

import static java.lang.Math.sqrt;
import static java.lang.String.format;
import static java.lang.System.out;

public class QuadGleichung {

	public static void main(String... args) {
		solve(0,0,0);
		solve(0,0,1);
		solve(1, 0, 0);
		solve(1, 1, -2);
		solve(1, 3, 2);
		solve(0, 2, -4);
	}

	static void solve(double a, double b, double c) {
		print("\n(%g) * x^2 + (%g) * x + (%g) = 0", a, b, c);
		if (a == 0) {
			if (b != 0) {
				double x = -c / b;
				print("x = %g", x);
			} else {
				print(c == 0 ? "Allgemeingültig" : "Keine Lösung");
			}
		} else {
			double d = (b * b) / (4 * a * a) - c / a;
			if (d < 0) {
				print("Keine Lösung");
			} else if (d == 0) {
				double x = -b / (2 * a);
				print("x = %g", x);
			} else { // D > 0
				double x1 = -b / (2 * a) + sqrt(d);
				double x2 = -b / (2 * a) - sqrt(d);
				print("x1 = %g", x1);
				print("x2 = %g", x2);
			}
		}
	}

	static void print(String text, Object... args) {
		out.println(format(text, args));
	}
}
