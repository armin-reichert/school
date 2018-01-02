package de.amr.schule.darts.counter;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.darts.counter.ui.DartsCounterUI;

public class CounterApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(CounterApp::new);
	}

	public CounterApp() {
		DartsCounterUI window = new DartsCounterUI();
		window.newGame(4);
		window.pack();
		window.setVisible(true);
	}
}