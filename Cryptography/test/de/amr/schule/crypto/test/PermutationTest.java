package de.amr.schule.crypto.test;

import static de.amr.schule.crypto.api.Permutation.identity;
import static de.amr.schule.crypto.api.Permutation.perm;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.amr.schule.crypto.api.Permutation;

public class PermutationTest {

	@Test
	public void testIdentity() {
		Permutation id3 = identity(3);
		assertEquals(3, id3.length());
		assertEquals(0, id3.value(0));
		assertEquals(1, id3.value(1));
		assertEquals(2, id3.value(2));
	}

	@Test
	public void testEquals() {
		assertEquals(identity(3), identity(3));
	}

	@Test
	public void testInv() {
		Permutation p = perm(0, 2, 1);
		Permutation q = p.inv();
		assertEquals(q, perm(0, 2, 1));
		assertEquals(p.after(q), identity(3));
		assertEquals(q.after(p), identity(3));
	}
	
	@Test
	public void testAfter() {
		Permutation p = perm(0, 2, 1, 3);
		Permutation q = p.after(p);
		assertEquals(q, perm(0, 1, 2, 3));
	}

	@Test
	public void testBefore() {
		Permutation p = perm(0, 2, 1, 3);
		Permutation q = p.before(p);
		assertEquals(q, perm(0, 1, 2, 3));
	}
}
