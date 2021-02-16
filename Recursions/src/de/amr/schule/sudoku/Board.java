package de.amr.schule.sudoku;

import java.util.BitSet;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Board {

	//@formatter:off
	static byte[] HOUSE = {
		0,0,0,	1,1,1,	2,2,2,
		0,0,0,	1,1,1,	2,2,2,
		0,0,0,	1,1,1,	2,2,2,
		3,3,3,	4,4,4,	5,5,5,
		3,3,3,	4,4,4,	5,5,5,
		3,3,3,	4,4,4,	5,5,5,
		6,6,6,	7,7,7,	8,8,8,
		6,6,6,	7,7,7,	8,8,8,
		6,6,6,	7,7,7,	8,8,8,
	};
	//@formatter:on

	public byte[] cells = new byte[81];

	public Board(byte[] values) {
		if (values.length != 81) {
			throw new IllegalArgumentException("Board must have 81 entries but has " + values.length);
		}
		System.arraycopy(values, 0, cells, 0, cells.length);
	}

	public void set(int pos, int value) {
		cells[pos] = (byte) value;
	}

	public byte value(int pos) {
		return cells[pos];
	}

	public byte value(int x, int y) {
		return cells[index(x, y)];
	}

	private int index(int x, int y) {
		return x + 9 * y;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < 9; ++y) {
			for (int x = 0; x < 9; ++x) {
				int value = value(x, y);
				sb.append(value == 0 ? "." : value).append(" ");
				if (x == 2 || x == 5) {
					sb.append("| ");
				}
			}
			sb.append("\n");
			if (y == 2 || y == 5) {
				sb.append("----- + ----- + -----\n");
			}
		}
		return sb.toString();
	}

	public IntStream cells() {
		return IntStream.rangeClosed(0, 80);
	}

	public IntStream house(int cell) {
		return cells().filter(c -> HOUSE[c] == HOUSE[cell]);
	}

	public OptionalInt emptyCell() {
		return cells().filter(c -> cells[c] == 0).findFirst();
	}

	public IntStream validNumbers(int emptyCell) {
		int col = emptyCell % 9, row = emptyCell / 9;
		BitSet validNumbers = new BitSet(10);
		validNumbers.set(1, 10);
		for (int i = 0; i < 9; ++i) {
			validNumbers.clear(value(i, row));
			validNumbers.clear(value(col, i));
		}
		house(emptyCell).forEach(cell -> validNumbers.clear(value(cell)));
		return validNumbers.stream();
	}
}