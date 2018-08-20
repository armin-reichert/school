package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vec3.NULL;
import static de.amr.schule.vektoren.Vec3.collinear;
import static de.amr.schule.vektoren.Vec3.cross;
import static de.amr.schule.vektoren.Vec3.diff;
import static de.amr.schule.vektoren.Vec3.sum;
import static de.amr.schule.vektoren.Vec3.times;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import de.amr.schule.vektoren.Vec3;

public class Kapitel1 extends VektorApp {

	@Test
	public void a2() {
		$(sum(new Vec3(0, -2, 0), new Vec3(-3, -1, 0), new Vec3(-1, -3, 0)));
		$(sum(new Vec3(-1, -5, 0), new Vec3(3, -2, 0), NULL));
	}

	@Test
	public void a3() {
		$(sum(new Vec3(1, -2, 5), new Vec3(-3, 0, -3), new Vec3(3, 1, 0)));
	}

	@Test
	public void a9() {
		Vec3 a = new Vec3(1, -2, 0), b = new Vec3(-3, 2, 0), c = new Vec3(-4, -3, 0);
		aufgabe("9 a)");
		$(sum(a, b.inv(), c.inv()));
		aufgabe("9 b)");
		$(sum(diff(a, diff(b, a)), c));
	}

	@Test
	public void a10() {
		Vec3 a = new Vec3(1, 4, 1), b = new Vec3(-1, 0, -3), c = new Vec3(-3, 2, -3);
		aufgabe("10 a)");
		$(sum(a, b.inv(), c.inv()));
		aufgabe("10 b)");
		$(sum(c, diff(b, c).inv(), a));
		aufgabe("10 c)");
		$(sum(a, sum(NULL, c.inv()).inv()));
		aufgabe("10 d)");
		$(sum(NULL, diff(b.inv(), a.inv()).inv(), b));
	}

	@Test
	public void a11() {
		aufgabe("11 a)");
		Vec3 x = sum(new Vec3(-1, 3, -2), new Vec3(1, 2, 6).inv());
		$(x);
		aufgabe("11 b)");
		x = diff(NULL, new Vec3(-2, 0, 3));
		$(x);
		aufgabe("11 c)");
		x = diff(new Vec3(-4, -1, 3), new Vec3(0, 3, 5)).inv();
		$(x);
	}

	@Test
	public void a12() {
		aufgabe("12 a)");
		Vec3 a = new Vec3(-2, -3, 0), b = new Vec3(-7, 1, -3);
		$(diff(b, a));
		aufgabe("12 b)");
		a = new Vec3(1, -3, -4);
		b = new Vec3(1, 0, -3);
		$(diff(b, a));
	}

	@Test
	public void a15() {
		aufgabe("15");
		Vec3 a = new Vec3(3, -4, 0), b = new Vec3(-2, 1, -1);
		$(a.inv(), "-a");
		$(b.inv(), "-b");
		$(times(2, a), "2*a");
		$(times(-2, a), "-2*a");
		$(times(-3, a), "-3*a");
		$(times(-4, b), "-4*b");
		$(times(1. / 2., b), "1/2*b");
		$(times(3, NULL), "3*0");
		$(times(-4, NULL), "-4*0");
		$(times(-5, a), "-5*a");
		$(times(3, b.inv()), "3*(-b)");
		$(times(0, b.inv()), "0*(-b)");

	}

	@Test
	public void a16() {
		Vec3 a = new Vec3(-2, -1, 0);
		Vec3 b = new Vec3(2, 0, -1);

		aufgabe("16 a)");
		$(sum(times(3, a), times(3, b)));

		aufgabe("16 b)");
		$(sum(times(-2, a), times(3, a)));

		aufgabe("16 c)");
		$(sum(times(-5, b), times(3, b)));

		aufgabe("16 d)");
		$(diff(b, times(2, b)));

		aufgabe("16 e)");
		$(diff(sum(times(3, b), a), times(2, b)));

		aufgabe("16 f)");
		$(diff(diff(times(4, a), times(3, b)), times(2, a)));

		aufgabe("16 g)");
		$(sum(diff(times(2, a), times(5, a)), b));

		aufgabe("16 h)");
		$(diff(sum(times(3, a), times(2, b)), times(4, a)));

		aufgabe("16 i)");
		$(diff(sum(diff(times(6, a), times(2, b)), a), a));
	}

	@Test
	public void a17() {
		Vec3 a = new Vec3(3, -1, -2), b = new Vec3(-2, 1, -3), c = new Vec3(-1, 5, 0), d = new Vec3(9, 1, 2);
		Vec3 ab = diff(b, a);
		Vec3 cd = diff(d, c);
		$(ab, "AB");
		$(cd, "CD");
		$(ab.equals(cd), "AB gleich CD");
		$(cross(ab, cd), "AB x CD");
		$(collinear(ab, cd), "AB || CD");
	}

	// @Test
	public void s_multiplikation() {
		Vec3 va, vb;
		double a, b;
		Random rnd = new Random();
		for (int i = 0; i < 100; ++i) {
			va = new Vec3(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			vb = new Vec3(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			a = rnd.nextDouble();
			b = rnd.nextDouble();
			assertTrue("Assoziativgesetz", times(a, times(b, va)).equals(times(a * b, va)));
			assertTrue("1. Distributivgesetz", times(a, sum(va, vb)).equals(sum(times(a, va), times(a, vb))));
			assertTrue("2. Distributivgesetz", times(a + b, va).equals(sum(times(a, va), times(b, vb))));
			assertTrue("Neutrales Element", times(1, va).equals(va));
		}
	}

}
