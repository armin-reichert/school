package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vektor;

/**
 * Parameterform der Ebene <code>e: x = a + r * u + s * v</code>.
 */
public class EbeneParameterForm implements Ebene {

	private final Vektor a;
	private final Vektor u;
	private final Vektor v;

	public EbeneParameterForm(Vektor a, Vektor u, Vektor v) {
		this.a = a;
		this.u = u;
		this.v = v;
	}

	public static EbeneParameterForm dreiPunkte(Vektor a, Vektor b, Vektor c) {
		return new EbeneParameterForm(a, Vektor.minus(b, a), Vektor.minus(c, a));
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
		return new EbenePunktNormalenForm(Vektor.cross(u, v), a);
	}
}