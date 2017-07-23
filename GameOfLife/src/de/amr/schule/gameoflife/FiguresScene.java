package de.amr.schule.gameoflife;

import static de.amr.easy.game.input.Keyboard.keyPressedOnce;
import static java.awt.event.KeyEvent.VK_SPACE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game of life scene.
 * 
 * @author Armin Reichert & Anna Schillo
 */
public class FiguresScene extends GameOfLifeScene {

	private static final Map<String, String> FIGURES;
	private static final String[] FIGURE_NAMES;

	static {
		FIGURES = new LinkedHashMap<>();
		FIGURES.put("GLIDER", ".x.\n..x\nxxx");
		FIGURES.put("SMALL_EXPLODER", ".x.\nxxx\nx.x\n.x.");
		FIGURES.put("EXPLODER", "x.x.x\nx...x\nx...x\nx...x\nx.x.x");
		FIGURES.put("TEN_CELL_ROW", "xxxxxxxxxx");
		FIGURES.put("LIGHTWEIGHT_SPACESHIP", ".xxxx\nx...x\n....x\nx..x.");
		FIGURES.put("TUMBLER", ".xx.xx.\n.xx.xx.\n..x.x..\nx.x.x.x\nx.x.x.x\nxx...xx");
		FIGURE_NAMES = FIGURES.keySet().toArray(new String[FIGURES.size()]);
	}

	private int figureIndex;

	public FiguresScene(GameOfLifeApp app) {
		super(app);
	}

	@Override
	protected void reset() {
		selectFigure(figureIndex);
	}

	@Override
	public void update() {
		handleSelectFigureKey();
		super.update();
	}

	private void handleSelectFigureKey() {
		if (keyPressedOnce(VK_SPACE)) {
			selectFigure(figureIndex == FIGURES.size() - 1 ? 0 : figureIndex + 1);
		}
	}

	private void selectFigure(int index) {
		figureIndex = index;
		world.reset();
		Random rand = new Random();
		for (int i = 0; i < 10; ++i) {
			int row = rand.nextInt(world.getGridSize());
			int col = rand.nextInt(world.getGridSize());
			placeFigure(FIGURES.get(FIGURE_NAMES[figureIndex]), row, col);
		}
	}

	private void placeFigure(String spec, int row, int col) {
		String[] specRows = spec.split("\n");
		for (int r = 0; r < specRows.length; ++r) {
			String specRow = specRows[r];
			for (int c = 0; c < specRow.length(); ++c) {
				if (specRow.charAt(c) == 'x') {
					world.set(row + r, col + c);
				} else {
					world.unset(row + r, col + c);
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 20));
		g.drawString(FIGURE_NAMES[figureIndex], 20, getHeight() - 40);
	}
}