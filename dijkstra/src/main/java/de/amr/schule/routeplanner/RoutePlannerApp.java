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
import de.amr.schule.routeplanner.model.SaarlandMap;
import de.amr.schule.routeplanner.ui.RoutePlannerWindow;

/**
 * @author Armin Reichert
 */
public class RoutePlannerApp {

	public static void main(String[] args) {
		var map = new SaarlandMap();
		printAllRoutes(map, System.out);
		SwingUtilities.invokeLater(() -> createAndShowUI(map));
	}

	private static void createAndShowUI(SaarlandMap map) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		var window = new RoutePlannerWindow();
		window.setMap(map);
		var cityNames = map.cityNames().toArray(String[]::new);
		window.getComboStart().setModel(new DefaultComboBoxModel<>(cityNames));
		window.getComboGoal().setModel(new DefaultComboBoxModel<>(cityNames));
		window.getListRoute().setModel(new DefaultListModel<>());
	}

	private static void printAllRoutes(RoadMap map, PrintStream out) {
		map.print(out, RoadMap::orderByCityName);
		var routePlanner = new RoutePlanner();
		var cityNames = map.cityNames().toArray(String[]::new);
		for (var start : cityNames) {
			for (var goal : cityNames) {
				var route = routePlanner.computeRoute(map, start, goal);
				out.println("%s nach %s: %s".formatted(start, goal, route));
			}
		}
	}
}