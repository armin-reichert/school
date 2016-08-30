package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vector3.dot;
import static de.amr.schule.vektoren.Vector3.inv;
import static de.amr.schule.vektoren.Vector3.plus;

import de.amr.schule.vektoren.Vector3;

/**
 * Punkt-Normalenform der Ebene mit Normalenvektor {@code n} und Aufpunkt {@code a}.
 * 
 * <p>
 * {@code n * (x - a) = c}.
 */
public class EbenePNF implements Ebene {

	public final Vector3 n;
	public final Vector3 a;

	/**
	 * Erzeugt eine Ebene in Punkt-Normalenform {@code n * (x - a) = c}.
	 * 
	 * @param n
	 *          Normalenvektor
	 * @param a
	 *          Aufpunkt
	 */
	public EbenePNF(Vector3 n, Vector3 a) {
		this.n = n;
		this.a = a;
	}

	@Override
	public boolean contains(Vector3 x) {
		return dot(n, plus(x, inv(a))) == 0;
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