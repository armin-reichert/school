package de.amr.schule.garagentor;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.ActiveScene;

public class GaragentorScene implements ActiveScene<Graphics2D> {

	private GaragentorApp app;
	private Garagentor tor;

	public GaragentorScene(GaragentorApp app) {
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
		tor = new Garagentor(app);
		tor.tf.setY(getHeight() - 100);
		tor.init();
	}

	@Override
	public void update() {
		tor.update();
	}

	@Override
	public void draw(Graphics2D g) {
		tor.draw(g);
	}
}