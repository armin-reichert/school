package de.amr.schule.ampel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.sprite.Sprite;
import de.amr.easy.statemachine.StateMachine;

/**
 * Die Ampel.
 * 
 * @author Armin Reichert & Anna Schillo
 *
 */
public class Ampel extends GameEntity {

	private final StateMachine<String, String> automat;
	private int width;
	private int height;

	public Ampel(AmpelApp app) {
		width = 100;
		height = 3 * width;

		// Definiere die Steuerung durch einen Automaten:
		automat = new StateMachine<>("Ampel Steuerung", String.class, "Aus");

		// Ampel beim Drücken der SPACE-Taste einschalten, für 3 Sekunden auf Rot
		automat.change("Aus", "Rot", () -> Keyboard.keyPressedOnce(KeyEvent.VK_SPACE),
				t -> t.to().setDuration(3 * 60));

		// Für 5 Sekunden auf Grün
		automat.changeOnTimeout("Rot", "Grün", t -> t.to().setDuration(5 * 60));

		// Für 1 Sekunde auf Gelb
		automat.changeOnTimeout("Grün", "Gelb", t -> t.to().setDuration(1 * 60));

		// Für 3 Sekunden auf Rot
		automat.changeOnTimeout("Gelb", "Rot", t -> t.to().setDuration(3 * 60));
	}

	@Override
	public void init() {
		automat.setLogger(Application.logger);
		automat.init();
	}

	@Override
	public void update() {
		automat.update();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
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
		g.translate(tf.getX(), tf.getY());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);
		int inset = 3;
		int diameter = width - inset * 2;
		if (automat.is("Rot")) {
			g.setColor(Color.RED);
			g.fillOval(inset, inset, diameter, diameter);
		} else if (automat.is("Gelb")) {
			g.setColor(Color.YELLOW);
			g.fillOval(inset, inset + height / 3, diameter, diameter);
		} else if (automat.is("Grün")) {
			g.setColor(Color.GREEN);
			g.fillOval(inset, inset + height * 2 / 3, diameter, diameter);
		}
		g.setStroke(new BasicStroke(inset));
		g.setColor(Color.BLACK);
		g.drawOval(inset, inset, diameter, diameter);
		g.drawOval(inset, inset + height / 3, diameter, diameter);
		g.drawOval(inset, inset + height * 2 / 3, diameter, diameter);
		g.translate(-tf.getX(), -tf.getY());
	}
}