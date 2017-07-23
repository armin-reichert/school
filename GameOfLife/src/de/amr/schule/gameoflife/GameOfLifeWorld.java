package de.amr.schule.gameoflife;

import java.awt.Graphics2D;
import java.util.BitSet;

import de.amr.easy.game.entity.GameEntity;

public class GameOfLifeWorld extends GameEntity {

	private BitSet grid1;
	private BitSet grid2;
	private BitSet current;
	private int gridSize;
	private int cellSize;

	public GameOfLifeWorld(int gridSize, int cellSize) {
		this.gridSize = gridSize;
		this.cellSize = cellSize;
		grid1 = new BitSet(gridSize * gridSize);
		grid2 = new BitSet(gridSize * gridSize);
		current = grid1;
	}

	public void reset() {
		grid1.clear();
		grid2.clear();
		current = grid1;
	}

	public void setGridSize(int gridSize) {
		if (this.gridSize != gridSize) {
			this.gridSize = gridSize;
			grid1 = new BitSet(gridSize * gridSize);
			grid2 = new BitSet(gridSize * gridSize);
			current = grid1;
		}
	}

	public int getGridSize() {
		return gridSize;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public void set(int row, int col) {
		current.set(row * gridSize + col);
	}

	public void unset(int row, int col) {
		current.set(row * gridSize + col, false);
	}

	public boolean isSet(int row, int col) {
		return current.get(row * gridSize + col);
	}

	private void set(BitSet bs, int row, int col) {
		bs.set(row * gridSize + col);
	}

	private void unset(BitSet bs, int row, int col) {
		bs.set(row * gridSize + col, false);
	}

	@Override
	public void update() {
		BitSet next = current == grid1 ? grid2 : grid1;
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				int numNeighbors = countNeighbors(row, col);
				if (isSet(row, col) && (numNeighbors == 2 || numNeighbors == 3) || numNeighbors == 3) {
					set(next, row, col);
				} else {
					unset(next, row, col);
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
		if (isSet(rowBefore, colBefore))
			++n;
		if (isSet(rowBefore, col))
			++n;
		if (isSet(rowBefore, colAfter))
			++n;
		if (isSet(row, colBefore))
			++n;
		if (isSet(row, colAfter))
			++n;
		if (isSet(rowAfter, colBefore))
			++n;
		if (isSet(rowAfter, col))
			++n;
		if (isSet(rowAfter, colAfter))
			++n;
		return n;
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(tf.getX(), tf.getY());
		for (int row = 0; row < gridSize; row += 1) {
			for (int col = 0; col < gridSize; col += 1) {
				if (isSet(row, col)) {
					g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				}
			}
		}
		g.translate(-tf.getX(), -tf.getY());
	}
}