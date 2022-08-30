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

		var los = addVertex("Losheim");
		var wad = addVertex("Wadern");
		var mzg = addVertex("Merzig");
		var slz = addVertex("Schmelz");
		var heu = addVertex("Heusweiler");
		var sls = addVertex("Saarlouis");
		var sb = addVertex("Saarbrücken");
		var wnd = addVertex("St. Wendel");
		var nk = addVertex("Neunkirchen");
		var epp = addVertex("Eppelborn");
		var hom = addVertex("Homburg");
		var igb = addVertex("St. Ingbert");

		twoWay(los, mzg, 13.2);
		twoWay(los, wad, 15.0);
		twoWay(los, slz, 17.0);
		twoWay(mzg, sls, 23.0);
		twoWay(slz, wad, 18.0);
		twoWay(slz, epp, 13.0);
		twoWay(slz, heu, 17.5);
		twoWay(sls, slz, 25.0);
		twoWay(sls, sb, 25.0);
		twoWay(epp, nk, 21.5);
		twoWay(epp, sb, 31.0);
		twoWay(epp, heu, 10.0);
		twoWay(heu, sb, 14.5);
		twoWay(heu, nk, 24.0);
		twoWay(sb, igb, 11.0);
		twoWay(igb, hom, 22.0);
		twoWay(wnd, nk, 19.0);
		twoWay(wnd, hom, 29.0);
		twoWay(nk, hom, 19.0);
	}
}