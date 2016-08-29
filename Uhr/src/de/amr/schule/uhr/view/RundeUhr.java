package de.amr.schule.uhr.view;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import de.amr.schule.uhr.model.Uhrwerk;

public class RundeUhr extends JPanel {

	protected int radius;
	protected Uhrwerk uhrwerk;

	public RundeUhr(int radius) {
		this.radius = radius;
		int size = 2 * radius;
		setPreferredSize(new Dimension(size, size));
		setMinimumSize(getPreferredSize());
	}

	public void setUhrwerk(Uhrwerk uhrwerk) {
		this.uhrwerk = uhrwerk;
		uhrwerk.beiÄnderung(e -> repaint());
	}

	protected Point2D stdZeigerSpitze(int std, int min, int radius) {
		double phi = Math.toRadians(450 - (std * 30 + min * 0.5));
		return new Point2D.Double(radius * cos(phi), radius * sin(phi));
	}

	protected Point2D minZeigerSpitze(int min, int radius) {
		double phi = Math.toRadians(450 - 6 * min);
		return new Point2D.Double(radius * cos(phi), radius * sin(phi));
	}

	protected Point2D sekZeigerSpitze(int sek, int radius) {
		double phi = Math.toRadians(450 - 6 * sek);
		return new Point2D.Double(radius * cos(phi), radius * sin(phi));
	}
}