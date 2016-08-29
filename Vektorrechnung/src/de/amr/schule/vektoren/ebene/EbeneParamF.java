package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

/**
 * Parameterform der Ebene <code>e: x = a + r * u + s * v</code>.
 */
public class EbeneParamF implements Ebene {

	private final Vector3 a;
	private final Vector3 u;
	private final Vector3 v;

	public EbeneParamF(Vector3 a, Vector3 u, Vector3 v) {
		this.a = a;
		this.u = u;
		this.v = v;
	}

	public static EbeneParamF dreiPunkte(Vector3 a, Vector3 b, Vector3 c) {
		return new EbeneParamF(a, Vector3.minus(b, a), Vector3.minus(c, a));
	}

	@Override
	public String toString() {
		return String.format("e: x = %s + r * %s + s * %s", a.toString(), u.toString(), v.toString());
	}

	@Override
	public EbeneParamF toParameterForm() {
		return this;
	}

	@Override
	public EbenePNF toPunktNormalenForm() {
		return new EbenePNF(Vector3.cross(u, v), a);
	}
}