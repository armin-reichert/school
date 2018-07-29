package de.amr.schule.ampel;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.ActiveScene;

public class AmpelScene implements ActiveScene {

	private AmpelApp app;
	private Ampel ampel;

	public AmpelScene(AmpelApp app) {
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
		ampel = new Ampel(app);
		ampel.setWidth(150);
		ampel.setHeight(450);
		ampel.init();
	}

	@Override
	public void update() {
		ampel.update();
	}

	@Override
	public void draw(Graphics2D g) {
		ampel.center(getWidth(), getHeight());
		ampel.draw(g);
	}
}