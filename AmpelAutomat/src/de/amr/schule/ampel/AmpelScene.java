package de.amr.schule.ampel;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;

public class AmpelScene extends Scene<AmpelApp> {

	private Ampel ampel;

	public AmpelScene(AmpelApp app) {
		super(app);
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