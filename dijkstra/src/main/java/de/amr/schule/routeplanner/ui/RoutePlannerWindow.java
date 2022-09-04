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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import de.amr.schule.routeplanner.RoutePlanner;
import de.amr.schule.routeplanner.model.RoadMap;
import net.miginfocom.swing.MigLayout;

/**
 * @author Armin Reichert
 */
public class RoutePlannerWindow extends JFrame {

	private class ComputeRouteAction extends AbstractAction {

		public ComputeRouteAction() {
			putValue(NAME, "Compute Route");
			putValue(SHORT_DESCRIPTION, "Computes the best route");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String startCity = (String) getComboStart().getSelectedItem();
			String goalCity = (String) getComboGoal().getSelectedItem();
			var route = routePlanner.computeRoute(map, startCity, goalCity);
			var data = new DefaultListModel<String>();
			data.addAll(route);
			getListRoute().setModel(data);
		}
	}

	private RoadMap map;
	private final RoutePlanner routePlanner = new RoutePlanner();

	private final Action actionComputeRoute = new ComputeRouteAction();
	private final Action actionClearRoute = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			listRoute.setModel(new DefaultListModel<>());
		}
	};
	private JComboBox<String> comboStart;
	private JComboBox<String> comboGoal;
	private JList<String> listRoute;

	public RoutePlannerWindow() {
		setTitle("Route Planner");
		setSize(600, 300);
		setLocation(30, 30);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "", "[10px][]"));

		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 0,aligny top");
		panel.setLayout(new MigLayout("", "[24px][102px][16px][102px][107px]", "[]"));

		JLabel lblStart = new JLabel("Start");
		panel.add(lblStart, "cell 0 0,alignx left,aligny center");

		comboStart = new JComboBox<>();
		comboStart.setMaximumRowCount(20);
		comboStart.setModel(new DefaultComboBoxModel<>(new String[] { "Eppelborn", "Losheim am See", "Wadern" }));
		panel.add(comboStart, "cell 1 0,alignx left,aligny center");
		comboStart.setAction(actionClearRoute);

		JLabel lblGoal = new JLabel("Ziel");
		panel.add(lblGoal, "cell 2 0,alignx left,aligny center");

		comboGoal = new JComboBox<>();
		comboGoal.setMaximumRowCount(20);
		comboGoal.setModel(new DefaultComboBoxModel<>(new String[] { "Eppelborn", "Losheim am See", "Wadern" }));
		panel.add(comboGoal, "cell 3 0,alignx left,aligny center");
		comboGoal.setAction(actionClearRoute);

		JButton btnComputeRoute = new JButton("Route Berechnen");
		btnComputeRoute.setAction(actionComputeRoute);
		panel.add(btnComputeRoute, "cell 4 0,alignx left,aligny top");

		listRoute = new JList<>();
		listRoute.setVisibleRowCount(20);
		listRoute.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(listRoute, "cell 0 1,growx");
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
	}

	public void setMap(RoadMap map) {
		this.map = map;
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
}