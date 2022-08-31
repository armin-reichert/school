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

package de.amr.schule.dijkstra.model;

/**
 * In der vierten (dezimalen) Schreibweise liegt der Wertebereich der Breitengrade zwischen −90° und +90°, der
 * Wertebereich der Längengrade zwischen −180° und +180°. Die Himmelsrichtungen N-S und E-W werden hier weggelassen.
 * Nördliche Breiten werden positiv und südliche Breiten negativ angegeben. Östliche Längen sind positiv und westliche
 * Längen sind negativ. Um die Breiten und Längen nicht zu verwechseln, müssen sie mit „Breite (Latitude, Lat)“ und
 * „Länge (Longitude, Long)“ bezeichnet werden.
 * 
 * @author Armin Reichert
 */
public record City(String name, GeoCoordinate coord) {

	public City(String name, float latitude, float longitude) {
		this(name, new GeoCoordinate(latitude, longitude));
	}

	public City(String name) {
		this(name, new GeoCoordinate(0, 0));
	}
}