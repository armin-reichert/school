package de.amr.schule.graphdrawing.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.model.GraphPoint;
import de.amr.schule.graphdrawing.view.api.GraphDrawingViewController;

public class CanvasView extends JPanel implements GraphDrawingViewController {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
	private static final int POINT_SIZE = 2;
	private static final Color AXIS_COLOR = Color.DARK_GRAY;
	private static final Color GRID_COLOR = Color.LIGHT_GRAY;

	private GraphDrawingModel model;
	private GraphDrawingController controller;

	private int originX;
	private int originY;
	private boolean originMovement;
	private boolean gridVisible;

	public CanvasView(GraphDrawingModel model, int width, int height) {
		this.model = model;
		this.gridVisible = true;
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.WHITE);
		registerEventHandlers();
	}

	@Override
	public void setController(GraphDrawingController controller) {
		this.controller = controller;
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

		registerActionForKey("C", "centerOrigin", e -> centerOrigin());
		registerActionForKey("G", "toggleGrid", e -> toggleGrid());
	}

	private void registerActionForKey(String key, String actionName,
			Consumer<ActionEvent> actionHandler) {
		AbstractAction action = new AbstractAction(actionName) {

			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler.accept(e);
			}
		};
		getInputMap().put(KeyStroke.getKeyStroke(key), actionName);
		getActionMap().put(actionName, action);
	}

	private void toggleGrid() {
		gridVisible = !gridVisible;
		repaint();
	}

	private void onResized() {
		controller.updateInterval(getWidth(), originX);
	}

	private void startOriginMovement() {
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		originMovement = true;
	}

	private void stopOriginMovement() {
		setCursor(Cursor.getDefaultCursor());
		originMovement = false;
	}

	private void onMousePressed(MouseEvent e) {
		int x1 = e.getX(), y1 = e.getY();
		int x2 = originX, y2 = originY;
		if ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) <= 100) {
			startOriginMovement();
		}
	}

	private void onMouseReleased(MouseEvent e) {
		if (originMovement) {
			moveOrigin(e.getX(), e.getY());
			stopOriginMovement();
		}
	}

	private void onMouseDragged(MouseEvent e) {
		if (originMovement) {
			moveOrigin(e.getX(), e.getY());
		}
	}

	private void moveOrigin(int x, int y) {
		originX = x;
		originY = y;
		controller.updateInterval(getWidth(), originX);
	}

	private int viewX(double modelX) {
		return (int) (originX + modelX * model.getXscale());
	}

	private int viewY(double modelY) {
		return (int) (originY - modelY * model.getYscale());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw((Graphics2D) g);
	}

	private void draw(Graphics2D g) {
		drawGrid(g, GRID_COLOR);
		drawAxes(g, AXIS_COLOR);
		for (GraphPoint gp : model.getPoints()) {
			drawPoint(g, gp.x, gp.fx, Color.BLACK, POINT_SIZE, "", 0, 0);
		}
	}

	private void drawAxes(Graphics2D g, Color color) {
		g.setColor(color);
		// x-Achse
		g.drawLine(0, originY, getWidth(), originY);
		int modelX = 1;
		for (int x = originX + model.getXscale(); x <= getWidth(); x += model.getXscale()) {
			g.fillRect(x, originY - 3, 1, 6);
			drawXCoord(g, modelX, x - 3, originY - 10);
			x += model.getXscale();
			modelX += 1;
		}
		modelX = -1;
		for (int x = originX + -model.getXscale(); x >= 0; x -= model.getXscale()) {
			g.fillRect(x, originY - 3, 1, 6);
			drawXCoord(g, modelX, x - 3, originY - 10);
			modelX -= 1;
		}
		// y-Achse
		g.drawLine(originX, 0, originX, getHeight());
		int modelY = -1;
		for (int y = originY + model.getYscale(); y <= getHeight(); y += model.getYscale()) {
			g.fillRect(originX - 3, y, 6, 1);
			drawYCoord(g, modelY, originX + 6, y + 3);
			modelY -= 1;
		}
		modelY = 1;
		for (int y = originY - model.getYscale(); y >= 0; y -= model.getYscale()) {
			g.fillRect(originX - 3, y, 6, 1);
			drawYCoord(g, modelY, originX + 6, y + 3);
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

	private void drawGrid(Graphics2D g, Color color) {
		if (gridVisible) {
			g.setColor(color);
			for (int y = originY; y >= 0; y -= model.getYscale()) {
				g.drawLine(0, y, getWidth(), y);
			}
			for (int y = originY; y < getHeight(); y += model.getYscale()) {
				g.drawLine(0, y, getWidth(), y);
			}
			for (int x = originX; x >= 0; x -= model.getXscale()) {
				g.drawLine(x, 0, x, getHeight());
			}
			for (int x = originX; x < getWidth(); x += model.getXscale()) {
				g.drawLine(x, 0, x, getHeight());
			}
		}
	}

	private void drawPoint(Graphics2D g, double mx, double my, Color color, int size, String text,
			int offsetX, int offsetY) {
		int x = viewX(mx);
		int y = viewY(my);
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRect(x - size / 2, y - size / 2, size, size);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(FONT);
		g.drawString(text, x + offsetX, y + offsetY);
	}
}