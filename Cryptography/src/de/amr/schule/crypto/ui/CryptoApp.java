package de.amr.schule.crypto.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
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
		JFrame window = new JFrame();
		window.setTitle("Peter's Crypto App");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(new CryptoPanel());
		window.pack();
		window.setVisible(true);
	}

}
