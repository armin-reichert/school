package de.amr.schule.graphdrawing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.ConfigView;
import de.amr.schule.graphdrawing.view.CanvasView;
import de.amr.schule.graphdrawing.view.PointsTableView;
import de.amr.schule.graphdrawing.view.MainWindowContent;

public class GraphDrawingApp {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		EventQueue.invokeLater(GraphDrawingApp::new);
	}

	private JFrame window;
	private GraphDrawingController controller;
	private CanvasView canvas;
	private PointsTableView tableView;
	private ConfigView controlView;
	private GraphDrawingModel model;

	public GraphDrawingApp() {

		model = new GraphDrawingModel();
		model.setStep(.05);
		model.setXscale(20);
		model.setYscale(20);

		controller = new GraphDrawingController(model);

		canvas = new CanvasView(model, controller);
		canvas.setPreferredSize(new Dimension(700, 600));
		canvas.centerOrigin();

		tableView = new PointsTableView(model, controller);

		controlView = new ConfigView(model, controller);

		controller.addViews(canvas, tableView, controlView);
		
		MainWindowContent mainPanel = new MainWindowContent(canvas, tableView, controlView);
		
		window = new JFrame("Funktionsgraphen zeichnen");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(mainPanel, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);
	}
}
