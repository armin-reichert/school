package de.amr.schule.gameoflife;

import static de.amr.easy.game.input.Keyboard.keyPressedOnce;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

import java.awt.Color;

import de.amr.easy.game.Application;
import de.amr.easy.game.scene.Scene;
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
		launch(new GameOfLifeApp());
	}

	private int current;
	private Scene<?>[] scenes;

	public GameOfLifeApp() {
		settings.title = "Game of Life";
		settings.width = 1024;
		settings.height = 1024;
		settings.bgColor = Color.DARK_GRAY;
		pulse.setFrequency(20);
	}

	@Override
	public void init() {
		scenes = new Scene<?>[3];
		scenes[0] = addView(new FiguresScene(this));
		scenes[1] = addView(new DiamondScene(this));
		scenes[2] = addView(new RandomFillScene(this));
		selectView(scenes[0]);
	}

	public void handleNavigationKeys() {
		if (keyPressedOnce(VK_RIGHT)) {
			nextScene();
		} else if (keyPressedOnce(VK_LEFT)) {
			prevScene();
		}
	}

	public void nextScene() {
		current = current == scenes.length - 1 ? 0 : current + 1;
		selectView(scenes[current]);
	}

	public void prevScene() {
		current = current == 0 ? scenes.length - 1 : current - 1;
		selectView(scenes[current]);
	}
}