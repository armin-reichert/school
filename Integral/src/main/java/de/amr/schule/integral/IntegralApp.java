package de.amr.schule.integral;

import static java.lang.Math.log;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.function.Function;

import de.amr.schule.integral.alg.IntegralAlgorithms;
import de.amr.schule.integral.alg.IntegralAlgorithms.Algorithm;

public class IntegralApp {

	public static void main(String[] args) {
		new IntegralApp().run();
	}

	void run() {
		IntegralAlgorithms.ALGORITHM = Algorithm.TRAPEZ;
		integrate("1", x -> 1.0, 0.0, 1.0, 1);
		integrate("x", x -> x, 0.0, 1.0, 0.5);
		integrate("x*x", x -> x * x, 0.0, 1.0, 1.0 / 3.0);
		integrate("4*sqrt(1-x*x)", x -> 4 * sqrt(1 - x * x), 0.0, 1.0, Math.PI);
		integrate("sin(x)", x -> sin(x), 0.0, Math.PI, 2);
		integrate("ln(x)", x -> log(x), 1.0, 2.0, 2 * log(2) - 1);
	}

	double integrate(String funText, Function<Double, Double> f, double a, double b, double expected) {
		System.out.println(String.format("integral %s [%.2g, %.2g]", funText, a, b));
		double time = System.nanoTime();
		double result = IntegralAlgorithms.integrate(f, a, b);
		time = (System.nanoTime() - time) / 1_000_000_000L;
		double diff = Math.abs(result - expected);
		String fmtResult = "\tresult:   %16.16g\n\texpected: %16.16g\n\tdiff:     %16.16g\n\tseconds:  %.1g\n";
		System.out.println(String.format(fmtResult, result, expected, diff, time));
		return result;
	}
}