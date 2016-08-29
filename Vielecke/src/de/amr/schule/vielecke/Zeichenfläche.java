package de.amr.schule.vielecke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Zeichenfläche extends JPanel {

	private int radius;
	private List<Path2D> figuren = new ArrayList<>();

	public Zeichenfläche(int breite, int höhe, int radius) {
		this.radius = radius;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(breite, höhe));
		setMinimumSize(getPreferredSize());
	}

	public void setRadius(int radius) {
		this.radius = radius;
		repaint();
	}
	
	public void löschen() {
		figuren.clear();
		repaint();
	}
	
	public void figurHinzufügen(Path2D figur) {
		figuren.add(figur);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g_) {
		Graphics2D g = (Graphics2D) g_;
		super.paintComponent(g);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.translate(getWidth() / 2, getHeight() / 2);
		g.setColor(Color.LIGHT_GRAY);
		g.drawOval(-radius, -radius, 2 * radius, 2 * radius);
		for (Path2D figur : figuren) {
			g.setColor(Color.BLUE);
			g.draw(figur);
		}
	}
}
