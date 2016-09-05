package de.amr.schule.vektoren.test;

import org.junit.Before;
import org.junit.Test;

import de.amr.schule.vektoren.Vector3;
import de.amr.schule.vektoren.aufgaben.VektorApp;
import de.amr.schule.vektoren.ebene.EbeneANF;
import de.amr.schule.vektoren.ebene.EbeneKoordF;
import de.amr.schule.vektoren.ebene.EbenePNF;
import de.amr.schule.vektoren.ebene.EbeneParamF;
import de.amr.schule.vektoren.figuren.Dreieck;
import de.amr.schule.vektoren.figuren.Parallelogramm;

public class EbenenTests extends VektorApp {

	@Before
	public void setup() {
	}

	@Test
	public void testPlanes() {
		{
			EbeneANF e = new EbeneANF(new Vector3(2, -3, 1), 6);
			$(e);
			$(e.contains(new Vector3(-1, -2, 2)), "Ebene enthält (-1|-2|2)");
			$(e.contains(new Vector3(5, -3, 1)), "Ebene enthält (5|-3|1)");
			EbeneParamF paramForm = e.toParamF();
			$(paramForm, "Parameterform");
			EbenePNF punktNormalenForm = e.toPNF();
			$(punktNormalenForm, "Punkt-Normalenform");
			EbeneKoordF koordForm = e.toKoordF();
			$(koordForm, "Koordinatenform");
			EbeneANF allgNormalenForm = paramForm.toANF();
			$(allgNormalenForm, "ANF aus Parameterform");
		}

		{
			Vector3 a = new Vector3(2, -1, 4), b = new Vector3(4, 1, 2), c = new Vector3(1, 5, 3);
			EbeneParamF paramf = EbeneParamF.dreiPunkte(a, b, c);
			$(paramf);
			$(paramf.contains(a), "Ebene enthält Punkt " + a);
			$(paramf.contains(b), "Ebene enthält Punkt " + b);
			$(paramf.contains(c), "Ebene enthält Punkt " + c);

			EbenePNF pnf = paramf.toPNF();
			$(pnf);
			$(pnf.contains(a), "Ebene enthält Punkt " + a);
			$(pnf.contains(b), "Ebene enthält Punkt " + b);
			$(pnf.contains(c), "Ebene enthält Punkt " + c);

			EbeneANF anf = paramf.toANF();
			$(anf);
			$(anf.contains(a), "Ebene enthält Punkt " + a);
			$(anf.contains(b), "Ebene enthält Punkt " + b);
			$(anf.contains(c), "Ebene enthält Punkt " + c);

			EbeneKoordF kf = paramf.toKoordF();
			$(kf);
			$(kf.contains(a), "Ebene enthält Punkt " + a);
			$(kf.contains(b), "Ebene enthält Punkt " + b);
			$(kf.contains(c), "Ebene enthält Punkt " + c);
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