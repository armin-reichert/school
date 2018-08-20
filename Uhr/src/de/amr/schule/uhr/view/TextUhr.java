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

public class TextUhr extends JPanel {

	private int fontSize;
	private Color farbe;
	private Uhrwerk uhrwerk;

	public TextUhr(int width, int fontSize, Color color) {
		this.fontSize = fontSize;
		this.farbe = color;
		setOpaque(false);
		setPreferredSize(new Dimension(width, fontSize * 2));
	}

	public void setUhrwerk(Uhrwerk uhrwerk) {
		this.uhrwerk = uhrwerk;
		uhrwerk.beiÄnderung(e -> repaint());
	}

	public void setFarbe(Color farbe) {
		this.farbe = farbe;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g_) {
		Graphics2D g = (Graphics2D) g_;
		g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Courier New", BOLD, fontSize));
		g.setColor(farbe);
		String text = "0 Stunden 0 Minuten 0 Sekunden";
		if (uhrwerk != null) {
			text = String.format("%02d Stunden %02d Minuten %02d Sekunden", uhrwerk.stunde(), uhrwerk.minute(),
					uhrwerk.sekunde());
		}
		g.drawString(text, 10, 30);
	}
}