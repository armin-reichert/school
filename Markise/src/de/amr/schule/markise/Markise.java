package de.amr.schule.markise;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.statemachine.StateMachine;

public class Markise extends GameEntity {

	private final MarkiseApp app;
	final StateMachine<String, String> automat;

	private int position;
	private boolean esRegnet;

	public Markise(MarkiseApp app) {
		this.app = app;

		automat = new StateMachine<>("MarkisenSteuerung", String.class, "Eingefahren");

		// Eingefahren

		automat.changeOnInput("down", "Eingefahren", "FährtAus", () -> !esRegnet);

		// FährtAus

		automat.state("FährtAus").update = s -> ausfahren();

		automat.change("FährtAus", "Ausgefahren", this::endpunktErreicht);

		automat.changeOnInput("stop", "FährtAus", "Gestoppt");

		automat.changeOnInput("up", "FährtAus", "FährtEin");

		automat.change("FährtAus", "Gestoppt", () -> esRegnet);

		// Ausgefahren

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		automat.change("Ausgefahren", "FährtEin", () -> esRegnet);

		// FährtEin

		automat.state("FährtEin").update = s -> einfahren();

		automat.change("FährtEin", "Eingefahren", this::anfangspunktErreicht);

		automat.changeOnInput("stop", "FährtEin", "Gestoppt");

		automat.changeOnInput("down", "FährtEin", "FährtAus");

		// Gestoppt

		automat.changeOnInput("up", "Gestoppt", "FährtEin");

		automat.changeOnInput("down", "Gestoppt", "FährtAus");

		automat.change("Gestoppt", "FährtEin", () -> esRegnet);
	}

	void ausfahren() {
		position += 1;
	}

	void einfahren() {
		position -= 1;
	}

	boolean endpunktErreicht() {
		return position == 50;
	}

	boolean anfangspunktErreicht() {
		return position == 0;
	}

	@Override
	public void init() {
		automat.setLogger(Application.LOG);
		automat.setFrequency(app.pulse.getFrequency());
		automat.init();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_DOWN)) {
			automat.addInput("down");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_UP)) {
			automat.addInput("up");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			automat.addInput("stop");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_R)) {
			esRegnet = !esRegnet;
		}
		automat.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		g.setColor(Color.BLUE);
		g.setFont(new Font("sans", Font.PLAIN, 36));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawString(
				String.format("Regen: %s, Position: %d, Zustand: %s", (esRegnet ? "Ja" : "Nein"), position, automat.stateID()),
				0, 0);
		g.translate(-tf.getX(), -tf.getY());
	}
}