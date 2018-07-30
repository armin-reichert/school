package de.amr.schule.gruppen;

import static java.lang.System.out;

import java.util.Arrays;

public class RestklassenGruppe {

	static char[] G = { '0', '1', '2', '3', '4' };

	static char[][] plus = {
			/*@formatter:off*/
			{ '0', '1', '2', '3', '4' }, 
			{ '1', '2', '3', '4', '0' }, 
			{ '2', '3', '4', '0', '1' },
			{ '3', '4', '0', '1', '2' }, 
			{ '4', '0', '1', '2', '3' }, 
			/*@formatter:on*/
	};

	static char plus(char a, char b) {
		int zeile_a = Arrays.binarySearch(G, a);
		int spalte_b = Arrays.binarySearch(G, b);
		return plus[zeile_a][spalte_b];
	}

	public static void main(String[] args) {
		prüfeAssoziativität();
		prüfeNeutralesElement('0');
		prüfeInversesElement('0');
		prüfeKommutativität();
	}

	static void prüfeAssoziativität() {
		out.println("Prüfe Assoziativität:");
		String fmtEq = "%3d: %c + (%c + %c) = %c = (%c + %c) + %c";
		String fmtNeq = "%3d: %c + (%c + %c) = %c != (%c + %c) + %c = %c";
		int zeile = 1;
		for (char a : G) {
			for (char b : G) {
				for (char c : G) {
					char klammerRechts = plus(a, plus(b, c)); // a + (b + c )
					char klammerLinks = plus(plus(a, b), c); // (a + b) + c
					if (klammerRechts != klammerLinks) {
						out.println(
								String.format(fmtNeq, zeile, a, b, c, klammerRechts, a, b, c, klammerLinks));
						out.println("+ ist nicht assoziativ");
						return;
					} else {
						out.println(String.format(fmtEq, zeile, a, b, c, klammerRechts, a, b, c));
					}
					++zeile;
				}
			}
		}
		System.out.println("+ ist assoziativ");
	}

	static void prüfeNeutralesElement(char e) {
		out.println("Prüfe Existenz des neutralen Elements:");
		for (char a : G) {
			char links = plus(e, a);
			char rechts = plus(a, e);
			if (links != a || rechts != a) {
				out.println(String.format("%c ist kein neutrales Element", e));
				return;
			}
		}
		out.println(String.format("%c ist neutrales Element", e));
	}

	static void prüfeInversesElement(char e) {
		out.println("Prüfe Existenz inverser Elemente:");
		for (char a : G) {
			boolean gefunden = false;
			for (char b : G) {
				if (plus(a, b) == e && plus(b, a) == e) {
					out.println(String.format("%c + %c = %c + %c = %c, also -%c = %c", a, b, b, a, e, a, b));
					gefunden = true;
					break;
				}
			}
			if (!gefunden) {
				out.println(String.format("%c besitzt kein inverses Element", a));
			}
		}
	}

	static void prüfeKommutativität() {
		out.println("Prüfe Kommutativität:");
		int zeile = 1;
		for (char a : G) {
			for (char b : G) {
				char aLinks = plus(a, b);
				char aRechts = plus(b, a);
				if (aLinks != aRechts) {
					out.println(String.format("%2d: (%c + %c) = %c != (%c + %c) = %c", zeile, a, b, aLinks, b,
							a, aRechts));
					out.println("+ ist nicht kommutativ");
					return;
				}
				out.println(String.format("%2d: %c + %c = %c + %c %c", zeile, a, b, aLinks, b, a));
				++zeile;
			}
		}
		System.out.println("+ ist kommutativ");
	}

}
