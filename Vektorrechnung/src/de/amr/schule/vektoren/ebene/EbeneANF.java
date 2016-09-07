package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vec3.dot;

import de.amr.schule.vektoren.Vec3;

/**
 * Allgemeine Normalenform der Ebene <code>e: n * x = c</code>
 */
public class EbeneANF implements Ebene {

	public final Vec3 n;
	public final double c;

	/**
	 * Erzeugt eine Ebene der Form <code>e: n * x = c</code>.
	 * 
	 * @param n
	 *          Normalenvektor
	 * @param c
	 *          Konstante
	 */
	public EbeneANF(Vec3 n, double c) {
		this.n = n;
		this.c = c;
	}

	@Override
	public boolean contains(Vec3 x) {
		return dot(n, x) == c;
	}

	@Override
	public String toString() {
		return String.format("e: %s * x = %.2f", n, c >= 0 ? c : -c);
	}

	@Override
	public EbenePNF toPNF() {
		if (n.x != 0) {
			return new EbenePNF(n, new Vec3(c / n.x, 0, 0));
		} else if (n.y != 0) {
			return new EbenePNF(n, new Vec3(0, c / n.y, 0));
		} else if (n.z != 0) {
			return new EbenePNF(n, new Vec3(0, 0, c / n.z));
		}
		throw new IllegalStateException("Normalenvektor darf nicht der Nullvektor sein: " + this);
	}

	@Override
	public EbeneANF toANF() {
		return this;
	}

	@Override
	public EbeneKoordF toKoordF() {
		return new EbeneKoordF(n.x, n.y, n.z, c);
	}
}