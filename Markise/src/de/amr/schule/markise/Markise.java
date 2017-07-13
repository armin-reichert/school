package de.amr.schule.markise;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.statemachine.StateMachine;

public class Markise extends GameEntity {

	private final MarkiseApp app;
	private final Motor motor;
	private final PositionsSensor positionsSensor;
	private final RegenSensor regenSensor;
	private final WindSensor windSensor;
	private final StateMachine<String, String> automat;

	private float position;

	public Markise(MarkiseApp app) {
		this.app = app;

		// Aktoren
		motor = new Motor(this);

		// Sensoren
		positionsSensor = new PositionsSensor(this);
		regenSensor = new RegenSensor();
		windSensor = new WindSensor();

		// Steuerung

		automat = new StateMachine<>("MarkisenSteuerung", String.class, "Eingefahren");

		// Eingefahren

		automat.state("Eingefahren").entry = s -> {
			position = 0;
			motor.stop();
		};

		automat.changeOnInput("down", "Eingefahren", "FährtAus", () -> !regenSensor.esRegnet());

		automat.change("Eingefahren", "FährtAus", () -> !regenSensor.esRegnet());

		// FährtAus

		automat.state("FährtAus").update = s -> motor.vor();

		automat.change("FährtAus", "Ausgefahren", () -> positionsSensor.endPositionErreicht(), (s, t) -> motor.stop());

		automat.changeOnInput("stop", "FährtAus", "Gestoppt", (s, t) -> motor.stop());

		automat.changeOnInput("up", "FährtAus", "FährtEin");

		automat.change("FährtAus", "Gestoppt", () -> regenSensor.esRegnet(), (s, t) -> motor.stop());

		// Ausgefahren

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		automat.change("Ausgefahren", "FährtEin", () -> regenSensor.esRegnet());

		// FährtEin

		automat.state("FährtEin").update = s -> {
			if (regenSensor.esRegnet()) {
				motor.schnellZurück();
			} else {
				motor.zurück();
			}
		};

		automat.change("FährtEin", "Eingefahren", () -> positionsSensor.startPositionErreicht(), (s, t) -> motor.stop());

		automat.changeOnInput("stop", "FährtEin", "Gestoppt", (s, t) -> motor.stop());

		automat.changeOnInput("down", "FährtEin", "FährtAus");

		// Gestoppt

		automat.changeOnInput("up", "Gestoppt", "FährtEin");

		automat.changeOnInput("down", "Gestoppt", "FährtAus");

		automat.change("Gestoppt", "FährtEin", () -> regenSensor.esRegnet());

		// Tracing
		automat.setLogger(Application.LOG);
		automat.setFrequency(app.pulse.getFrequency());
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

	@Override
	public void init() {
		positionsSensor.setStartPosition(0);
		positionsSensor.setEndPosition(100);
		automat.init();
	}

	@Override
	public void update() {
		regenSensor.update();
		windSensor.update();
		automat.update();
		motor.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Math.round(position / 100f * app.settings.width), 50);
		g.translate(-tf.getX(), -tf.getY());

		g.translate(tf.getX(), tf.getY() + 80);
		g.setFont(new Font("sans", Font.PLAIN, 20));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawString(format("Wetter: %s %s  Geschw: %.1f  Position: %.1f  Zustand: %s",
				regenSensor.esRegnet() ? "Regen" : "Sonnenschein", windSensor.esIstWindig() ? "Windig" : "Windstill",
				tf.getVelocityX(), position, automat.stateID()), 0, 0);
		g.translate(-tf.getX(), -(tf.getY() + 80));
	}

	public void raiseEvent(String event) {
		automat.addInput(event);
	}
}