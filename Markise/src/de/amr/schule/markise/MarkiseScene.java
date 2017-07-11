package de.amr.schule.markise;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;

public class MarkiseScene extends Scene<MarkiseApp> {

	Markise markise;

	public MarkiseScene(MarkiseApp app) {
		super(app);
	}

	@Override
	public void init() {
		markise = new Markise();
		markise.init();
	}

	@Override
	public void draw(Graphics2D pen) {
		markise.center(getWidth(), getHeight());
		markise.draw(pen);
	}

	@Override
	public void update() {
		markise.update();
	}

}
