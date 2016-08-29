package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vektor;

/**
 * Ebene in Koordinatenform <code>a*x + b*y + c*z = d</code>;
 *
 */
public class EbeneKoordinatenForm implements Ebene {

	private final double a, b, c, d;

	public EbeneKoordinatenForm(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public boolean contains(Vektor v) {
		return a * v.x1() + b * v.x2() + c * v.x3() == d;
	}

	@Override
	public String toString() {
		return String.format("e: (%.2f) * x1 + (%.2f) * x2 + (%.2f) * x3 = %.2f", a, b, c, d);
	}

	@Override
	public EbeneAllgNormalenForm toAllgNormalenForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbeneKoordinatenForm toKoordinatenForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbeneParameterForm toParameterForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbenePunktNormalenForm toPunktNormalenForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}
}