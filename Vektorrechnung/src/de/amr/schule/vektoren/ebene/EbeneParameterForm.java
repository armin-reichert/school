package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

/**
 * Parameterform der Ebene <code>e: x = a + r * u + s * v</code>.
 */
public class EbeneParameterForm implements Ebene {

	private final Vector3 a;
	private final Vector3 u;
	private final Vector3 v;

	public EbeneParameterForm(Vector3 a, Vector3 u, Vector3 v) {
		this.a = a;
		this.u = u;
		this.v = v;
	}

	public static EbeneParameterForm dreiPunkte(Vector3 a, Vector3 b, Vector3 c) {
		return new EbeneParameterForm(a, Vector3.minus(b, a), Vector3.minus(c, a));
	}

	@Override
	public String toString() {
		return String.format("e: x = %s + r * %s + s * %s", a.toString(), u.toString(), v.toString());
	}

	@Override
	public EbeneParameterForm toParameterForm() {
		return this;
	}

	@Override
	public EbenePunktNormalenForm toPunktNormalenForm() {
		return new EbenePunktNormalenForm(Vector3.cross(u, v), a);
	}
}