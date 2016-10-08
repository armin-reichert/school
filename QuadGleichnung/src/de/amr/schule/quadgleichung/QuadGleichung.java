package de.amr.schule.quadgleichung;

import static java.lang.System.in;
import static java.lang.System.out;

import java.util.Scanner;

public class QuadGleichung {

	public static void main(String[] args) {
		try (Scanner input = new Scanner(in)) {
			while (true) {
				out.println("Löse x^2 + p*x + q = 0");
				out.println("Gib p und q ein:");
				double p = input.nextDouble();
				double q = input.nextDouble();
				solve(p, q);
				out.println();
			}
		}
	}

	public static void solve(double p, double q) {
		out.print("x^2 + " + p + "x + " + q);
		double d = (-p / 2) * (-p / 2) - q;
		if (d < 0) {
			out.println(" besitzt keine Lösung");
		} else if (d == 0) {
			double x = -p / 2;
			out.println(" besitzt die einzige Lösung: " + x);
		} else {
			double x1 = -p / 2 + Math.sqrt(d);
			double x2 = -p / 2 - Math.sqrt(d);
			out.println(" besitzt die Lösungen " + x1 + " und " + x2);
		}
	}
}