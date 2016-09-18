package de.amr.schule.rotellipse;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Math.toRadians;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class EllipsePanel extends JPanel {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame f = new JFrame("Rotierende Ellipse");
			f.add(new EllipsePanel());
			f.pack();
			f.setDefaultCloseOperation(EXIT_ON_CLOSE);
			f.setVisible(true);
		});
	}

	private int phi = 0;

	private Timer animation = new Timer(5, e -> {
		phi = (phi + 2) % 360;
		repaint();
	});

	public EllipsePanel() {
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.WHITE);
		animation.start();
	}

	@Override
	protected void paintComponent(Graphics g_) {
		super.paintComponent(g_);
		Graphics2D g = (Graphics2D) g_;
		g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		int w = getWidth(), h = getHeight();
		int ew = h * 3 / 8, eh = h / 16;

		g.setColor(Color.BLACK);
		g.drawString("Phi=" + phi, 50, 50);

		AffineTransform viewPort = new AffineTransform();
		viewPort.setToTranslation(w / 2, h / 2);
		viewPort.scale(1, -1);

		AffineTransform oldTransform = g.getTransform();
		g.transform(viewPort);

		// Achsen und Kreis
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, -h / 2, 0, h / 2);
		g.drawLine(-w / 2, 0, w / 2, 0);
		g.drawOval(-ew, -ew, 2 * ew, 2 * ew);

		Ellipse2D.Float ellipse = new Ellipse2D.Float(0, -eh / 2, ew, eh);

		/*@formatter:off*/
		IntStream.of(0, 120, 240)
			.forEach(angle -> {
				g.rotate(toRadians(phi + angle));
				g.setColor(angle == 0 ? Color.BLACK : angle == 120 ? Color.RED : Color.YELLOW);
				g.fill(ellipse);
				g.rotate(-toRadians(phi + angle));
			});
		/*@formatter:on*/

		g.transform(oldTransform);
	}

}
