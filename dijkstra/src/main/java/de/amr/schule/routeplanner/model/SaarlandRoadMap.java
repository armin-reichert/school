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
 */
public class SaarlandRoadMap extends RoadMap {

	public SaarlandRoadMap() {
		var los = mapLocation("Losheim am See", 49.51f, 6.75f);
		var wad = mapLocation("Wadern", 49.534f, 6.89f);
		var mzg = mapLocation("Merzig", 49.449f, 6.643f);
		var bec = mapLocation("Beckingen", 49.391f, 6.705f);
		var dil = mapLocation("Dillingen", 49.353f, 6.714f);
		var slz = mapLocation("Schmelz", 49.432f, 6.843f);
		var leb = mapLocation("Lebach", 49.41f, 6.91f);
		var nal = mapLocation("Nalbach", 49.378f, 6.781f);
		var heu = mapLocation("Heusweiler", 49.338f, 6.929f);
		var sls = mapLocation("Saarlouis", 49.313f, 6.752f);
		var vlk = mapLocation("Völklingen", 49.255f, 6.859f);
		var sbr = mapLocation("Saarbrücken", 49.238f, 6.997f);
		var wnd = mapLocation("St. Wendel", 49.468f, 7.167f);
		var nkr = mapLocation("Neunkirchen", 49.349f, 7.177f);
		var epp = mapLocation("Eppelborn", 49.409f, 6.964f);
		var hom = mapLocation("Homburg", 49.329f, 7.339f);
		var igb = mapLocation("St. Ingbert", 49.278f, 7.112f);
		var tho = mapLocation("Tholey", 49.482f, 7.032f);

		street(bec, dil, 3.5f);
		street(epp, nkr, 21.5f);
		street(epp, sbr, 31.0f);
		street(epp, heu, 10.0f);
		street(epp, sls, 24.0f);
		street(dil, nal, 6.0f);
		street(dil, sls, 9.0f);
		street(heu, vlk, 12.0f);
		street(heu, sbr, 14.5f);
		street(heu, sls, 20.5f);
		street(heu, nkr, 24.0f);
		street(igb, hom, 22.0f);
		street(igb, nkr, 10.5f);
		street(leb, epp, 8.0f);
		street(leb, nal, 11.0f);
		street(leb, tho, 17f);
		street(leb, heu, 9.5f);
		street(los, mzg, 13.2f);
		street(los, wad, 15.0f);
		street(los, slz, 17.0f);
		street(mzg, bec, 12.5f);
		street(nkr, hom, 19.0f);
		street(sbr, igb, 11.0f);
		street(slz, nal, 10.5f);
		street(slz, wad, 18.0f);
		street(slz, leb, 5f);
		street(sls, nal, 11.0f);
		street(sls, vlk, 14.0f);
		street(vlk, sbr, 12.5f);
		street(tho, wnd, 13.0f);
		street(wad, tho, 19.5f);
		street(wnd, nkr, 19.0f);
		street(wnd, hom, 29.0f);
	}
}