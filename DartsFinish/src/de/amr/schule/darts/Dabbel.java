package de.amr.schule.darts;

public class Dabbel implements DartField {

	private final int number;

	public Dabbel(int number) {
		this.number = number;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public FieldType getType() {
		return FieldType.DOUBLE;
	}

	@Override
	public int getScore() {
		return 2 * this.number;
	}

	@Override
	public String toString() {
		return number == 25 ? "BULLS" : String.format("D%d", number);
	}
}
