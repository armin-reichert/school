package de.amr.schule.perm;

import java.util.BitSet;

public class Permutation {

	static int counter;

	public static void main(String[] args) {
		int n = 1;
		while (n <= 6) {
			System.out.println("n = " + n + ":");
			BitSet available = new BitSet();
			available.set(1, n + 1); // 1..n
			printPermutations(available, "");
			System.out.println("Rekursive Aufrufe: " + counter);
			System.out.println();
			counter = 0;
			++n;
		}
	}

	static void printPermutations(BitSet available, String permutation) {
		counter += 1;
		if (available.isEmpty()) {
			System.out.println("(" + permutation + ")");
		} else {
			available.stream().forEach(digit -> {
				available.clear(digit);
				printPermutations(available, permutation.concat(digit + ""));
				available.set(digit);
			});
		}
	}
}
