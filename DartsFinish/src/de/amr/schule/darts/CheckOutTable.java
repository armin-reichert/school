package de.amr.schule.darts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutTable {

	private final Map<Integer, List<CheckOut>> checkOutsByScore = new HashMap<>();

	private void add(CheckOut co) {
		int score = co.getScore();
		if (!checkOutsByScore.containsKey(score)) {
			checkOutsByScore.put(score, new ArrayList<>());
		}
		checkOutsByScore.get(score).add(co);
	}

	public CheckOutTable() {

		final int[] fields = new int[20];
		final int[] fields25 = new int[21];
		for (int i = 1; i <= 20; i += 1) {
			fields[i - 1] = fields25[i - 1] = i;
		}
		fields25[20] = 25;

		// Double-Out
		for (int i : fields25) {
			add(new CheckOut(DartField.Double(i)));
		}

		// Single-Double
		for (int i : fields25) {
			for (int j : fields25) {
				add(new CheckOut(DartField.Single(i), DartField.Double(j)));
			}
		}

		// Double-Double
		for (int i : fields25) {
			for (int j : fields25) {
				add(new CheckOut(DartField.Double(i), DartField.Double(j)));
			}
		}

		// Triple-Double
		for (int i : fields) {
			for (int j : fields25) {
				add(new CheckOut(DartField.Triple(i), DartField.Double(j)));
			}
		}

		// Single-Single-Double
		for (int i : fields25) {
			for (int j : fields25) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Single(i), DartField.Single(j), DartField.Double(k)));
				}
			}
		}

		// Single-Double-Double
		for (int i : fields25) {
			for (int j : fields25) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Single(i), DartField.Double(j), DartField.Double(k)));
				}
			}
		}

		// Single-Triple-Double
		for (int i : fields25) {
			for (int j : fields) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Single(i), DartField.Triple(j), DartField.Double(k)));
				}
			}
		}

		// Double-Single-Double
		for (int i : fields25) {
			for (int j : fields25) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Double(i), DartField.Single(j), DartField.Double(k)));
				}
			}
		}

		// Double-Double-Double
		for (int i : fields25) {
			for (int j : fields25) {
				for (int k : fields25) {
					CheckOut finish = new CheckOut(DartField.Double(i), DartField.Double(j),
							DartField.Double(k));
					add(finish);
				}
			}
		}

		// Double-Triple-Double
		for (int i : fields25) {
			for (int j : fields) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Double(i), DartField.Triple(j), DartField.Double(k)));
				}
			}
		}

		// Triple-Single-Double
		for (int i : fields) {
			for (int j : fields25) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Triple(i), DartField.Single(j), DartField.Double(k)));
				}
			}
		}

		// Triple-Double-Double
		for (int i : fields) {
			for (int j : fields25) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Triple(i), DartField.Double(j), DartField.Double(k)));
				}
			}
		}

		// Triple-Triple-Double
		for (int i : fields) {
			for (int j : fields) {
				for (int k : fields25) {
					add(new CheckOut(DartField.Triple(i), DartField.Triple(j), DartField.Double(k)));
				}
			}
		}
	}

	public List<CheckOut> getCheckOuts(int score) {
		return checkOutsByScore.containsKey(score)
				? Collections.unmodifiableList(checkOutsByScore.get(score))
				: Collections.emptyList();
	}
}
