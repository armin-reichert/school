package de.amr.schule.fib;

public class Fib {

	public static void main(String[] args) {
		Fib fib = new Fib();
		int n = args.length > 0 ? Integer.valueOf(args[0]) : 40;
		int value = fib.fib(n);
		System.out.println("Fib(" + n + ")=" + value);
		System.out.println("Calls=" + fib.calls);
	}

	int calls = 0;

	int fib(int n) {
		++calls;
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else {
			return fib(n - 2) + fib(n - 1);
		}
	}
}
