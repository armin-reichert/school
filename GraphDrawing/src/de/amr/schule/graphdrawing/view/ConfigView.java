package de.amr.schule.graphdrawing.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ConfigView extends JPanel implements IView {

	private static final Font TEXT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);

	private GraphDrawingModel model;
	private GraphDrawingController controller;

	private JTextField fieldStep;
	private JTextField fieldTerm;
	private JSlider sliderXScale, sliderYScale;
	private JButton buttonOK;

	public ConfigView(GraphDrawingModel model, GraphDrawingController controller) {
		this.model = model;
		this.controller = controller;

		setBackground(Color.LIGHT_GRAY);

		fieldTerm = new JTextField();
		fieldTerm.setFont(TEXT_FONT);
		fieldTerm.setText("x"); //TODO

		fieldStep = new JTextField();
		fieldStep.setFont(TEXT_FONT);
		fieldStep.setText(String.valueOf(model.getStep()));

		sliderXScale = new JSlider();
		sliderXScale.setMinimum(5);
		sliderXScale.setMaximum(300);
		sliderXScale.setValue(model.getXscale());
		sliderXScale.setMinorTickSpacing(10);
		sliderXScale.setMajorTickSpacing(100);
		sliderXScale.setPaintTicks(true);

		sliderYScale = new JSlider();
		sliderYScale.setMinimum(5);
		sliderYScale.setMaximum(300);
		sliderYScale.setValue(model.getYscale());
		sliderYScale.setMinorTickSpacing(10);
		sliderYScale.setMajorTickSpacing(100);
		sliderYScale.setPaintTicks(true);

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(4, 2));
		grid.add(new JLabel("Funktion"));
		grid.add(fieldTerm);
		grid.add(new JLabel("Schrittweite"));
		grid.add(fieldStep);
		grid.add(new JLabel("Skalierung x-Achse"));
		grid.add(sliderXScale);
		grid.add(new JLabel("Skalierung y-Achse"));
		grid.add(sliderYScale);

		buttonOK = new JButton("OK");

		JPanel south = new JPanel();
		south.add(buttonOK);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(grid);
		add(south);

		registerEventHandlers();
	}

	private void registerEventHandlers() {
		sliderXScale.addChangeListener(this::xscaleChanged);
		sliderYScale.addChangeListener(this::yscaleChanged);
		buttonOK.addActionListener(this::okPressed);

	}

	private void xscaleChanged(ChangeEvent e) {
		controller.changeXScale(sliderXScale.getValue());
	}

	private void yscaleChanged(ChangeEvent e) {
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
