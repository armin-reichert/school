package de.amr.schule.darts.counter;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class CounterApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> new CounterApp());
	}

	public CounterApp() {

		// UI erzeugen
		CounterWindow window = new CounterWindow();
		window.newGame();

		// UI anzeigen
		window.setVisible(true);
	}

}
