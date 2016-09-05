package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vector3.collinear;
import static de.amr.schule.vektoren.Vector3.cross;
import static de.amr.schule.vektoren.Vector3.diff;
import static de.amr.schule.vektoren.Vector3.sum;
import static de.amr.schule.vektoren.Vector3.times;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.vektoren.Vector3;

public class Kapitel1 extends VektorApp {

	@Test
	public void a16() {
		Vector3 a = new Vector3(-2, -1, 0);
		Vector3 b = new Vector3(2, 0, -1);

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
		Vector3 a = new Vector3(3, -1, -2), b = new Vector3(-2, 1, -3), c = new Vector3(-1, 5, 0),
				d = new Vector3(9, 1, 2);
		Vector3 ab = diff(b, a);
		Vector3 cd = diff(d, c);
		$(ab, "AB");
		$(cd, "CD");
		$(ab.equals(cd), "AB gleich CD");
		$(cross(ab, cd), "AB x CD");
		$(collinear(ab, cd), "AB || CD");
	}

	// @Test
	public void s_multiplikation() {
		Vector3 va, vb;
		double a, b;
		Random rnd = new Random();
		for (int i = 0; i < 100; ++i) {
			va = new Vector3(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			vb = new Vector3(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			a = rnd.nextDouble();
			b = rnd.nextDouble();
			Assert.assertTrue("Assoziativgesetz", times(a, times(b, va)).equals(times(a * b, va)));
			Assert.assertTrue("1. Distributivgesetz",
					times(a, sum(va, vb)).equals(sum(times(a, va), times(a, vb))));
			Assert.assertTrue("2. Distributivgesetz",
					times(a + b, va).equals(sum(times(a, va), times(b, vb))));
			Assert.assertTrue("Neutrales Element", times(1, va).equals(va));
		}
	}

}
