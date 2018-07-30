package de.amr.schule.uhr.view;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.amr.easy.game.assets.Assets;
import de.amr.schule.uhr.controller.UhrController;
import de.amr.schule.uhr.model.Uhrwerk;
import net.miginfocom.swing.MigLayout;

public class UhrFenster extends JFrame {

	private final ImagePanel contentPanel;
	private final BahnhofsUhr bahnhofsUhr;
	private final DigitalUhr digitalUhr;
	private final TextUhr textUhr;
	private Color textFarbe = Color.BLUE;

	private Action action_farbeÄndern = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color gewählteFarbe = JColorChooser.showDialog(UhrFenster.this, "Textfarbe auswählen",
					textFarbe);
			if (gewählteFarbe != null) {
				textFarbe = gewählteFarbe;
				bahnhofsUhr.setFarbe(gewählteFarbe);
				digitalUhr.setFarbe(gewählteFarbe);
				textUhr.setFarbe(gewählteFarbe);
			}
		}
	};

	private Action action_hintergrundBildÄndern = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser bildAuswahl = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
			bildAuswahl.setFileFilter(filter);
			int returnVal = bildAuswahl.showOpenDialog(UhrFenster.this);
			if (returnVal == APPROVE_OPTION) {
				File file = bildAuswahl.getSelectedFile();
				setzeHintergrundBild(file);
			}
		}
	};

	private Action action_hintergrundFarbeÄndern = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color gewählteFarbe = JColorChooser.showDialog(UhrFenster.this, "Hintergrundfarbe auswählen",
					Color.BLUE);
			if (gewählteFarbe != null) {
				contentPanel.setzeFarbe(gewählteFarbe);
			}
		}
	};

	public UhrFenster() {
		super("Uhr");
		Uhrwerk uhrwerk = new Uhrwerk();
		bahnhofsUhr = new BahnhofsUhr(150);
		bahnhofsUhr.setUhrwerk(uhrwerk);
		bahnhofsUhr.setFarbe(textFarbe);
		digitalUhr = new DigitalUhr(250, 50, 48);
		digitalUhr.setUhrwerk(uhrwerk);
		digitalUhr.setFarbe(textFarbe);
		textUhr = new TextUhr(500, 30, textFarbe);
		textUhr.setUhrwerk(uhrwerk);
		JPanel viewsArea = new JPanel(new MigLayout("", "[][][grow]", "[][30px][]"));
		viewsArea.setOpaque(false);
		viewsArea.add(bahnhofsUhr, "cell 0 0");
		viewsArea.add(digitalUhr, "cell 1 0 2 1,alignx center");
		viewsArea.add(textUhr, "cell 0 2 3 1,growx");
		UhrController uhrenController = new UhrController();
		uhrenController.setUhrwerk(uhrwerk);
		contentPanel = new ImagePanel(Assets.image("zwick.jpg"));
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(viewsArea, BorderLayout.CENTER);
		contentPanel.add(uhrenController, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		contentPanel.getInputMap().put(KeyStroke.getKeyStroke("F"), "farbeÄndern");
		contentPanel.getActionMap().put("farbeÄndern", action_farbeÄndern);
		contentPanel.getInputMap().put(KeyStroke.getKeyStroke("H"), "hintergrundBildÄndern");
		contentPanel.getActionMap().put("hintergrundBildÄndern", action_hintergrundBildÄndern);
		contentPanel.getInputMap().put(KeyStroke.getKeyStroke("control H"), "hintergrundFarbeÄndern");
		contentPanel.getActionMap().put("hintergrundFarbeÄndern", action_hintergrundFarbeÄndern);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		// Uhr starten
		uhrwerk.start();
	}

	private void setzeHintergrundBild(File file) {
		try {
			BufferedImage bild = ImageIO.read(file);
			contentPanel.setzeBild(bild);
		} catch (IOException e) {
			System.out.println("Bild konnte nicht gelesen werden: " + file);
		}
	}
}