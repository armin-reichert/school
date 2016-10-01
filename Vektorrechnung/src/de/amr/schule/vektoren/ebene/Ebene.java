package de.amr.schule.vektoren.ebene;

import static de.amr.schule.vektoren.Vec3.dot;
import static java.lang.Math.abs;

import de.amr.schule.vektoren.Vec3;

public interface Ebene {

	public default boolean contains(Vec3 point) {
		return toKoordF().contains(point);
	}

	public default EbeneParamF toParamF() {
		return toKoordF().toParamF();
	}

	public default EbenePNF toPNF() {
		return toANF().toPNF();
	}

	public default EbeneANF toANF() {
		return toPNF().toANF();
	}

	public default EbeneKoordF toKoordF() {
		return toANF().toKoordF();
	}

	public default double dist(Vec3 p) {
		EbeneANF hesse = toANF().toHesseNF();
		return abs(dot(hesse.n, p.sub(anyPoint())));
	}

	public default Vec3 anyPoint() {
		return toParamF().a;
	}
}
