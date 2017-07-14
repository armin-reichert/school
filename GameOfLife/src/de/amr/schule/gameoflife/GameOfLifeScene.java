package de.amr.schule.gameoflife;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.scene.Scene;

public class GameOfLifeScene extends Scene<GameOfLifeApp> {

	private int gridSize = 500;
	private int updatesPerSecond = 10;

	private final Random rand = new Random();
	private boolean[][] grid;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		grid = new boolean[gridSize][gridSize];
		app.pulse.setFrequency(updatesPerSecond);
	}

	@Override
	public void init() {
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				// grid[row][col] = (rand.nextInt(10) < 3 ? false : true);
				// grid[row][col] = (row + col) % 4 == 0;
				grid[row][col] = (row >= 50 && row <= gridSize - 50) && (col >= 50 && col <= gridSize - 50);
			}
		}
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			init();
		}
		boolean[][] next = new boolean[gridSize][gridSize];
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				int numNeighbors = countNeighbors(row, col);
				if (numNeighbors > 3 || numNeighbors < 2) {
					next[row][col] = false; // Tod
				} else if (numNeighbors == 3) {
					next[row][col] = true; // Geburt
				} else {
					next[row][col] = grid[row][col];
				}
			}
		}
		grid = next;
	}

	@Override
	public void draw(Graphics2D g) {
		int cellSize = app.settings.width / gridSize;
		g.setColor(Color.BLACK);
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				if (grid[row][col]) {
					// g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				}
			}
		}
	}

	private int countNeighbors(int row, int col) {
		int n = 0;
		for (int r = row - 1; r <= row + 1; ++r) {
			for (int c = col - 1; c <= col + 1; ++c) {
				int nr = r;
				if (nr < 0) {
					nr = gridSize - 1;
				} else if (nr == gridSize) {
					nr = 0;
				}
				int nc = c;
				if (nc < 0) {
					nc = gridSize - 1;
				} else if (nc == gridSize) {
					nc = 0;
				}
				if (grid[nr][nc]) {
					++n;
				}
			}
		}
		if (grid[row][col]) {
			--n;
		}
		return n;
	}
}