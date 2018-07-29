package de.amr.schule.markise;

import java.awt.event.KeyEvent;
import java.util.stream.Stream;

import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.sprite.Sprite;

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
	public Sprite currentSprite() {
		return null;
	}

	@Override
	public Stream<Sprite> getSprites() {
		return Stream.empty();
	}
}
