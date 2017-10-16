package de.amr.schule.graphdrawing.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.CanvasView;
import de.amr.schule.graphdrawing.view.api.GraphDrawingView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

public class GraphDrawingController {

	private final GraphDrawingModel model;
	private final Set<GraphDrawingView> views = new HashSet<>();

	public GraphDrawingController(GraphDrawingModel model) {
		this.model = model;
	}

	public void addViews(GraphDrawingView... viewList) {
		Arrays.stream(viewList).forEach(views::add);
	}

	private void updateViews() {
		views.stream().forEach(GraphDrawingView::update);
	}

	public void changeStep(String stepText) throws NumberFormatException {
		model.setStep(Double.parseDouble(stepText));
		model.computePoints();
		updateViews();
	}

	public void changeFunctionTerm(String termText) throws IllegalArgumentException {
		Expression term = new ExpressionBuilder(termText).variables("x").build();
		if (term.validate(false) != ValidationResult.SUCCESS) {
			throw new IllegalArgumentException();
		}
		model.setTerm(term);
		model.computePoints();
		updateViews();
	}

	public void updateInterval(int width, int originX) {
		double xmin = -(double) originX / model.getXscale();
		double xmax = (double) (width - originX) / model.getXscale();
		model.setXmin(xmin);
		model.setXmax(xmax);
		model.computePoints();
		updateViews();
	}

	public void changeXScale(int xscale) {
		model.setXscale(xscale);
		// hack
		for (GraphDrawingView view : views) {
			if (view instanceof CanvasView) {
				CanvasView gdv = (CanvasView) view;
				updateInterval(gdv.getWidth(), gdv.getOriginX());
			}
		}
	}

	public void changeYScale(int yscale) {
		model.setYscale(yscale);
		// hack
		for (GraphDrawingView view : views) {
			if (view instanceof CanvasView) {
				CanvasView gdv = (CanvasView) view;
				updateInterval(gdv.getWidth(), gdv.getOriginX());
			}
		}
	}
}