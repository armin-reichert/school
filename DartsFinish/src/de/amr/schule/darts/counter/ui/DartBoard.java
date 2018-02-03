package de.amr.schule.darts.counter.ui;

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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DartBoard extends JPanel {

	public enum Ring {
		BULLS_EYE(0, 12),
		SINGLE_BULL(13, 32),
		TRIPLE(190, 208),
		DOUBLE(317, 335),
		OUT(336, Integer.MAX_VALUE),
		SIMPLE(-1, -1);

		public boolean contains(int radius) {
			return inner <= radius && radius <= outer;
		}

		private Ring(int inner, int outer) {
			this.inner = inner;
			this.outer = outer;
		}

		int inner;
		int outer;
	};

	private static int[] SEGMENT_VALUES = {
			/*@formatter:off*/
			6, 13, 4, 18, 1, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19, 3, 17, 2, 15, 10
			/*@formatter:on*/
	};

	private final Image board;
	private final Point center;

	private Ring currentRing;
	private int currentSegmentValue;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public DartBoard(String imagePath, int size) {
		try {
			board = ImageIO.read(getClass().getResourceAsStream(imagePath)).getScaledInstance(size, size,
					BufferedImage.SCALE_SMOOTH);
			int offsetX = -2, offsetY = 4;
			center = new Point(size / 2 + offsetX, size / 2 + offsetY);
			setPreferredSize(new Dimension(size, size));
			setSize(getPreferredSize());
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
		} catch (Exception e) {
			throw new MissingResourceException("Dart board image not found", getClass().getName(),
					imagePath);
		}
	}

	protected void onMouseMoved(int viewX, int viewY) {
		identifyPosition(viewX, viewY);
		repaint();
	}

	protected void onMouseClicked(int viewX, int viewY) {
		// System.out.println("Clicked at x=" + viewX + ",y=" + viewY);
		identifyPosition(viewX, viewY);
//		System.out.println(currentRing + " " + currentSegmentValue);
		repaint();
		pcs.firePropertyChange("points", -1, computePoints());
	}

	private int computePoints() {
		switch (currentRing) {
		case OUT:
			return 0;
		case SIMPLE:
			return currentSegmentValue;
		case DOUBLE:
			return 2 * currentSegmentValue;
		case TRIPLE:
			return 3 * currentSegmentValue;
		case SINGLE_BULL:
			return 25;
		case BULLS_EYE:
			return 50;
		}
		throw new IllegalStateException();
	}

	private void identifyPosition(int viewX, int viewY) {
		// Compute model coordinates
		int x = viewX - center.x;
		int y = center.y - viewY;
		// System.out.println("Model coordinate: x=" + x + ",y=" + y);
		// Compute polar coordinate
		double radius = Math.sqrt(x * x + y * y);
		double phi = Math.atan2(y, x);
		double phiDeg = Math.toDegrees(phi);
		if (phiDeg < 0) {
			phiDeg += 360;
		}
		// System.out.println("Polar coordinate: r=" + radius + ", phi=" + phiDeg + " degrees");
		currentSegmentValue = computeSegmentValue(phiDeg);
		currentRing = computeRing((int) radius);
	}

	private void drawCurrentValue(Graphics2D g) {
		String text = "";
		if (currentRing == null) {
			return;
		}
		switch (currentRing) {
		case OUT:
			text = "Aus";
			break;
		case DOUBLE:
			text = "Doppel " + currentSegmentValue;
			break;
		case TRIPLE:
			text = "Triple " + currentSegmentValue;
			break;
		case SINGLE_BULL:
			text = "Single Bull";
			break;
		case BULLS_EYE:
			text = "Bulls-Eye";
			break;
		case SIMPLE:
			text = "" + currentSegmentValue;
			break;
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.GRAY);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(text, 20, 60);
	}

	/**
	 * 
	 * @param angle
	 *          angle in degrees
	 * @return segment value or <code>-1</code>
	 */
	private int computeSegmentValue(double angle) {
		if (angle > 353) {
			return SEGMENT_VALUES[0];
		}
		int segment = 0;
		int lower = -9;
		while (segment < SEGMENT_VALUES.length) {
			if (angle > lower && angle <= lower + 18) {
				return SEGMENT_VALUES[segment];
			}
			lower += 18;
			++segment;
		}
		System.out.println("No segment found for angle " + angle);
		return -1;
	}

	private Ring computeRing(int radius) {
		if (Ring.BULLS_EYE.contains(radius))
			return Ring.BULLS_EYE;
		if (Ring.SINGLE_BULL.contains(radius))
			return Ring.SINGLE_BULL;
		if (Ring.TRIPLE.contains(radius))
			return Ring.TRIPLE;
		if (Ring.DOUBLE.contains(radius))
			return Ring.DOUBLE;
		if (Ring.OUT.contains(radius))
			return Ring.OUT;
		return Ring.SIMPLE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw((Graphics2D) g);
	}

	private void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(board, 0, 0, getWidth(), getHeight(), null);

		g.setColor(Color.YELLOW);
		drawRing(g, Ring.BULLS_EYE);
		drawRing(g, Ring.SINGLE_BULL);
		drawRing(g, Ring.TRIPLE);
		drawRing(g, Ring.DOUBLE);

		for (int angle = 9; angle < 360; angle += 18) {
			g.translate(center.x, center.y);
			g.rotate(-Math.toRadians(angle));
			g.drawLine(Ring.SINGLE_BULL.outer, 0, Ring.DOUBLE.outer, 0);
			g.rotate(Math.toRadians(angle));
			g.translate(-center.x, -center.y);
		}

		drawCurrentValue(g);
	}

	private void drawRing(Graphics2D g, Ring ring) {
		int radius = ring.inner;
		if (radius > 0) {
			g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
		}
		radius = ring.outer;
		if (radius <= getWidth()) {
			g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
		}
	}

}