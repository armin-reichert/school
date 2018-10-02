package de.amr.schule.crypto.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class CryptoWindow extends JFrame {

	private Action actionExitApp = new AbstractAction("Exit") {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	private final Action actionGartenzaun2Zeilen = new AbstractAction("Gartenzaun (2 Zeilen)") {

		@Override
		public void actionPerformed(ActionEvent e) {
			cryptoPanel.setAlgorithm(Algorithm.GARTENZAUN);
			cryptoPanel.setGartenzaunZeilen(2);
		}
	};

	private final Action actionGartenzaun3Zeilen = new AbstractAction("Gartenzaun (3 Zeilen)") {

		@Override
		public void actionPerformed(ActionEvent e) {
			cryptoPanel.setAlgorithm(Algorithm.GARTENZAUN);
			cryptoPanel.setGartenzaunZeilen(3);
		}
	};

	private final Action actionKamasutra = new AbstractAction("Kamasutra") {

		@Override
		public void actionPerformed(ActionEvent e) {
			cryptoPanel.setAlgorithm(Algorithm.KAMASUTRA);

		}
	};

	private final Action actionCaesar = new AbstractAction("Cäsar") {

		@Override
		public void actionPerformed(ActionEvent e) {
			cryptoPanel.setAlgorithm(Algorithm.CAESAR);
		}
	};

	private CryptoPanel cryptoPanel;
	private final ButtonGroup algorithmSelection = new ButtonGroup();
	private JRadioButtonMenuItem rbGartenzaun2Rows;
	private JRadioButtonMenuItem rbGartenzaun3Rows;

	public CryptoWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Kryptographie");

		cryptoPanel = new CryptoPanel();
		getContentPane().add(cryptoPanel, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnApp = new JMenu("App");
		menuBar.add(mnApp);

		JMenuItem miExit = new JMenuItem("Exit");
		miExit.setAction(actionExitApp);
		mnApp.add(miExit);

		JMenu mnAlgorithmus = new JMenu("Algorithmus");
		menuBar.add(mnAlgorithmus);

		JRadioButtonMenuItem miKamasutra = new JRadioButtonMenuItem("Kamasutra");
		miKamasutra.setAction(actionKamasutra);
		algorithmSelection.add(miKamasutra);
		mnAlgorithmus.add(miKamasutra);

		JRadioButtonMenuItem miCaesar = new JRadioButtonMenuItem("Cäsar");
		miCaesar.setAction(actionCaesar);
		algorithmSelection.add(miCaesar);
		mnAlgorithmus.add(miCaesar);

		rbGartenzaun2Rows = new JRadioButtonMenuItem("2 Zeilen");
		algorithmSelection.add(rbGartenzaun2Rows);
		mnAlgorithmus.add(rbGartenzaun2Rows);
		rbGartenzaun2Rows.setAction(actionGartenzaun2Zeilen);

		rbGartenzaun3Rows = new JRadioButtonMenuItem("3 Zeilen");
		algorithmSelection.add(rbGartenzaun3Rows);
		mnAlgorithmus.add(rbGartenzaun3Rows);
		rbGartenzaun3Rows.setAction(actionGartenzaun3Zeilen);

		// intialize selection
		if (cryptoPanel.getGartenzaunZeilen() == 2) {
			rbGartenzaun2Rows.setSelected(true);
		} else if (cryptoPanel.getGartenzaunZeilen() == 3) {
			rbGartenzaun3Rows.setSelected(true);
		}

	}
}
