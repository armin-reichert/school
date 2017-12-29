package de.amr.schule.darts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinishesTable {

	private final Map<Integer, List<Finish>> finishesForScore = new HashMap<>();

	private void add(Finish finish) {
		int score = finish.getScore();
		if (!finishesForScore.containsKey(score)) {
			finishesForScore.put(score, new ArrayList<>());
		}
		finishesForScore.get(score).add(finish);
	}

	public FinishesTable() {

		final int[] numbers = new int[20];
		final int[] numbers25 = new int[21];
		for (int number = 1; number <= 20; number += 1) {
			numbers[number - 1] = numbers25[number - 1] = number;
		}
		numbers25[20] = 25;

		// Double
		for (int i : numbers25) {
			add(new Finish(new Dabbel(i)));
		}

		// Single-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				add(new Finish(new Single(i), new Dabbel(j)));
			}
		}

		// Double-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				add(new Finish(new Dabbel(i), new Dabbel(j)));
			}
		}

		// Triple-Double
		for (int i : numbers) {
			for (int j : numbers25) {
				add(new Finish(new Triple(i), new Dabbel(j)));
			}
		}

		// Single-Single-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					add(new Finish(new Single(i), new Single(j), new Dabbel(k)));
				}
			}
		}

		// Single-Double-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					add(new Finish(new Single(i), new Dabbel(j), new Dabbel(k)));
				}
			}
		}

		// Single-Triple-Double
		for (int i : numbers25) {
			for (int j : numbers) {
				for (int k : numbers25) {
					add(new Finish(new Single(i), new Triple(j), new Dabbel(k)));
				}
			}
		}

		// Double-Single-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					add(new Finish(new Dabbel(i), new Single(j), new Dabbel(k)));
				}
			}
		}

		// Double-Double-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Dabbel(i), new Dabbel(j), new Dabbel(k));
					add(finish);
				}
			}
		}

		// Double-Triple-Double
		for (int i : numbers25) {
			for (int j : numbers) {
				for (int k : numbers25) {
					add(new Finish(new Dabbel(i), new Triple(j), new Dabbel(k)));
				}
			}
		}

		// Triple-Single-Double
		for (int i : numbers) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					add(new Finish(new Triple(i), new Single(j), new Dabbel(k)));
				}
			}
		}

		// Triple-Double-Double
		for (int i : numbers) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					add(new Finish(new Triple(i), new Dabbel(j), new Dabbel(k)));
				}
			}
		}

		// Triple-Triple-Double
		for (int i : numbers) {
			for (int j : numbers) {
				for (int k : numbers25) {
					add(new Finish(new Triple(i), new Triple(j), new Dabbel(k)));
				}
			}
		}
	}

	public List<Finish> getFinishes(int score) {
		return finishesForScore.containsKey(score)
				? Collections.unmodifiableList(finishesForScore.get(score))
				: Collections.emptyList();
	}
}
