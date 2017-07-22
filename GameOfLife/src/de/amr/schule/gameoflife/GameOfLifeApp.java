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
		launch(new GameOfLifeApp());
	}

	public GameOfLifeApp() {
		settings.title = "Game of Life";
		settings.width = 1024;
		settings.height = 1024;
		settings.bgColor = Color.DARK_GRAY;
	}

	@Override
	public void init() {
		selectView(new GameOfLifeScene(this));
	}
}