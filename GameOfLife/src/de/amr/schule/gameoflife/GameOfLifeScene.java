package de.amr.schule.gameoflife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.scene.Scene;

/**
 * Game of life scene.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class GameOfLifeScene extends Scene<GameOfLifeApp> {

	private static final Map<String, String> FIGURES;
	private static final String[] FIGURE_NAMES;

	static {
		FIGURES = new LinkedHashMap<>();
		FIGURES.put("GLIDER", ".x.\n..x\nxxx");
		FIGURES.put("SMALL_EXPLODER", ".x.\nxxx\nx.x\n.x.");
		FIGURES.put("EXPLODER", "x.x.x\nx...x\nx...x\nx...x\nx.x.x");
		FIGURES.put("TEN_CELL_ROW", "xxxxxxxxxx");
		FIGURES.put("LIGHTWEIGHT_SPACESHIP", ".xxxx\nx...x\n....x\nx..x.");
		FIGURES.put("Tumbler", ".xx.xx.\n.xx.xx.\n..x.x..\nx.x.x.x\nx.x.x.x\nxx...xx");
		FIGURE_NAMES = FIGURES.keySet().toArray(new String[FIGURES.size()]);
	}

	private BitSet grid1;
	private BitSet grid2;
	private BitSet current;
	private int gridSize;
	private int cellSize;
	private int selectedFigureIndex;

	public GameOfLifeScene(GameOfLifeApp app) {
		super(app);
		gridSize = 16;
		app.pulse.setFrequency(10);
	}

	@Override
	public void init() {
		reset();
	}

	private void reset() {
		grid1 = new BitSet(gridSize * gridSize);
		grid2 = new BitSet(gridSize * gridSize);
		cellSize = app.settings.width / gridSize;
		current = grid1;
		selectedFigureIndex = 0;
		selectFigure();
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_PLUS)) {
			gridSize = gridSize * 2;
			reset();
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_MINUS)) {
			gridSize = gridSize / 2;
			reset();
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			grid1.clear();
			grid2.clear();
			current = grid1;
			selectedFigureIndex += 1;
			if (selectedFigureIndex > FIGURES.size()) {
				selectedFigureIndex = 0;
			}
			selectFigure();
		}
		updateGrid();
	}

	private void set(BitSet bs, int row, int col) {
		bs.set(row * gridSize + col);
	}

	private void unset(BitSet bs, int row, int col) {
		bs.set(row * gridSize + col, false);
	}

	private boolean isSet(BitSet bs, int row, int col) {
		return bs.get(row * gridSize + col);
	}

	private String selectedFigureName() {
		return FIGURE_NAMES[selectedFigureIndex];
	}

	private void selectFigure() {
		if (selectedFigureIndex == FIGURES.size()) {
			square(gridSize / 2, gridSize / 2);
		} else {
			figure(FIGURES.get(selectedFigureName()), gridSize / 2, gridSize / 2);
		}
	}

	private void square(int row, int col) {
		Random rand = new Random();
		for (int r = 0; r < gridSize; r += 1) {
			for (int c = 0; c < gridSize; c += 1) {
				if (rand.nextBoolean()) {
					set(current, r, c);
				}
			}
		}
	}

	private void figure(String bits, int row, int col) {
		String[] bitRows = bits.split("\n");
		for (int r = 0; r < bitRows.length; ++r) {
			String bitRow = bitRows[r];
			for (int c = 0; c < bitRow.length(); ++c) {
				if (bitRow.charAt(c) == 'x') {
					set(current, row + r, col + c);
				} else {
					unset(current, row + r, col + c);
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				if (isSet(current, row, col)) {
					// g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				}
			}
		}
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		if (selectedFigureIndex < FIGURES.size()) {
			g.drawString(selectedFigureName(), 20, getHeight() - 40);
		} else {
			g.drawString("Random square", 20, getHeight() - 40);
		}
		g.drawString(String.format("Size: %d", gridSize), getWidth() - 150, getHeight() - 40);
	}

	private void updateGrid() {
		BitSet next = current == grid1 ? grid2 : grid1;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				int numNeighbors = countNeighbors(current, row, col);
				if (isSet(current, row, col) && (numNeighbors == 2 || numNeighbors == 3) || numNeighbors == 3) {
					set(next, row, col);
				} else {
					unset(next, row, col);
				}
			}
		}
		current = next;
	}

	private int countNeighbors(BitSet grid, int row, int col) {
		int rowBefore = row > 0 ? row - 1 : gridSize - 1;
		int colBefore = col > 0 ? col - 1 : gridSize - 1;
		int rowAfter = row < gridSize - 1 ? row + 1 : 0;
		int colAfter = col < gridSize - 1 ? col + 1 : 0;
		int n = 0;
		if (isSet(grid, rowBefore, colBefore))
			++n;
		if (isSet(grid, rowBefore, col))
			++n;
		if (isSet(grid, rowBefore, colAfter))
			++n;
		if (isSet(grid, row, colBefore))
			++n;
		if (isSet(grid, row, colAfter))
			++n;
		if (isSet(grid, rowAfter, colBefore))
			++n;
		if (isSet(grid, rowAfter, col))
			++n;
		if (isSet(grid, rowAfter, colAfter))
			++n;
		return n;
	}
}