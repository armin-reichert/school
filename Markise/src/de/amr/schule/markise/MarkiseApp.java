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
		app.pulse.setFrequency(5);
		launch(app);
	}

	@Override
	public void init() {
		views.select(new MarkiseScene(this));
	}
}