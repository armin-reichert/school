package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

/**
 * Ebene in Koordinatenform <code>a*x + b*y + c*z = d</code>;
 *
 */
public class EbeneKoordF implements Ebene {

	public final double a, b, c, d;

	public EbeneKoordF(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public boolean contains(Vector3 v) {
		return a * v.x + b * v.y + c * v.z == d;
	}

	@Override
	public String toString() {
		return String.format("e: (%.2f) * x1 + (%.2f) * x2 + (%.2f) * x3 = %.2f", a, b, c, d);
	}

	@Override
	public EbeneANF toANF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbeneKoordF toKoordF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbeneParamF toParamF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}

	@Override
	public EbenePNF toPNF() {
		System.out.println("NOCH NICHT IMPLEMENTIERT!");
		return null;
	}
}