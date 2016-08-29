package de.amr.schule.vielecke;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.miginfocom.swing.MigLayout;

public class KontrollBereich extends JPanel {

	private JSlider radiusSlider;
	private JSlider eckenSlider;

	public KontrollBereich() {
		setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblRadius = new JLabel("Radius");
		add(lblRadius, "flowx,cell 0 0");

		radiusSlider = new JSlider();
		radiusSlider.setSnapToTicks(true);
		radiusSlider.setValue(100);
		radiusSlider.setPaintLabels(true);
		radiusSlider.setPaintTicks(true);
		radiusSlider.setMajorTickSpacing(100);
		radiusSlider.setMinorTickSpacing(10);
		radiusSlider.setMaximum(600);
		radiusSlider.setMinimum(20);
		add(radiusSlider, "cell 1 0,growx");

		JLabel lblAnzahlEcken = new JLabel("Anzahl Ecken");
		add(lblAnzahlEcken, "flowx,cell 0 1");

		eckenSlider = new JSlider();
		eckenSlider.setSnapToTicks(true);
		eckenSlider.setValue(3);
		eckenSlider.setPaintLabels(true);
		eckenSlider.setPaintTicks(true);
		eckenSlider.setMajorTickSpacing(1);
		eckenSlider.setMinorTickSpacing(1);
		eckenSlider.setMaximum(20);
		eckenSlider.setMinimum(3);
		add(eckenSlider, "cell 1 1,growx");
	}

	public JSlider getRadiusSlider() {
		return radiusSlider;
	}

	public JSlider getEckenSlider() {
		return eckenSlider;
	}
}
