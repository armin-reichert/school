package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vector3.EX;
import static de.amr.schule.vektoren.Vector3.EY;
import static de.amr.schule.vektoren.Vector3.EZ;
import static de.amr.schule.vektoren.Vector3.NULL;
import static de.amr.schule.vektoren.Vector3.cross;
import static de.amr.schule.vektoren.Vector3.diff;
import static de.amr.schule.vektoren.Vector3.dot;
import static de.amr.schule.vektoren.Vector3.length;
import static de.amr.schule.vektoren.Vector3.sum;
import static de.amr.schule.vektoren.Vector3.times;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.amr.schule.vektoren.Vector3;
import de.amr.schule.vektoren.ebene.EbeneANF;
import de.amr.schule.vektoren.ebene.EbeneKoordF;
import de.amr.schule.vektoren.ebene.EbenePNF;
import de.amr.schule.vektoren.ebene.EbeneParamF;
import de.amr.schule.vektoren.figuren.Dreieck;
import de.amr.schule.vektoren.figuren.Parallelogramm;

public class VektorTests extends VektorApp {

	private Vector3 u, v, w;

	@Before
	public void setup() {
		u = new Vector3(-1, 3, 5);
		v = new Vector3(2, 3, -1);
		w = new Vector3(0, 1, 2);
	}

	@Test
	public void showConstants() {
		$(NULL, "Nullvektor");
		$(EX, "EX");
		$(EY, "EY");
		$(EZ, "EZ");
	}

	@Test
	public void testBinarySum() {
		Vector3 sum = sum(u, v);
		$(u, "u");
		$(v, "v");
		$(sum, "u + v");
		assertTrue(sum.x == 1);
		assertTrue(sum.y == 6);
		assertTrue(sum.z == 4);
	}

	@Test
	public void testSum() {
		assertTrue(sum().equals(NULL));
		assertTrue(sum(u).equals(u));
		assertTrue(sum(u, v).equals(sum(u, v)));
		assertTrue(sum(u, v, w).equals(sum(u, sum(v, w))));
		assertTrue(Stream.of(u, v, w).reduce(NULL, Vector3::sum).equals(sum(u, v, w)));
	}

	@Test
	public void testAssociativity() {
		assertTrue(sum(u, sum(v, w)).equals(sum(sum(u, v), w)));
	}

	@Test
	public void testCommutativity() {
		assertTrue(sum(u, v).equals(sum(v, u)));
	}

	@Test
	public void testNullVector() {
		assertTrue(sum(NULL, u).equals(u));
		assertTrue(sum(u, NULL).equals(u));
		assertTrue(sum(NULL, NULL).equals(NULL));
	}

	@Test
	public void testInverse() {
		assertTrue(sum(u, u.inv()).equals(NULL));
		assertTrue(sum(u.inv(), u).equals(NULL));
	}

	@Test
	public void testDifference() {
		assertTrue(diff(u, v).equals(sum(u, v.inv())));
		assertTrue(diff(u, u).equals(NULL));
		assertTrue(diff(u, NULL).equals(u));
		assertTrue(diff(NULL, u).equals(u.inv()));
	}

	@Test
	public void testBasics() {
		assertTrue(u.x == -1);
		assertTrue(u.y == 3);
		assertTrue(u.z == 5);
		assertTrue(u.equals(new Vector3(-1, 3, 5)));
		assertTrue(u.equals(new Vector3(u)));
	}

	@Test
	public void testSMultiplication() {
		Vector3 multiple = times(5, u);
		$(u, "u");
		$(multiple, "5 * u");
		assertTrue(multiple.x == -5);
		assertTrue(multiple.y == 15);
		assertTrue(multiple.z == 25);
	}

	@Test
	public void testScalarProduct() {
		double dot = dot(u, v);
		$(u, "u");
		$(v, "v");
		$(dot, "Skalarprodukt u * v");
		assertTrue(dot == 2);
		assertTrue(dot(EX, EX) == 1);
		assertTrue(dot(EY, EY) == 1);
		assertTrue(dot(EZ, EZ) == 1);
		assertTrue(dot(EX, EY) == 0);
		assertTrue(dot(EY, EZ) == 0);
		assertTrue(dot(EX, EZ) == 0);
		assertTrue(2 * dot(u, v) == dot(times(2, u), v));
		assertTrue(2 * dot(u, v) == dot(u, times(2, v)));
	}

	@Test
	public void testLength() {
		assertTrue("Nullvektor hat Länge 0", length(NULL) == 0);
		assertTrue("EX hat Länge 1", length(EX) == 1);
		assertTrue("EY hat Länge 1", length(EY) == 1);
		assertTrue("EZ hat Länge 1", length(EZ) == 1);
		assertTrue("(0,3,4) hat Länge 5", length(new Vector3(0, 3, 4)) == 5);
	}

	@Test
	public void testCrossProduct() {
		$(u, "u");
		$(v, "v");
		$(cross(u, v), "Kreuzprodukt u x v");

		$(cross(EX, EY), "EX x EY");
		assertTrue(cross(EX, EY).equals(EZ));

		$(cross(EX, EZ), "EX x EZ");
		assertTrue(cross(EX, EZ).equals(EY.inv()));

		$(cross(EY, EZ), "EY x EZ");
		assertTrue(cross(EY, EZ).equals(EX));

		$(cross(EX, EX), "EX x EX");
		assertTrue(cross(EX, EX).equals(NULL));

		$(cross(EY, EY), "EY x EY");
		assertTrue(cross(EY, EY).equals(NULL));

		$(cross(EZ, EZ), "EZ x EZ");
		assertTrue(cross(EZ, EZ).equals(NULL));

		// anti-symmetry
		assertTrue(cross(u, v).equals(cross(v, u).inv()));
	}

	@Test
	public void testMethods() {
		Vector3 v = new Vector3(1, 2, 3);
		$(v.add(v.times(5)), "(1,2,3) + 5 * (1,2,3)");
		$(v.add(v).times(5), "5 * ((1,2,3) + (1,2,3))");
		$(v.dot(v), "(1,2,3) * (1,2,3)");
		Assert.assertTrue(v.add(v).equals(new Vector3(2, 4, 6)));
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