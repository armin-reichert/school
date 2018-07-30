package de.amr.schule.primes;

public class Primes {

	public static void main(String[] args) {
		int n = 1000 * 1000;
		long time = System.currentTimeMillis();
		Sieve sieve = new Sieve(n);
		time = System.currentTimeMillis() - time;
		System.out.println("Berechnungszeit: " + time + " Millisekunden");
		sieve.print(15, System.out);
	}
}
