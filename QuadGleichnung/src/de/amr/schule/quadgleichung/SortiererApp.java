package de.amr.schule.quadgleichung;

import java.util.Comparator;

public class SortiererApp {

	private static <T> void print(T[] values) {
		for (int i = 0; i <= values.length - 1; i += 1) {
			System.out.print(values[i]);
			System.out.print(", ");
		}
		System.out.println("");
	}

	private static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	}

	private static class IntComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	}

	private static class PersonNachAlter implements Comparator<Person> {

		@Override
		public int compare(Person p1, Person p2) {
			return p1.alter < p2.alter ? -1 : p1.alter > p2.alter ? 1 : 0;
		}
	}

	private static class PersonNachAlterAbsteigend implements Comparator<Person> {

		@Override
		public int compare(Person p1, Person p2) {
			return p1.alter < p2.alter ? 1 : p1.alter > p2.alter ? -1 : 0;
		}
	}

	private static class PersonNachName implements Comparator<Person> {

		@Override
		public int compare(Person p1, Person p2) {
			return (p1.name + " " + p1.vorname).compareTo(p2.name + " " + p2.vorname);
		}
	}

	public static void main(String[] args) {
		SortiererDurchAuswahl<String> sortierer = new SortiererDurchAuswahl<>(new StringComparator());
		sortierer.sort("Peter", "Anna", "Armin", "Zwick", "Strack");

		SortiererDurchAuswahl<Integer> sortierer2 = new SortiererDurchAuswahl<>(new IntComparator());
		sortierer2.sort(-1, 2, -5, 7, 10, 0, 0);

		Person[] personen = { new Person("Schillo", "Peter", 15), new Person("Schillo", "Anna", 12),
				new Person("Reichert", "Armin", 51), };

		print(personen);

		SortiererDurchAuswahl<Person> nachAlter = new SortiererDurchAuswahl<>(new PersonNachAlter());
		nachAlter.sort(personen);
		print(personen);

		SortiererDurchAuswahl<Person> nachAlterAbsteigend = new SortiererDurchAuswahl<>(
				new PersonNachAlterAbsteigend());
		nachAlterAbsteigend.sort(personen);
		print(personen);

		SortiererDurchAuswahl<Person> nachName = new SortiererDurchAuswahl<>(new PersonNachName());
		nachName.sort(personen);
		print(personen);

	}

}
