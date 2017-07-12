package de.amr.schule.markise;

import java.awt.Color;

import de.amr.easy.game.Application;

public class MarkiseApp extends Application {

	public static void main(String[] args) {
		MarkiseApp app = new MarkiseApp();
		app.settings.title = "Markise Simulation";
		app.settings.width = 800;
		app.settings.height = 600;
		app.settings.bgColor = Color.WHITE;
		launch(app);
	}

	@Override
	public void init() {
		pulse.setFrequency(5);
		MarkiseScene scene = new MarkiseScene(this);
		views.add(scene);
		views.show(scene);
	}
}