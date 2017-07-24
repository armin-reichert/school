package de.amr.schule.gameoflife.scenes;

import static de.amr.easy.game.input.Keyboard.keyPressedOnce;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_PLUS;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.amr.easy.game.scene.Scene;
import de.amr.schule.gameoflife.GameOfLifeApp;
import de.amr.schule.gameoflife.GameOfLifeWorld;

public class GameOfLifeScene extends Scene<GameOfLifeApp> {

	private static final int MIN_SIZE = 16;
	private static final int MAX_SIZE = 512;

	protected final GameOfLifeWorld world;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		world = new GameOfLifeWorld(64, getWidth() / 64);
	}

	@Override
	public final void init() {
		reset();
	}

	protected void reset() {
	}

	@Override
	public void update() {
		app.handleNavigationKeys();
		handleResizeKeys();
		world.update();
	}

	private void handleResizeKeys() {
		if (keyPressedOnce(VK_PLUS) && world.getGridSize() * 2 <= MAX_SIZE) {
			world.setGridSize(2 * world.getGridSize());
			world.setCellSize(getWidth() / world.getGridSize());
			reset();
		} else if (keyPressedOnce(VK_MINUS) && world.getGridSize() / 2 >= MIN_SIZE) {
			world.setGridSize(world.getGridSize() / 2);
			world.setCellSize(getWidth() / world.getGridSize());
			reset();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		world.draw(g);
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString(String.format("Size: %d", world.getGridSize()), getWidth() - 150, getHeight() - 40);
	}
}