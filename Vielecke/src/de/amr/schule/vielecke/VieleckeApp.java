package de.amr.schule.vielecke;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.geom.Path2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class VieleckeApp extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(VieleckeApp::new);
	}

	private static Path2D n_eck(int n, int r) {
		double segment = 360.0 / n;
		double angle = 0;
		Path2D n_eck = new Path2D.Float();
		n_eck.moveTo(r * cos(angle), r * sin(angle));
		for (int i = 1; i < n; ++i) {
			angle += segment;
			double angle_rad = toRadians(angle);
			n_eck.lineTo(r * cos(angle_rad), r * sin(angle_rad));
		}
		n_eck.closePath();
		return n_eck;
	}

	static final int PANEL_BREITE = 800;
	static final int PANEL_HÖHE = 580;

	private int radius = 200;
	private int anzahlEcken = 3;
	private Zeichenfläche zeichenFläche;
	private JToolBar toolbar;
	private KontrollBereich kontrollBereich;

	public VieleckeApp() {
		setTitle("Vielecke");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(toolbar = new JToolBar(), BorderLayout.NORTH);
		add(zeichenFläche = new Zeichenfläche(PANEL_BREITE, PANEL_HÖHE, radius), BorderLayout.CENTER);
		add(kontrollBereich = new KontrollBereich(), BorderLayout.SOUTH);
		toolbarFüllen();
		kontrollBereichInit();
		pack();
		setVisible(true);
	}

	private void kontrollBereichInit() {
		kontrollBereich.getRadiusSlider().setMaximum(PANEL_HÖHE / 2);
		kontrollBereich.getRadiusSlider().setValue(radius);
		kontrollBereich.getRadiusSlider().addChangeListener(e -> {
			radius = kontrollBereich.getRadiusSlider().getValue();
			zeichenFläche.setRadius(radius);
		});
		kontrollBereich.getEckenSlider().setMaximum(30);
		kontrollBereich.getEckenSlider().setValue(anzahlEcken);
		kontrollBereich.getEckenSlider().addChangeListener(e -> {
			anzahlEcken = kontrollBereich.getEckenSlider().getValue();
		});
	}

	private void toolbarFüllen() {
		JButton button;
		toolbar.add(button = new JButton("Alles löschen"));
		button.addActionListener(this::action_alles_löschen);
		toolbar.addSeparator();
		for (int n = 3; n <= 12; ++n) {
			toolbar.add(button = new JButton(n + "-Eck"));
			button.setActionCommand(String.valueOf(n));
			button.addActionListener(this::action_erzeuge_n_eck);
		}
		toolbar.addSeparator();
		toolbar.add(button = new JButton("N-Eck"));
		button.addActionListener(this::action_erzeuge_n_eck);
	}

	private void action_alles_löschen(ActionEvent e) {
		zeichenFläche.löschen();
	}

	private void action_erzeuge_n_eck(ActionEvent e) {
		int ecken = anzahlEcken;
		try {
			ecken = Integer.parseInt(e.getActionCommand());
		} catch (NumberFormatException x) {
			// egal
		}
		zeichenFläche.figurHinzufügen(n_eck(ecken, radius));
	}
}