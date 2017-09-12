package de.amr.schule.garagentor;

import java.awt.Graphics2D;

import de.amr.easy.game.scene.ActiveScene;
import de.amr.easy.game.view.ViewController;

public class GaragentorScene extends ActiveScene<GaragentorApp> implements ViewController {

	private Garagentor tor;

	public GaragentorScene(GaragentorApp app) {
		super(app);
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
