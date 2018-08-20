package de.amr.schule.uhr.view;

import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.amr.schule.uhr.model.Uhrwerk;

public class DigitalUhr extends JPanel {

	private Color farbe;
	private Font font;
	private Uhrwerk uhrwerk;

	public DigitalUhr(int width, int height, int fontSize) {
		this.font = new Font("Arial", BOLD, fontSize);
		farbe = Color.GREEN;
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(getPreferredSize());
		setOpaque(false);
	}

	public void setUhrwerk(Uhrwerk uhrwerk) {
		this.uhrwerk = uhrwerk;
		uhrwerk.beiÄnderung(e -> repaint());
		repaint();
	}

	public void setFarbe(Color farbe) {
		this.farbe = farbe;
		repaint();
	}

	public void setFontSize(int fontSize) {
		font = font.deriveFont((float) fontSize);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g_) {
		Graphics2D g = (Graphics2D) g_;
		super.paintComponent(g);
		g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(farbe);
		g.drawString(
				uhrwerk == null ? "00 : 00 : 00"
						: String.format("%02d : %02d : %02d", uhrwerk.stunde(), uhrwerk.minute(), uhrwerk.sekunde()),
				0, getHeight());
	}
}
