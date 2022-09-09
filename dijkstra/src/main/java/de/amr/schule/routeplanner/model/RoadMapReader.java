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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Armin Reichert
 */
public class RoadMapReader {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	static final int MODE_SKIP = 0;
	static final int MODE_LOCATIONS = 1;
	static final int MODE_ROADS = 2;

	private RoadMap map;
	private Map<String, RoadMapLocation> locationByName = new HashMap<>();
	private int mode = MODE_SKIP;
	private int lineNumber;

	public RoadMap read(InputStream is) {
		map = new RoadMap();
		lineNumber = 0;
		try (BufferedReader rdr = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			rdr.lines().forEach(line -> {
				++lineNumber;
				if (line.startsWith("#")) {
					// skip comment line
				} else {
					processLine(line);
				}
			});
		} catch (Exception x) {
			x.printStackTrace();
		}
		return map;
	}

	private void processLine(String line) {
		if (".locations".equals(line)) {
			mode = MODE_LOCATIONS;
		} else if (".roads".equals(line)) {
			mode = MODE_ROADS;
		} else if (!line.isBlank()) {
			switch (mode) {
			case MODE_LOCATIONS -> {
				parseLocation(line);
			}
			case MODE_ROADS -> {
				parseRoad(line);
			}
			default -> {
			}
			}
		}
	}

	private void parseLocation(String line) {
		// key, location name, latitude, longitude
		String[] tokens = line.split(",");
		if (tokens.length != 4) {
			LOGGER.error("Line %d: '%s': Invalid location spec".formatted(lineNumber, line));
			return;
		}
		String key = tokens[0];
		String name = tokens[1];
		float latitude;
		try {
			latitude = Float.parseFloat(tokens[2]);
		} catch (NumberFormatException x) {
			LOGGER.error("Line %d: '%s': Invalid latitude: '%s'".formatted(lineNumber, line, tokens[2]));
			return;
		}
		float longitude = Float.parseFloat(tokens[3]);
		try {
			longitude = Float.parseFloat(tokens[3]);
		} catch (NumberFormatException x) {
			LOGGER.error("Line %d: '%s': Invalid longitude: '%s'".formatted(lineNumber, line, tokens[3]));
			return;
		}
		locationByName.put(key, map.getOrCreateLocation(name, latitude, longitude));
	}

	private void parseRoad(String line) {
		// from to cost
		String[] tokens = line.split(",");
		if (tokens.length != 3) {
			LOGGER.error("Line %d: '%s': Invalid road spec".formatted(lineNumber, line));
			return;
		}
		var fromLocation = locationByName.get(tokens[0].trim());
		if (fromLocation == null) {
			LOGGER.error("Line %d: '%s': Invalid location: '%s'".formatted(lineNumber, line, tokens[0]));
			return;
		}
		var toLocation = locationByName.get(tokens[1].trim());
		if (toLocation == null) {
			LOGGER.error("Line %d: '%s': Invalid location: '%s'".formatted(lineNumber, line, tokens[1]));
			return;
		}
		float dist;
		try {
			dist = Float.parseFloat(tokens[2]);
		} catch (NumberFormatException x) {
			LOGGER.error("Line %d: '%s': Invalid distance: '%s'".formatted(lineNumber, line, tokens[2]));
			return;
		}
		map.addEdge(fromLocation, toLocation, dist);
	}
}