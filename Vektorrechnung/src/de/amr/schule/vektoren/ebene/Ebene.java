package de.amr.schule.vektoren.ebene;

import de.amr.schule.vektoren.Vector3;

public interface Ebene {
	
	public default boolean contains(Vector3 v) {
		return toKoordinatenForm().contains(v);
	}
	
	public EbeneParameterForm toParameterForm();

	public EbenePunktNormalenForm toPunktNormalenForm();

	public default EbeneAllgNormalenForm toAllgNormalenForm() {
		return toPunktNormalenForm().toAllgNormalenForm();
	}
	
	public default EbeneKoordinatenForm toKoordinatenForm() {
		return toAllgNormalenForm().toKoordinatenForm();
	}
}
