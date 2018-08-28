package de.amr.schule.markise;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;

public class WindSensor extends GameEntity {

	private float windSpeed;

	public boolean esIstWindig() {
		return windSpeed > 10;
	}

	@Override
	public void update() {
		if (Keyboard.keyDown(KeyEvent.VK_W)) {
			windSpeed += 1;
		} else if (Keyboard.keyDown(KeyEvent.VK_Q)) {
			windSpeed -= 1;
			if (windSpeed <= 0) {
				windSpeed = 0;
			}
		}
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public void init() {
	}

	@Override
	public void draw(Graphics2D g) {
	}
}
