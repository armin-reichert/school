package de.amr.schule.integral.alg;

import java.util.function.Function;

public class IntegralAlgorithms {

	public static Algorithm ALGORITHM = Algorithm.TRAPEZ;
	public static final int N = 1 << 20;

	public enum Algorithm {
		RECTANGLES, TRAPEZ, SIMPSON
	}

	public static double integrate(Function<Double, Double> f, double a, double b) {
		return integrate(ALGORITHM, f, a, b, N);
	}

	public static double integrate(Algorithm algorithmus, Function<Double, Double> f, double a, double b, int n) {
		switch (algorithmus) {
		case RECTANGLES:
			return rectangle(f, a, b, n);
		case TRAPEZ:
			return trapez(f, a, b, n);
		case SIMPSON:
			return simpson_faster(f, a, b, n);
		default:
			throw new IllegalArgumentException("Unbekannter Algorithmus: " + algorithmus);
		}
	}

	static double rectangle(Function<Double, Double> f, double a, double b, int n) {
		double sum = 0.0;
		double h = (b - a) / n;
		double x_i = a;
		for (int i = 0; i <= (n - 1); i += 1) {
			sum += h * f.apply(x_i);
			x_i += h;
		}
		return sum;
	}

	static double trapez(Function<Double, Double> f, double a, double b, int n) {
		double h = (b - a) / n;
		double sum = h * f.apply(a) / 2.0 + h * f.apply(b) / 2.0;
		double x_i = a;
		for (int i = 1; i <= (n - 1); i += 1) {
			sum += h * f.apply(x_i);
			x_i += h;
		}
		return sum;
	}

	static double simpson(Function<Double, Double> f, double a, double b, int n) {
		double sum = 0.0;
		double h = (b - a) / n;
		double left = a, right = left + h;
		for (int i = 0; i <= (n - 1); i += 1) {
			sum += (h / 6) * (f.apply(left) + 4 * f.apply(middle(left, right)) + f.apply(right));
			left = right;
			right = left + h;
		}
		return sum;
	}

	static double simpson_faster(Function<Double, Double> f, double a, double b, int n) {
		double h = (b - a) / n;
		double s = 0.0;
		double x_k;
		x_k = a;
		for (int k = 1; k <= n - 1; k += 1) {
			x_k += h;
			s += f.apply(x_k);
		}
		double t = 0.0;
		x_k = a;
		for (int k = 1; k <= n; k += 1) {
			x_k += h;
			t += f.apply(x_k - h / 2);
		}
		return (h / 6.0) * (f.apply(a) + 2.0 * s + 4.0 * t + f.apply(b));
	}

	static double middle(double left, double right) {
		return (left + right) / 2;
	}
}
