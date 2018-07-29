package de.amr.schule.markise;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.ActiveScene;

public class MarkiseScene implements ActiveScene {

	private MarkiseApp app;
	private Markise markise;
	private Fernbedienung remote;

	public MarkiseScene(MarkiseApp app) {
		this.app = app;
	}

	@Override
	public int getWidth() {
		return app.getWidth();
	}

	@Override
	public int getHeight() {
		return app.getHeight();
	}

	@Override
	public void init() {
		markise = new Markise(app);
		remote = new Fernbedienung(app, markise);
		markise.init();
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