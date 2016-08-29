package de.amr.schule.vektoren.figuren;

import static de.amr.schule.vektoren.Vektor.*;

import de.amr.schule.vektoren.Vektor;

public class Parallelogramm implements Figur {

	private final Vektor a;
	private final Vektor b;

	public Parallelogramm(Vektor a, Vektor b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double umfang() {
		return 2 * (length(a) + length(b));
	}

	@Override
	public double fläche() {
		return length(cross(a, b));
	}
	
	public boolean istRechteck() {
		return dot(a, b) == 0;
	}
	
	public boolean istQuadrat() {
		return istRechteck() && length(a) == length(b);
		
	}
	
	@Override
	public String toString() {
		return String.format("a=%s, b=%s", a.toString(), b.toString());
	}
}
