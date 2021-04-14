package de.amr.schule.graphdrawing.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;
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
		updateViews();
	}

	public void changeFunctionTerm(String termText) throws IllegalArgumentException {
		Expression term = new ExpressionBuilder(termText).variables("x").build();
		if (term.validate(false) != ValidationResult.SUCCESS) {
			throw new IllegalArgumentException();
		}
		model.setTerm(term);
		updateViews();
	}

	public void changeXRange(int canvasWidth, int originX) {
		double xmin = -(double) originX / model.getXscale();
		double xmax = (double) (canvasWidth - originX) / model.getXscale();
		model.setInterval(xmin, xmax);
		updateViews();
	}

	public void changeXScale(int xScale, int canvasWidth, int originX) {
		model.setXscale(xScale);
		changeXRange(canvasWidth, originX);
	}

	public void changeYScale(int yScale, int canvasWidth, int originX) {
		model.setXscale(yScale);
		changeXRange(canvasWidth, originX);
	}
}