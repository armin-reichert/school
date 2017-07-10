package de.amr.schule.ampel;

import de.amr.easy.game.Application;

public class AmpelApp extends Application {

	public static void main(String[] args) {
		AmpelApp app = new AmpelApp();
		app.settings.title = "Ampel Simulation";
		app.settings.height = 600;
		app.settings.width = 600;
		launch(app);
	}

	@Override
	public void init() {
		AmpelScene scene = views.add(new AmpelScene(this));
		views.show(scene);
	}
}