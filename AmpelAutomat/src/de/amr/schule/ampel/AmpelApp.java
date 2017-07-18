package de.amr.schule.ampel;

import de.amr.easy.game.Application;

/**
 * Simuliert eine Ampel mithilfe eines Zustandsautomaten.
 * 
 * @author Armin Reichert & Anna Schillo
 */
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
		views.select(new AmpelScene(this));
	}
}