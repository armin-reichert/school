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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteMapApp {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	private static final Graph MAP_SAARLAND = createSaarlandMap();

	public static void main(String[] args) {
		System.out.println(RouteMap.computeRoute(MAP_SAARLAND, "Losheim", "Saarbrücken"));
		System.out.println(RouteMap.computeRoute(MAP_SAARLAND, "Losheim", "Saarlouis"));
		System.out.println(RouteMap.computeRoute(MAP_SAARLAND, "Saarlouis", "Wadern"));
	}

	private static Graph createSaarlandMap() {
		var g = new Graph(100);

		g.createVertex(0, "Losheim");
		g.createVertex(1, "Merzig");
		g.createVertex(2, "Saarlouis");
		g.createVertex(3, "Saarbrücken");
		g.createVertex(4, "Wadern");
		g.createVertex(5, "St. Wendel");
		g.createVertex(6, "Neunkirchen");
		g.createVertex(7, "Eppelborn");
		g.createVertex(8, "Homburg");

		g.addUndirectedEdge(0, 1, 12.5);
		g.addUndirectedEdge(0, 4, 15.0);
		g.addUndirectedEdge(0, 7, 26.0);
		g.addUndirectedEdge(1, 2, 22.0);
		g.addUndirectedEdge(2, 3, 25.0);

		g.print(System.out, true);
		return g;
	}
}