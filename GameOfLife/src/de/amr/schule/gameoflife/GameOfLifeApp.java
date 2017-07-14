package de.amr.schule.gameoflife;

import java.awt.Color;

import de.amr.easy.game.Application;

public class GameOfLifeApp extends Application {

	public static void main(String[] args) {
		GameOfLifeApp app = new GameOfLifeApp();
		app.settings.title = "Game of Life";
		app.settings.width = 1000;
		app.settings.height = 1000;
		app.settings.bgColor = Color.WHITE;
		launch(app);
	}

	@Override
	public void init() {
		GameOfLifeScene scene = new GameOfLifeScene(this);
		views.add(scene);
		views.show(scene);
	}

}
