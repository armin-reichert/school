package de.amr.schule.gameoflife;

import static de.amr.easy.game.input.Keyboard.keyPressedOnce;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Collectors;

import de.amr.easy.game.Application;
import de.amr.easy.game.view.View;

/**
 * Game of life.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class GameOfLifeApp extends Application {

	private int current;
	private ArrayList<View> scenes;

	public static void main(String[] args) {
		launch(new GameOfLifeApp());
	}

	public GameOfLifeApp() {
		settings.title = "Game of Life";
		settings.width = 1024;
		settings.height = 1024;
		settings.bgColor = Color.DARK_GRAY;
		pulse.setFrequency(20);
	}

	@Override
	public void init() {
		addView(new FiguresScene(this));
		addView(new TriangleScene(this));
		addView(new RandomFillScene(this));
		selectView(TriangleScene.class);

		scenes = new ArrayList<>(views().collect(Collectors.toList()));
		current = scenes.indexOf(getSelectedView());
	}

	public void handleNavigationKeys() {
		if (keyPressedOnce(VK_RIGHT)) {
			nextScene();
		} else if (keyPressedOnce(VK_LEFT)) {
			prevScene();
		}
	}

	public void nextScene() {
		if (current == scenes.size() - 1) {
			current = 0;
		} else {
			++current;
		}
		selectView(scenes.get(current));
	}

	public void prevScene() {
		if (current == 0) {
			current = scenes.size() - 1;
		} else {
			--current;
		}
		selectView(scenes.get(current));
	}
}