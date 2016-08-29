package de.amr.schule.koord.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.amr.schule.koord.model.CoordSystem;

public class FunctionPlotView extends JPanel {

	private final CoordSystem model;
	private final Map<String, List<Point2D>> graphs;
	private double density;
	private int numPoints;
	private Color drawColor;

	private Action action_increaseDensity = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			density += 100;
			updateGraphs();
			repaint();
		}
	};

	private Action action_decreaseDensity = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			density = Math.max(density - 100, 0);
			updateGraphs();
			repaint();
		}
	};

	public FunctionPlotView(int width, int height, CoordSystem model) {
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(getPreferredSize());
		setBackground(Color.WHITE);
		this.model = model;
		graphs = new HashMap<>();
		drawColor = Color.BLUE;
		density = 1000;
		getActionMap().put(action_increaseDensity, action_increaseDensity);
		getInputMap().put(KeyStroke.getKeyStroke("PLUS"), action_increaseDensity);
		getActionMap().put(action_decreaseDensity, action_decreaseDensity);
		getInputMap().put(KeyStroke.getKeyStroke("MINUS"), action_decreaseDensity);
		updateGraphs();
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
		repaint();
	}

	private void createGraph(String id, Function<Double, Double> f) {
		List<Point2D> graphPoints = new ArrayList<>();
		graphs.put(id, graphPoints);
		double dx = (model.xmax - model.xmin) / density;
		for (double x = model.xmin; x <= model.xmax; x += dx) {
			double y = f.apply(x);
			if (y >= model.ymin && y <= model.ymax) {
				graphPoints.add(new Point2D.Double(x, y));
				++numPoints;
			}
		}
	}

	public void updateGraphs() {
		long time = System.nanoTime();
		graphs.clear();
		numPoints = 0;
		for (Map.Entry<String, Function<Double, Double>> functions : model.functions()) {
			createGraph(functions.getKey(), functions.getValue());
		}
		time = (System.nanoTime() - time) / 1000000L;
		System.out.println("Graph build time: " + time + " milliseconds");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		long time = System.nanoTime();
		draw(g2);
		time = (System.nanoTime() - time) / 1000000L;
		System.out.println("Drawing time: " + time + " milliseconds");
	}

	private void draw(Graphics2D g) {
		int x = getWidth() - 150, y = getHeight() - 150;
		g.setColor(Color.BLACK);
		g.drawString("xmin:" + model.xmin, x, y);
		g.drawString("xmax:" + model.xmax, x, y + 20);
		g.drawString("ymin:" + model.ymin, x, y + 40);
		g.drawString("ymax:" + model.ymax, x, y + 60);
		g.drawString(String.format("Punkte/Graph: %.0f", density), x, y + 80);
		g.drawString("Punkte/Total:" + numPoints, x, y + 100);

		// Achsen
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

		// Model-View-Transformation
		AffineTransform at = new AffineTransform();
		double sx = getWidth() / (model.xmax - model.xmin);
		double sy = getHeight() / (model.ymax - model.ymin);
		double xm = (model.xmin + model.xmax) / 2;
		double ym = (model.ymin + model.ymax) / 2;
		at.translate(getWidth() / 2 - sx * xm, getHeight() / 2 - sy * ym);
		at.scale(sx, -sy);

		g.setColor(drawColor);
		for (Map.Entry<String, List<Point2D>> entry : graphs.entrySet()) {
			for (Point2D point : entry.getValue()) {
				Point2D viewPoint = at.transform(point, null);
				int px = (int) viewPoint.getX();
				int py = (int) viewPoint.getY();
				g.drawLine(px, py, px, py);
			}
		}
	}
}
