package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

/**
 * Allgemeine Normalenform der Ebene <code>e: n * x = c</code>
 */
public class EbeneANF implements Ebene {

	private final Vector3 n;
	private final double c;

	public EbeneANF(Vector3 n, double c) {
		this.n = n;
		this.c = c;
	}

	@Override
	public boolean contains(Vector3 x) {
		return Vector3.dot(n, x) == c;
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
	public EbeneParamF toParameterForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbenePNF toPunktNormalenForm() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbeneANF toAllgNormalenForm() {
		return this;
	}

	@Override
	public EbeneKoordF toKoordinatenForm() {
		return new EbeneKoordF(n.x1(), n.x2(), n.x3(), c);
	}

}