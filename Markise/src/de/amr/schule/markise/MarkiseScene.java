package de.amr.schule.markise;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;

public class MarkiseScene extends Scene<MarkiseApp> {

	private Markise markise;

	public MarkiseScene(MarkiseApp app) {
		super(app);
	}

	@Override
	public void init() {
		markise = new Markise(app);
		markise.init();
	}

	@Override
	public void draw(Graphics2D pen) {
		markise.vCenter(getHeight());
		markise.draw(pen);
	}

	@Override
	public void update() {
		markise.update();
	}
}