package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vec3.collinear;
import static de.amr.schule.vektoren.Vec3.diff;

import de.amr.schule.vektoren.Vec3;

/**
 * Parameterform der Ebene:
 * <p>
 * {@code e: x = a + r * u + s * v}
 */
public class EbeneParamF implements Ebene {

	public final Vec3 a;
	public final Vec3 u;
	public final Vec3 v;

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
	public EbeneParamF(Vec3 a, Vec3 u, Vec3 v) {
		if (collinear(u, v)) {
			throw new IllegalArgumentException("Die Punkte liegen auf einer Geraden");
		}
		this.a = a;
		this.u = u;
		this.v = v;
	}

	/**
	 * Erzeugt eine Ebene in Parameterform durch die Punkte a, b und c.
	 * 
	 * @param a
	 *          Punkt der Ebene
	 * @param b
	 *          Punkt der Ebene
	 * @param c
	 *          Punkt der Ebene
	 */
	public static EbeneParamF dreiPunkte(Vec3 a, Vec3 b, Vec3 c) {
		Vec3 u = diff(b, a);
		Vec3 v = diff(c, a);
		if (collinear(u, v)) {
			throw new IllegalArgumentException("Die Punkte liegen auf einer Geraden");
		}
		return new EbeneParamF(a, u, v);
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
		return new EbenePNF(Vec3.cross(u, v), a);
	}
}