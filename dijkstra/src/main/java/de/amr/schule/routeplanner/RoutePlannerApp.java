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

package de.amr.schule.routeplanner;

import java.io.PrintStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.RoadMapReader;
import de.amr.schule.routeplanner.model.RoutePlanner;
import de.amr.schule.routeplanner.ui.RoutePlannerWindow;

/**
 * @author Armin Reichert
 */
public class RoutePlannerApp {

	public static void main(String[] args) {
//		var map = new SaarlandRoadMap();
		var map = new RoadMapReader().read(RoutePlannerApp.class.getResourceAsStream("/saarland.txt"));
//		printAllRoutes(map, System.out);
		SwingUtilities.invokeLater(() -> createAndShowUI(map));
	}

	private static void createAndShowUI(RoadMap map) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		var window = new RoutePlannerWindow();
		var locationNames = map.locationNames().toArray(String[]::new);
		window.getComboStart().setModel(new DefaultComboBoxModel<>(locationNames));
		window.getComboGoal().setModel(new DefaultComboBoxModel<>(locationNames));
		window.getListRoute().setModel(new DefaultListModel<>());
		window.setMap(map);
	}

	private static void printAllRoutes(RoadMap map, PrintStream out) {
		map.print(out, RoadMap::orderByName);
		var routePlanner = new RoutePlanner(map);
		var locationNames = map.locationNames().toArray(String[]::new);
		for (var start : locationNames) {
			for (var goal : locationNames) {
				var route = routePlanner.computeRoute(start, goal);
				var routeDesc = route.stream().map(point -> "%s %.1f km".formatted(point.name(), routePlanner.cost(point)))
						.toList();
				out.println("%s nach %s: %s".formatted(start, goal, routeDesc));
			}
		}
	}
}