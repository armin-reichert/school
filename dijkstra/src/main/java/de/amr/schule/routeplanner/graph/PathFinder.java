/*
MIT License

Copyright (c) 2022 Armin Reichert

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package de.amr.schule.routeplanner.graph;

import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Armin Reichert
 *
 */
public class PathFinder {

	private PathFinder() {
	}

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	/**
	 * Computes the shortest path from the given start vertex to all vertices using the Dijkstra algorithm.
	 * 
	 * @param g     graph
	 * @param start start vertex
	 */
	public static void computeShortestPaths(Graph g, Vertex start) {
		LOGGER.info(() -> "Compute shortest paths starting at %s".formatted(start));
		var q = new PriorityQueue<Vertex>((v1, v2) -> Float.compare(v1.cost, v2.cost));
		g.vertices().forEach(v -> {
			v.parent = null;
			v.cost = Float.POSITIVE_INFINITY;
			v.visited = false;
		});
		start.cost = 0;
		q.add(start);
		while (!q.isEmpty()) {
			var u = q.poll(); // min cost vertex in queue
			if (!u.visited) {
				u.visited = true;
				LOGGER.trace(() -> "%s visited".formatted(u));
				for (var edge : u.outgoingEdgeList) {
					var v = edge.to();
					var altCost = u.cost + edge.cost();
					if (altCost < v.cost) {
						v.cost = altCost;
						v.parent = u;
						q.add(v);
					}
				}
			}
		}
	}
}