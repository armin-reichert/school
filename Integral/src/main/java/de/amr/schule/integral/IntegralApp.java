package de.amr.schule.integral;

import java.util.function.Function;

public class IntegralApp {

	String fmt = "%-10s: %18.18g, expected=%18.18g, diff=%18.18g, seconds=%.2g";
	double time;

	Function<Double, Double> circle = x -> 4 * Math.sqrt(1 - x * x);
	Function<Double, Double> const1 = x -> 1.0;
	Function<Double, Double> linear = x -> x;
	Function<Double, Double> quadrat = x -> x * x;
	Function<Double, Double> sinus = x -> Math.sin(x);
	Function<Double, Double> exp = x -> Math.exp(x);
	Function<Double, Double> wand = x -> (0 <= x && x <= 3) ? x * x : Math.sqrt(x);
	Function<Double, Double> ln = x -> Math.log(x);

	public static void main(String[] args) {
		new IntegralApp().execute();
	}

	void execute() {
		Function<Double, Double> f = ln;
		double a = 1.0;
		double b = 2.0;
		double expected = 2 * ln.apply(2.0) - 1;
		double area;

		int n = pow2(20);
		System.out.println("n: " + String.format("%,d", n));

		startClock();
		area = IntegralAlgorithmen.untere_rechtecksumme(f, a, b, n);
		stopClock();
		print("Untersumme", expected, area, time);

		startClock();
		area = IntegralAlgorithmen.simpson(f, a, b, n);
		stopClock();
		print("Simpson", expected, area, time);

		startClock();
		area = IntegralAlgorithmen.simpson2(f, a, b, n);
		stopClock();
		print("Simpson2", expected, area, time);
	}

	void startClock() {
		time = System.nanoTime();
	}

	void stopClock() {
		time = (System.nanoTime() - time) / 1_000_000_000L;
	}

	void print(String name, double expected, double value, double time) {
		System.out.println(String.format(fmt, name, value, expected, Math.abs(value - expected), time));
	}

	int pow2(int n) {
		return 1 << n;
	}
}