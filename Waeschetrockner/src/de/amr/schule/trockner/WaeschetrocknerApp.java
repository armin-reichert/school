package de.amr.schule.trockner;

import de.amr.easy.game.Application;

public class WaeschetrocknerApp extends Application {

	public static void main(String[] args) {
		launch(new WaeschetrocknerApp());
	}

	public WaeschetrocknerApp() {
		settings.title = "Wäschetrockner Simulation";
		settings.width = 800;
		pulse.setFrequency(10);
	}

	@Override
	public void init() {
		select(new WaeschetrocknerScene(this));
	}
}