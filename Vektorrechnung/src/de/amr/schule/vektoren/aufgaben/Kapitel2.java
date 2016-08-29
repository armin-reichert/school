package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vektor.minus;
import static de.amr.schule.vektoren.Vektor.plus;

import org.junit.Test;

import de.amr.schule.vektoren.Vektor;
import de.amr.schule.vektoren.ebene.EbeneAllgNormalenForm;
import de.amr.schule.vektoren.ebene.EbeneKoordinatenForm;
import de.amr.schule.vektoren.ebene.EbeneParameterForm;
import de.amr.schule.vektoren.ebene.EbenePunktNormalenForm;

public class Kapitel2 extends Aufgabe {

	@Test
	public void a16a() {
		aufgabe("16a)");
		EbeneAllgNormalenForm e1 = new EbeneAllgNormalenForm(new Vektor(1, 2, 6), -9);
		$(e1);
		Vektor a = new Vektor(0, 1, 3);
		$(e1.contains(a), "e1 enthält A " + a);
		Vektor b = new Vektor(-1, -1, -1);
		$(e1.contains(b), "e1 enthält B " + b);
	}

	@Test
	public void a16b() {
		aufgabe("16b)");
		Vektor p = new Vektor(3, 0, 1), q = new Vektor(3, -2, -1), r = new Vektor(-3, 0, 0);
		EbeneKoordinatenForm e2 = new EbeneKoordinatenForm(1, 2, 3, 8);
		$(e2);
		$(e2.contains(p), "e2 enthält P " + p);
		$(e2.contains(q), "e2 enthält Q " + q);
		$(e2.contains(r), "e2 enthält R " + r);
	}

	@Test
	public void a17a() {
		aufgabe("17a)");
		EbeneKoordinatenForm e = new EbeneKoordinatenForm(-5, 7, 3, 2);
		Vektor p = new Vektor(1, 1, 0), q = new Vektor(2, 3, -3), r = new Vektor(5, -1, 11);
		$(e);
		$(e.contains(p), "e enthält P" + p);
		$(e.contains(q), "e enthält Q" + q);
		$(e.contains(r), "e enthält R" + r);
	}

	@Test
	public void a18a() {
		aufgabe("18a)");
		EbeneKoordinatenForm e = new EbeneKoordinatenForm(2, -1, 0, 5);
		$(e);
		EbeneAllgNormalenForm anf = e.toAllgNormalenForm();
		$(anf);
	}

	@Test
	public void a19a() {
		aufgabe("19a)");
		EbeneParameterForm e = new EbeneParameterForm(new Vektor(0, 1, 0), new Vektor(1, 1, 0),
				new Vektor(0, 4, 1));
		$(e);
		EbeneKoordinatenForm ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19b() {
		aufgabe("19b)");
		EbeneParameterForm e = new EbeneParameterForm(new Vektor(1, 0, 0), new Vektor(1, 0, 0),
				new Vektor(0, 1, 0));
		$(e);
		EbeneKoordinatenForm ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19c() {
		aufgabe("19c)");
		EbeneParameterForm e = new EbeneParameterForm(new Vektor(2, 1, 4), new Vektor(3, 2, -1),
				new Vektor(0, 4, -1));
		$(e);
		EbeneKoordinatenForm ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a19d() {
		aufgabe("19d)");
		EbeneParameterForm e = new EbeneParameterForm(new Vektor(0, 0, 0), new Vektor(5, 0, 1),
				new Vektor(-3, 1, 0));
		$(e);
		EbeneKoordinatenForm ekf = e.toKoordinatenForm();
		$(ekf);
	}

	@Test
	public void a20a() {
		aufgabe("20a)");
		Vektor a = new Vektor(-1, 3, 1), b = new Vektor(3, -4, 1), c = new Vektor(0, 0, -1);
		EbeneParameterForm e = EbeneParameterForm.dreiPunkte(a, b, c);
		$(e.toPunktNormalenForm());
		$(e.toKoordinatenForm());
	}

	@Test
	public void a20b() {
		aufgabe("20b)");
		Vektor a = new Vektor(6, -2, 1), b = new Vektor(-1, 0, 2), c = new Vektor(0, 0, 1);
		EbeneParameterForm e = EbeneParameterForm.dreiPunkte(a, b, c);
		$(e.toPunktNormalenForm());
		$(e.toKoordinatenForm());
	}

	@Test
	public void a22a() {
		aufgabe("22a)");
		EbenePunktNormalenForm e = new EbenePunktNormalenForm(new Vektor(2, -1, 0),
				new Vektor(1, -1, 0));
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22b() {
		aufgabe("22b)");
		EbenePunktNormalenForm e = new EbenePunktNormalenForm(new Vektor(0, 0, 1), new Vektor());
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22c() {
		aufgabe("22c)");
		EbenePunktNormalenForm e = new EbenePunktNormalenForm(new Vektor(0, 1, 0),
				new Vektor(-4, 1, 3));
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22d() {
		aufgabe("22d)");
		Vektor a = new Vektor(3, -2, 5);
		EbenePunktNormalenForm e = new EbenePunktNormalenForm(a, a);
		$(e.toAllgNormalenForm());
	}

	@Test
	public void a22e() {
		aufgabe("22e)");
		Vektor a = new Vektor(3, 5, 1);
		Vektor b = new Vektor(7, -1, 3);
		EbenePunktNormalenForm e = new EbenePunktNormalenForm(minus(b, a),
				Vektor.times(0.5, plus(a, b)));
		$(e.toAllgNormalenForm());
	}
}
