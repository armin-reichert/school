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
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import de.amr.schule.darts.counter.model.DartBoard;

public class DartBoardUI extends JComponent {

	public static final String PROPERTY_POINTS = "points";

	private static String getValueAsText(DartBoard.Ring ring, int segment) {
		switch (ring) {
		case OUT:
			return "Out";
		case SIMPLE:
			return "" + segment;
		case DOUBLE:
			return "Double " + segment;
		case TRIPLE:
			return "Triple " + segment;
		case SINGLE_BULL:
			return "Single Bull";
		case BULLS_EYE:
			return "Bulls-Eye";
		}
		return "";
	}

	private final Image boardImage;
	private final Image dartImage;
	private final Point center;
	private final double scaling;
	private final int diameter;

	private Point currentTarget;
	private DartBoard.Ring currentRing;
	private int currentSegment;

	public DartBoardUI() {
		this(600);
	}

	public DartBoardUI(int diameter) {
		this.diameter = diameter;
		InputStream dartImageSource = getClass().getResourceAsStream("/dart.png");
		if (dartImageSource == null) {
			throw new RuntimeException("Dart image not found");
		}
		try {
			dartImage = ImageIO.read(dartImageSource);
		} catch (IOException e) {
			throw new RuntimeException("Dart image could not be loaded");
		}
		InputStream boardImageSource = getClass().getResourceAsStream("/dartboard.png");
		if (boardImageSource == null) {
			throw new RuntimeException("Board image not found");
		}
		try {
			boardImage = ImageIO.read(boardImageSource).getScaledInstance(diameter, diameter,
					BufferedImage.SCALE_SMOOTH);
		} catch (IOException x) {
			throw new RuntimeException("Could not load board image");
		}
		scaling = (double) diameter / DartBoard.BOARD_REFERENCE_DIAMETER;
		// correction for image inaccuracy
		int offsetX = (int) (scaling * -2), offsetY = (int) (scaling * 4);
		center = new Point(diameter / 2 + offsetX, diameter / 2 + offsetY);

		Dimension dim = new Dimension(diameter, diameter);
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
		setCurrentTarget(viewX, viewY);
		repaint();
	}

	protected void onMouseClicked(int viewX, int viewY) {
		setCurrentTarget(viewX, viewY);
		repaint();
		int points = DartBoard.getPoints(currentRing, currentSegment);
		firePropertyChange(PROPERTY_POINTS, -1, points);
	}

	private void setCurrentTarget(int viewX, int viewY) {
		currentTarget = new Point(viewX, viewY);
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
		// g.setColor(Color.BLACK);
		// g.fillOval(0, 0, diameter, diameter);
		g.drawImage(boardImage, 0, 0, diameter, diameter, null);
		g.setColor(Color.YELLOW);
		drawRing(g, BULLS_EYE);
		drawRing(g, SINGLE_BULL);
		drawRing(g, TRIPLE);
		drawRing(g, DOUBLE);
		for (int angle = 9; angle < 360; angle += 18) {
			double rad = toRadians(angle);
			g.translate(center.x, center.y);
			g.rotate(rad);
			g.drawLine((int) (scaling * SINGLE_BULL.outer), 0, (int) (scaling * DOUBLE.outer), 0);
			g.rotate(-rad);
			g.translate(-center.x, -center.y);
		}
		// draw dart at target position
		if (currentTarget != null && currentTarget.distance(center) <= diameter / 2) {
			g.setColor(currentRing == DartBoard.Ring.OUT ? Color.GRAY : Color.PINK);
			double scale = scaling / 2;
			int targetWidth = (int) (scale * dartImage.getWidth(null)),
					targetHeight = (int) (scale * dartImage.getHeight(null));
			g.fillOval(currentTarget.x - 5, currentTarget.y - 5, 10, 10);
			g.drawImage(dartImage, currentTarget.x, currentTarget.y - targetHeight, targetWidth,
					targetHeight, null);
		}
		// draw current value text
		if (currentRing != null) {
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, diameter / 30));
			g.drawString(getValueAsText(currentRing, currentSegment), 5, getHeight() - diameter / 60);
		}
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
}