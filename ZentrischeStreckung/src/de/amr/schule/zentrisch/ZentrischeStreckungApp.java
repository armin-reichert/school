package de.amr.schule.zentrisch;

import java.awt.EventQueue;

public class ZentrischeStreckungApp {

	public static void main(String[] args) {

		ZentrischeStreckungFenster fenster = new ZentrischeStreckungFenster(1024,700);
		EventQueue.invokeLater(() -> fenster.setVisible(true));
	}

}
