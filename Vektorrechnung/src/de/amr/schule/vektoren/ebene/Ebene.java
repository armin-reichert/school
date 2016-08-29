package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

public interface Ebene {
	
	public default boolean contains(Vector3 v) {
		return toKoordinatenForm().contains(v);
	}
	
	public EbeneParamF toParameterForm();

	public EbenePNF toPunktNormalenForm();

	public default EbeneANF toAllgNormalenForm() {
		return toPunktNormalenForm().toAllgNormalenForm();
	}
	
	public default EbeneKoordF toKoordinatenForm() {
		return toAllgNormalenForm().toKoordinatenForm();
	}
}
