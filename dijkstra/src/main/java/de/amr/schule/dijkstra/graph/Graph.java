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

package de.amr.schule.dijkstra.graph;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Graph {

	private final Map<String, Vertex> vertexByKey = new HashMap<>();

	public Optional<Vertex> findVertex(String key) {
		return Optional.ofNullable(vertexByKey.get(key));
	}

	public Stream<Vertex> vertices() {
		return vertexByKey.values().stream().sorted((v1, v2) -> v1.key.compareTo(v2.key));
	}

	public Stream<Edge> outgoingEdges(Vertex vertex) {
		return vertex.outgoingEdges.stream();
	}

	public Vertex vertex(String key) {
		if (vertexByKey.containsKey(key)) {
			return vertexByKey.get(key);
		}
		var vertex = new Vertex(key);
		vertexByKey.put(key, vertex);
		return vertex;
	}

	public void twoWay(Vertex either, Vertex other, double cost) {
		oneWay(either, other, cost);
		oneWay(other, either, cost);
	}

	public void oneWay(Vertex source, Vertex target, double cost) {
		source.outgoingEdges.add(new Edge(source, target, cost));
	}

	public void twoWay(String eitherKey, String otherKey, double cost) {
		var either = findVertex(eitherKey);
		if (either.isEmpty()) {
			throw new IllegalArgumentException("No vertex with key " + eitherKey);
		}
		var other = findVertex(otherKey);
		if (other.isEmpty()) {
			throw new IllegalArgumentException("No vertex with key " + otherKey);
		}
		twoWay(either.get(), other.get(), cost);
	}

	public void print(PrintStream out, boolean printEdges) {
		vertices().forEach(out::println);
		if (printEdges) {
			vertices().forEach(v -> {
				v.outgoingEdges.forEach(edge -> {
					out.println("Edge[%s -> %s %.1f km]".formatted(edge.from().key, edge.to().key, edge.cost()));
				});
			});
		}
	}
}