package de.amr.schule.graphdrawing.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import de.amr.schule.graphdrawing.controller.GraphDrawingController;
import de.amr.schule.graphdrawing.model.GraphDrawingModel;
import de.amr.schule.graphdrawing.view.api.GraphDrawingViewController;

public class ConfigView extends JPanel implements GraphDrawingViewController {

	private static final Font TEXT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);

	private final GraphDrawingModel model;
	private GraphDrawingController controller;
	private final CanvasView canvasView;

	private JTextField fieldStep;
	private JTextField fieldTerm;
	private JSlider sliderXScale, sliderYScale;
	private JButton buttonOK;

	public ConfigView(GraphDrawingModel model, CanvasView canvasView) {
		this.model = model;
		this.canvasView = canvasView;

		fieldTerm = new JTextField();
		fieldTerm.setFont(TEXT_FONT);
		fieldTerm.setText(GraphDrawingModel.DEFAULT_TERM_TEXT);

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
		grid.add(new JLabel("Funktionsterm"));
		grid.add(fieldTerm);
		grid.add(new JLabel("Schrittweite"));
		grid.add(fieldStep);
		grid.add(new JLabel("Skalierung x-Achse"));
		grid.add(sliderXScale);
		grid.add(new JLabel("Skalierung y-Achse"));
		grid.add(sliderYScale);

		buttonOK = new JButton("OK");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buttonOK);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(grid);
		add(buttonPanel);

		registerEventHandlers();
	}

	@Override
	public void setController(GraphDrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void update() {
		// Nothing to do
	}

	private void registerEventHandlers() {
		sliderXScale.addChangeListener(this::xscaleChanged);
		sliderYScale.addChangeListener(this::yscaleChanged);
		buttonOK.addActionListener(this::okPressed);
	}

	private void xscaleChanged(ChangeEvent e) {
		controller.changeXScale(sliderXScale.getValue(), canvasView.getWidth(), canvasView.getOriginX());
	}

	private void yscaleChanged(ChangeEvent e) {
		controller.changeYScale(sliderYScale.getValue(), canvasView.getWidth(), canvasView.getOriginX());
	}

	private void okPressed(ActionEvent e) {
		List<String> errors = new ArrayList<>();
		try {
			controller.changeFunctionTerm(fieldTerm.getText());
		} catch (IllegalArgumentException x) {
			errors.add("Ungültiger Funktionsterm: " + fieldTerm.getText());
		}
		try {
			controller.changeStep(fieldStep.getText());
		} catch (NumberFormatException nfe) {
			errors.add("Ungültige Schrittweite (keine Dezimalzahl): " + fieldStep.getText());
		}
		if (!errors.isEmpty()) {
			JOptionPane.showMessageDialog(null, String.join("\n", errors), "Eingabedaten prüfen!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public JButton getButtonOK() {
		return buttonOK;
	}
}