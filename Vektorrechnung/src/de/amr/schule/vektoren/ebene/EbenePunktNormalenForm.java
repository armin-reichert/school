package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

/**
 * Punkt-Normalenform der Ebene.
 * <p>
 * <code>n * (x - a) = c</code>
 */
public class EbenePunktNormalenForm implements Ebene {

	Vector3 n;
	Vector3 a;

	public EbenePunktNormalenForm(Vector3 n, Vector3 a) {
		this.n = n;
		this.a = a;
	}

	@Override
	public boolean contains(Vector3 x) {
		return Vector3.dot(n, Vector3.plus(x, Vector3.inv(a))) == 0;
	}

	@Override
	public String toString() {
		return String.format("e: %s * (x - %s) = 0", n.toString(), a.toString());
	}

	@Override
	public EbeneAllgNormalenForm toAllgNormalenForm() {
		return new EbeneAllgNormalenForm(n, Vector3.dot(n, a));
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