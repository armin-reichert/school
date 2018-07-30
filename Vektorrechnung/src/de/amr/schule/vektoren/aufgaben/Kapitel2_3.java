package de.amr.schule.vektoren.aufgaben;

import static de.amr.schule.vektoren.Vec3.diff;
import static de.amr.schule.vektoren.Vec3.sum;
import static de.amr.schule.vektoren.Vec3.times;

import org.junit.Test;

import de.amr.schule.vektoren.Vec3;
import de.amr.schule.vektoren.ebene.Ebene;
import de.amr.schule.vektoren.ebene.EbeneANF;
import de.amr.schule.vektoren.ebene.EbeneKoordF;
import de.amr.schule.vektoren.ebene.EbenePNF;
import de.amr.schule.vektoren.ebene.EbeneParamF;

public class Kapitel2_3 extends VektorApp {

	@Test
	public void a16a() {
		aufgabe("16a)");
		EbeneANF e1 = new EbeneANF(new Vec3(1, 2, 6), -9);
		$(e1);
		Vec3 a = new Vec3(0, 1, 3);
		$(e1.contains(a), "e1 enthält A " + a);
		Vec3 b = new Vec3(-1, -1, -1);
		$(e1.contains(b), "e1 enthält B " + b);
	}

	@Test
	public void a16b() {
		aufgabe("16b)");
		Vec3 p = new Vec3(3, 0, 1), q = new Vec3(3, -2, -1), r = new Vec3(-3, 0, 0);
		EbeneKoordF e2 = new EbeneKoordF(1, 2, 3, 8);
		$(e2);
		$(e2.contains(p), "e2 enthält P " + p);
		$(e2.contains(q), "e2 enthält Q " + q);
		$(e2.contains(r), "e2 enthält R " + r);
	}

	@Test
	public void a17a() {
		aufgabe("17a)");
		EbeneKoordF e = new EbeneKoordF(-5, 7, 3, 2);
		Vec3 p = new Vec3(1, 1, 0), q = new Vec3(2, 3, -3), r = new Vec3(5, -1, 11);
		$(e);
		$(e.contains(p), "e enthält P" + p);
		$(e.contains(q), "e enthält Q" + q);
		$(e.contains(r), "e enthält R" + r);
	}

	@Test
	public void a18a() {
		aufgabe("18a)");
		EbeneKoordF e = new EbeneKoordF(2, -1, 0, 5);
		$(e);
		EbeneANF anf = e.toANF();
		$(anf);
	}

	@Test
	public void a19a() {
		aufgabe("19a)");
		EbeneParamF e = new EbeneParamF(new Vec3(0, 1, 0), new Vec3(1, 1, 0), new Vec3(0, 4, 1));
		$(e);
		EbeneKoordF ekf = e.toKoordF();
		$(ekf);
	}

	@Test
	public void a19b() {
		aufgabe("19b)");
		EbeneParamF e = new EbeneParamF(new Vec3(1, 0, 0), new Vec3(1, 0, 0), new Vec3(0, 1, 0));
		$(e);
		EbeneKoordF ekf = e.toKoordF();
		$(ekf);
	}

	@Test
	public void a19c() {
		aufgabe("19c)");
		EbeneParamF e = new EbeneParamF(new Vec3(2, 1, 4), new Vec3(3, 2, -1), new Vec3(0, 4, -1));
		$(e);
		EbeneKoordF ekf = e.toKoordF();
		$(ekf);
	}

	@Test
	public void a19d() {
		aufgabe("19d)");
		EbeneParamF e = new EbeneParamF(new Vec3(0, 0, 0), new Vec3(5, 0, 1), new Vec3(-3, 1, 0));
		$(e);
		EbeneKoordF ekf = e.toKoordF();
		$(ekf);
	}

	@Test
	public void a20a() {
		aufgabe("20a)");
		Vec3 a = new Vec3(-1, 3, 1), b = new Vec3(3, -4, 1), c = new Vec3(0, 0, -1);
		EbeneParamF e = EbeneParamF.dreiPunkte(a, b, c);
		$(e.toPNF());
		$(e.toKoordF());
	}

	@Test
	public void a20b() {
		aufgabe("20b)");
		Vec3 a = new Vec3(6, -2, 1), b = new Vec3(-1, 0, 2), c = new Vec3(0, 0, 1);
		EbeneParamF e = EbeneParamF.dreiPunkte(a, b, c);
		$(e.toPNF());
		$(e.toKoordF());
	}

	@Test
	public void a22a() {
		aufgabe("22a)");
		EbenePNF e = new EbenePNF(new Vec3(2, -1, 0), new Vec3(1, -1, 0));
		$(e.toANF());
	}

	@Test
	public void a22b() {
		aufgabe("22b)");
		EbenePNF e = new EbenePNF(new Vec3(0, 0, 1), new Vec3());
		$(e.toANF());
	}

	@Test
	public void a22c() {
		aufgabe("22c)");
		EbenePNF e = new EbenePNF(new Vec3(0, 1, 0), new Vec3(-4, 1, 3));
		$(e.toANF());
	}

	@Test
	public void a22d() {
		aufgabe("22d)");
		Vec3 a = new Vec3(3, -2, 5);
		EbenePNF e = new EbenePNF(a, a);
		$(e.toANF());
	}

	@Test
	public void a22e() {
		aufgabe("22e)");
		Vec3 a = new Vec3(3, 5, 1);
		Vec3 b = new Vec3(7, -1, 3);
		EbenePNF e = new EbenePNF(diff(b, a), times(0.5, sum(a, b)));
		$(e.toANF());
	}

	@Test
	public void a99() {
		Ebene e = new EbeneKoordF(4, 0, 1, -8);
		$(e);
		Vec3 p = new Vec3(2, 1, 1);
		$(p, "Punkt P");
		$(e.dist(p), "Abstand P von e");
	}
}
