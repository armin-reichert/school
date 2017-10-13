package de.amr.schule.graphdrawing.model;

import java.util.ArrayList;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class GraphDrawingModel {

	private int xscale;
	private int yscale;

	private final List<GraphPoint> points;

	private double xmin;
	private double xmax;
	private double step;

	// Allgemeine Funktion
	private Expression term;

	public GraphDrawingModel() {
		points = new ArrayList<>();
		step = 1;
		term = new ExpressionBuilder("x").variable("x").build();
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
			term.setVariable("x", x);
			p.fx = term.evaluate();
			if (!new Double(p.fx).isNaN()) {
				points.add(p);
			}
			x += step;
		}
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

	public List<GraphPoint> getPoints() {
		return points;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public Expression getTerm() {
		return term;
	}

	public void setTerm(Expression term) {
		this.term = term;
	}

}