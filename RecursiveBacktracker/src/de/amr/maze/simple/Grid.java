package de.amr.maze.simple;

import java.util.BitSet;

/**
 * Grid graph implementation. Edge set is represented by a single bit-set.
 */
public class Grid {

	/** Directions. */
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	/** Opposite directions. */
	public static final int[] OPPOSITE = { SOUTH, WEST, NORTH, EAST };

	/* Direction vectors. */
	public static final int[] X = { 0, 1, 0, -1 };
	public static final int[] Y = { -1, 0, 1, 0 };

	/** Index representing no vertex. */
	public static final int NO_VERTEX = -1;

	/** Number of grid columns. */
	public final int numCols;

	/** Number of grid rows. */
	public final int numRows;

	private BitSet edges;

	/** The bit-set index of the edge leaving vertex {@code v} towards direction {@code dir}. */
	private int bit(int v, int dir) {
		return 4 * v + dir;
	}

	/** Creates an empty grid of the given size. */
	public Grid(int numCols, int numRows) {
		this.numCols = numCols;
		this.numRows = numRows;
		edges = new BitSet(numCols * numRows * 4);
	}

	/** Vertex at column {@code col} and row {@code row}. */
	public int vertex(int col, int row) {
		return numCols * row + col;
	}

	/** Column of vertex {@code v}. */
	public int col(int v) {
		return v % numCols;
	}

	/** Row of vertex {@code v}. */
	public int row(int v) {
		return v / numCols;
	}

	/** Returns the number of (undirected) edges. */
	public int numEdges() {
		return edges.cardinality() / 2;
	}

	/** Adds the edge from vertex {@code v} towards direction {@code dir}. */
	public void addEdge(int v, int dir) {
		edges.set(bit(v, dir));
		edges.set(bit(neighbor(v, dir), OPPOSITE[dir]));
	}

	/** Tells if the edge from vertex {@code v} towards direction {@code dir} exists. */
	public boolean hasEdge(int v, int dir) {
		return edges.get(bit(v, dir));
	}

	/**
	 * Returns the neighbor of vertex {@code v} towards direction {@code dir} or {@link #NO_VERTEX}.
	 */
	public int neighbor(int v, int dir) {
		int col = col(v) + X[dir], row = row(v) + Y[dir];
		return col >= 0 && col < numCols && row >= 0 && row < numRows ? vertex(col, row) : NO_VERTEX;
	}

	/** Returns a textual representation of this grid. */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < numRows; ++row) {
			for (int col = 0; col < numCols; ++col) {
				sb.append(String.format("[%2d,%2d]", row, col));
				sb.append(col < numCols - 1 && hasEdge(vertex(col, row), EAST) ? "--" : "  ");
			}
			if (row < numRows - 1) {
				sb.append("\n");
				for (int col = 0; col < numCols; ++col) {
					sb.append(hasEdge(vertex(col, row), SOUTH) ? "   |     " : "         ");
				}
				sb.append("\n");
			}
		}
		sb.append(String.format("\n\n[%d cols, %d rows, %d edges]\n", numCols, numRows, numEdges()));
		return sb.toString();
	}
}