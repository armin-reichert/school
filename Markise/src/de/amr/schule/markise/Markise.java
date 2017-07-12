package de.amr.schule.markise;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.statemachine.StateMachine;

public class Markise extends GameEntity {

	private MarkiseApp app;
	private StateMachine<String, String> automat;
	private int position;
	private boolean esRegnet;

	public Markise(MarkiseApp app) {

		this.app = app;

		automat = new StateMachine<>("Markise", String.class, "Eingefahren");

		// Eingefahren

		automat.changeOnInput("down", "Eingefahren", "FährtAus");

		// FährtAus

		automat.state("FährtAus").update = s -> {
			position += 1;
		};

		automat.change("FährtAus", "Ausgefahren", this::endpunktErreicht);

		automat.changeOnInput("stop", "FährtAus", "Gestoppt");

		automat.change("FährtAus", "Gestoppt", this::esRegnet);

		// Ausgefahren

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		// FährtEin

		automat.change("FährtEin", "Eingefahren", this::anfangspunktErreicht);

		automat.state("FährtEin").update = s -> {
			position -= 1;
		};

		// Gestoppt

		automat.changeOnInput("up", "Gestoppt", "FährtEin");

		automat.changeOnInput("down", "Gestoppt", "FährtAus");

		automat.change("Gestoppt", "FährtEin", this::esRegnet);
	}

	boolean endpunktErreicht() {
		return position == 50;
	}

	boolean anfangspunktErreicht() {
		return position == 0;
	}

	boolean esRegnet() {
		return esRegnet;
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
		g.setColor(Color.BLUE);
		g.setFont(new Font("sans", Font.PLAIN, 36));
		g.drawString(
				String.format("Regen: %s, Position: %d, Zustand: %s", (esRegnet ? "Ja" : "Nein"), position, automat.stateID()),
				tf.getX(), tf.getY());
	}
}