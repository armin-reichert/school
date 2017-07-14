package de.amr.schule.gameoflife;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.scene.Scene;

public class GameOfLifeScene extends Scene<GameOfLifeApp> {

	private int gridSize = 250;
	private int updatesPerSecond = 5;

	private boolean[][] current, grid1, grid2;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		app.pulse.setFrequency(updatesPerSecond);
		grid1 = new boolean[gridSize][gridSize];
		grid2 = new boolean[gridSize][gridSize];
		current = grid1;
	}

	@Override
	public void init() {
		Random rand = new Random();
		int size = rand.nextInt(gridSize - 20);
		int center = gridSize / 2;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				current[row][col] = center - size / 2 <= row && row <= center + size / 2 && center - size / 2 <= col
						&& col <= center + size / 2;
			}
		}
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			init();
		}
		computeNextGrid();
	}

	@Override
	public void draw(Graphics2D g) {
		int cellSize = app.settings.width / gridSize;
		g.setColor(Color.BLACK);
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				if (current[row][col]) {
					// g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				}
			}
		}
	}

	private void computeNextGrid() {
		boolean[][] next = current == grid1 ? grid2 : grid1;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				int numNeighbors = countNeighbors(row, col);
				if (numNeighbors > 3 || numNeighbors < 2) {
					next[row][col] = false; // Tod
				} else if (numNeighbors == 3) {
					next[row][col] = true; // Geburt
				} else {
					next[row][col] = current[row][col];
				}
			}
		}
		current = next;
	}

	private int countNeighbors(int row, int col) {
		int rowBefore = row > 0 ? row - 1 : gridSize - 1;
		int colBefore = col > 0 ? col - 1 : gridSize - 1;
		int rowAfter = row < gridSize - 1 ? row + 1 : 0;
		int colAfter = col < gridSize - 1 ? col + 1 : 0;
		int n = 0;
		if (current[rowBefore][colBefore])
			++n;
		if (current[rowBefore][col])
			++n;
		if (current[rowBefore][colAfter])
			++n;
		if (current[row][colBefore])
			++n;
		if (current[row][colAfter])
			++n;
		if (current[rowAfter][colBefore])
			++n;
		if (current[rowAfter][col])
			++n;
		if (current[rowAfter][colAfter])
			++n;
		return n;
	}
}