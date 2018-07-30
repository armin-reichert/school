package de.amr.schule.koord.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CoordSystem {

	public final double xmin;
	public final double xmax;
	public final double ymin;
	public final double ymax;

	private final Map<String, Function<Double, Double>> functionMap;

	public CoordSystem(double xmin, double xmax, double ymin, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		functionMap = new HashMap<>();
	}

	public void addFunction(String id, Function<Double, Double> f) {
		functionMap.put(id, f);
	}

	public void addFunction(Function<Double, Double> f) {
		functionMap.put("f" + functionMap.size(), f);
	}

	public void removeFunction(String id) {
		functionMap.remove(id);
	}

	public Function<Double, Double> getFunction(String id) {
		return functionMap.get(id);
	}

	public Iterable<Map.Entry<String, Function<Double, Double>>> functions() {
		return functionMap.entrySet();
	}
}
