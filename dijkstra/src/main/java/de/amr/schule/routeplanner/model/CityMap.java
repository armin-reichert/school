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

import java.io.PrintStream;
import java.util.Comparator;
import java.util.stream.Stream;

import de.amr.schule.routeplanner.graph.Graph;
import de.amr.schule.routeplanner.graph.Vertex;

/**
 * @author Armin Reichert
 */
public class CityMap extends Graph {

	public static int orderByCityName(CityMapVertex v1, CityMapVertex v2) {
		return v1.city.name().compareTo(v2.city.name());
	}

	public CityMapVertex vertex(City city) {
		var v = vertex(city.name());
		if (v.isPresent()) {
			return (CityMapVertex) v.get();
		}
		var vertex = new CityMapVertex(city);
		addVertex(city.name(), vertex);
		return vertex;
	}

	public void street(City eitherCity, City otherCity, float cost) {
		addEdge(vertex(eitherCity), vertex(otherCity), cost);
	}

	public Stream<CityMapVertex> vertices(Comparator<CityMapVertex> order) {
		return vertices().map(CityMapVertex.class::cast).sorted(order);
	}

	public void print(PrintStream out, Comparator<CityMapVertex> order) {
		vertices(order).forEach(out::println);
		vertices(order).flatMap(Vertex::outgoingEdges)
				.map(edge -> "Edge[%s -> %s %.1f km]".formatted(edge.from().key(), edge.to().key(), edge.cost()))
				.forEach(out::println);
	}
}