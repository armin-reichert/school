package de.amr.schule.trockner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;

public class WaeschetrocknerUI implements View, Controller {

	private WaeschetrocknerApp app;
	private Waeschetrockner trockner;

	public WaeschetrocknerUI(WaeschetrocknerApp app) {
		this.app = app;
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
		g.drawString(String.format("Trockner: %s, Tür: %s, Zeit %s", trockner.hauptAutomat.getState(),
				trockner.türAutomat.getState(), trockner.zeitAutomat.getState()), 100, app.settings.height - 50);
	}
}