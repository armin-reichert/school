package de.amr.schule.garagentor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;
import de.amr.easy.statemachine.StateMachine;

public class Garagentor extends GameEntity implements View,Controller {

	private GaragentorApp app;
	private StateMachine<String, String> automat;
	private int position;
	private boolean hindernis;
	private boolean lichtBrennt;

	public Garagentor(GaragentorApp app) {

		this.app = app;

		automat = new StateMachine<>("Garagentor Steuerung", String.class, "Geschlossen");

		// Geschlossen

		automat.state("Geschlossen").entry = s -> {
			lichtAn();
			s.setDuration(Application.CLOCK.sec(5));
		};

		automat.state("Geschlossen").exit = s -> {
			lichtAus();
		};

		automat.changeOnInput("SchalterGedrückt", "Geschlossen", "Öffnet");

		automat.changeOnInput("FBGedrückt", "Geschlossen", "Öffnet");

		automat.changeOnTimeout("Geschlossen", "Geschlossen", t -> lichtAus());

		// Öffnet

		automat.state("Öffnet").update = s -> {
			position += 1;
		};

		automat.change("Öffnet", "Offen", () -> endPunktErreicht());

		automat.changeOnInput("FBGedrückt", "Öffnet", "GestopptBeimÖffnen");

		automat.changeOnInput("SchalterGedrückt", "Öffnet", "GestopptBeimÖffnen");

		// Offen
		automat.changeOnInput("SchalterGedrückt", "Offen", "Schließt");

		automat.changeOnInput("FBGedrückt", "Offen", "Schließt");

		// Schließt

		automat.state("Schließt").update = s -> {
			position -= 1;
		};

		automat.change("Schließt", "Geschlossen", () -> anfangsPunktErreicht());

		automat.changeOnInput("FBGedrückt", "Schließt", "GestopptBeimSchließen");

		automat.changeOnInput("SchalterGedrückt", "Schließt", "GestopptBeimSchließen");

		automat.change("Schließt", "Öffnet", () -> hindernisErkannt());

		// GestopptBeimÖffnen
		automat.changeOnInput("SchalterGedrückt", "GestopptBeimÖffnen", "Schließt");

		automat.changeOnInput("FBGedrückt", "GestopptBeimÖffnen", "Schließt");

		// GestopptBeimSchließen
		automat.changeOnInput("SchalterGedrückt", "GestopptBeimSchließen", "Öffnet");

		automat.changeOnInput("FBGedrückt", "GestopptBeimSchließen", "Öffnet");
	}

	public int getWidth() {
		return 800;
	}

	public int getHeight() {
		return 600;
	}

	@Override
	public void init() {
		automat.init();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			automat.addInput("SchalterGedrückt");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_F)) {
			automat.addInput("FBGedrückt");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_H)) {
			if (automat.is("Geschlossen") && !hindernis) {
				// Hindernis nicht einschalten
			} else {
				hindernis = !hindernis;
			}
		}
		automat.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		g.setColor(Color.BLUE);
		int w = position * app.settings.width / 100;
		g.fillRect(0, 0, w, 20);
		g.translate(-tf.getX(), -tf.getY());
	
		g.translate(tf.getX(), tf.getY() + 40);
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.drawString(String.format("Position: %d, Zustand: %s, Hindernis: %s, %s", position, automat.stateID(),
				hindernis ? "Ja" : "Nein", lichtBrennt ? "Licht brennt" : ""), 0, 0);
		g.translate(-tf.getX(), -tf.getY());
	}

	private boolean endPunktErreicht() {
		return position == 100;
	}

	private boolean anfangsPunktErreicht() {
		return position == 0;
	}

	private boolean hindernisErkannt() {
		return hindernis;
	}

	private void lichtAn() {
		lichtBrennt = true;
	}

	private void lichtAus() {
		lichtBrennt = false;
	}
}