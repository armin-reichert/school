package de.amr.schule.uhr.view;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

public class BahnhofsUhr extends RundeUhr {

	private static final int MIN_MARK_LÄNGE = 6;
	private static final int STD_MARK_LÄNGE = 20;

	private static final int STD_ZEIGER_DICKE = 12;
	private static final int MIN_ZEIGER_DICKE = 6;
	private static final int SEK_ZEIGER_DICKE = 2;

	private static final Stroke STD_ZEIGER_STIFT = stift(STD_ZEIGER_DICKE);
	private static final Stroke MIN_ZEIGER_STIFT = stift(MIN_ZEIGER_DICKE);
	private static final Stroke SEK_ZEIGER_STIFT = stift(SEK_ZEIGER_DICKE);

	private static Stroke stift(int dicke) {
		return new BasicStroke(dicke, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
	}

	private Color farbe = Color.BLACK;

	public BahnhofsUhr(int radius) {
		super(radius);
		setOpaque(false);
	}

	public void setFarbe(Color farbe) {
		this.farbe = farbe;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics _g) {
		Graphics2D g = (Graphics2D) _g;
		super.paintComponent(_g);
		g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		g.translate(getWidth() / 2, getHeight() / 2);
		g.scale(1, -1);

		// Markierungen
		g.setColor(farbe);

		// Minuten-Ticks
		g.setStroke(SEK_ZEIGER_STIFT);
		for (int m = 0; m <= 59; ++m) {
			Point2D außen = minZeigerSpitze(m, radius);
			Point2D innen = minZeigerSpitze(m, radius - MIN_MARK_LÄNGE);
			g.drawLine((int) außen.getX(), (int) außen.getY(), (int) innen.getX(), (int) innen.getY());
		}

		// Stunden-Ticks
		g.setStroke(MIN_ZEIGER_STIFT);
		for (int h = 0; h <= 11; ++h) {
			Point2D außen = stdZeigerSpitze(h, 0, radius);
			Point2D innen = stdZeigerSpitze(h, 0, radius - STD_MARK_LÄNGE);
			g.drawLine((int) außen.getX(), (int) außen.getY(), (int) innen.getX(), (int) innen.getY());
		}

		// Zeiger
		int std = uhrwerk != null ? uhrwerk.stunde() : 0;
		int min = uhrwerk != null ? uhrwerk.minute() : 0;
		int sek = uhrwerk != null ? uhrwerk.sekunde() : 0;

		Point2D stdZeigerSpitze = stdZeigerSpitze(std, min, radius - STD_MARK_LÄNGE - STD_ZEIGER_DICKE);
		g.setStroke(STD_ZEIGER_STIFT);
		g.drawLine(0, 0, (int) stdZeigerSpitze.getX(), (int) stdZeigerSpitze.getY());

		Point2D minZeigerSpitze = minZeigerSpitze(min, radius - MIN_MARK_LÄNGE - MIN_ZEIGER_DICKE);
		g.setStroke(MIN_ZEIGER_STIFT);
		g.drawLine(0, 0, (int) minZeigerSpitze.getX(), (int) minZeigerSpitze.getY());

		Point2D sekZeigerSpitze = sekZeigerSpitze(sek, radius - MIN_MARK_LÄNGE - SEK_ZEIGER_DICKE);
		g.setStroke(SEK_ZEIGER_STIFT);
		g.setColor(Color.RED);
		g.drawLine(0, 0, (int) sekZeigerSpitze.getX(), (int) sekZeigerSpitze.getY());
	}
}