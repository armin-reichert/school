package de.amr.schule.graphdrawing.model;

public class GraphPoint {

	public final double x;
	public final double fx;

	public GraphPoint(double x, double fx) {
		this.x = x;
		this.fx = fx;
	}

	@Override
	public String toString() {
		return "(" + x + "," + fx + ")";
	}
}
