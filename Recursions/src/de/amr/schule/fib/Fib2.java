package de.amr.schule.fib;

import java.math.BigInteger;

public class Fib2 {

	public static void main(String[] args) {
		Fib2 fib = new Fib2();
		int n = args.length > 0 ? Integer.valueOf(args[0]) : 40;
		System.out.println("Fib " + n + "=" + fib.fib(n));
	}

	BigInteger fib(int n) {
		BigInteger[] values = new BigInteger[n + 1];
		values[0] = BigInteger.ZERO;
		values[1] = BigInteger.ONE;
		for (int i = 2; i <= n; ++i) {
			values[i] = values[i - 2].add(values[i - 1]);
		}
		return values[n];
	}
}
