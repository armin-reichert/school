package de.amr.schule.uhr.controller;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.amr.schule.uhr.model.Uhrwerk;
import net.miginfocom.swing.MigLayout;

public class UhrController extends JPanel {

	private Uhrwerk uhrwerk;

	private JSlider minSchieber = new JSlider();
	private JSlider stdSchieber = new JSlider();
	private JSlider sekSchieber = new JSlider();
	private JCheckBox stummSchalter;

	public void setUhrwerk(Uhrwerk uhrwerk) {
		this.uhrwerk = uhrwerk;

		stdSchieber.addChangeListener(e -> uhrwerk.stunde(stdSchieber.getValue() % 12));
		minSchieber.addChangeListener(e -> uhrwerk.minute(minSchieber.getValue() % 60));
		sekSchieber.addChangeListener(e -> uhrwerk.sekunde(sekSchieber.getValue() % 60));

		stummSchalter.addActionListener(e -> uhrwerk.tickSoundEinAus(!stummSchalter.isSelected()));
		stummSchalter.setSelected(!uhrwerk.tickSoundAn());

		uhrwerk.beiÄnderung(e -> {
			stdSchieber.setValue(uhrwerk.stunde());
			minSchieber.setValue(uhrwerk.minute());
			sekSchieber.setValue(uhrwerk.sekunde());
			if (uhrwerk.minute() == 0 && uhrwerk.sekunde() == 0) {
				uhrwerk.spieleStundenGong();
			}
		});
	}

	public UhrController() {
		setLayout(new MigLayout("", "", ""));
		minSchieber.setMinimum(0);
		minSchieber.setMaximum(60);
		minSchieber.setValue(0);
		minSchieber.setMinorTickSpacing(1);
		minSchieber.setMajorTickSpacing(15);
		minSchieber.setPaintTicks(true);
		minSchieber.setPaintLabels(true);
		stdSchieber.setPreferredSize(new Dimension(800, 40));
		stdSchieber.setMinimum(0);
		stdSchieber.setMaximum(12);
		stdSchieber.setValue(0);
		stdSchieber.setMinorTickSpacing(1);
		stdSchieber.setMajorTickSpacing(1);
		stdSchieber.setPaintTicks(true);
		stdSchieber.setPaintLabels(true);
		JLabel lblStunde = new JLabel("Stunde");
		add(lblStunde, "cell 0 0");
		add(stdSchieber, "cell 1 0,growx");
		JLabel lblMinute = new JLabel("Minute");
		add(lblMinute, "cell 0 1");
		add(minSchieber, "cell 1 1,growx");
		stummSchalter = new JCheckBox("Ton aus");
		JLabel lblSekunde = new JLabel("Sekunde");
		add(lblSekunde, "cell 0 2");
		sekSchieber.setValue(0);
		sekSchieber.setSnapToTicks(true);
		sekSchieber.setPaintLabels(true);
		sekSchieber.setPaintTicks(true);
		sekSchieber.setMinorTickSpacing(1);
		sekSchieber.setMajorTickSpacing(15);
		sekSchieber.setMaximum(60);
		add(sekSchieber, "cell 1 2,growx");
		add(stummSchalter, "cell 1 3");
	}
}
