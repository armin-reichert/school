package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vector3.dot;

import de.amr.schule.vektoren.Vector3;

/**
 * Allgemeine Normalenform der Ebene <code>e: n * x = c</code>
 */
public class EbeneANF implements Ebene {

	public final Vector3 n;
	public final double c;

	/**
	 * Erzeugt eine Ebene der Form <code>e: n * x = c</code>.
	 * 
	 * @param n
	 *          Normalenvektor
	 * @param c
	 *          Konstante
	 */
	public EbeneANF(Vector3 n, double c) {
		this.n = n;
		this.c = c;
	}

	@Override
	public boolean contains(Vector3 x) {
		return dot(n, x) == c;
	}

	@Override
	public String toString() {
		return String.format("e: %s * x + %.2f = 0", n, c >= 0 ? c : -c);
	}

	@Override
	public EbenePNF toPNF() {
		if (n.x != 0) {
			return new EbenePNF(n, new Vector3(c / n.x, 0, 0));
		} else if (n.y != 0) {
			return new EbenePNF(n, new Vector3(0, c / n.y, 0));
		} else if (n.z != 0) {
			return new EbenePNF(n, new Vector3(0, 0, c / n.z));
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