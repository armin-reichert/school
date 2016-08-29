package de.amr.schule.quadgleichung;

import java.util.Scanner;

public class QuadGleichung {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Eingabe p,q:");
			double p = scan.nextDouble();
			double q = scan.nextDouble();
			solve(p, q);
		}
	}

	public static void solve(double p, double q) {
		System.out.println("x^2 + " + p + "x + " + q);
		double d = (-p / 2) * (-p / 2) - q;
		if (d < 0) {
			System.out.println("Keine Lösung");
		} else if (d == 0) {
			double x = -p / 2;
			System.out.println("Exakt eine Lösung: " + x);
		} else {
			double x1 = -p / 2 + Math.sqrt(d);
			double x2 = -p / 2 - Math.sqrt(d);
			System.out.println("Erste Lösung: " + x1);
			System.out.println("Zweite Lösung: " + x2);
		}
	}

}
