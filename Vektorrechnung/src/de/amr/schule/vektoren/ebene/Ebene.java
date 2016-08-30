package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

public interface Ebene {
	
	public default boolean contains(Vector3 v) {
		return toKoordF().contains(v);
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
}
