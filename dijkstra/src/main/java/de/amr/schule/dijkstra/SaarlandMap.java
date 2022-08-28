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

/**
 * @author Armin Reichert
 *
 */
public class SaarlandMap extends Graph {

	public SaarlandMap() {
		super(20);

		addVertex("Losheim");
		addVertex("Merzig");
		addVertex("Saarlouis");
		addVertex("Saarbrücken");
		addVertex("Wadern");
		addVertex("St. Wendel");
		addVertex("Neunkirchen");
		addVertex("Eppelborn");
		addVertex("Homburg");
		addVertex("Schmelz");
		addVertex("St. Ingbert");

		addUndirectedEdge(0, 1, 12.5);
		addUndirectedEdge(0, 4, 15.0);
		addUndirectedEdge(0, 9, 11.0);
		addUndirectedEdge(1, 2, 22.0);
		addUndirectedEdge(2, 3, 25.0);
		addUndirectedEdge(3, 7, 20.0);
		addUndirectedEdge(3, 10, 15.0);
		addUndirectedEdge(4, 5, 30.0);
		addUndirectedEdge(5, 8, 30.0);
		addUndirectedEdge(6, 7, 25.0);
		addUndirectedEdge(6, 8, 20.0);
		addUndirectedEdge(6, 10, 15.0);
		addUndirectedEdge(7, 9, 15.0);
	}
}