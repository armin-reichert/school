package de.amr.schule.hanoi;

public class Hanoi {

	int calls = 0;

	public static void main(String[] args) {
		Hanoi h = new Hanoi();
		int n = args.length > 0 ? Integer.valueOf(args[0]) : 4;
		h.solve(n, "A", "B", "C");
		System.out.println("\nCalls: " + h.calls);
	}

	void solve(int n, String A, String B, String C) {
		++calls;
		if (n == 1) {
			move(A, B);
		} else {
			solve(n - 1, A, C, B);
			move(A, B);
			solve(n - 1, C, B, A);
		}
	}

	void move(String A, String B) {
		System.out.print(A + "->" + B + ", ");
	}
}
