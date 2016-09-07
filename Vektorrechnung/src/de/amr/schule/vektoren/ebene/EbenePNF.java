package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vec3.dot;
import static de.amr.schule.vektoren.Vec3.inv;
import static de.amr.schule.vektoren.Vec3.sum;

import de.amr.schule.vektoren.Vec3;

/**
 * Punkt-Normalenform der Ebene mit Normalenvektor {@code n} und Aufpunkt {@code a}.
 * 
 * <p>
 * {@code n * (x - a) = c}.
 */
public class EbenePNF implements Ebene {

	public final Vec3 n;
	public final Vec3 a;

	/**
	 * Erzeugt eine Ebene in Punkt-Normalenform {@code n * (x - a) = c}.
	 * 
	 * @param n
	 *          Normalenvektor
	 * @param a
	 *          Aufpunkt
	 */
	public EbenePNF(Vec3 n, Vec3 a) {
		this.n = n;
		this.a = a;
	}

	@Override
	public boolean contains(Vec3 x) {
		return dot(n, sum(x, inv(a))) == 0;
	}

	@Override
	public String toString() {
		return String.format("e: %s * (x - %s) = 0", n.toString(), a.toString());
	}

	@Override
	public EbeneANF toANF() {
		return new EbeneANF(n, dot(n, a));
	}

	@Override
	public EbeneKoordF toKoordF() {
		return new EbeneKoordF(n.x, n.y, n.z, dot(n, a));
	}

	@Override
	public EbeneParamF toParamF() {
		return toKoordF().toParamF();
	}

	@Override
	public EbenePNF toPNF() {
		return this;
	}
}