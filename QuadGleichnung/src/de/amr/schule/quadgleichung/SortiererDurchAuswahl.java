package de.amr.schule.quadgleichung;

import java.util.Comparator;

public class SortiererDurchAuswahl<T> {

	private final Comparator<T> comparator;

	public SortiererDurchAuswahl(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public void sort(T... values) {
		for (int start = 0; start <= values.length - 1; start += 1) {
			swap(values, start, min_index(values, start));
		}
	}

	private void swap(T[] values, int i, int j) {
		T tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	private int min_index(T[] values, int start) {
		int min_index = start;
		while (start <= values.length - 1) {
			if (comparator.compare(values[start], values[min_index]) < 0) {
				min_index = start;
			}
			start += 1;
		}
		return min_index;
	}
}
