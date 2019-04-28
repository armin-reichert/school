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
		int numCols = 100, numRows = 1000;
		int numRuns = 100;
		long totalTime = 0;
		for (int i = 0; i < numRuns; ++i) {
			long time = System.nanoTime();
			Grid maze = maze(numCols, numRows, 0, 0, false);
			time = System.nanoTime() - time;
			totalTime += time;
			// System.out.println(String.format("Time: %d millis ", time / 1_000_000L));
			if (maze.numEdges() != maze.numCols * maze.numRows - 1) {
				throw new IllegalStateException();
			}
		}
		System.out.println(String.format("%,d cells. Average time: %.2f millis", numCols * numRows,
				(float) totalTime / numRuns / 1_000_000L));
	}

	/** Returns a maze of the given size starting generation at the given grid position. */
	public static Grid maze(int numCols, int numRows, int startCol, int startRow, boolean recursive) {
		Grid grid = new Grid(numCols, numRows);
		BitSet visited = new BitSet(numCols * numRows);
		if (recursive) {
			traverseRecursive(grid, grid.vertex(startCol, startRow), visited);
		}
		else {
			traverseUsingStack(grid, grid.vertex(startCol, startRow), visited);
		}
		return grid;
	}

	/** Recursive procedure traversing the grid and creating the maze (spanning tree). */
	private static void traverseRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (int dir = unvisitedDir(grid, v, visited); dir != -1; dir = unvisitedDir(grid, v, visited)) {
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
			int dir = unvisitedDir(grid, v, visited);
			if (dir != -1) {
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

	/** Returns a random direction to an unvisited neighbor or {@code -1}. */
	private static int unvisitedDir(Grid grid, int v, BitSet visited) {
		int[] candidates = new int[4];
		int numCandidates = 0;
		for (int dir : new int[] { Grid.NORTH, Grid.EAST, Grid.SOUTH, Grid.WEST }) {
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				candidates[numCandidates++] = dir;
			}
		}
		return numCandidates == 0 ? -1 : candidates[new Random().nextInt(numCandidates)];
	}
}