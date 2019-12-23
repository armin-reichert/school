package de.amr.schule.gameoflife;

import static de.amr.easy.game.input.Keyboard.keyPressedOnce;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

import java.awt.Color;

import de.amr.easy.game.Application;
import de.amr.easy.game.view.View;
import de.amr.schule.gameoflife.scenes.DiamondScene;
import de.amr.schule.gameoflife.scenes.FiguresScene;
import de.amr.schule.gameoflife.scenes.RandomFillScene;

/**
 * Game of life.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class GameOfLifeApp extends Application {

	public static void main(String[] args) {
		launch(new GameOfLifeApp(), args);
	}

	private int current;
	private View[] scenes;

	public GameOfLifeApp() {
		settings.title = "Game of Life";
		settings.width = 1024;
		settings.height = 1024;
		settings.bgColor = Color.DARK_GRAY;
	}

	@Override
	public void init() {
		clock.setFrequency(20);
		scenes = new View[3];
		scenes[0] = new FiguresScene(this);
		scenes[1] = new DiamondScene(this);
		scenes[2] = new RandomFillScene(this);
		setController(scenes[0]);
	}

	public void handleNavigationKeys() {
		if (keyPressedOnce(VK_RIGHT)) {
			nextScene();
		}
		else if (keyPressedOnce(VK_LEFT)) {
			prevScene();
		}
	}

	public void nextScene() {
		current = current == scenes.length - 1 ? 0 : current + 1;
		setController(scenes[current]);
	}

	public void prevScene() {
		current = current == 0 ? scenes.length - 1 : current - 1;
		setController(scenes[current]);
	}
}