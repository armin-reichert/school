package de.amr.schule.sudoku;

import java.util.OptionalInt;

public class Solver {

	/**
	 * https://sudoku.soeinding.de/sudokuAusdrucken.php
	 */
	static byte[] SAMPLE_1 = {
		//@formatter:off
		0,0,0,	0,0,4,	8,2,0,
		0,5,0,	8,0,0,	0,0,1,
		0,0,3,	0,9,0,	0,0,0,
		
		0,0,0,	0,2,6,	0,0,0,
		0,9,0,	0,0,0,	0,0,0,
		5,0,4,	0,0,8,	0,0,9,

		0,1,2,	0,0,0,	4,0,0,
		0,0,0,	5,6,0,	0,0,0,
		0,3,0,	0,0,0,	0,9,0,
		//@formatter:on
	};
	static byte[] SAMPLE_2 = {
		//@formatter:off
		0,0,0,	8,0,0,	3,0,0,
		4,0,0,	0,0,0,	0,0,5,
		0,0,0,	0,0,0,	2,0,7,
		
		0,0,8,	0,0,5,	0,6,0,
		0,0,0,	1,6,0,	0,0,0,
		6,5,0,	0,0,2,	0,0,9,

		0,0,0,	0,3,0,	0,0,0,
		0,0,9,	5,0,4,	0,1,0,
		0,6,1,	0,0,0,	0,0,0,
		//@formatter:on
	};

	static long calls;

	public static void main(String[] args) {
		solve(SAMPLE_1);
		solve(SAMPLE_2);
	}

	public static void solve(byte[] sample) {
		calls = 0;
		Board board = new Board(sample);
		System.out.println("Problem:");
		System.out.println(board);
		long time = System.nanoTime();
		System.out.println("Solution:");
		new Solver().solve(board);
		time = System.nanoTime() - time;
		System.out.println(String.format("Recursive calls: %d, time: %d millis\n", calls, time / 1000_000L));
	}

	public void solve(Board board) {
		++calls;
		OptionalInt emptyCell = board.emptyCell();
		if (!emptyCell.isPresent()) {
			System.out.println(board);
		} else {
			int cell = emptyCell.getAsInt();
			board.validNumbers(cell).forEach(number -> {
				board.set(cell, (byte) number);
				solve(board);
				board.set(cell, (byte) 0);
			});
		}
	}
}