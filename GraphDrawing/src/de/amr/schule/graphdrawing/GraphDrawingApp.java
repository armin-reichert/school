package de.amr.schule.graphdrawing;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.CanvasView;
import de.amr.schule.graphdrawing.view.ConfigView;
import de.amr.schule.graphdrawing.view.MainWindowLayout;
import de.amr.schule.graphdrawing.view.PointsTableView;

public class GraphDrawingApp {

	public static void main(String[] args) throws Exception {
		setLookAndFeel(NimbusLookAndFeel.class.getName());
		invokeLater(GraphDrawingApp::new);
	}

	public GraphDrawingApp() {

		// Model
		GraphDrawingModel model = new GraphDrawingModel();
		model.setStep(.01);
		model.setXscale(20);
		model.setYscale(20);

		// Views
		CanvasView canvasView = new CanvasView(model);
		canvasView.setPreferredSize(new Dimension(800, 800));

		PointsTableView pointsTableView = new PointsTableView(model);

		ConfigView configView = new ConfigView(model);

		// Controller
		GraphDrawingController controller = new GraphDrawingController(model);
		canvasView.setController(controller);
		pointsTableView.setController(controller);
		configView.setController(controller);
		controller.addViews(canvasView, pointsTableView, configView);

		// Window
		JFrame window = new JFrame("Funktionsgraphen zeichnen");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new MainWindowLayout(canvasView, pointsTableView, configView),
				BorderLayout.CENTER);
		window.getRootPane().setDefaultButton(configView.getButtonOK());
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// Needs canvas width and height to be computed
		canvasView.centerOrigin();
	}
}
