package de.amr.schule.graphdrawing.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.model.GraphPoint;

public class CanvasView extends JPanel implements GraphDrawingViewController {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
	private static final int POINT_SIZE = 2;

	private GraphDrawingModel model;
	private GraphDrawingController controller;
	private int originX;
	private int originY;
	private boolean originIsMoving;

	public CanvasView(GraphDrawingModel model) {
		this.model = model;
		setBackground(Color.WHITE);
		registerEventHandlers();
	}

	@Override
	public void setController(GraphDrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void update() {
		repaint();
		requestFocus();
	}

	private void registerEventHandlers() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				onResized();
			}
		});

		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				onMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				onMouseReleased(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}
		};
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		Action actionCenterOrigin = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				centerOrigin();
			}
		};
		getInputMap().put(KeyStroke.getKeyStroke("C"), "centerOrigin");
		getActionMap().put("centerOrigin", actionCenterOrigin);
	}

	private void onResized() {
		controller.updateInterval(getWidth(), originX);
	}

	private void onMousePressed(MouseEvent e) {
		int x1 = e.getX(), y1 = e.getY();
		int x2 = originX, y2 = originY;
		if ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) <= 100) {
			originIsMoving = true;
		}
	}

	private void onMouseReleased(MouseEvent e) {
		if (originIsMoving) {
			moveOrigin(e.getX(), e.getY());
			originIsMoving = false;
		}
	}

	private void onMouseDragged(MouseEvent e) {
		moveOrigin(e.getX(), e.getY());
	}

	private void moveOrigin(int x, int y) {
		originX = x;
		originY = y;
		controller.updateInterval(getWidth(), originX);
	}

	public void centerOrigin() {
		int width = getWidth() != 0 ? getWidth() : getPreferredSize().width;
		int height = getHeight() != 0 ? getHeight() : getPreferredSize().height;
		originX = width / 2;
		originY = height / 2;
		controller.updateInterval(getWidth(), originX);
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

	private int toViewX(double mx) {
		return (int) (originX + mx * model.getXscale());
	}

	private int toViewY(double my) {
		return (int) (originY - my * model.getYscale());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw((Graphics2D) g);
	}

	private void draw(Graphics2D g) {
		drawGrid(g);
		drawAxes(g);
		for (GraphPoint gp : model.getPoints()) {
			drawPoint(g, gp.x, gp.fx, Color.BLACK, POINT_SIZE, "", 0, 0);
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
		// TODO
	}

	private void drawPoint(Graphics2D g, double mx, double my, Color color, int size, String text,
			int offsetX, int offsetY) {
		int x = toViewX(mx);
		int y = toViewY(my);
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRect(x - size / 2, y - size / 2, size, size);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(FONT);
		g.drawString(text, x + offsetX, y + offsetY);
	}
}