package org.wikipedia.maze;

public class MazeTest {

	public static void main(String[] args) {
		// Using non-recursive procedure:
		int numCols = 1000, numRows = 1000;
		int numRuns = 40;
		long totalTime = 0;
		for (int i = 0; i < numRuns; ++i) {
			long time = System.nanoTime();
			Grid maze = RandomizedDFS.maze(numCols, numRows, 0, 0, false);
			time = System.nanoTime() - time;
			totalTime += time;
			if (maze.numEdges() != maze.numCols * maze.numRows - 1) {
				throw new IllegalStateException();
			}
		}
		System.out.println(String.format("%d runs, %,d cells each. Average time: %.2f millis", numRuns,
				numCols * numRows, (float) totalTime / numRuns / 1_000_000L));
	}
}
