package de.amr.schule.ampel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.statemachine.StateMachine;

public class Ampel extends GameEntity {

	private StateMachine<String, String> automat;
	private int width;
	private int height;

	public Ampel(AmpelApp app) {

		width = 100;
		height = 3 * width;

		automat = new StateMachine<>("Ampel", String.class, "Aus");

		automat.change("Aus", "Rot", () -> Keyboard.keyPressedOnce(KeyEvent.VK_6), (aus, rot) -> rot.setDuration(3 * 60));

		automat.changeOnTimeout("Rot", "Grün", (rot, grün) -> grün.setDuration(5 * 60));

		automat.changeOnTimeout("Grün", "Gelb", (grün, gelb) -> gelb.setDuration(1 * 60));

		automat.changeOnTimeout("Gelb", "Rot", (gelb, rot) -> rot.setDuration(3 * 60));
	}

	@Override
	public void init() {
		automat.setLogger(Application.LOG);
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