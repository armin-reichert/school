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

/**
 * @author Armin Reichert
 *
 */
public class SaarlandMap extends RoadMap {

	public SaarlandMap() {
		var los = new City("Losheim am See", 49.51f, 6.75f);
		var wad = new City("Wadern", 49.534f, 6.89f);
		var mzg = new City("Merzig", 49.449f, 6.643f);
		var slz = new City("Schmelz", 49.432f, 6.843f);
		var heu = new City("Heusweiler", 49.338f, 6.929f);
		var sls = new City("Saarlouis", 49.313f, 6.752f);
		var sb = new City("Saarbrücken", 49.238f, 6.997f);
		var wnd = new City("St. Wendel", 49.468f, 7.167f);
		var nk = new City("Neunkirchen", 49.349f, 7.177f);
		var epp = new City("Eppelborn", 49.409f, 6.964f);
		var hom = new City("Homburg", 49.329f, 7.339f);
		var igb = new City("St. Ingbert", 49.278f, 7.112f);

		street(los, mzg, 13.2f);
		street(los, wad, 15.0f);
		street(los, slz, 17.0f);
		street(mzg, sls, 23.0f);
		street(slz, wad, 18.0f);
		street(slz, epp, 13.0f);
		street(slz, heu, 17.5f);
		street(sls, slz, 25.0f);
		street(sls, sb, 25.0f);
		street(epp, sls, 24.0f);
		street(epp, nk, 21.5f);
		street(epp, sb, 31.0f);
		street(epp, heu, 10.0f);
		street(heu, sb, 14.5f);
		street(heu, nk, 24.0f);
		street(heu, hom, 37.5f);
		street(sb, igb, 11.0f);
		street(igb, hom, 22.0f);
		street(wnd, nk, 19.0f);
		street(wnd, hom, 29.0f);
		street(nk, hom, 19.0f);
	}
}