package de.amr.schule.graphdrawing.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.GraphDrawingView;
import de.amr.schule.graphdrawing.view.GraphPointTableView;

public class GraphDrawingApp {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		EventQueue.invokeLater(GraphDrawingApp::new);
	}

	private JFrame window;
	private GraphDrawingController controller;
	private GraphDrawingView canvas;
	private GraphPointTableView tableView;
	private ConfigView controlView;
	private GraphDrawingModel model;

	public GraphDrawingApp() {

		model = new GraphDrawingModel();
		model.setStep(.2);
		model.setXscale(20);
		model.setYscale(20);
		model.computeRoots();
		model.computeVertexPoint();

		controller = new GraphDrawingController(model);

		canvas = new GraphDrawingView(model, controller);
		canvas.setPreferredSize(new Dimension(700, 600));
		canvas.setOriginX(250);
		canvas.setOriginY(300);

		tableView = new GraphPointTableView(model, controller);

		controlView = new ConfigView(model, controller);

		controller.addView(canvas);
		controller.addView(tableView);
		controller.addView(controlView);

		JPanel east = new JPanel();
		east.setLayout(new GridLayout(2, 1));
		east.add(tableView);
		east.add(controlView);

		window = new JFrame("Funktionsgraphen zeichnen");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(canvas, BorderLayout.CENTER);
		window.getContentPane().add(east, BorderLayout.EAST);
		window.pack();
		window.setVisible(true);
	}
}
