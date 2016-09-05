package de.amr.schule.vektoren.aufgaben;

public abstract class VektorApp {

	public void $(Object o, String... s) {
		if (s.length > 0) {
			System.out.println(s[0] + " = " + o);
		} else {
			System.out.println(o);
		}
	}
	
	protected void aufgabe(String nr) {
		System.out.println("\nAufgabe " + nr);
	}

}