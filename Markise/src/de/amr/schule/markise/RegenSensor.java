package de.amr.schule.markise;

import java.awt.event.KeyEvent;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;

public class RegenSensor implements Controller {

	private int regenTropfen;

	public boolean esRegnet() {
		return regenTropfen > 10;
	}

	@Override
	public void init() {
	}

	@Override
	public View currentView() {
		return null;
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