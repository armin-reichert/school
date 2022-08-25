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

package de.amr.schule.dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Armin Reichert
 *
 */
public class RouteMap {

	private RouteMap() {
	}

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	public static List<String> computeRoute(Graph map, String startCity, String goalCity) {
		var start = map.findVertex(startCity);
		var goal = map.findVertex(goalCity);
		if (start.isPresent() && goal.isPresent()) {
			computeShortestPaths(map, start.get());
			return buildRoute(start.get(), goal.get());
		}
		return List.of();
	}

	private static List<String> buildRoute(Vertex start, Vertex goal) {
		var route = new ArrayList<String>();
		var current = goal;
		while (current != null) {
			route.add(0, current.city + " " + current.dist + " km");
			current = current.parent;
		}
		return route;
	}

	private static void computeShortestPaths(Graph g, Vertex start) {
		LOGGER.info("Compute all paths from %s".formatted(start.city));

		PriorityQueue<Vertex> q = new PriorityQueue<>();
		boolean[] visited = new boolean[g.maxSize()];

		g.vertices().forEach(v -> {
			v.parent = null;
			if (v.equals(start)) {
				v.dist = 0;
				visited[v.index] = true;
				q.add(v);
				LOGGER.trace("%s visited".formatted(v.city));
			} else {
				v.dist = Double.MAX_VALUE;
			}
		});

		while (!q.isEmpty()) {
			var u = q.poll();
			for (var e : u.adjEdges) {
				var v = g.vertex(e.v).get();
				var tentativeDist = u.dist + e.weight;
				if (tentativeDist < v.dist) {
					v.dist = tentativeDist;
					v.parent = u;
					visited[v.index] = true;
					if (visited[v.index]) {
						// "decrease key"
						q.remove(v);
						q.add(v);
					} else {
						q.add(v);
					}
					LOGGER.trace("%s visited".formatted(v));
				}
			}
		}
	}
}