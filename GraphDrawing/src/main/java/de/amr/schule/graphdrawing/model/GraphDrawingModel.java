package de.amr.schule.graphdrawing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class GraphDrawingModel {

	public static final String DEFAULT_TERM_TEXT = "x^2";
	public static final Expression DEFAULT_TERM = new ExpressionBuilder(DEFAULT_TERM_TEXT).variable("x").build();

	private final List<GraphPoint> points = new ArrayList<>();
	private int xscale;
	private int yscale;
	private double xmin;
	private double xmax;
	private double step;
	private Expression term;

	public GraphDrawingModel() {
		xscale = 20;
		yscale = 20;
		step = 0.1;
		xmin = -10;
		xmax = 10;
		term = DEFAULT_TERM;
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
		for (double x = xmin; x <= xmax; x += step) {
			Double value = term.setVariable("x", x).evaluate();
			if (!value.isNaN()) {
				points.add(new GraphPoint(x, value));
			}
		}
	}

	public double getXmin() {
		return xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setInterval(double xmin, double xmax) {
		this.xmin = xmin;
		this.xmax = xmax;
		computePoints();
	}

	public List<GraphPoint> getPoints() {
		return Collections.unmodifiableList(points);
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
		computePoints();
	}

	public Expression getTerm() {
		return term;
	}

	public void setTerm(Expression term) {
		this.term = term;
		computePoints();
	}

}