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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Graph {

	private final List<Vertex> vertices;
	private final Map<String, Integer> vertexIndexByKey = new HashMap<>();

	public Graph(int initialCapacity) {
		vertices = new ArrayList<>(initialCapacity);
	}

	public Optional<Vertex> vertex(int index) {
		return Optional.ofNullable(vertices.get(index));
	}

	public Optional<Vertex> findVertex(String key) {
		var index = vertexIndexByKey.get(key);
		return index == null ? Optional.empty() : vertex(index);
	}

	public Stream<Vertex> vertices() {
		return vertices.stream();
	}

	public Stream<Edge> outgoingEdges(Vertex vertex) {
		return vertex.outgoingEdges.stream();
	}

	public Vertex addVertex(String key) {
		var vertex = new Vertex(vertices.size(), key);
		vertices.add(vertex);
		vertexIndexByKey.put(key, vertex.index);
		return vertex;
	}

	public void twoWay(Vertex either, Vertex other, double cost) {
		oneWay(either, other, cost);
		oneWay(other, either, cost);
	}

	public void oneWay(Vertex source, Vertex target, double cost) {
		source.outgoingEdges.add(new Edge(source.index, target.index, cost));
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
					var from = vertices.get(edge.from());
					var to = vertices.get(edge.to());
					out.println("Edge[%s -> %s %.1f km]".formatted(from.key, to.key, edge.cost()));
				});
			});
		}
	}
}