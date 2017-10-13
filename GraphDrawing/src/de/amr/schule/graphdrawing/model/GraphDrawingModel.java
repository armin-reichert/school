package de.amr.schule.graphdrawing.model;

import java.util.ArrayList;
import java.util.List;

public class GraphDrawingModel {

	private int xscale;
	private int yscale;

	private double xmin;
	private double xmax;
	private double step;

	private double a;
	private double b;
	private double c;

	double sx;
	double sy;

	private final List<GraphPoint> points;
	private final List<Double> roots;

	public GraphDrawingModel() {
		points = new ArrayList<>();
		roots = new ArrayList<>();
		step = 1;
		a = 1;
		c = -3;
	}

	public int getXscale() {
		return xscale;
	}

	public void setXscale(int xscale) {
		this.xscale = xscale;
	}

	public int getYscale() {
		return yscale;
	}

	public void setYscale(int yscale) {
		this.yscale = yscale;
	}

	public void computePoints() {
		points.clear();
		double x = xmin;
		while (x <= xmax) {
			GraphPoint p = new GraphPoint();
			p.x = x;
			p.fx = a * x * x + b * x + c;
			points.add(p);
			x += step;
		}
	}

	public void computeRoots() {
		roots.clear();
		if (a == 0) {
			if (b == 0) {
				if (c == 0) {
					// allgemeingültig
					// TODO
				} else {
					// keine Lösung
					// TODO
				}
			} else {
				double x = -c / b;
				roots.add(x);
			}
		} else {
			double d = b * b / (4 * a * a) - c / a;
			if (d < 0) {
				// keine Lösung
			} else if (d == 0) {
				double x = -b / (2 * a);
				roots.add(x);
			} else {
				double x1 = -b / (2 * a) + Math.sqrt(d);
				double x2 = -b / (2 * a) - Math.sqrt(d);
				roots.add(x1);
				roots.add(x2);
			}
		}
	}

	public void computeVertexPoint() {
		sx = -b / (2 * a);
		sy = (4 * a * c - b * b) / (4 * a);
	}

	public double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getSx() {
		return sx;
	}

	public double getSy() {
		return sy;
	}

	public List<Double> getRoots() {
		return roots;
	}

	public List<GraphPoint> getPoints() {
		return points;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

}