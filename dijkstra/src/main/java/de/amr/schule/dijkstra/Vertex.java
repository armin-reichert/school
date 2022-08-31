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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Armin Reichert
 */
public class Vertex {

	public final Set<Edge> outgoingEdges = new LinkedHashSet<>();

	public final String key;
	public Vertex parent;
	public double cost;
	public boolean visited;

	public Vertex(String key) {
		this.key = key;
		this.parent = null;
		this.cost = Double.MAX_VALUE;
		this.visited = false;
	}

	@Override
	public String toString() {
		var parentText = parent != null ? parent.key : "none";
		var costText = cost == Double.MAX_VALUE ? "unknown" : "%.1f".formatted(cost);
		return "Vertex[key=%s, parent=%s, dist=%s, visited=%s]".formatted(key, parentText, costText, visited);
	}
}