package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vector3.diff;

import de.amr.schule.vektoren.Vector3;

/**
 * Parameterform der Ebene <code>e: x = a + r * u + s * v</code>.
 */
public class EbeneParamF implements Ebene {

	public final Vector3 a;
	public final Vector3 u;
	public final Vector3 v;

	/**
	 * Erzeugt eine Ebene in Parameterform mit Aufpunkt a und Richtungsvektoren u und v.
	 * 
	 * @param a
	 *          Aufpunkt der Ebene
	 * @param u
	 *          Richtungsvektor
	 * @param v
	 *          Richtungsvektor
	 */
	public EbeneParamF(Vector3 a, Vector3 u, Vector3 v) {
		this.a = a;
		this.u = u;
		this.v = v;
	}

	/**
	 * Erzeugt eine Ebene in Parameterform mit Aufpunkt a und Richtungsvektoren (b-a) und (c-a).
	 * 
	 * @param a
	 *          Punkt der Ebene
	 * @param b
	 *          Punkt der Ebene
	 * @param c
	 *          Punkt der Ebene
	 */
	public static EbeneParamF dreiPunkte(Vector3 a, Vector3 b, Vector3 c) {
		return new EbeneParamF(a, diff(b, a), diff(c, a));
	}

	@Override
	public String toString() {
		return String.format("e: x = %s + r * %s + s * %s", a.toString(), u.toString(), v.toString());
	}

	@Override
	public EbeneParamF toParamF() {
		return this;
	}

	@Override
	public EbenePNF toPNF() {
		return new EbenePNF(Vector3.cross(u, v), a);
	}
}