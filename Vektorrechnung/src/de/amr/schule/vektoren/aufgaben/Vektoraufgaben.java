package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vektor.cross;
import static de.amr.schule.vektoren.Vektor.dot;
import static de.amr.schule.vektoren.Vektor.length;
import static de.amr.schule.vektoren.Vektor.plus;
import static de.amr.schule.vektoren.Vektor.times;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.vektoren.Vektor;
import de.amr.schule.vektoren.ebene.EbeneAllgNormalenForm;
import de.amr.schule.vektoren.ebene.EbeneKoordinatenForm;
import de.amr.schule.vektoren.ebene.EbeneParameterForm;
import de.amr.schule.vektoren.ebene.EbenePunktNormalenForm;
import de.amr.schule.vektoren.figuren.Dreieck;
import de.amr.schule.vektoren.figuren.Parallelogramm;

public class Vektoraufgaben extends Aufgabe {

	@Test
	public void basics() {
		Vektor u = new Vektor(-1, 3, 5);
		Vektor v = new Vektor(2, 3, -1);

		$(u, "u");
		$(v, "v");
		$(plus(u, v), "u + v");
		$(times(5, u), "5 * u");

		$(dot(u, v), "Skalarprodukt u * v");
		$(length(u), "Länge von u");

		$(cross(u, v), "Kreuzprodukt u x v");
		$(cross(new Vektor(1, 0, 0), new Vektor(0, 1, 0)), "e_x x e_y");
		$(cross(new Vektor(1, 0, 0), new Vektor(0, 0, 1)), "e_x x e_z");
		$(cross(new Vektor(0, 1, 0), new Vektor(0, 0, 1)), "e_y x e_z");
	}

	@Test
	public void methoden() {
		$(new Vektor(1, 2, 3).plus(new Vektor(1, 2, 3).times(5)), "(1,2,3) + 5 * (1,2,3)");
		$(new Vektor(1,2,3).plus(new Vektor(1,2,3)).times(5), "5 * ((1,2,3) + (1,2,3))");
		$(new Vektor(1,2,3).dot(new Vektor(1,2,3)), "(1,2,3) * (1,2,3)");
		Vektor v = new Vektor(1,2,3);
		v.plus(v);
		Assert.assertTrue(v.equals(new Vektor(2,4,6)));
		
	}

	@Test
	public void ebenen() {
		{
			EbeneAllgNormalenForm e = new EbeneAllgNormalenForm(new Vektor(2, -3, 1), 6);
			$(e);
			$(e.contains(new Vektor(-1, -2, 2)), "Ebene enthält (-1|-2|2)");
			$(e.contains(new Vektor(5, -3, 1)), "Ebene enthält (5|-3|1)");
		}

		{
			Vektor a = new Vektor(2, -1, 4), b = new Vektor(4, 1, 2), c = new Vektor(1, 5, 3);
			EbeneParameterForm e = EbeneParameterForm.dreiPunkte(a, b, c);
			$(e);
			$(e.contains(a), "Ebene enthält Punkt " + a);
			$(e.contains(b), "Ebene enthält Punkt " + b);
			$(e.contains(c), "Ebene enthält Punkt " + c);

			EbenePunktNormalenForm epnf = e.toPunktNormalenForm();
			$(epnf);
			$(epnf.contains(a), "Ebene enthält Punkt " + a);
			$(epnf.contains(b), "Ebene enthält Punkt " + b);
			$(epnf.contains(c), "Ebene enthält Punkt " + c);

			EbeneAllgNormalenForm eanf = e.toAllgNormalenForm();
			$(eanf);
			$(eanf.contains(a), "Ebene enthält Punkt " + a);
			$(eanf.contains(b), "Ebene enthält Punkt " + b);
			$(eanf.contains(c), "Ebene enthält Punkt " + c);

			EbeneKoordinatenForm ekf = e.toKoordinatenForm();
			$(ekf);
			$(ekf.contains(a), "Ebene enthält Punkt " + a);
			$(ekf.contains(b), "Ebene enthält Punkt " + b);
			$(ekf.contains(c), "Ebene enthält Punkt " + c);
		}

		{
			Vektor a = new Vektor(2, 2, 0), b = new Vektor(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vektor a = new Vektor(4, 0, 0), b = new Vektor(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vektor a = new Vektor(4, 0, 0), b = new Vektor(0, 4, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vektor a = new Vektor(4, 0, 0), b = new Vektor(0, 4, 0), c = new Vektor(0, 0, 0);
			Dreieck d = new Dreieck(a, b, c);
			$(d);
			$(d.fläche(), "Fläche");
			$(d.umfang(), "Umfang");
		}

	}
}