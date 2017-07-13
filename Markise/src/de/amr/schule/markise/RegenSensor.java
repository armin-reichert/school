package de.amr.schule.markise;

import java.awt.event.KeyEvent;

import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;

public class RegenSensor extends GameEntity {

	private int regenTropfen;

	public RegenSensor() {
	}

	public boolean esRegnet() {
		return regenTropfen > 10;
	}

	@Override
	public void update() {
		if (Keyboard.keyDown(KeyEvent.VK_R)) {
			regenTropfen += 1;
		} else if (Keyboard.keyDown(KeyEvent.VK_S)) {
			regenTropfen -= 1;
		}
	}
}