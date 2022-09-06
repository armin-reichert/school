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

package de.amr.schule.routeplanner.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.amr.schule.routeplanner.RoutePlanner;
import de.amr.schule.routeplanner.model.GeoCoord;
import de.amr.schule.routeplanner.model.RoadMap;
import de.amr.schule.routeplanner.model.RoadMapLocation;
import net.miginfocom.swing.MigLayout;

/**
 * @author Armin Reichert
 */
public class RoutePlannerWindow extends JFrame {

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	private static final float MAP_LATITUDE_TOP_LEFT = 49.639407f;
	private static final float MAP_LATITUDE_BOTTOM_RIGHT = 49.111948f;
	private static final float MAP_LONGITUDE_TOP_LEFT = 6.356f;
	private static final float MAP_LONGITUDE_BOTTOM_RIGHT = 7.402f;

	private class ComputeRouteAction extends AbstractAction {

		public ComputeRouteAction() {
			putValue(NAME, "Route");
			putValue(SHORT_DESCRIPTION, "Computes the best route");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String startCity = (String) getComboStart().getSelectedItem();
			String goalCity = (String) getComboGoal().getSelectedItem();
			var route = routePlanner.computeRoute(startCity, goalCity);
			var data = new DefaultListModel<String>();
			var routeDesc = route.stream().map(point -> "%s %.1f km".formatted(point.name(), routePlanner.cost(point)))
					.toList();
			data.addAll(routeDesc);
			getListRoute().setModel(data);
			mapImage.repaint();
		}
	}

	private RoadMap map;
	private RoutePlanner routePlanner;

	private final Action actionComputeRoute = new ComputeRouteAction();
	private JComboBox<String> comboStart;
	private JComboBox<String> comboGoal;
	private JList<String> listRoute;
	private ImagePanel mapImage;
	private Point lastMousePosition;
	private boolean shiftPressed;

	public RoutePlannerWindow() {
		setTitle("Route Planner");
		setResizable(false);
		setSize(1020, 670);
		setLocation(30, 30);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow]"));

		JPanel panelStartGoal = new JPanel();
		getContentPane().add(panelStartGoal, "cell 0 0");
		panelStartGoal.setLayout(new MigLayout("", "[][][]", "[][]"));

		JLabel lblStart = new JLabel("Start");
		panelStartGoal.add(lblStart, "cell 0 0,alignx left,aligny center");

		comboStart = new JComboBox<>();
		comboStart.setMaximumRowCount(20);
		comboStart.setModel(new DefaultComboBoxModel<>(new String[] { "Eppelborn", "Losheim am See", "Wadern" }));
		panelStartGoal.add(comboStart, "cell 1 0,alignx left,aligny center");
		comboStart.setAction(actionComputeRoute);

		JLabel lblGoal = new JLabel("Ziel");
		panelStartGoal.add(lblGoal, "cell 0 1,alignx left,aligny center");

		comboGoal = new JComboBox<>();
		comboGoal.setMaximumRowCount(20);
		comboGoal.setModel(new DefaultComboBoxModel<>(new String[] { "Eppelborn", "Losheim am See", "Wadern" }));
		panelStartGoal.add(comboGoal, "cell 1 1,alignx left,aligny center");
		comboGoal.setAction(actionComputeRoute);

		mapImage = new ImagePanel();
		mapImage.setBackground(new Color(255, 255, 255));
		getContentPane().add(mapImage, "cell 1 0 1 2,grow");
		mapImage.setLayout(new MigLayout("", "[]", "[]"));
		mapImage.setOnRepaint(this::onRepaint);
		mapImage.setOnMouseClicked(this::onMouseClicked);
		mapImage.setOnMouseMoved(this::onMouseMoved);
		mapImage.setOnKeyPressed(e -> {
			shiftPressed = e.isShiftDown();
			mapImage.repaint();
		});
		mapImage.setOnKeyReleased(e -> {
			shiftPressed = e.isShiftDown();
			mapImage.repaint();
		});

		listRoute = new JList<>();
		listRoute.setVisibleRowCount(20);
		listRoute.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(listRoute, "cell 0 1,grow");
		listRoute.setModel(new AbstractListModel<>() {
			String[] values = new String[] { "Wadern 0.0 km", "Schmelz 18.0 km", "Eppelborn 31.0 km", "Neunkirchen 52.5 km" };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public String getElementAt(int index) {
				return values[index];
			}
		});

