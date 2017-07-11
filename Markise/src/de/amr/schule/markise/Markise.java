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

	StateMachine<String, String> automat;
	int position;

	public Markise() {
		automat = new StateMachine<>("Markise", String.class, "Eingefahren");

		automat.changeOnInput("down", "Eingefahren", "FährtAus");

		automat.state("FährtAus").update = s -> {
			position += 1;
			Application.LOG.info(position + "");
		};

		automat.change("FährtAus", "Ausgefahren", this::endpunktErreicht);

		automat.changeOnInput("up", "Ausgefahren", "FährtEin");

		automat.change("FährtEin", "Eingefahren", this::anfangspunktErreicht);

		automat.state("FährtEin").update = s -> {
			position -= 1;
			Application.LOG.info(position + "");
		};
	}

	boolean endpunktErreicht() {
		return position == 180;

	}

	boolean anfangspunktErreicht() {
		return position == 0;
	}

	@Override
	public void init() {
		automat.setLogger(Application.LOG);
		automat.init();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_DOWN)) {
			automat.addInput("down");
		}

		if (Keyboard.keyPressedOnce(KeyEvent.VK_UP)) {
			automat.addInput("up");
		}

		automat.update();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("sans", Font.PLAIN, 60));
		g.drawString(position + "", tf.getX(), tf.getY());
	}

}
