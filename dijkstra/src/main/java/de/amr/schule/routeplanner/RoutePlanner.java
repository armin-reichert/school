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

package de.amr.schule.routeplanner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.amr.schule.routeplanner.graph.Graph;
import de.amr.schule.routeplanner.graph.Vertex;
import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.RoadMapLocation;

/**
 * @author Armin Reichert
 * 
 * @see https://cs.au.dk/~gerth/papers/fun22.pdf
 */
public class RoutePlanner {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	private final Map<Vertex, Float> cost = new HashMap<>();
	private final Map<Vertex, Vertex> parent = new HashMap<>();
	private final PriorityQueue<Vertex> q = new PriorityQueue<>((v1, v2) -> Float.compare(cost.get(v1), cost.get(v2)));
	private Vertex currentStartVertex;

	public float cost(Vertex v) {
		Float value = cost.get(v);
		return value == null ? Float.POSITIVE_INFINITY : value;
	}

	public List<RoadMapLocation> computeRoute(RoadMap map, String startCity, String goalCity) {
		var start = map.vertex(startCity).orElse(null);
		var goal = map.vertex(goalCity).orElse(null);
		return computeRoute(map, (RoadMapLocation) start, (RoadMapLocation) goal);
	}

	public List<RoadMapLocation> computeRoute(RoadMap map, RoadMapLocation start, RoadMapLocation goal) {
		if (start == null || goal == null) {
			return List.of();
		}
		if (start != currentStartVertex) {
			currentStartVertex = start;
			computeShortestPaths(map, start);
		}
		return buildRoute(goal);
	}

	private List<RoadMapLocation> buildRoute(RoadMapLocation goal) {
		var route = new LinkedList<RoadMapLocation>();
		for (RoadMapLocation v = goal; v != null; v = (RoadMapLocation) parent.get(v)) {
			route.addFirst(v);
		}
		return route;
	}

	/**
	 * Computes the shortest path from the given start vertex to all vertices using the Dijkstra algorithm.
	 * 
	 * @param g     graph
	 * @param start start vertex
	 */
	private void computeShortestPaths(Graph g, Vertex start) {
		q.clear();
		cost.clear();
		parent.clear();
		LOGGER.info(() -> "Compute shortest paths starting at %s".formatted(start));
		g.vertices().forEach(v -> {
			parent.put(v, null);
			cost.put(v, Float.POSITIVE_INFINITY);
		});
		cost.put(start, 0f);
		q.add(start);
		while (!q.isEmpty()) {
			var u = q.poll(); // extract min cost vertex from queue
			u.outgoingEdges().forEach(edge -> {
				var altCost = cost.get(u) + edge.cost();
				var v = edge.to();
				if (altCost < cost.get(v)) {
					cost.put(v, altCost);
					parent.put(v, u);
					q.remove(v); // decrease key is not supported by Java priority queue
					q.add(v);
				}
			});
		}
	}
}