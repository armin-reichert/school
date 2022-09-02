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

import java.io.PrintStream;

import de.amr.schule.routeplanner.model.CityMap;
import de.amr.schule.routeplanner.model.CityMapVertex;

public class RoutePlannerApp {

	public static void main(String[] args) {
		var map = new SaarlandMap();
		map.print(System.out, true, RoutePlannerApp::sortedByCityName);
		printAllPaths(map, System.out);
	}

	private static int sortedByCityName(CityMapVertex v1, CityMapVertex v2) {
		return v1.city.name().compareTo(v2.city.name());
	}

	private static void printAllPaths(CityMap map, PrintStream out) {
		var routePlanner = new RoutePlanner();
		map.vertices(RoutePlannerApp::sortedByCityName)
				.forEach(start -> map.vertices(RoutePlannerApp::sortedByCityName).forEach(goal -> {
					var startVertex = (CityMapVertex) start;
					var goalVertex = (CityMapVertex) goal;
					out.println("%s nach %s: %s".formatted(startVertex.city.name(), goalVertex.city.name(),
							routePlanner.computeRoute(map, startVertex, goalVertex)));
				}));
	}
}