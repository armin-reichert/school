package de.amr.schule.gameoflife;

import java.awt.Color;

import de.amr.easy.game.Application;

/**
 * Game of life.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class GameOfLifeApp extends Application {

	public static void main(String[] args) {
		GameOfLifeApp app = new GameOfLifeApp();
		app.settings.title = "Game of Life";
		app.settings.width = 1000;
		app.settings.height = 1000;
		app.settings.bgColor = Color.DARK_GRAY;
		launch(app);
	}

	@Override
	public void init() {
		views.show(new GameOfLifeScene(this));
	}
}