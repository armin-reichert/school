package de.amr.schule.graphdrawing.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.IGraphDrawingView;

@SuppressWarnings("serial")
public class ConfigView extends JPanel implements IGraphDrawingView {

	private GraphDrawingModel model;
	private GraphDrawingController controller;

	private JLabel functionLabel;
	private JTextField fieldA, fieldB, fieldC;
	private JTextField fieldStep;
	private JSlider sliderXScale, sliderYScale;

	public ConfigView(GraphDrawingModel model, GraphDrawingController controller) {
		this.model = model;
		this.controller = controller;

		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());
		functionLabel = new JLabel();
		functionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateText();
		add(functionLabel, BorderLayout.NORTH);

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(8, 2));
		add(grid, BorderLayout.CENTER);

		fieldA = new JTextField();
		fieldA.setText(String.valueOf(model.getA()));
		fieldB = new JTextField();
		fieldB.setText(String.valueOf(model.getB()));
		fieldC = new JTextField();
		fieldC.setText(String.valueOf(model.getC()));
		fieldStep = new JTextField();
		fieldStep.setText(String.valueOf(model.getStep()));

		JLabel labelA = new JLabel("a=");
		JLabel labelB = new JLabel("b=");
		JLabel labelC = new JLabel("c=");
		JLabel labelStep = new JLabel("step=");

		sliderXScale = new JSlider();
		sliderXScale.setMinimum(5);
		sliderXScale.setMaximum(100);
		sliderXScale.setValue(model.getXscale());
		sliderXScale.setMinorTickSpacing(5);
		sliderXScale.setMajorTickSpacing(20);
		sliderXScale.setPaintTicks(true);
		
		sliderXScale.addChangeListener(e -> xscaleChanged());
		
		sliderYScale = new JSlider();
		sliderYScale.setMinimum(5);
		sliderYScale.setMaximum(100);
		sliderYScale.setValue(model.getYscale());
		sliderYScale.setMinorTickSpacing(5);
		sliderYScale.setMajorTickSpacing(20);
		sliderYScale.setPaintTicks(true);
		
		sliderYScale.addChangeListener(e -> yscaleChanged());
		
		
		JLabel labelXScale = new JLabel("xscale=");
		JLabel labelYScale = new JLabel("yscale=");
		
		grid.add(labelA);
		grid.add(fieldA);
		grid.add(labelB);
		grid.add(fieldB);
		grid.add(labelC);
		grid.add(fieldC);
		grid.add(labelStep);
		grid.add(fieldStep);
		grid.add(labelXScale);
		grid.add(sliderXScale);
		grid.add(labelYScale);
		grid.add(sliderYScale);
		

		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(this::okPressed);
		add(buttonOK, BorderLayout.SOUTH);
	}

	private void xscaleChanged() {
		controller.changeXScale(sliderXScale.getValue());
	}
	
	private void yscaleChanged() {
		controller.changeYScale(sliderYScale.getValue());
	}
	
	@Override
	public void update() {

	}

	private void okPressed(ActionEvent e) {
		// Eingaben pr�fen
		try {
			model.setA(Double.parseDouble(fieldA.getText()));
			model.setB(Double.parseDouble(fieldB.getText()));
			model.setC(Double.parseDouble(fieldC.getText()));
			model.setStep(Double.parseDouble(fieldStep.getText()));
		} catch (NumberFormatException x) {
			fieldA.setText(String.valueOf(model.getA()));
			fieldB.setText(String.valueOf(model.getB()));
			fieldC.setText(String.valueOf(model.getC()));
			fieldStep.setText(String.valueOf(model.getStep()));
			return;
		}
		updateText();
		controller.functionChanged();
	}

	private void updateText() {
		String text = String.format("%g * x^2 + %g * x + %g", model.getA(), model.getB(), model.getC());
		functionLabel.setText(text);
	}

}
