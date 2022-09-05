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

import java.util.LinkedList;
import java.util.List;

import de.amr.schule.routeplanner.graph.Vertex;
import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.RoadMapPoint;

/**
 * @author Armin Reichert
 * 
 * @see https://cs.au.dk/~gerth/papers/fun22.pdf
 */
public class RoutePlanner {

	private Vertex currentStartVertex;

	public List<RoadMapPoint> computeRoute(RoadMap map, String startCity, String goalCity) {
		var start = map.vertex(startCity).orElse(null);
		var goal = map.vertex(goalCity).orElse(null);
		return computeRoute(map, (RoadMapPoint) start, (RoadMapPoint) goal);
	}

	public List<RoadMapPoint> computeRoute(RoadMap map, RoadMapPoint start, RoadMapPoint goal) {
		if (start == null || goal == null) {
			return List.of();
		}
		if (start != currentStartVertex) {
			currentStartVertex = start;
			map.computeShortestPaths(start);
		}
		return buildRoute(goal);
	}

	private List<RoadMapPoint> buildRoute(RoadMapPoint goal) {
		var route = new LinkedList<RoadMapPoint>();
		for (RoadMapPoint v = goal; v != null; v = (RoadMapPoint) v.parent) {
			route.addFirst(v);
		}
		return route;
	}
}