package de.amr.schule.koord;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.EventQueue;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.Timer;

import de.amr.schule.koord.model.CoordSystem;
import de.amr.schule.koord.view.FunctionPlotView;

public class BspApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(BspApp::new);
	}

	private static abstract class AnimatedFunction {

		private String id;
		private Timer timer;
		private CoordSystem coord;
		private FunctionPlotView view;

		public AnimatedFunction(String id, CoordSystem coord, FunctionPlotView view) {
			this.id = id;
			this.coord = coord;
			this.view = view;
			timer = new Timer(0, e -> {
				coord.removeFunction(id);
				coord.addFunction(getFunction());
				view.updateGraphs();
				view.repaint();
			});
		}

		public void startAnimation() {
			timer.start();
		}

		protected abstract Function<Double, Double> getFunction();

		protected abstract double x();
	}

	private class Function1 extends AnimatedFunction {

		private double b;
		private double x;
		private double xmin;
		private double xmax;
		private double dx;

		Function1() {
			super(Function1.class.getName(), coord, view);
			b = 2;
			xmin = coord.xmin;
			xmax = coord.xmax;
			x = xmin;
			dx = 0.1;
		}

		@Override
		protected Function<Double, Double> getFunction() {
			return x -> Math.sin(b * x);
		}

		@Override
		protected double x() {
			double oldX = x;
			x += dx;
			if (x > xmax) {
				x = xmax;
				dx = -dx;
			} else if (x < xmin) {
				x = xmin;
				dx = -dx;
			}
			return oldX;
		}

	};

	private final CoordSystem coord;
	private final FunctionPlotView view;

	public BspApp() {
		coord = new CoordSystem(-10, 10, -10, 10);
		view = new FunctionPlotView(1200, 720, coord);
		Function1 f1 = new Function1();
		EventQueue.invokeLater(() -> {
			JFrame window = new JFrame("Funktionen Plotter");
			window.setDefaultCloseOperation(EXIT_ON_CLOSE);
			window.add(view);
			window.pack();
			window.setVisible(true);
			f1.startAnimation();
		});
	}
}