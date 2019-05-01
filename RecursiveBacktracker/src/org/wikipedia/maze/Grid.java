package org.wikipedia.maze;

import java.util.BitSet;

/**
 * Grid graph implementation. Vertices are represented by integers, edge set by a single bit-set.
 */
public class Grid {

	/** Index representing no vertex. */
	public static final int NO_VERTEX = -1;

	public final int numCols;
	public final int numRows;

	private BitSet edges;

	/* The bit-set index of the edge leaving vertex {@code v} towards direction {@code dir}. */
	private int bit(int v, Direction dir) {
		return 4 * v + dir.ordinal();
	}

	/** Creates an empty grid of the given size. */
	public Grid(int numCols, int numRows) {
		this.numCols = numCols;
		this.numRows = numRows;
		this.edges = new BitSet(numCols * numRows * 4);
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

	/** Adds the (undirected) edge from vertex {@code v} towards direction {@code dir}. */
	public void addEdge(int v, Direction dir) {
		edges.set(bit(v, dir));
		edges.set(bit(neighbor(v, dir), dir.opposite()));
	}

	/** Tells if the edge from vertex {@code v} towards direction {@code dir} exists. */
	public boolean hasEdge(int v, Direction dir) {
		return edges.get(bit(v, dir));
	}

	/** Returns the neighbor of vertex {@code v} towards direction {@code dir} or {@link #NO_VERTEX}. */
	public int neighbor(int v, Direction dir) {
		int col = col(v) + dir.x, row = row(v) + dir.y;
		return col >= 0 && col < numCols && row >= 0 && row < numRows ? vertex(col, row) : NO_VERTEX;
	}

	/** Returns a textual representation of this grid. */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < numRows; ++row) {
			for (int col = 0; col < numCols; ++col) {
				sb.append(String.format("[%2d,%2d]", row, col));
				sb.append(col < numCols - 1 && hasEdge(vertex(col, row), Direction.EAST) ? "--" : "  ");
			}
			if (row < numRows - 1) {
				sb.append("\n");
				for (int col = 0; col < numCols; ++col) {
					sb.append(hasEdge(vertex(col, row), Direction.SOUTH) ? "   |     " : "         ");
				}
				sb.append("\n");
			}
		}
		sb.append(String.format("\n\n[%d cols, %d rows, %d edges]\n", numCols, numRows, numEdges()));
		return sb.toString();
	}
}