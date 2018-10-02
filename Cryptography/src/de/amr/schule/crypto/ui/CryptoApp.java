package de.amr.schule.crypto.ui;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class CryptoApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(CryptoApp::new);
	}

	public CryptoApp() {
		CryptoWindow window = new CryptoWindow();
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}