package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vector3.EX;
import static de.amr.schule.vektoren.Vector3.EY;
import static de.amr.schule.vektoren.Vector3.EZ;
import static de.amr.schule.vektoren.Vector3.cross;
import static de.amr.schule.vektoren.Vector3.dot;
import static de.amr.schule.vektoren.Vector3.length;
import static de.amr.schule.vektoren.Vector3.plus;
import static de.amr.schule.vektoren.Vector3.times;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.vektoren.Vector3;
import de.amr.schule.vektoren.ebene.EbeneANF;
import de.amr.schule.vektoren.ebene.EbeneKoordF;
import de.amr.schule.vektoren.ebene.EbeneParamF;
import de.amr.schule.vektoren.ebene.EbenePNF;
import de.amr.schule.vektoren.figuren.Dreieck;
import de.amr.schule.vektoren.figuren.Parallelogramm;

public class Vektoraufgaben extends Aufgabe {

	@Test
	public void basics() {
		Vector3 u = new Vector3(-1, 3, 5);
		Vector3 v = new Vector3(2, 3, -1);

		$(u, "u");
		$(v, "v");
		$(plus(u, v), "u + v");
		$(times(5, u), "5 * u");

		$(dot(u, v), "Skalarprodukt u * v");
		$(length(u), "Länge von u");

		$(cross(u, v), "Kreuzprodukt u x v");
		$(cross(EX, EY), "e_x x e_y");
		$(cross(EX, EZ), "e_x x e_z");
		$(cross(EY, EZ), "e_y x e_z");
	}

	@Test
	public void methoden() {
		Vector3 v = new Vector3(1, 2, 3);
		$(v.plus(v.times(5)), "(1,2,3) + 5 * (1,2,3)");
		$(v.plus(v).times(5), "5 * ((1,2,3) + (1,2,3))");
		$(v.dot(v), "(1,2,3) * (1,2,3)");
		Assert.assertTrue(v.plus(v).equals(new Vector3(2, 4, 6)));
	}

	@Test
	public void ebenen() {
		{
			EbeneANF e = new EbeneANF(new Vector3(2, -3, 1), 6);
			$(e);
			$(e.contains(new Vector3(-1, -2, 2)), "Ebene enthält (-1|-2|2)");
			$(e.contains(new Vector3(5, -3, 1)), "Ebene enthält (5|-3|1)");
		}

		{
			Vector3 a = new Vector3(2, -1, 4), b = new Vector3(4, 1, 2), c = new Vector3(1, 5, 3);
			EbeneParamF e = EbeneParamF.dreiPunkte(a, b, c);
			$(e);
			$(e.contains(a), "Ebene enthält Punkt " + a);
			$(e.contains(b), "Ebene enthält Punkt " + b);
			$(e.contains(c), "Ebene enthält Punkt " + c);

			EbenePNF epnf = e.toPunktNormalenForm();
			$(epnf);
			$(epnf.contains(a), "Ebene enthält Punkt " + a);
			$(epnf.contains(b), "Ebene enthält Punkt " + b);
			$(epnf.contains(c), "Ebene enthält Punkt " + c);

			EbeneANF eanf = e.toAllgNormalenForm();
			$(eanf);
			$(eanf.contains(a), "Ebene enthält Punkt " + a);
			$(eanf.contains(b), "Ebene enthält Punkt " + b);
			$(eanf.contains(c), "Ebene enthält Punkt " + c);

			EbeneKoordF ekf = e.toKoordinatenForm();
			$(ekf);
			$(ekf.contains(a), "Ebene enthält Punkt " + a);
			$(ekf.contains(b), "Ebene enthält Punkt " + b);
			$(ekf.contains(c), "Ebene enthält Punkt " + c);
		}

		{
			Vector3 a = new Vector3(2, 2, 0), b = new Vector3(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vector3 a = new Vector3(4, 0, 0), b = new Vector3(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vector3 a = new Vector3(4, 0, 0), b = new Vector3(0, 4, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vector3 a = new Vector3(4, 0, 0), b = new Vector3(0, 4, 0), c = new Vector3(0, 0, 0);
			Dreieck d = new Dreieck(a, b, c);
			$(d);
			$(d.fläche(), "Fläche");
			$(d.umfang(), "Umfang");
		}

	}
}