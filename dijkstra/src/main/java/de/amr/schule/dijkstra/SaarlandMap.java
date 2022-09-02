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

import de.amr.schule.dijkstra.graph.Graph;
import de.amr.schule.dijkstra.model.City;

/**
 * @author Armin Reichert
 *
 */
public class SaarlandMap extends Graph {

	public SaarlandMap() {

		var los = new City("Losheim am See");
		var wad = new City("Wadern");
		var mzg = new City("Merzig");
		var slz = new City("Schmelz");
		var heu = new City("Heusweiler");
		var sls = new City("Saarlouis");
		var sb = new City("Saarbrücken");
		var wnd = new City("St. Wendel");
		var nk = new City("Neunkirchen");
		var epp = new City("Eppelborn");
		var hom = new City("Homburg");
		var igb = new City("St. Ingbert");

		twoWay(los, mzg, 13.2f);
		twoWay(los, wad, 15.0f);
		twoWay(los, slz, 17.0f);
		twoWay(mzg, sls, 23.0f);
		twoWay(slz, wad, 18.0f);
		twoWay(slz, epp, 13.0f);
		twoWay(slz, heu, 17.5f);
		twoWay(sls, slz, 25.0f);
		twoWay(sls, sb, 25.0f);
		twoWay(epp, sls, 24.0f);
		twoWay(epp, nk, 21.5f);
		twoWay(epp, sb, 31.0f);
		twoWay(epp, heu, 10.0f);
		twoWay(heu, sb, 14.5f);
		twoWay(heu, nk, 24.0f);
		twoWay(heu, hom, 37.5f);
		twoWay(sb, igb, 11.0f);
		twoWay(igb, hom, 22.0f);
		twoWay(wnd, nk, 19.0f);
		twoWay(wnd, hom, 29.0f);
		twoWay(nk, hom, 19.0f);
	}
}