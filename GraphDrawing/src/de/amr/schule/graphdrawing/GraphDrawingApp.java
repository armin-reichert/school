package de.amr.schule.graphdrawing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.CanvasView;
import de.amr.schule.graphdrawing.view.ConfigView;
import de.amr.schule.graphdrawing.view.MainWindowContent;
import de.amr.schule.graphdrawing.view.PointsTableView;

public class GraphDrawingApp {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		EventQueue.invokeLater(GraphDrawingApp::new);
	}

	private JFrame window;
	private GraphDrawingModel model;
	private CanvasView canvasView;
	private PointsTableView pointsTableView;
	private ConfigView configView;
	private GraphDrawingController controller;

	public GraphDrawingApp() {

		model = new GraphDrawingModel();
		model.setStep(.01);
		model.setXscale(20);
		model.setYscale(20);

		canvasView = new CanvasView(model);
		canvasView.setPreferredSize(new Dimension(800, 800));
		pointsTableView = new PointsTableView(model);
		configView = new ConfigView(model);

		controller = new GraphDrawingController(model);
		canvasView.setController(controller);
		pointsTableView.setController(controller);
		configView.setController(controller);
		controller.addViews(canvasView, pointsTableView, configView);

		window = new JFrame("Funktionsgraphen zeichnen");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new MainWindowContent(canvasView, pointsTableView, configView),
				BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);

		canvasView.centerOrigin();
	}
}
