package de.amr.schule.vektoren.figuren;

import static de.amr.schule.vektoren.Vektor.*;

import de.amr.schule.vektoren.Vektor;

public class Dreieck implements Figur {

	private final Vektor a, b, c;

	public Dreieck(Vektor a, Vektor b, Vektor c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public String toString() {
		return String.format("Dreieck A%s B%s C%s", a.toString(), b.toString(), c.toString());
	}

	@Override
	public double umfang() {
		return length(minus(b, a)) + length(minus(c, b)) + length(minus(c, a));
	}

	@Override
	public double fläche() {
		return length(cross(minus(b, a), minus(c, a))) * 0.5;
	}
}
