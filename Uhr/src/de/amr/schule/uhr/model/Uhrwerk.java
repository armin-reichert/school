package de.amr.schule.uhr.model;

import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

import javax.swing.Timer;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.assets.Sound;

/**
 * Modell einer Uhr mit Tick-Funktion.
 */
public class Uhrwerk {

	private int std;
	private int min;
	private int sek;

	private final Timer timer;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private boolean soundAn;
	private Assets assets = new Assets();

	public Uhrwerk() {
		soundAn = false;
		timer = new Timer(0, e -> tick());
		timer.setDelay(1000);
		setzeAktuelleZeit();
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void beiÄnderung(PropertyChangeListener zuhörer) {
		pcs.addPropertyChangeListener(zuhörer);
	}

	public void setzeAktuelleZeit() {
		Calendar zeit = Calendar.getInstance();
		std = zeit.get(HOUR);
		min = zeit.get(MINUTE);
		sek = zeit.get(SECOND);
	}

	private void tick() {
		sek = sek + 1;
		if (sek == 60) {
			sek = 0;
			min = min + 1;
			if (min == 60) {
				min = 0;
				std = std + 1;
				if (std == 12) {
					std = 0;
				}
			}
		}
		neueZeitVerkünden();
		if (soundAn) {
			assets.sound("tick.mp3").play();
		}
	}

	private void neueZeitVerkünden() {
		pcs.firePropertyChange("tick", false, true);
	}

	public void tickSoundEinAus(boolean b) {
		soundAn = b;
	}

	public boolean tickSoundAn() {
		return soundAn;
	}

	public int sekunde() {
		return sek;
	}

	public int minute() {
		return min;
	}

	public int stunde() {
		return std;
	}

	public Uhrwerk stunde(int neueStunde) {
		if (neueStunde >= 0 && neueStunde <= 11) {
			std = neueStunde;
			neueZeitVerkünden();
		} else {
			throw new IllegalStateException("Ungültige Stunde: " + neueStunde);
		}
		return this;
	}

	public Uhrwerk minute(int neueMinute) {
		if (neueMinute >= 0 && neueMinute <= 59) {
			min = neueMinute;
			neueZeitVerkünden();
		} else {
			throw new IllegalStateException("Ungültige Minute: " + neueMinute);
		}
		return this;
	}

	/**
	 * Setzt einen neuen Sekundenwert. Falls dieser Wert ungültig ist, wird eine Exception ausgelöst.
	 * 
	 * @param neueSekunde
	 *          gibt den neuen Sekundenwert an
	 * @return das Uhrwerk
	 */
	public Uhrwerk sekunde(int neueSekunde) {
		if (neueSekunde >= 0 && neueSekunde <= 59) {
			sek = neueSekunde;
			neueZeitVerkünden();
		} else {
			throw new IllegalStateException("Ungültige Sekunde: " + neueSekunde);
		}
		return this;
	}

	@Override
	public String toString() {
		return std + ":" + min + ":" + sek;
	}

	public void spieleStundenGong() {
		if (soundAn) {
			Sound gong = assets.sound("stunde.mp3");
			gong.play();
		}
	}
}
