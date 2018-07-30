package de.amr.schule.uhr.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private Image image;
	private Color farbe;

	public ImagePanel(Image image) {
		this.image = image;
	}

	public void setzeBild(Image image) {
		this.image = image;
		this.farbe = null;
		repaint();
	}

	public void setzeFarbe(Color farbe) {
		this.farbe = farbe;
		this.image = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} else {
			g.setColor(farbe);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}