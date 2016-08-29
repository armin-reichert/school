package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vector3.minus;
import static de.amr.schule.vektoren.Vector3.plus;

import org.junit.Test;

import de.amr.schule.vektoren.Vector3;
import de.amr.schule.vektoren.ebene.EbeneANF;
import de.amr.schule.vektoren.ebene.EbeneKoordF;
import de.amr.schule.vektoren.ebene.EbeneParamF;
import de.amr.schule.vektoren.ebene.EbenePNF;

public class Kapitel2 extends Aufgabe {

	@Test
	public void a16a() {
		aufgabe("16a)");
		EbeneANF e1 = new EbeneANF(new Vector3(1, 2, 6), -9);
		$(e1);
		Vector3 a = new Vector3(0, 1, 3);
		$(e1.contains(a), "e1 enthält A " + a);
		Vector3 b = new Vector3(-1, -1, -1);
		$(e1.contains(b), "e1 enthält B " + b);
	}

	@Test
	public void a16b() {
		aufgabe("16b)");
		Vector3 p = new Vector3(3, 0, 1), q = new Vector3(3, -2, -1), r = new Vector3(-3, 0, 0);
		EbeneKoordF e2 = new EbeneKoordF(1, 2, 3, 8);
		$(e2);
		$(e2.contains(p), "e2 enthält P " + p);
		$(e2.contains(q), "e2 enthält Q " + q);
		$(e2.contains(r), "e2 enthält R " + r);
	}

	@Test
	public void a17a() {
		aufgabe("17a)");
		EbeneKoordF e = new EbeneKoordF(-5, 7, 3, 2);
		Vector3 p = new Vector3(1, 1, 0), q = new Vector3(2, 3, -3), r = new Vector3(5, -1, 11);
		$(e);
		$(e.contains(p), "e enthält P" + p);
		$(e.contains(q), "e enthält Q" + q);
		$(e.contains(r), "e enthält R" + r);
	}

	@Test
	public void a18a() {
		aufgabe("18a)");
		EbeneKoordF e = new EbeneKoordF(2, -1, 0, 5);
		$(e);
		EbeneANF anf = e.toAllgNormalenForm();
		$(anf);
	}

	@Test
	public void a19a() {
		aufgabe("19a)");
		EbeneParamF e = new EbeneParamF(new Vector3(0, 1, 0), new Vector3(1, 1, 0),
				new Vector3(0, 4, 1));
		$(e);
		EbeneKoordF ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19b() {
		aufgabe("19b)");
		EbeneParamF e = new EbeneParamF(new Vector3(1, 0, 0), new Vector3(1, 0, 0),
				new Vector3(0, 1, 0));
		$(e);
		EbeneKoordF ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19c() {
		aufgabe("19c)");
		EbeneParamF e = new EbeneParamF(new Vector3(2, 1, 4), new Vector3(3, 2, -1),
				new Vector3(0, 4, -1));
		$(e);
		EbeneKoordF ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19d() {
		aufgabe("19d)");
		EbeneParamF e = new EbeneParamF(new Vector3(0, 0, 0), new Vector3(5, 0, 1),
				new Vector3(-3, 1, 0));
		$(e);
		EbeneKoordF ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a20a() {
		aufgabe("20a)");
		Vector3 a = new Vector3(-1, 3, 1), b = new Vector3(3, -4, 1), c = new Vector3(0, 0, -1);
		EbeneParamF e = EbeneParamF.dreiPunkte(a, b, c);
		$(e.toPunktNormalenForm());
		$(e.toKoordinatenForm());
	}

	@Test
	public void a20b() {
		aufgabe("20b)");
		Vector3 a = new Vector3(6, -2, 1), b = new Vector3(-1, 0, 2), c = new Vector3(0, 0, 1);
		EbeneParamF e = EbeneParamF.dreiPunkte(a, b, c);
		$(e.toPunktNormalenForm());
		$(e.toKoordinatenForm());
	}

	@Test
	public void a22a() {
		aufgabe("22a)");
		EbenePNF e = new EbenePNF(new Vector3(2, -1, 0),
				new Vector3(1, -1, 0));
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22b() {
		aufgabe("22b)");
		EbenePNF e = new EbenePNF(new Vector3(0, 0, 1), new Vector3());
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22c() {
		aufgabe("22c)");
		EbenePNF e = new EbenePNF(new Vector3(0, 1, 0),
				new Vector3(-4, 1, 3));
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22d() {
		aufgabe("22d)");
		Vector3 a = new Vector3(3, -2, 5);
		EbenePNF e = new EbenePNF(a, a);
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22e() {
		aufgabe("22e)");
		Vector3 a = new Vector3(3, 5, 1);
		Vector3 b = new Vector3(7, -1, 3);
		EbenePNF e = new EbenePNF(minus(b, a),
				Vector3.times(0.5, plus(a, b)));
		$(e.toAllgNormalenForm());
	}
}
