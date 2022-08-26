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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Graph {

	private Vertex[] vertices;
	private Map<String, Integer> vertexIndexByKey = new HashMap<>();

	public Graph(int maxSize) {
		vertices = new Vertex[maxSize];
	}

	public int maxSize() {
		return vertices.length;
	}

	public Optional<Vertex> vertex(int index) {
		return Optional.ofNullable(vertices[index]);
	}

	public Optional<Vertex> findVertex(String key) {
		var index = vertexIndexByKey.get(key);
		return index == null ? Optional.empty() : vertex(index);
	}

	public Stream<Vertex> vertices() {
		return Stream.of(vertices).filter(Objects::nonNull);
	}

	public Stream<Edge> adjEdges(Vertex vertex) {
		return vertex.adjEdges.stream();
	}

	public void createVertex(int index, String key) {
		vertices[index] = new Vertex(index, key);
		vertexIndexByKey.put(key, index);
	}

	public void addUndirectedEdge(int either, int other, double distance) {
		addDirectedEdge(either, other, distance);
		addDirectedEdge(other, either, distance);
	}

	public void addDirectedEdge(int source, int target, double distance) {
		vertices[source].adjEdges.add(new Edge(source, target, distance));
	}

	public void print(PrintStream out, boolean printEdges) {
		vertices().forEach(v -> out.println("%s".formatted(v)));
		if (printEdges) {
			vertices().forEach(v -> {
				v.adjEdges.forEach(edge -> {
					var from = vertices[edge.from()];
					var to = vertices[edge.to()];
					out.println("Edge[%s -> %s %.1f km]".formatted(from.key, to.key, edge.cost()));
				});
			});
		}
	}
}