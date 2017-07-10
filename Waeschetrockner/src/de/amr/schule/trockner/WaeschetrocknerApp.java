package de.amr.schule.trockner;

import de.amr.easy.game.Application;

public class WaeschetrocknerApp extends Application {

	public static void main(String[] args) {
		WaeschetrocknerApp app = new WaeschetrocknerApp();
		app.settings.title = "Wäschetrockner Simulation";
		app.settings.width = 800;
		launch(app);
	}

	@Override
	public void init() {
		WaeschetrocknerScene scene = new WaeschetrocknerScene(this);
		views.add(scene);
		views.show(scene);
	}
}