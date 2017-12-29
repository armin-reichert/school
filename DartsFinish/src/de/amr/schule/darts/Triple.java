package de.amr.schule.darts;

public class Triple implements DartField {

	private final int number;

	public Triple(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public FieldType getType() {
		return FieldType.TRIPLE;
	}

	@Override
	public int getScore() {
		return 3 * this.number;
	}

	@Override
	public String toString() {
		return String.format("T%d", number);
	}

}
