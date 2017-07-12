package de.amr.schule.trockner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;

public class WaeschetrocknerScene extends Scene<WaeschetrocknerApp> {

	private Waeschetrockner trockner;

	public WaeschetrocknerScene(WaeschetrocknerApp app) {
		super(app);
	}

	@Override
	public void init() {
		trockner = new Waeschetrockner(app);
		trockner.init();
	}

	@Override
	public void update() {
		trockner.update();
	}

	@Override
	public void draw(Graphics2D g) {
		trockner.draw(g);
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.PLAIN, 30));
		g.drawString(String.format("Trockner: %s, Tür: %s, Zeit %s", trockner.hauptAutomat.stateID(),
				trockner.türAutomat.stateID(), trockner.zeitAutomat.stateID()), 100, getHeight() - 50);
	}
}