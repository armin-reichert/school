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

package de.amr.schule.routeplanner.model;

import de.amr.schule.routeplanner.graph.Vertex;

/**
 * @author Armin Reichert
 */
public class RoadMapPoint extends Vertex {

	public final City city;

	public RoadMapPoint(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	@Override
	public String key() {
		return city.name();
	}

	@Override
	public String toString() {
		var parentText = parent != null ? parent.key() : "none";
		var costText = cost == Float.MAX_VALUE ? "indefinite" : "%.1f".formatted(cost);
		var visitedText = visited ? "visited" : "unvisited";
		return "Vertex[city=%s, parent=%s, cost=%s, %s]".formatted(city, parentText, costText, visitedText);
	}
}