package de.amr.schule.quadgleichung;

public class Max3 {

	public static void main(String[] args) {
		berechneMax(7, -3, 10);
		berechneMax(-7, -3, -10);
	}

	static void berechneMax(int a, int b, int c) {
		int max;
		if (a >= b) {
			max = a;
		} else {
			max = b;
		}
		// max = max(a,b)
		if (max >= c) {
			// max ist max(a,b,c)
		} else {
			max = c;
		}
		System.out.println(max);
	}
}