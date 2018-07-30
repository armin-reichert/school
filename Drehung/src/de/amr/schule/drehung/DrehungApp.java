package de.amr.schule.drehung;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.String.format;
import static java.lang.System.out;

import java.awt.geom.Point2D;

public class DrehungApp {

	public static void main(String[] args) {
		Point2D p = new Point2D.Double(5, 0);
		for (double phi = 0; phi <= 360; phi = phi + 30) {
			Point2D q = drehe(p, toRadians(phi));
			out.println(
					format("(%g|%g) drehe(%g) -> (%g|%g)", p.getX(), p.getY(), phi, q.getX(), q.getY()));
		}
	}

	public static Point2D drehe(Point2D p, double phi) {
		double cos = cos(phi), sin = sin(phi);
		return new Point2D.Double(cos * p.getX() - sin * p.getY(), sin * p.getX() + cos * p.getY());
	}
}