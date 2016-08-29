package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vektor;

/**
 * Allgemeine Normalenform der Ebene <code>e: n * x = c</code>
 */
public class EbeneAllgNormalenForm implements Ebene {

	private final Vektor n;
	private final double c;

	public EbeneAllgNormalenForm(Vektor n, double c) {
		this.n = n;
		this.c = c;
	}

	@Override
	public boolean contains(Vektor x) {
		return Vektor.dot(n, x) == c;
	}

	@Override
	public String toString() {
		if (c < 0) {
			return String.format("e: %s * x + %.2f = 0", n, -c);
		} else {
			return String.format("e: %s * x - %.2f = 0", n, c);
		}
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

	@Override
	public EbeneAllgNormalenForm toAllgNormalenForm() {
		return this;
	}

	@Override
	public EbeneKoordinatenForm toKoordinatenForm() {
		return new EbeneKoordinatenForm(n.x1(), n.x2(), n.x3(), c);
	}

}