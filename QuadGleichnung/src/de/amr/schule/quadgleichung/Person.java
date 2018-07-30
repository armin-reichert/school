package de.amr.schule.quadgleichung;

public class Person {

	public String name;

	public String vorname;

	public int alter;

	public Person(String name, String vorname, int alter) {
		this.name = name;
		this.vorname = vorname;
		this.alter = alter;
	}

	@Override
	public String toString() {
		return vorname + " " + name + "(" + alter + ")";
	}

}
