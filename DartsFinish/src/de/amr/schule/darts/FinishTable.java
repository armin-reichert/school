package de.amr.schule.darts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinishTable {

	private final Map<Integer, List<Finish>> finishesTable = new HashMap<>();

	private void addFinishFor(int number, Finish finish) {
		if (!finishesTable.containsKey(number)) {
			List<Finish> finishes = new ArrayList<>();
			finishesTable.put(number, finishes);
		}
		finishesTable.get(number).add(finish);
	}

	public FinishTable() {
		computeFinishes();
	}

	private void computeFinishes() {

		final int[] numbers = new int[20];
		for (int number = 1; number <= 20; number += 1) {
			numbers[number - 1] = number;
		}
		
		final int[] numbers25 = new int[21];
		for (int number = 1; number <= 20; number += 1) {
			numbers25[number - 1] = number;
		}
		numbers25[20] = 25;

		// Double
		for (int number : numbers25) {
			Finish finish = new Finish(new Dabbel(number));
			addFinishFor(finish.getScore(), finish);
		}

		// Single-Double
		for (int s : numbers25) {
			for (int d : numbers25) {
				Finish finish = new Finish(new Single(s), new Dabbel(d));
				addFinishFor(finish.getScore(), finish);
			}
		}

		// Double-Double
		for (int d1 : numbers25) {
			for (int d2 : numbers25) {
				Finish finish = new Finish(new Dabbel(d1), new Dabbel(d2));
				addFinishFor(finish.getScore(), finish);
			}
		}

		// Triple-Double
		for (int t : numbers) {
			for (int d : numbers25) {
				Finish finish = new Finish(new Triple(t), new Dabbel(d));
				addFinishFor(finish.getScore(), finish);
			}
		}

		// Single-Single-Double
		for (int s1 : numbers25) {
			for (int s2 : numbers25) {
				for (int d : numbers25) {
					Finish finish = new Finish(new Single(s1), new Single(s2), new Dabbel(d));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Single-Double-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Single(i), new Dabbel(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Single-Triple-Double
		for (int i : numbers25) {
			for (int j : numbers) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Single(i), new Triple(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Double-Single-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Dabbel(i), new Single(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Double-Double-Double
		for (int i : numbers25) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Dabbel(i), new Dabbel(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Double-Triple-Double
		for (int i : numbers25) {
			for (int j : numbers) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Dabbel(i), new Triple(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Triple-Single-Double
		for (int i : numbers) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Triple(i), new Single(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Triple-Double-Double
		for (int i : numbers) {
			for (int j : numbers25) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Triple(i), new Dabbel(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

		// Triple-Triple-Double
		for (int i : numbers) {
			for (int j : numbers) {
				for (int k : numbers25) {
					Finish finish = new Finish(new Triple(i), new Triple(j), new Dabbel(k));
					addFinishFor(finish.getScore(), finish);
				}
			}
		}

	}

	public List<Finish> getFinishes(int score) {
		return finishesTable.containsKey(score) ? finishesTable.get(score) : Collections.emptyList();
	}

	public void printKeys() {
		for (int key : finishesTable.keySet()) {
			System.out.println(key);
		}
	}
}
