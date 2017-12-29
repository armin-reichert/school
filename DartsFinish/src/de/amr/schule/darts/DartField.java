package de.amr.schule.darts;

/**
 * Common interface for single, double and triple fields.
 */
public interface DartField {

	public int getNumber();
	
	public FieldType getType();
	
	public int getScore();
	
}
