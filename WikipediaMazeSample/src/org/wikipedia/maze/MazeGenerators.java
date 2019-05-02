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
		RANDOM_DFS_RECURSIVE, RANDOM_DFS_NONRECURSIVE, RANDOM_BFS
	}

	public static void main(String[] args) {
		System.out.println(maze(10, 10, 0, 0, Algorithm.RANDOM_BFS));
		System.out.println(maze(10, 10, 0, 0, Algorithm.RANDOM_DFS_NONRECURSIVE));
		System.out.println(maze(10, 10, 0, 0, Algorithm.RANDOM_DFS_RECURSIVE));
	}

	/** Returns a maze of the given size starting generation at the given grid position. */
	public static Grid maze(int numCols, int numRows, int startCol, int startRow,
			Algorithm algorithm) {
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
		default:
			break;
		}
		return grid;
	}

	private static void randomDFSRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v,
				visited)) {
			grid.addEdge(v, dir);
			randomDFSRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}

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

	/**
	 * Returns a random direction to an unvisited neighbor or {@code null} if no such direction
	 * exists.
	 */
	private static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		List<Direction> unvisitedDirections = unvisitedDirections(grid, v, visited);
		return unvisitedDirections.isEmpty() ? null : unvisitedDirections.get(0);
	}
}