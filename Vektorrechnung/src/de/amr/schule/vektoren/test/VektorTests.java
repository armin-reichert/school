package de.amr.schule.vektoren.test;

import static de.amr.schule.vektoren.Vec3.EX;
import static de.amr.schule.vektoren.Vec3.EY;
import static de.amr.schule.vektoren.Vec3.EZ;
import static de.amr.schule.vektoren.Vec3.NULL;
import static de.amr.schule.vektoren.Vec3.aboutEqual;
import static de.amr.schule.vektoren.Vec3.angle;
import static de.amr.schule.vektoren.Vec3.cross;
import static de.amr.schule.vektoren.Vec3.diff;
import static de.amr.schule.vektoren.Vec3.dot;
import static de.amr.schule.vektoren.Vec3.length;
import static de.amr.schule.vektoren.Vec3.sum;
import static de.amr.schule.vektoren.Vec3.times;
import static java.lang.Math.PI;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.amr.schule.vektoren.Vec3;
import de.amr.schule.vektoren.aufgaben.VektorApp;

public class VektorTests extends VektorApp {

	private Vec3 u, v, w;

	@Before
	public void setup() {
		u = new Vec3(-1, 3, 5);
		v = new Vec3(2, 3, -1);
		w = new Vec3(0, 1, 2);
	}

	@Test
	public void showConstants() {
		$(NULL, "Nullvektor");
		$(EX, "EX");
		$(EY, "EY");
		$(EZ, "EZ");
	}

	@Test
	public void testConstruction() {
		assertTrue(u.x == -1);
		assertTrue(u.y == 3);
		assertTrue(u.z == 5);
		assertTrue(u.equals(new Vec3(-1, 3, 5)));
		assertTrue(u.equals(new Vec3(u)));
	}

	@Test
	public void testBinarySum() {
		Vec3 sum = sum(u, v);
		$(u, "u");
		$(v, "v");
		$(sum, "u + v");
		assertTrue(sum.x == 1);
		assertTrue(sum.y == 6);
		assertTrue(sum.z == 4);
	}

	@Test
	public void testMultiSum() {
		assertTrue(sum().equals(NULL));
		assertTrue(sum(u).equals(u));
		assertTrue(sum(u, v).equals(u.add(v)));
		assertTrue(sum(u, v, w).equals(sum(u, sum(v, w))));
		assertTrue(Stream.of(u, v, w).reduce(NULL, Vec3::sum).equals(sum(u, v, w)));
	}

	@Test
	public void testSumAssociativity() {
		assertTrue(sum(u, sum(v, w)).equals(sum(sum(u, v), w)));
	}

	@Test
	public void testSumCommutativity() {
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
		assertTrue(NULL.inv().equals(NULL));
	}

	@Test
	public void testDifference() {
		assertTrue(diff(u, v).equals(sum(u, v.inv())));
		assertTrue(diff(u, u).equals(NULL));
		assertTrue(diff(u, NULL).equals(u));
		assertTrue(diff(NULL, u).equals(u.inv()));
	}

	@Test
	public void testSMultiplication() {
		Vec3 times5 = times(5, u);
		$(u, "u");
		$(times5, "5 * u");
		assertTrue(times5.x == -5);
		assertTrue(times5.y == 15);
		assertTrue(times5.z == 25);

		assertTrue(times(-1, u).equals(u.inv()));
		assertTrue(times(0, u).equals(NULL));
		assertTrue(times(1, u).equals(u));
		assertTrue(times(2, u).equals(sum(u, u)));

		assertTrue(times(2, times(3, u)).equals(times(2 * 3, u)));
		assertTrue(times(2 + 3, u).equals(times(2, u).add(times(3, u))));
		assertTrue(times(2, u.add(v)).equals(times(2, u).add(times(2, v))));
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

		assertTrue(dot(NULL, u) == 0);
		assertTrue(dot(u, v) == dot(v, u));
		assertTrue(dot(u, v.add(w)) == dot(u, v) + dot(u, w));
		assertTrue(2 * dot(u, v) == dot(times(2, u), v));
		assertTrue(2 * dot(u, v) == dot(u, times(2, v)));

		assertTrue(dot(u, u) == u.length() * u.length());

		// sign
		Vec3 a = new Vec3(2, 0, 0);
		Vec3 b = new Vec3(2, 2, 0);
		Vec3 c = new Vec3(0, 2, 0);
		Vec3 d = new Vec3(-2, 2, 0);

		assertTrue(dot(a, a) > 0);
		assertTrue(dot(a, b) > 0);
		assertTrue(dot(a, c) == 0);
		assertTrue(dot(a, d) < 0);
	}

	@Test
	public void testLength() {
		assertTrue("Nullvektor hat Länge 0", length(NULL) == 0);
		assertTrue("EX hat Länge 1", length(EX) == 1);
		assertTrue("EY hat Länge 1", length(EY) == 1);
		assertTrue("EZ hat Länge 1", length(EZ) == 1);
		assertTrue("(0,3,4) hat Länge 5", length(new Vec3(0, 3, 4)) == 5);
		assertTrue(length(times(5, u)) == 5 * length(u));
		assertTrue(length(u) == length(u.inv()));
		assertTrue(length(u) > 0);
		assertTrue(length(sum(u, v)) <= length(u) + length(v));
	}

	@Test
	public void testAngle() {
		assertTrue(angle(EX, EX) == 0);
		assertTrue(angle(EX, EY) == PI / 2);
		assertTrue(angle(EX, EZ) == PI / 2);
		assertTrue(angle(EX, EX.inv()) == PI);

		Vec3 diag1 = new Vec3(1, 1, 0);
		double angle = angle(EX, diag1);
		$(Math.toDegrees(angle), "Winkel in Grad");
		assertTrue(aboutEqual(angle, PI / 4.0));

		Vec3 diag2 = new Vec3(-1, -1, 0);
		angle = angle(EX, diag2);
		$(Math.toDegrees(angle), "Winkel in Grad");
		assertTrue(aboutEqual(angle, 3 * PI / 2.0));
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
		Vec3 v = new Vec3(1, 2, 3);
		$(v.add(v.times(5)), "(1,2,3) + 5 * (1,2,3)");
		$(v.add(v).times(5), "5 * ((1,2,3) + (1,2,3))");
		$(v.dot(v), "(1,2,3) * (1,2,3)");
		Assert.assertTrue(v.add(v).equals(new Vec3(2, 4, 6)));
	}
}