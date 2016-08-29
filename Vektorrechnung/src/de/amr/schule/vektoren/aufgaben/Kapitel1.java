package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vektor.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import de.amr.schule.vektoren.Vektor;

public class Kapitel1 extends Aufgabe {

	@Test
	public void a16() {
		Vektor a = new Vektor(-2, -1, 0);
		Vektor b = new Vektor(2, 0, -1);

		aufgabe("16 a)");
		$(plus(times(3, a), times(3, b)));

		aufgabe("16 b)");
		$(plus(times(-2, a), times(3, a)));

		aufgabe("16 c)");
		$(plus(times(-5, b), times(3, b)));

		aufgabe("16 d)");
		$(minus(b, times(2, b)));

		aufgabe("16 e)");
		$(minus(plus(times(3, b), a), times(2, b)));

		aufgabe("16 f)");
		$(minus(minus(times(4, a), times(3, b)), times(2, a)));

		aufgabe("16 g)");
		$(plus(minus(times(2, a), times(5, a)), b));

		aufgabe("16 h)");
		$(minus(plus(times(3, a), times(2, b)), times(4, a)));

		aufgabe("16 i)");
		$(minus(plus(minus(times(6, a), times(2, b)), a), a));
	}

	@Test
	public void a17() {
		Vektor a = new Vektor(3, -1, -2), b = new Vektor(-2, 1, -3), c = new Vektor(-1, 5, 0),
				d = new Vektor(9, 1, 2);
		Vektor ab = minus(b, a);
		Vektor cd = minus(d, c);
		$(ab, "AB");
		$(cd, "CD");
		$(ab.equals(cd), "AB gleich CD");
		$(cross(ab, cd), "AB x CD");
		$(collinear(ab, cd), "AB || CD");
	}

//	@Test
	public void s_multiplikation() {
		Vektor va, vb;
		double a, b;
		Random rnd = new Random();
		for (int i = 0; i < 100; ++i) {
			va = new Vektor(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			vb = new Vektor(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble());
			a = rnd.nextDouble();
			b = rnd.nextDouble();
			Assert.assertTrue("Assoziativgesetz", times(a, times(b, va)).equals(times(a * b, va)));
			Assert.assertTrue("1. Distributivgesetz",
					times(a, plus(va, vb)).equals(plus(times(a, va), times(a, vb))));
			Assert.assertTrue("2. Distributivgesetz",
					times(a + b, va).equals(plus(times(a, va), times(b, vb))));
			Assert.assertTrue("Neutrales Element", times(1, va).equals(va));
		}
	}

}
