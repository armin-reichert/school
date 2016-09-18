package de.amr.schule.matheolym;

import static java.lang.System.out;

import java.util.stream.IntStream;

public class Zahlenapparillo {

	private int zahl;

	public static void main(String[] args) {
		Zahlenapparillo apparillo = new Zahlenapparillo();
		IntStream.range(2, 100).forEach(zahl -> apparillo.verfahren1(zahl));
		IntStream.range(2, 1000).forEach(zahl -> apparillo.verfahren2(zahl));
	}

	private void verfahren1(int startZahl) {
		StringBuilder ausgabe = new StringBuilder();
		zahl = startZahl;
		out.println("\nstart: " + zahl);
		while (zahl != 1) {
			if (zahl % 2 == 0) {
				/* gerade */
				zahl = zahl / 2;
				ausgabe.append(zahl);
				ausgabe.append(" ");
			} else {
				/* ungerade */
				zahl = zahl + 3;
				ausgabe.append(zahl);
				ausgabe.append(" ");
			}
			if (zahl == 3) {
				ausgabe.append("\nEndlosschleife");
				out.println(ausgabe.toString());
				return;
			}
		}
		ausgabe.append("\nZiel erreicht");
		out.println(ausgabe.toString());
	}

	private void verfahren2(int startZahl) {
		StringBuilder ausgabe = new StringBuilder();
		zahl = startZahl;
		out.println("\nstart: " + zahl);
		while (zahl != 1) {
			if (zahl % 2 == 0) {
				/* gerade */
				zahl = zahl / 2;
				ausgabe.append(zahl);
				ausgabe.append(" ");
			} else {
				/* ungerade */
				if (zahl % 3 == 0) {
					zahl = zahl + 5;
				} else {
					zahl = zahl + 3;
				}
				ausgabe.append(zahl);
				ausgabe.append(" ");
			}
		}
		ausgabe.append("\nZiel erreicht");
		out.println(ausgabe.toString());
	}
}
