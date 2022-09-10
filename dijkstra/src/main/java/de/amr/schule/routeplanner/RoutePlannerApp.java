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

import java.awt.event.ActionEvent;
import java.util.MissingResourceException;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.RoadMapReader;
import de.amr.schule.routeplanner.model.RoutePlanner;
import de.amr.schule.routeplanner.ui.RoutePlannerWindow;

/**
 * @author Armin Reichert
 */
public class RoutePlannerApp {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	private static final String MAP_FILE = "saarland.txt";

	public static void main(String[] args) {
		var is = RoutePlannerApp.class.getResourceAsStream("/" + MAP_FILE);
		if (is == null) {
			throw new MissingResourceException("Could not read map from file '%s'".formatted(MAP_FILE),
					RoutePlannerApp.class.getName(), MAP_FILE);
		}
		var map = RoadMapReader.readMap(is);
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
		window.comboStart().setModel(new DefaultComboBoxModel<>(locationNames));
		window.comboGoal().setModel(new DefaultComboBoxModel<>(locationNames));
		window.listRoute().setModel(new DefaultListModel<>());
		window.setMap(map);

		window.listRoute().getActionMap().put("printAll", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printAllRoutes(map);
			}
		});
		window.listRoute().getInputMap().put(KeyStroke.getKeyStroke('p'), "printAll");
	}

	private static void printAllRoutes(RoadMap map) {
		map.print(LOGGER::info, RoadMap::orderByName);
		var routePlanner = new RoutePlanner(map);
		var locationNames = map.locationNames().toArray(String[]::new);
		for (var start : locationNames) {
			for (var goal : locationNames) {
				var route = routePlanner.computeRoute(start, goal);
				var routeDesc = route.stream().map(point -> "%s %.1f km".formatted(point.name(), routePlanner.cost(point)))
						.toList();
				LOGGER.info(() -> "%s nach %s: %s".formatted(start, goal, routeDesc));
			}
		}
	}
}