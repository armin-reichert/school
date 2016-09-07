package de.amr.schule.vektoren.test;

import static de.amr.schule.vektoren.Vec3.EX;
import static de.amr.schule.vektoren.Vec3.EY;
import static de.amr.schule.vektoren.Vec3.EZ;
import static de.amr.schule.vektoren.Vec3.NULL;
import static de.amr.schule.vektoren.Vec3.times;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import de.amr.schule.vektoren.Vec3;
import de.amr.schule.vektoren.aufgaben.VektorApp;
import de.amr.schule.vektoren.ebene.Ebene;
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

	@Test(expected = IllegalArgumentException.class)
	public void testRichtungsvektorenCollinear() {
		new EbeneParamF(NULL, EX, EX);
	}

	@Test
	public void testRichtungsvektorenNichtCollinear() {
		$(new EbeneParamF(NULL, EX, EY));
	}

	@Test
	public void testDreiPunkte() {
		Vec3 a = new Vec3(2, -1, 4), b = new Vec3(4, 1, 2), c = new Vec3(1, 5, 3);
		EbeneParamF paramf = EbeneParamF.dreiPunkte(a, b, c);
		$(paramf);
		Stream.of(a, b, c).forEach(p -> {
			$(paramf.contains(p), "Ebene enthält Punkt " + p);
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDreiPunkteAufGerade() {
		EbeneParamF.dreiPunkte(NULL, EX, times(2, EX));
	}

	@Test
	public void testPunktProbe() {
		Ebene e = new EbeneParamF(NULL, EX, EY); // x1-x2-Ebene
		assertTrue(e.contains(EX));
		assertTrue(e.contains(EY));
		assertTrue(!e.contains(EZ));
		assertTrue(e.contains(EX.add(EY)));
		assertTrue(!e.contains(EX.add(EZ)));
	}

	@Test
	public void testEbenenKonvertierung() {
		Vec3 n = new Vec3(2, -3, 1);
		EbeneANF e = new EbeneANF(n, 6);
		$(e);

		Vec3 a = new Vec3(3, 0, 0);

		Ebene e2;
		e2 = e.toParamF();
		assertTrue(e2.contains(a));
		$(e2, "Parameterform");

		e2 = e.toPNF();
		assertTrue(e2.contains(a));
		$(e2, "Punkt-Normalenform");

		e2 = e.toKoordF();
		assertTrue(e2.contains(a));
		$(e2, "Koordinatenform");
	}

	@Test
	public void testEbenenKonvertierung2() {
		Vec3 a = new Vec3(2, -1, 4), b = new Vec3(4, 1, 2), c = new Vec3(1, 5, 3);
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

	@Test
	public void testParallelogramm() {
		{
			Vec3 a = new Vec3(2, 2, 0), b = new Vec3(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vec3 a = new Vec3(4, 0, 0), b = new Vec3(0, 3, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}

		{
			Vec3 a = new Vec3(4, 0, 0), b = new Vec3(0, 4, 0);
			Parallelogramm p = new Parallelogramm(a, b);
			$(p.istRechteck(), p + " ist Rechteck");
			$(p.istQuadrat(), p + " ist Quadrat");
			$(p.fläche(), "Fläche");
			$(p.umfang(), "Umfang");
		}
	}

	@Test
	public void testDreieck() {
		Vec3 a = new Vec3(4, 0, 0), b = new Vec3(0, 4, 0), c = new Vec3(0, 0, 0);
		Dreieck d = new Dreieck(a, b, c);
		$(d);
		$(d.fläche(), "Fläche");
		$(d.umfang(), "Umfang");
	}
}