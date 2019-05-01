package de.amr.maze.simple;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Random;

/**
 * Creates a maze from a grid graph using <em>randomized depth-first search</em>.
 * 
 * @author Armin Reichert
 */
public class RandomizedDFS {

	public static void main(String[] args) {
		// Using recursive procedure:
		{
			// Grid maze = maze(10, 10, 0, 0, true);
			// System.out.println(maze);
		}
		// Using non-recursive procedure:
		int numCols = 1000, numRows = 1000;
		int numRuns = 40;
		long totalTime = 0;
		for (int i = 0; i < numRuns; ++i) {
			long time = System.nanoTime();
			Grid maze = maze(numCols, numRows, 0, 0, false);
			time = System.nanoTime() - time;
			totalTime += time;
			if (maze.numEdges() != maze.numCols * maze.numRows - 1) {
				throw new IllegalStateException();
			}
		}
		System.out.println(String.format("%d runs, %,d cells each. Average time: %.2f millis", numRuns,
				numCols * numRows, (float) totalTime / numRuns / 1_000_000L));
	}

	/** Returns a maze of the given size starting generation at the given grid position. */
	public static Grid maze(int numCols, int numRows, int startCol, int startRow, boolean recursive) {
		Grid grid = new Grid(numCols, numRows);
		int startVertex = grid.vertex(startCol, startRow);
		BitSet visited = new BitSet(numCols * numRows);
		if (recursive) {
			traverseRecursive(grid, startVertex, visited);
		}
		else {
			traverseUsingStack(grid, startVertex, visited);
		}
		return grid;
	}

	/** Recursive procedure traversing the grid and creating the maze (spanning tree). */
	private static void traverseRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v, visited)) {
			grid.addEdge(v, dir);
			traverseRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}

	/** Non-recursive procedure traversing the grid and creating the maze (spanning tree). */
	private static void traverseUsingStack(Grid grid, int v, BitSet visited) {
		Deque<Integer> stack = new ArrayDeque<>();
		visited.set(v);
		stack.push(v);
		while (!stack.isEmpty()) {
			Direction dir = unvisitedDir(grid, v, visited);
			if (dir != null) {
				int neighbor = grid.neighbor(v, dir);
				grid.addEdge(v, dir);
				visited.set(neighbor);
				stack.push(neighbor);
				v = neighbor;
			}
			else {
				v = stack.pop();
			}
		}
	}

	/**
	 * Returns a random direction to an unvisited neighbor or {@code null} if no such direction exists.
	 */
	private static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		Direction[] candidates = new Direction[4];
		int numCandidates = 0;
		for (Direction dir : Direction.values()) {
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				candidates[numCandidates++] = dir;
			}
		}
		return numCandidates == 0 ? null : candidates[new Random().nextInt(numCandidates)];
	}
}