package de.amr.schule.markise;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;

public class MarkiseScene extends Scene<MarkiseApp> {

	private Markise markise;
	private Fernbedienung remote;

	public MarkiseScene(MarkiseApp app) {
		super(app);
	}

	@Override
	public void init() {
		markise = new Markise(app);
		remote = new Fernbedienung(app, markise);
		markise.init();
		markise.tf.setX(20);
		markise.tf.setY(getHeight() - 100);
	}

	@Override
	public void update() {
		remote.update();
		markise.update();
	}

	@Override
	public void draw(Graphics2D pen) {
		markise.draw(pen);
		remote.draw(pen);
	}
}