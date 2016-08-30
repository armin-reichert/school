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
	public EbeneParamF toParamF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbenePNF toPNF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
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