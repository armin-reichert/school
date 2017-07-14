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

	private final Random rand = new Random();
	private boolean[][] current, grid1, grid2;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		grid1 = new boolean[gridSize][gridSize];
		grid2 = new boolean[gridSize][gridSize];
		current = grid1;
		app.pulse.setFrequency(updatesPerSecond);
	}

	@Override
	public void init() {
		int size = 100;
		int center = gridSize / 2;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				// current[row][col] = (row >= margin && row < gridSize - margin) && (col >= margin && col <
				// gridSize - margin);
				// current[row][col] = col % 3 == 0 || (col + row) % 5 == 0;
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
				if (current[nr][nc]) {
					++n;
				}
			}
		}
		if (current[row][col]) {
			--n;
		}
		return n;
	}
}