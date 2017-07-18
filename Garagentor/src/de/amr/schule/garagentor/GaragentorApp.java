package de.amr.schule.garagentor;

import java.awt.Color;

import de.amr.easy.game.Application;

/**
 * Simuliert unser Garagentor.
 * 
 * @author Armin Reichert, Anna und Peter Schillo
 */
public class GaragentorApp extends Application {

	public static void main(String[] args) {
		GaragentorApp app = new GaragentorApp();
		app.settings.title = "Garagentor Simulation";
		app.settings.width = 800;
		app.settings.height = 600;
		app.settings.bgColor = Color.WHITE;
		app.pulse.setFrequency(10);
		launch(app);
	}

	@Override
	public void init() {
		views.select(new GaragentorScene(this));
	}

}
