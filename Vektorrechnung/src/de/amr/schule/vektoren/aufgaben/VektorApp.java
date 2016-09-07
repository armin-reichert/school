package de.amr.schule.vektoren.aufgaben;

import static java.lang.System.out;

public abstract class VektorApp {

	public void $(Object value, String text) {
		out.println(text + " = " + value);
	}

	public void $(Object value) {
		out.println(value);
	}

	protected void aufgabe(String nr) {
		System.out.println("\nAufgabe " + nr);
	}
}