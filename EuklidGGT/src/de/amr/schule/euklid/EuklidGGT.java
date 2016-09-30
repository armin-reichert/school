package de.amr.schule.euklid;

import static java.lang.String.format;
import static java.lang.System.out;

import java.util.Random;

public class EuklidGGT {

	static void println(String format, Object... args) {
		out.println(format(format, args));
	}

	static int ggt(int a, int b) {
		if (a < b) {
			int c = a;
			a = b;
			b = c;
		}
		println("\nggT(%d,%d):", a, b);
		while (b != 0) {
			int r = a % b;
			println("%5d%5d%5d", a, b, r);
			a = b;
			b = r;
		}
		return a;
	}

	public static void main(String[] args) {
		Random rand = new Random();
		for (int i = 0; i < 100; ++i) {
			int a = rand.nextInt(999) + 1, b = rand.nextInt(999) + 1;
			println("ggT(%d,%d) = %d", a, b, ggt(a, b));
		}
	}
}