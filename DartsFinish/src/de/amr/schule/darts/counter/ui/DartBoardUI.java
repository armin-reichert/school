package de.amr.schule.darts.counter.ui;

import static de.amr.schule.darts.counter.model.DartBoard.Ring.BULLS_EYE;
import static de.amr.schule.darts.counter.model.DartBoard.Ring.DOUBLE;
import static de.amr.schule.darts.counter.model.DartBoard.Ring.SINGLE_BULL;
import static de.amr.schule.darts.counter.model.DartBoard.Ring.TRIPLE;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.amr.schule.darts.counter.model.DartBoard;

public class DartBoardUI extends JPanel {

	public static final String PROPERTY_POINTS = "points";

	private BufferedImage boardImage;
	private Point center;
	private double scaling;
	private int boardSize;

	private DartBoard.Ring currentRing;
	private int currentSegment;

	public DartBoardUI(int boardSize) {
		try {
			BufferedImage boardImage = ImageIO.read(getClass().getResourceAsStream("/dartboard.png"));
			init(boardImage, boardSize);
		} catch (Exception x) {
			throw new RuntimeException("Could not load board image");
		}
	}

	public DartBoardUI(BufferedImage boardImage, int boardSize) {
		init(boardImage, boardSize);
	}

	private void init(BufferedImage boardImage, int boardSize) {
		this.boardImage = boardImage;
		this.boardSize = boardSize;
		scaling = (double) boardSize / DartBoard.BOARD_REFERENCE_SIZE;
		// correction for image inaccuracy
		int offsetX = (int) (scaling * -2), offsetY = (int) (scaling * 4);
		center = new Point(boardSize / 2 + offsetX, boardSize / 2 + offsetY);

		Dimension dim = new Dimension(boardSize, boardSize);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClicked(e.getX(), e.getY());
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				onMouseMoved(e.getX(), e.getY());
			}
		});
	}

	protected void onMouseMoved(int viewX, int viewY) {
		targetCoordinateChanged(viewX, viewY);
		repaint();
	}

	protected void onMouseClicked(int viewX, int viewY) {
		targetCoordinateChanged(viewX, viewY);
		repaint();
		int points = DartBoard.getPoints(currentRing, currentSegment);
		firePropertyChange(PROPERTY_POINTS, -1, points);
	}

	private void targetCoordinateChanged(int viewX, int viewY) {
		// Convert view to model coordinate
		int x = viewX - center.x;
		int y = center.y - viewY;
		// Compute polar coordinate
		int radius = (int) sqrt(x * x + y * y);
		int angle = ((int) toDegrees(atan2(y, x)) + 360) % 360;
		// Compute segment and ring
		currentSegment = DartBoard.getSegment(angle);
		currentRing = DartBoard.getRing(radius, scaling);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw((Graphics2D) g);
	}

	private void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(boardImage, 0, 0, boardSize, boardSize, null);
		g.setColor(Color.YELLOW);
		drawRing(g, BULLS_EYE);
		drawRing(g, SINGLE_BULL);
		drawRing(g, TRIPLE);
		drawRing(g, DOUBLE);
		for (int angle = 9; angle < 360; angle += 18) {
			double rad = toRadians(angle);
			g.translate(center.x, center.y);
			g.rotate(-rad);
			g.drawLine((int) (scaling * SINGLE_BULL.outer), 0, (int) (scaling * DOUBLE.outer), 0);
			g.rotate(rad);
			g.translate(-center.x, -center.y);
		}
		drawCurrentValue(g);
	}

	private void drawRing(Graphics2D g, DartBoard.Ring ring) {
		int radius = (int) (scaling * ring.inner);
		if (radius > 0) {
			g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
		}
		radius = (int) (scaling * ring.outer);
		if (radius <= getWidth()) {
			g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
		}
	}

	private void drawCurrentValue(Graphics2D g) {
		if (currentRing == null) {
			return;
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.BOLD, boardSize / 30));
		g.drawString(getCurrentValueAsText(), 5, getHeight() - boardSize / 60);
	}

	private String getCurrentValueAsText() {
		switch (currentRing) {
		case OUT:
			return "Out";
		case SIMPLE:
			return "" + currentSegment;
		case DOUBLE:
			return "Double " + currentSegment;
		case TRIPLE:
			return "Triple " + currentSegment;
		case SINGLE_BULL:
			return "Single-Bull";
		case BULLS_EYE:
			return "Bulls-Eye";
		}
		return "???";
	}
}