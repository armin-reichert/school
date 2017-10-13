package de.amr.schule.graphdrawing.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.model.GraphPoint;

@SuppressWarnings("serial")
public class GraphDrawingView extends JPanel implements IGraphDrawingView {

	private int originX;
	private int originY;
	private GraphDrawingModel model;
	private GraphDrawingController controller;

	public GraphDrawingView(GraphDrawingModel model, GraphDrawingController controller) {
		this.model = model;
		this.controller = controller;
		setBackground(Color.WHITE);
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				areaResized();
			}
		});
		Dimension prefSize = getPreferredSize();
		originX = prefSize.width / 2;
		originY = prefSize.height / 2;
	}

	protected void areaResized() {
		controller.updateInterval(getWidth(), originX);
	}

	@Override
	public void update() {
		repaint();
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw((Graphics2D) g);
	}

	private void draw(Graphics2D g) {
		drawGrid(g);
		drawAxes(g);
		for (GraphPoint p : model.getPoints()) {
			drawPoint(g, p.x, p.fx, Color.BLACK, 4, "", 0, 0);
		}
	}

	private void drawAxes(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawLine(0, originY, getWidth(), originY);
		g.drawLine(originX, 0, originX, getHeight());

		// Ticks x-Achse
		int modelX = 1;
		int x = originX + model.getXscale();
		while (x <= getWidth()) {
			g.setColor(Color.BLACK);
			g.fillRect(x, originY - 3, 1, 6);
			drawXCoord(g, modelX, x - 3, originY - 10);
			x += model.getXscale();
			modelX += 1;
		}
		modelX = -1;
		x = originX + -model.getXscale();
		while (x >= 0) {
			g.setColor(Color.BLACK);
			g.fillRect(x, originY - 3, 1, 6);
			drawXCoord(g, modelX, x - 3, originY - 10);
			x -= model.getXscale();
			modelX -= 1;
		}
		// Ticks y-Achse
		int modelY = -1;
		int y = originY + model.getYscale();
		while (y <= getHeight()) {
			g.setColor(Color.BLACK);
			g.fillRect(originX - 3, y, 6, 1);
			drawYCoord(g, modelY, originX + 6, y + 3);
			y += model.getYscale();
			modelY -= 1;
		}
		modelY = 1;
		y = originY - model.getYscale();
		while (y >= 0) {
			g.setColor(Color.BLACK);
			g.fillRect(originX - 3, y, 6, 1);
			drawYCoord(g, modelY, originX + 6, y + 3);
			y -= model.getYscale();
			modelY += 1;
		}
	}

	private void drawXCoord(Graphics2D g, int xvalue, int x, int y) {
		if (model.getXscale() > 20 || xvalue % 5 == 0) {
			g.drawString("" + xvalue, x, y);
		}
	}

	private void drawYCoord(Graphics2D g, int yvalue, int x, int y) {
		if (model.getYscale() > 20 || yvalue % 5 == 0) {
			g.drawString("" + yvalue, x, y);
		}
	}

	private void drawGrid(Graphics2D g) {
	}

	private void drawPoint(Graphics2D g, double mx, double my, Color color, int size, String text,
			int offsetX, int offsetY) {
		int x = toViewX(mx);
		int y = toViewY(my);
		g.setColor(color);
		g.fillOval(x - size / 2, y - size / 2, size, size);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString(text, x + offsetX, y + offsetY);
	}

	private int toViewX(double mx) {
		return (int) (originX + mx * model.getXscale());
	}

	private int toViewY(double my) {
		return (int) (originY - my * model.getYscale());
	}
}