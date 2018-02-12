package de.amr.schule.darts;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import de.amr.schule.darts.counter.ui.DartsCounterUI;

public class DartsCounterApp {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(DartsCounterApp::new);
	}

	public DartsCounterApp() {
		DartsCounterUI window = new DartsCounterUI();
		window.newGame(4, 501);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}