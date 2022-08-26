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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Armin Reichert
 */
public class RoutePlanner {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	private static final Comparator<Vertex> COMPARE_BY_COST = (v1, v2) -> Double.compare(v1.cost, v2.cost);

	private Vertex startVertex;

	public void setStart(Graph map, Vertex startVertex) {
		if (startVertex != this.startVertex) {
			this.startVertex = startVertex;
			dijkstraAlgorithm(map, COMPARE_BY_COST, startVertex);
		}
	}

	public List<String> computeRoute(Graph map, String startCity, String goalCity) {
		var start = map.findVertex(startCity);
		var goal = map.findVertex(goalCity);
		if (start.isPresent() && goal.isPresent()) {
			setStart(map, start.get());
			return buildRoute(goal.get());
		}
		return List.of();
	}

	private List<String> buildRoute(Vertex goal) {
		var route = new LinkedList<String>();
		for (Vertex v = goal; v != null; v = v.parent) {
			route.addFirst(v.city + " " + v.cost + " km");
		}
		return route;
	}

	private static void dijkstraAlgorithm(Graph g, Comparator<Vertex> vertexComparator, Vertex start) {
		var q = new PriorityQueue<Vertex>(vertexComparator);

		g.vertices().forEach(v -> {
			v.parent = null;
			v.cost = Double.MAX_VALUE;
			v.visited = false;
		});

		LOGGER.info(() -> "Compute all paths from %s".formatted(start));

		start.cost = 0.0;
		start.visited = true;
		q.add(start);
		LOGGER.trace(() -> "%s visited".formatted(start));

		while (!q.isEmpty()) {
			var u = q.poll(); // min cost vertex in queue
			for (var edge : u.adjEdges) {
				g.vertex(edge.to()).ifPresent(v -> {
					var altCost = u.cost + edge.cost();
					if (altCost < v.cost) {
						v.cost = altCost;
						v.parent = u;
						if (v.visited) { // "decrease key"
							q.remove(v);
							q.add(v);
						} else {
							v.visited = true;
							q.add(v);
						}
						LOGGER.trace(() -> "%s visited".formatted(v));
					}
				});
			}
		}
	}
}