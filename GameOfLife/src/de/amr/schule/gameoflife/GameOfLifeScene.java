package de.amr.schule.gameoflife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.scene.Scene;

/**
 * Game of life.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class GameOfLifeScene extends Scene<GameOfLifeApp> {

	private static final Map<String, String> FIGURES = new LinkedHashMap<>();
	static {
		FIGURES.put("GLIDER", ".x.\n..x\nxxx");
		FIGURES.put("SMALL_EXPLODER", ".x.\nxxx\nx.x\n.x.");
		FIGURES.put("EXPLODER", "x.x.x\nx...x\nx...x\nx...x\nx.x.x");
		FIGURES.put("TEN_CELL_ROW", "xxxxxxxxxx");
		FIGURES.put("LIGHTWEIGHT_SPACESHIP", ".xxxx\nx...x\n....x\nx..x.");
		FIGURES.put("Tumbler", ".xx.xx.\n.xx.xx.\n..x.x..\nx.x.x.x\nx.x.x.x\nxx...xx");
	}
	private static String[] FIGURE_NAMES = FIGURES.keySet().toArray(new String[FIGURES.size()]);

	private final int gridSize = 100;
	private final int cellSize;
	private int updatesPerSecond = 10;
	private boolean[][] current, grid1, grid2;
	private int selectedIndex;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		cellSize = app.settings.width / gridSize;
		app.pulse.setFrequency(updatesPerSecond);
	}

	@Override
	public void init() {
		grid1 = new boolean[gridSize][gridSize];
		grid2 = new boolean[gridSize][gridSize];
		current = grid1;
		selectedIndex = 0;
		selectFigure();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			grid1 = new boolean[gridSize][gridSize];
			grid2 = new boolean[gridSize][gridSize];
			current = grid1;
			selectedIndex += 1;
			if (selectedIndex > FIGURES.size()) {
				selectedIndex = 0;
			}
			selectFigure();
		}
		updateGrid();
	}

	private String selectedFigureName() {
		return FIGURE_NAMES[selectedIndex];
	}

	private void selectFigure() {
		if (selectedIndex == FIGURES.size()) {
			randomSquare(gridSize / 2, gridSize / 2);
		} else {
			figure(FIGURES.get(selectedFigureName()), gridSize / 2, gridSize / 2);
		}
	}

	private void randomSquare(int row, int col) {
		Random rand = new Random();
		int halfSize = rand.nextInt(gridSize * 3 / 4) / 2;
		int center = gridSize / 2;
		for (int r = 0; r < gridSize; r += 1) {
			for (int c = 0; c < gridSize; c += 1) {
				if (r > center - halfSize && r < center + halfSize && c > center - halfSize && c < center + halfSize) {
					current[r][c] = true;
				}
			}
		}
	}

	private void figure(String bits, int row, int col) {
		String[] bitRows = bits.split("\n");
		for (int r = 0; r < bitRows.length; ++r) {
			String bitRow = bitRows[r];
			for (int c = 0; c < bitRow.length(); ++c) {
				current[row + r][col + c] = bitRow.charAt(c) == 'x';
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				if (current[row][col]) {
					// g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				}
			}
		}
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		if (selectedIndex < FIGURES.size()) {
			g.drawString(selectedFigureName(), 20, getHeight() - 40);
		} else {
			g.drawString("Random square", 20, getHeight() - 40);
		}
	}

	private void updateGrid() {
		boolean[][] next = current == grid1 ? grid2 : grid1;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				int numNeighbors = countNeighbors(current, row, col);
				next[row][col] = (current[row][col] && (numNeighbors == 2 || numNeighbors == 3)) || numNeighbors == 3;
			}
		}
		current = next;
	}

	private int countNeighbors(boolean[][] grid, int row, int col) {
		int rowBefore = row > 0 ? row - 1 : gridSize - 1;
		int colBefore = col > 0 ? col - 1 : gridSize - 1;
		int rowAfter = row < gridSize - 1 ? row + 1 : 0;
		int colAfter = col < gridSize - 1 ? col + 1 : 0;
		int n = 0;
		if (grid[rowBefore][colBefore])
			++n;
		if (grid[rowBefore][col])
			++n;
		if (grid[rowBefore][colAfter])
			++n;
		if (grid[row][colBefore])
			++n;
		if (grid[row][colAfter])
			++n;
		if (grid[rowAfter][colBefore])
			++n;
		if (grid[rowAfter][col])
			++n;
		if (grid[rowAfter][colAfter])
			++n;
		return n;
	}
}