package de.amr.schule.vektoren.figuren;

import static de.amr.schule.vektoren.Vec3.cross;
import static de.amr.schule.vektoren.Vec3.length;
import static de.amr.schule.vektoren.Vec3.orthogonal;

import de.amr.schule.vektoren.Vec3;

public class Parallelogramm implements Figur {

	private final Vec3 a;
	private final Vec3 b;

	public Parallelogramm(Vec3 a, Vec3 b) {
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
		return orthogonal(a, b);
	}

	public boolean istQuadrat() {
		return istRechteck() && length(a) == length(b);

	}

	@Override
	public String toString() {
		return String.format("a=%s, b=%s", a.toString(), b.toString());
	}
}
