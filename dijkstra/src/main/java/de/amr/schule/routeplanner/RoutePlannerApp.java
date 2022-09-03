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

import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.SaarlandMap;

/**
 * @author Armin Reichert
 */
public class RoutePlannerApp {

	public static void main(String[] args) {
		var map = new SaarlandMap();
		map.print(System.out, RoadMap::orderByCityName);
		printAllPaths(map, System.out);
	}

	private static void printAllPaths(RoadMap map, PrintStream out) {
		var routePlanner = new RoutePlanner();
		map.vertices(RoadMap::orderByCityName).forEach(start -> map.vertices(RoadMap::orderByCityName)//
				.forEach(goal -> {
					var route = routePlanner.computeRoute(map, start, goal);
					out.println("%s nach %s: %s".formatted(start.city.name(), goal.city.name(), route));
				}));
	}
}