package de.amr.schule.wurzel;

public class Calculator {

	static final int ITERATIONS = 1000;

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		for (double x = 169; x <= 169; x += 1) {
			calc.printSquareRoot(x);
		}
	}

	void printSquareRoot(double x) {
		System.out.println("wurzel(" + x + ") = " + sqrt(x));
	}

	double sqrt(double x) {
		double lower, upper, middle, middle_squared;
		lower = 0;
		upper = x;
		middle = (lower + upper) / 2;
		for (int i = 0; i < ITERATIONS; ++i) {
			middle_squared = middle * middle;
			if (middle_squared > x) {
				upper = middle;
				middle = (lower + upper) / 2;
			} else if (middle_squared < x) {
				lower = middle;
				middle = (lower + upper) / 2;
			} else {
				break;
			}
		}
		return middle;
	}

}
