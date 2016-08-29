package de.amr.schule.zentrisch;

import javax.swing.JFrame;


public class ZentrischeStreckungFenster extends JFrame {
	
	Zeichenfläche zf;
	
	public ZentrischeStreckungFenster(int zfBreite, int zfHöhe) {
		setTitle("Zentrische Streckung");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		zf = new Zeichenfläche(zfBreite, zfHöhe);
		add(zf);
		pack();
	}

}
