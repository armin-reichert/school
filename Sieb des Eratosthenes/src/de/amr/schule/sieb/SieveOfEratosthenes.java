package de.amr.schule.sieb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.BitSet;

/**
 * Berechnet die ersten N Primzahlen mithilfe des Sieb des Eratosthenes.
 * 
 * @author Armin Reichert
 */
public class SieveOfEratosthenes {

	public static void main(String[] args) {
		SieveOfEratosthenes sieve = new SieveOfEratosthenes(100000000);
		sieve.run();
		sieve.countPrimes();
		// sieve.save(new File("primes.txt"));
	}

	private final int maxNumber;
	private final int maxFactor;
	private final BitSet excluded;
	private int current;

	public SieveOfEratosthenes(int maxNumber) {
		this.maxNumber = maxNumber;
		this.maxFactor = (int) Math.sqrt(maxNumber);
		this.excluded = new BitSet(maxNumber);
		this.current = 2;
	}

	private void exclude(int number) {
		excluded.set(number - 1);
	}

	private boolean inSieve(int number) {
		return !excluded.get(number - 1);
	}

	private void run() {
		int pten = 10;
		while (current <= maxFactor) {
			removeMultiples();
			nextPrime();
			if (current >= pten) {
				System.out.println("Reached " + current);
				pten = Math.multiplyExact(pten, 10);
			}
		}
	}

	private void removeMultiples() {
		int multiple = current + current;
		while (multiple <= maxNumber) {
			exclude(multiple);
			multiple = Math.addExact(multiple, current);
		}
	}

	private void nextPrime() {
		do {
			++current;
		} while (current <= maxFactor && !inSieve(current));
	}

	private int countPrimes() {
		int primesCnt = 0;
		int pten = 10;
		for (int number = 2; number <= maxNumber; ++number) {
			if (inSieve(number)) {
				++primesCnt;
			}
			if (number == pten) {
				System.out.println(primesCnt + " primes between 1 and " + pten);
				try {
					pten = Math.multiplyExact(pten, 10);
				} catch (ArithmeticException e) {
					break;
				}
			}
		}
		return primesCnt;
	}

	private void save(File file) {
		try {
			PrintStream ps = new PrintStream(file);
			int primesCnt = 0;
			System.out.println("Saving primes to file " + file.getAbsolutePath());
			for (int i = 2; i <= maxNumber; ++i) {
				if (inSieve(i)) {
					++primesCnt;
					ps.print(i);
					ps.print(',');
					if (primesCnt % 10 == 0) {
						ps.println();
					}
				}
			}
			ps.close();
			System.out.println("Done.");
		} catch (FileNotFoundException e) {
			System.err.println("Primes could not be saved into file");
			e.printStackTrace(System.err);
		}
	}
}
