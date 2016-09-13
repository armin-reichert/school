package de.amr.schule.rotellipse;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Math.toRadians;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class EllipsePanel extends JPanel {

	public static void main(String[] args) {
		JFrame win = new JFrame("Rotierende Ellipse");
		win.add(new EllipsePanel());
		win.pack();
		win.setDefaultCloseOperation(EXIT_ON_CLOSE);
		win.setVisible(true);
	}

	private int phi = 0;
	private int delta = 2;
	private Timer animation = new Timer(5, e -> {
		phi = (phi + delta) % 360;
		repaint();
	});

	public EllipsePanel() {
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.WHITE);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		animation.start();
	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		int w = getWidth(), h = getHeight();
		int ew = h * 3 / 8, eh = h / 16;

		AffineTransform t = new AffineTransform();
		t.setToTranslation(w / 2, h / 2);
		AffineTransform ot = g.getTransform();
		g.transform(t);

		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, -h / 2, 0, h / 2);
		g.drawLine(-w / 2, 0, w / 2, 0);
		g.drawOval(-ew, -ew, 2 * ew, 2 * ew);

		g.setColor(Color.BLACK);
		g.drawString("Phi=" + phi, 200, 200);

		Ellipse2D.Float ellipse = new Ellipse2D.Float(0, -eh / 2, ew, eh);

		g.rotate(toRadians(phi));
		g.setColor(Color.BLACK);
		g.fill(ellipse);
		g.rotate(-toRadians(phi));

		double phi_ = toRadians((phi + 120)) % 360;
		g.rotate(phi_);
		g.setColor(Color.RED);
		g.fill(ellipse);
		g.rotate(-phi_);

		phi_ = toRadians((phi + 240)) % 360;
		g.rotate(phi_);
		g.setColor(Color.YELLOW);
		g.fill(ellipse);
		g.rotate(-phi_);

		g.transform(ot);
	}

}
