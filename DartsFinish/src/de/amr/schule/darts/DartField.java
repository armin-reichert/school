package de.amr.schule.darts;

/**
 * Common interface for single, double and triple fields.
 */
public class DartField {

	public static DartField Single(int number) {
		return new DartField(number, FieldType.SINGLE);
	}

	public static DartField Double(int number) {
		return new DartField(number, FieldType.DOUBLE);
	}

	public static DartField Triple(int number) {
		return new DartField(number, FieldType.TRIPLE);
	}

	private final int number;
	private final FieldType type;

	private DartField(int number, FieldType type) {
		this.number = number;
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public FieldType getType() {
		return type;
	}

	public int getScore() {
		switch (type) {
		case SINGLE:
			return number;
		case DOUBLE:
			return 2 * number;
		case TRIPLE:
			return 3 * number;
		}
		throw new IllegalStateException();
	}

	@Override
	public String toString() {
		switch (type) {
		case SINGLE:
			return "" + number;
		case DOUBLE:
			return "D" + number;
		case TRIPLE:
			return "T" + number;
		}
		return super.toString();
	}

}
