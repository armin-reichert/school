package de.amr.schule.zinseszins;

public class ZinsRechner {

	private double[][] tabelle;

	public ZinsRechner(double startKapital, int p, int jahre) {
		tabelle = new double[jahre][2];
		for (int i = 0; i < jahre; ++i) {
			tabelle[i][0] = (i == 0) ? startKapital : tabelle[i - 1][0]
					+ tabelle[i - 1][1];
			tabelle[i][1] = tabelle[i][0] * p / 100d;
		}
	}

	public void ausgabe() {
		System.out.println(String.format("%10s %10s", "Kapital", "Zinsen"));
		for (int i = 0; i < tabelle.length; ++i) {
			System.out.println(String.format("%10.2f %10.2f", tabelle[i][0],
					tabelle[i][1]));
		}
	}

	public static void main(String[] args) {
		new ZinsRechner(350000, 3, 12).ausgabe();
	}

}
