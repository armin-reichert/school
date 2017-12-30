package de.amr.schule.darts.checkout;

import de.amr.schule.darts.DartField;

public class CheckOut {

	private final DartField fields[];

	public CheckOut(DartField... fields) {
		this.fields = fields;
	}

	public int getScore() {
		int sum = 0;
		for (DartField field : fields) {
			sum += field.getScore();
		}
		return sum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fields.length; ++i) {
			DartField field = fields[i];
			sb.append(field);
			if (i < fields.length - 1) {
				sb.append("->");
			}
		}
		return sb.toString();
	}
}
