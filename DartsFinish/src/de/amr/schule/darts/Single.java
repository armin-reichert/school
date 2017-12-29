package de.amr.schule.darts;

public class Single implements DartField {

	private final int number;

	public Single(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public FieldType getType() {
		return FieldType.SINGLE;
	}

	@Override
	public int getScore() {
		return number;
	}

	@Override
	public String toString() {
		return String.format("%d", number);
	}
}