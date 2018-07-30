package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vec3;

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
	public boolean contains(Vec3 v) {
		return a * v.x + b * v.y + c * v.z == d;
	}

	@Override
	public String toString() {
		return String.format("e: (%.2f) * x1 + (%.2f) * x2 + (%.2f) * x3 = %.2f", a, b, c, d);
	}

	@Override
	public EbeneANF toANF() {
		return new EbeneANF(new Vec3(a, b, c), d);
	}

	@Override
	public EbeneKoordF toKoordF() {
		return this;
	}

	@Override
	public EbeneParamF toParamF() {
		if (c != 0) {
			return new EbeneParamF(new Vec3(0, 0, d / c), new Vec3(1, 0, -a / c), new Vec3(0, 1, -b / c));
		} else if (b != 0) {
			return new EbeneParamF(new Vec3(0, d / b, 0), new Vec3(1, -a / b, 0), new Vec3(0, -c / b, 1));
		} else if (a != 0) {
			return new EbeneParamF(new Vec3(d / a, 0, 0), new Vec3(-b / a, 1, 0), new Vec3(-c / a, 0, 1));
		}
		throw new IllegalStateException("Ebene in Koordinatenform ist ungültig: " + this);
	}
}