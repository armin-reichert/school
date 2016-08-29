package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vektor;

/**
 * Punkt-Normalenform der Ebene.
 * <p>
 * <code>n * (x - a) = c</code>
 */
public class EbenePunktNormalenForm implements Ebene {

	Vektor n;
	Vektor a;

	public EbenePunktNormalenForm(Vektor n, Vektor a) {
		this.n = n;
		this.a = a;
	}

	@Override
	public boolean contains(Vektor x) {
		return Vektor.dot(n, Vektor.plus(x, Vektor.inv(a))) == 0;
	}

	@Override
	public String toString() {
		return String.format("e: %s * (x - %s) = 0", n.toString(), a.toString());
	}

	@Override
	public EbeneAllgNormalenForm toAllgNormalenForm() {
		return new EbeneAllgNormalenForm(n, Vektor.dot(n, a));
	}

	@Override
	public EbeneKoordinatenForm toKoordinatenForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EbeneParameterForm toParameterForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EbenePunktNormalenForm toPunktNormalenForm() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}