		try {
			mapImage.setImage(ImageIO.read(getClass().getResource("/saarlandkarte.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMap(RoadMap map) {
		this.map = Objects.requireNonNull(map);
		this.routePlanner = new RoutePlanner(map);
		var locationNames = map.locationNames().toList();
		getComboStart().setSelectedItem(locationNames.get(0));
		getComboGoal().setSelectedItem(locationNames.get(locationNames.size() - 1));
	}

	public JComboBox<String> getComboStart() {
		return comboStart;
	}

	public JComboBox<String> getComboGoal() {
		return comboGoal;
	}

	public JList<String> getListRoute() {
		return listRoute;
	}

	private void onMouseMoved(MouseEvent e) {
		mapImage.requestFocus();
		lastMousePosition = e.getPoint();
		mapImage.repaint();
	}

	private void onMouseClicked(MouseEvent e) {
		var coord = getCoordAtPosition(e.getX(), e.getY());
		var nearestCity = getNearestCity(coord);
		if (nearestCity != null) {
			if (e.isShiftDown()) {
				getComboGoal().setSelectedItem(nearestCity.name());
			} else {
				getComboStart().setSelectedItem(nearestCity.name());
			}
		}
		lastMousePosition = e.getPoint();
		mapImage.requestFocus();
		mapImage.repaint();
	}

	private void onRepaint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String startCity = (String) getComboStart().getSelectedItem();
		String goalCity = (String) getComboGoal().getSelectedItem();
		var route = routePlanner.computeRoute(startCity, goalCity);
		for (int i = 0; i < route.size(); ++i) {
			var p = getPointAtCoord(route.get(i).coord());
			if (i > 0) {
				var q = getPointAtCoord(route.get(i - 1).coord());
				g.setColor(Color.RED);
				g.drawLine(p.x, p.y, q.x, q.y);
			}
		}
		RoadMapLocation nearestLocation = null;
		if (lastMousePosition != null) {
			GeoCoord coord = getCoordAtPosition(lastMousePosition.x, lastMousePosition.y);
			g.setColor(Color.BLUE);
			g.setFont(new Font("Sans", Font.PLAIN, 10));
			g.drawString("%.3f %.3f".formatted(coord.latitude(), coord.longitude()), lastMousePosition.x,
					lastMousePosition.y);
			nearestLocation = getNearestCity(coord);
		}
		for (var location : map.locations().toList()) {
			Point p = getPointAtCoord(location.coord());
			if (location.name().equals(getComboStart().getSelectedItem())) {
				circle(g, p, Color.GREEN, 6);
			} else if (location.name().equals(getComboGoal().getSelectedItem())) {
				circle(g, p, Color.BLUE, 6);
			} else if (location == nearestLocation) {
				circle(g, p, shiftPressed ? Color.BLUE : Color.GREEN, 8);
			} else {
				circle(g, p, Color.BLACK, 3);
			}
		}
	}

	private Point getPointAtCoord(GeoCoord coord) {
		float tx = (coord.longitude() - MAP_LONGITUDE_TOP_LEFT) / (MAP_LONGITUDE_BOTTOM_RIGHT - MAP_LONGITUDE_TOP_LEFT);
		float ty = (coord.latitude() - MAP_LATITUDE_BOTTOM_RIGHT) / (MAP_LATITUDE_TOP_LEFT - MAP_LATITUDE_BOTTOM_RIGHT);
		return new Point((int) (tx * mapImage.getWidth()), (int) ((1 - ty) * mapImage.getHeight()));
	}

	private GeoCoord getCoordAtPosition(int x, int y) {
		int width = mapImage.getWidth();
		int height = mapImage.getHeight();
		float tx = (float) x / width;
		float ty = (float) y / height;
		float longitude = MAP_LONGITUDE_TOP_LEFT + tx * (MAP_LONGITUDE_BOTTOM_RIGHT - MAP_LONGITUDE_TOP_LEFT);
		float latitude = MAP_LATITUDE_TOP_LEFT + ty * (MAP_LATITUDE_BOTTOM_RIGHT - MAP_LATITUDE_TOP_LEFT);
		return new GeoCoord(latitude, longitude);
	}

	private RoadMapLocation getNearestCity(GeoCoord coord) {
		double minDist = Double.POSITIVE_INFINITY;
		RoadMapLocation nearestCity = null;
		var locations = map.locations().toList();
		for (var city : locations) {
			double dist = distance(coord, city.coord());
			if (dist < minDist) {
				nearestCity = city;
				minDist = dist;
			}
		}
		return minDist < 50 ? nearestCity : null;
	}

	private double distance(GeoCoord c1, GeoCoord c2) {
		Point p1 = getPointAtCoord(c1);
		Point p2 = getPointAtCoord(c2);
		return Math.hypot(p1.x - p2.x, p1.y - p2.y);
	}

	private void circle(Graphics2D g, Point p, Color color, int radius) {
		g.setColor(color);
		g.fillOval(p.x - radius, p.y - radius, 2 * radius, 2 * radius);
	}
}