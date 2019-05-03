package org.wikipedia.maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 * Maze generators.
 * 
 * @author Armin Reichert
 */
public class MazeGenerators {

	public enum Algorithm {
		RANDOM_DFS_RECURSIVE, RANDOM_DFS_NONRECURSIVE, RANDOM_BFS, RECURSIVE_DIVISION, ALDOUS_BRODER
	}

	public static void main(String[] args) {
		for (Algorithm algorithm : Algorithm.values()) {
			System.out.println(algorithm + "\n" + maze(10, 10, 0, 0, algorithm));
		}
	}

	/** Returns a maze of the given size starting generation at the given grid position. */
	public static Grid maze(int numCols, int numRows, int startCol, int startRow, Algorithm algorithm) {
		Grid grid = new Grid(numCols, numRows);
		int startVertex = grid.vertex(startCol, startRow);
		BitSet visited = new BitSet(numCols * numRows);
		switch (algorithm) {
		case RANDOM_BFS:
			randomBFS(grid, startVertex, visited);
			break;
		case RANDOM_DFS_NONRECURSIVE:
			randomDFSNonrecursive(grid, startVertex, visited);
			break;
		case RANDOM_DFS_RECURSIVE:
			randomDFSRecursive(grid, startVertex, visited);
			break;
		case RECURSIVE_DIVISION:
			recursiveDivision(grid);
			break;
		case ALDOUS_BRODER:
			aldousBroder(grid, startVertex, visited);
			break;
		default:
			break;
		}
		return grid;
	}

	// Randomized Depth-First Search (recursive)

	private static void randomDFSRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v, visited)) {
			grid.addEdge(v, dir);
			randomDFSRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}

	// Randomized Depth-First Search (non-recursive)

	private static void randomDFSNonrecursive(Grid grid, int v, BitSet visited) {
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

	// Randomized Breadth-First Search

	private static void randomBFS(Grid grid, int v, BitSet visited) {
		List<Integer> frontier = new ArrayList<>();
		visited.set(v);
		frontier.add(v);
		Random rnd = new Random();
		while (!frontier.isEmpty()) {
			v = frontier.remove(rnd.nextInt(frontier.size()));
			for (Direction dir : unvisitedDirections(grid, v, visited)) {
				int neighbor = grid.neighbor(v, dir);
				grid.addEdge(v, dir);
				visited.set(neighbor);
				frontier.add(neighbor);
			}
		}
	}

	// Recursive division

	public static void recursiveDivision(Grid grid) {
		for (int row = 0; row < grid.numRows; ++row) {
			for (int col = 0; col < grid.numCols; ++col) {
				int vertex = grid.vertex(col, row);
				if (row > 0) {
					grid.addEdge(vertex, Direction.NORTH);
				}
				if (col > 0) {
					grid.addEdge(vertex, Direction.WEST);
				}
			}
		}
		divide(grid, new Random(), 0, 0, grid.numCols, grid.numRows);
	}

	private static void divide(Grid grid, Random rnd, int x0, int y0, int w, int h) {
		if (w <= 1 && h <= 1) {
			return;
		}
		if (w < h || (w == h && rnd.nextBoolean())) {
			// Build "horizontal wall" at random y from [y0 + 1, y0 + h - 1], keep random door
			int y = y0 + 1 + rnd.nextInt(h - 1);
			int door = x0 + rnd.nextInt(w);
			for (int x = x0; x < x0 + w; ++x) {
				if (x != door) {
					grid.removeEdge(grid.vertex(x, y - 1), Direction.SOUTH);
				}
			}
			divide(grid, rnd, x0, y0, w, y - y0);
			divide(grid, rnd, x0, y, w, h - (y - y0));
		}
		else {
			// Build "vertical wall" at random x from [x0 + 1, x0 + w - 1], keep random door
			int x = x0 + 1 + rnd.nextInt(w - 1);
			int door = y0 + rnd.nextInt(h);
			for (int y = y0; y < y0 + h; ++y) {
				if (y != door) {
					grid.removeEdge(grid.vertex(x - 1, y), Direction.EAST);
				}
			}
			divide(grid, rnd, x0, y0, x - x0, h);
			divide(grid, rnd, x, y0, w - (x - x0), h);
		}
	}

	// Aldous/Broder algorithm

	private static void aldousBroder(Grid grid, int v, BitSet visited) {
		visited.set(v);
		while (visited.cardinality() < grid.numCols * grid.numRows) {
			Direction dir = Direction.values()[new Random().nextInt(4)];
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				grid.addEdge(v, dir);
				visited.set(neighbor);
			}
			v = neighbor;
		}
	}

	/** Returns directions to unvisited neighbors of {@code v} in random order. */
	private static List<Direction> unvisitedDirections(Grid grid, int v, BitSet visited) {
		List<Direction> candidates = new ArrayList<>(4);
		for (Direction dir : Direction.values()) {
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				candidates.add(dir);
			}
		}
		Collections.shuffle(candidates);
		return candidates;
	}

	/** Returns direction to some unvisited neighbor or {@code null} if no such neighbor exists. */
	private static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		List<Direction> unvisitedDirections = unvisitedDirections(grid, v, visited);
		return unvisitedDirections.isEmpty() ? null : unvisitedDirections.get(0);
	}
}