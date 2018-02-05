package de.amr.schule.darts.counter.model;

/**
 * Ring dimensions are given for a board diameter of 900;
 */
public enum BoardRing {
	BULLS_EYE(0, 12),
	SINGLE_BULL(13, 32),
	TRIPLE(190, 208),
	DOUBLE(317, 335),
	OUT(336, Integer.MAX_VALUE),
	SIMPLE(0, 335);
	
	public static final int BOARD_REFERENCE_SIZE = 900;

	public boolean contains(int radius, double scaling) {
		return (int) (scaling * inner) <= radius && radius <= (int) (scaling * outer);
	}

	private BoardRing(int inner, int outer) {
		this.inner = inner;
		this.outer = outer;
	}

	public final int inner;
	public final int outer;

}
