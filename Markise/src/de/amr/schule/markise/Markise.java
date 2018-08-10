package de.amr.schule.markise;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.stream.Stream;

import de.amr.easy.game.Application;
import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.sprite.Sprite;
import de.amr.easy.statemachine.StateMachine;

/**
 * Modell einer Markise mit Wind- und Regensensor.
 * 
 * @author Armin Reichert & Anna u. Peter Schillo
 */
public class Markise extends GameEntity {

	private final MarkiseApp app;
	private final Motor motor;
	private final PositionsSensor positionsSensor;
	private final RegenSensor regenSensor;
	private final WindSensor windSensor;
	private final StateMachine<String, String> automat;

	private int position;

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

		automat.state("Eingefahren").entry = s -> motor.stop();

		automat.changeOnInput("down", "Eingefahren", "FährtAus", () -> !regenSensor.esRegnet());

		// FährtAus

		automat.state("FährtAus").entry = s -> {
			motor.vor();
			Assets.sound("bewegen.mp3").play();
		};

		automat.state("FährtAus").update = s -> {
			if (position > 50 && !Assets.sound("quietschen.mp3").isRunning()) {
				Assets.sound("quietschen.mp3").play();
			}
		};

		automat.state("FährtAus").exit = s -> {
			Assets.sound("quietschen.mp3").stop();
			Assets.sound("bewegen.mp3").stop();
		};

		automat.change("FährtAus", "Ausgefahren", positionsSensor::inEndPosition);

		automat.changeOnInput("stop", "FährtAus", "Gestoppt");

		automat.changeOnInput("up", "FährtAus", "FährtEin");

		automat.change("FährtAus", "Gestoppt", regenSensor::esRegnet);

		// Ausgefahren

		automat.state("Ausgefahren").entry = s -> motor.stop();

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		automat.change("Ausgefahren", "FährtEin", regenSensor::esRegnet);

		// FährtEin

		automat.state("FährtEin").entry = s -> {
			if (regenSensor.esRegnet()) {
				motor.schnellZurück();
			} else {
				motor.zurück();
			}
			Assets.sound("bewegen.mp3").play();
		};

		automat.state("FährtEin").exit = s -> {
			Assets.sound("bewegen.mp3").stop();
		};

		automat.change("FährtEin", "Eingefahren", positionsSensor::inStartPosition);

		automat.changeOnInput("stop", "FährtEin", "Gestoppt");

		automat.changeOnInput("down", "FährtEin", "FährtAus");

		// Gestoppt

		automat.state("Gestoppt").entry = s -> motor.stop();

		automat.changeOnInput("up", "Gestoppt", "FährtEin");

		automat.changeOnInput("down", "Gestoppt", "FährtAus");

		automat.change("Gestoppt", "FährtEin", regenSensor::esRegnet);

		// Tracing
		automat.setLogger(Application.logger);
		automat.ticksToSec = app.pulse::ticksToSec;
	}

	public void raiseEvent(String event) {
		automat.addInput(event);
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void init() {
		Assets.sound("bewegen.mp3");
		Assets.sound("quietschen.mp3");
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
	public Sprite currentSprite() {
		return null;
	}

	@Override
	public Stream<Sprite> getSprites() {
		return Stream.empty();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Balken zum Anzeigen der Ausfahrposition
		g.translate(tf.getX(), tf.getY());
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Math.round(position / 100f * app.settings.width), 30);
		g.translate(-tf.getX(), -tf.getY());

		// Statustext
		g.translate(tf.getX(), tf.getY() + 80);
		g.setFont(new Font("Monospaced", Font.BOLD, 16));
		g.drawString(format("Wetter: %s %s  Geschw: %.1f  Position: %d%%  Zustand: %s",
				regenSensor.esRegnet() ? "Regen" : "Sonnenschein",
				windSensor.esIstWindig() ? "Windig" : "Windstill", tf.getVelocityX(), position,
				automat.stateID()), 0, 0);
		g.translate(-tf.getX(), -(tf.getY() + 80));
	}
}