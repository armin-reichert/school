package de.amr.schule.primes;

import java.io.PrintStream;
import java.util.BitSet;

public class Sieve {
  
  private final BitSet bits;
  private final int n;
  
  public Sieve(int n) {
    this.n = n;
    bits = new BitSet(n);
    int p = 2;
    while (p*p <= n) {
      int v = p+p;
      while (v < n) {
        bits.set(v);
        v += p;
      }
      ++p;
    }
  }
  
  public void print(int cols, PrintStream out) {
    int colCount = 0, primeCount = 0;
    for (int i = bits.nextClearBit(2); i >= 0 && i < n; i = bits.nextClearBit(i+1)) {
      ++primeCount;
      out.print(i);
      if (++colCount == cols) {
        out.println();
        colCount = 0;
      } else {
        out.print(", ");
      }
    }
    out.println();
    out.println(primeCount + " Primzahlen gefunden.");
  }

}
