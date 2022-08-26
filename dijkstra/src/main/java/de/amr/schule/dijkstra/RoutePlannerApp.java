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

public class RoutePlannerApp {

	private static final Graph MAP_SAARLAND = new SaarlandMap();
	private static final RoutePlanner PLANNER = new RoutePlanner();

	public static void main(String[] args) {
		MAP_SAARLAND.print(System.out, true);
		route("Losheim", "Saarbrücken");
		route("Losheim", "Saarlouis");
		route("Saarlouis", "Wadern");
		route("Losheim", "Losheim");
		route("Schmelz", "Homburg");
		route("Saarlouis", "Homburg");
		route("Wadern", "Saarbrücken");
	}

	private static void route(String fromCity, String toCity) {
		var route = PLANNER.computeRoute(MAP_SAARLAND, fromCity, toCity);
		System.out.println("Route von %s nach %s: %s".formatted(fromCity, toCity, route));
	}
}