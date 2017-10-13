package de.amr.schule.graphdrawing.controller;

import java.util.HashSet;
import java.util.Set;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.GraphDrawingView;
import de.amr.schule.graphdrawing.view.IGraphDrawingView;

public class GraphDrawingController {

	private GraphDrawingModel model;
	private final Set<IGraphDrawingView> views = new HashSet<>();

	public void addView(IGraphDrawingView view) {
		views.add(view);
	}

	public GraphDrawingController(GraphDrawingModel model) {
		this.model = model;
	}

	public void functionChanged() {
		model.computePoints();
		for (IGraphDrawingView view : views) {
			view.update();
		}
	}

	public void updateInterval(int width, int originX) {
		double xmin = -(double) originX / model.getXscale();
		double xmax = (double) (width - originX) / model.getXscale();
		model.setXmin(xmin);
		model.setXmax(xmax);
		model.computePoints();
		for (IGraphDrawingView view : views) {
			view.update();
		}
	}

	public void changeXScale(int xscale) {
		model.setXscale(xscale);
		// hack
		for (IGraphDrawingView view : views) {
			if (view instanceof GraphDrawingView) {
				GraphDrawingView gdv = (GraphDrawingView) view;
				updateInterval(gdv.getWidth(), gdv.getOriginX());
			}
		}
	}

	public void changeYScale(int yscale) {
		model.setYscale(yscale);
		// hack
		for (IGraphDrawingView view : views) {
			if (view instanceof GraphDrawingView) {
				GraphDrawingView gdv = (GraphDrawingView) view;
				updateInterval(gdv.getWidth(), gdv.getOriginX());
			}
		}
	}

}