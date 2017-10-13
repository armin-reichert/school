package de.amr.schule.graphdrawing.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@SuppressWarnings("serial")
public class ConfigView extends JPanel implements IGraphDrawingView {
	
	private static final Font TEXT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);

	private GraphDrawingModel model;
	private GraphDrawingController controller;

	private JTextField fieldStep;
	private JTextField fieldTerm;
	private JSlider sliderXScale, sliderYScale;

	public ConfigView(GraphDrawingModel model, GraphDrawingController controller) {
		this.model = model;
		this.controller = controller;

		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(4, 2));
		add(grid, BorderLayout.CENTER);

		JLabel labelStep = new JLabel("step=");
		fieldStep = new JTextField();
		fieldStep.setText(String.valueOf(model.getStep()));

		JLabel labelTerm = new JLabel("function=");
		fieldTerm = new JTextField();
		fieldTerm.setFont(TEXT_FONT);
		fieldTerm.setText("x");

		JLabel labelXScale = new JLabel("xscale=");
		sliderXScale = new JSlider();
		sliderXScale.setMinimum(5);
		sliderXScale.setMaximum(100);
		sliderXScale.setValue(model.getXscale());
		sliderXScale.setMinorTickSpacing(5);
		sliderXScale.setMajorTickSpacing(20);
		sliderXScale.setPaintTicks(true);
		sliderXScale.addChangeListener(e -> xscaleChanged());

		JLabel labelYScale = new JLabel("yscale=");
		sliderYScale = new JSlider();
		sliderYScale.setMinimum(5);
		sliderYScale.setMaximum(100);
		sliderYScale.setValue(model.getYscale());
		sliderYScale.setMinorTickSpacing(5);
		sliderYScale.setMajorTickSpacing(20);
		sliderYScale.setPaintTicks(true);
		sliderYScale.addChangeListener(e -> yscaleChanged());

		grid.add(labelTerm);
		grid.add(fieldTerm);
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
		try {
			Expression term = new ExpressionBuilder(fieldTerm.getText()).variables("x").build();
			model.setTerm(term);
			// TODO validate expression
			model.setStep(Double.parseDouble(fieldStep.getText()));
		} catch (NumberFormatException x) {
			fieldStep.setText(String.valueOf(model.getStep()));
			return;
		}
		controller.functionChanged();
	}
}
