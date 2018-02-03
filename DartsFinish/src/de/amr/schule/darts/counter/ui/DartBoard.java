package de.amr.schule.darts.counter.ui;

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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.MissingResourceException;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DartBoard extends JPanel {

	public enum Ring {
		BULLS_EYE(0, 12),
		SINGLE_BULL(13, 32),
		TRIPLE(190, 208),
		DOUBLE(317, 335),
		OUT(336, Integer.MAX_VALUE),
		SIMPLE(0, 335);

		public boolean contains(int radius) {
			return inner <= radius && radius <= outer;
		}

		private Ring(int inner, int outer) {
			this.inner = inner;
			this.outer = outer;
		}

		public final int inner;
		public final int outer;
	};

	private static int[] SEGMENT_VALUES = {
			/*@formatter:off*/
			6, 13, 4, 18, 1, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19, 3, 17, 2, 15, 10
			/*@formatter:on*/
	};

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private final Image board;
	private final Point center;

	private Ring currentRing;
	private int currentSegmentValue;

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
			int offsetX = -2, offsetY = 4; // TODO: handle scaling
			center = new Point(size / 2 + offsetX, size / 2 + offsetY);
		} catch (Exception e) {
			throw new MissingResourceException("Dart board image not found", getClass().getName(),
					imagePath);
		}
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
	}

	protected void onMouseMoved(int viewX, int viewY) {
		updatePosition(viewX, viewY);
		repaint();
	}

	protected void onMouseClicked(int viewX, int viewY) {
		updatePosition(viewX, viewY);
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

	private void updatePosition(int viewX, int viewY) {
		// Compute model coordinates
		int x = viewX - center.x;
		int y = center.y - viewY;
		// Compute polar coordinate
		int radius = (int) sqrt(x * x + y * y);
		int angle = ((int) toDegrees(atan2(y, x)) + 360) % 360;
		currentSegmentValue = computeSegmentValue(angle);
		currentRing = computeRing(radius);
	}

	private int computeSegmentValue(int angle) {
		if (angle < 0 || angle > 360) {
			throw new IllegalArgumentException();
		}
		angle = (angle + 9) % 360;
		return SEGMENT_VALUES[angle / 18];
	}

	private Ring computeRing(int radius) {
		return Stream.of(Ring.BULLS_EYE, Ring.SINGLE_BULL, Ring.TRIPLE, Ring.DOUBLE, Ring.OUT)
				.filter(ring -> ring.contains(radius)).findFirst().orElse(Ring.SIMPLE);
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
			double rad = toRadians(angle);
			g.translate(center.x, center.y);
			g.rotate(-rad);
			g.drawLine(Ring.SINGLE_BULL.outer, 0, Ring.DOUBLE.outer, 0);
			g.rotate(rad);
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

	private void drawCurrentValue(Graphics2D g) {
		if (currentRing == null) {
			return;
		}
		String text = "";
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

}