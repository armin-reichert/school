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

	final Motor motor;

	private int regenTropfen;

	public Markise(MarkiseApp app) {
		this.app = app;

		motor = new Motor(this);

		automat = new StateMachine<>("MarkisenSteuerung", String.class, "Eingefahren");

		// Eingefahren

		automat.changeOnInput("down", "Eingefahren", "FährtAus", () -> !esRegnet());
		automat.change("Eingefahren", "FährtAus", () -> !esRegnet());

		// FährtAus

		automat.state("FährtAus").update = s -> motor.vor();

		automat.change("FährtAus", "Ausgefahren", this::endpunktErreicht, (s, t) -> motor.stop());

		automat.changeOnInput("stop", "FährtAus", "Gestoppt", (s, t) -> motor.stop());

		automat.changeOnInput("up", "FährtAus", "FährtEin");

		automat.change("FährtAus", "Gestoppt", this::esRegnet, (s, t) -> motor.stop());

		// Ausgefahren

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		automat.change("Ausgefahren", "FährtEin", this::esRegnet);

		// FährtEin

		automat.state("FährtEin").update = s -> {
			if (esRegnet()) {
				motor.schnellzurück();
			} else {
				motor.zurück();
			}
		};

		automat.change("FährtEin", "Eingefahren", this::anfangspunktErreicht, (s, t) -> motor.stop());

		automat.changeOnInput("stop", "FährtEin", "Gestoppt", (s, t) -> motor.stop());

		automat.changeOnInput("down", "FährtEin", "FährtAus");

		// Gestoppt

		automat.changeOnInput("up", "Gestoppt", "FährtEin");

		automat.changeOnInput("down", "Gestoppt", "FährtAus");

		automat.change("Gestoppt", "FährtEin", this::esRegnet);
	}

	boolean endpunktErreicht() {
		return tf.getX() >= 50;
	}

	boolean anfangspunktErreicht() {
		return tf.getX() <= 0;
	}

	boolean esRegnet() {
		return regenTropfen > 10;
	}

	@Override
	public void init() {
		automat.setLogger(Application.LOG);
		automat.setFrequency(app.pulse.getFrequency());
		automat.init();
		tf.setVelocityX(0);
		tf.setX(0);
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_DOWN)) {
			automat.addInput("down");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_UP)) {
			automat.addInput("up");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			automat.addInput("stop");
		} else if (Keyboard.keyDown(KeyEvent.VK_R)) {
			regenTropfen += 1;
		} else if (Keyboard.keyDown(KeyEvent.VK_S)) {
			regenTropfen -= 1;
		}
		automat.update();
		motor.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		g.setColor(Color.BLUE);
		g.setFont(new Font("sans", Font.PLAIN, 20));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawString(String.format("%s, Geschw: %.1f, Position: %.1f, Zustand: %s", (esRegnet() ? "Regen" : "Sonnenschein"),
				tf.getVelocityX(), tf.getX(), automat.stateID()), 0, 0);
		g.translate(-tf.getX(), -tf.getY());
	}
}