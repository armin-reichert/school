package de.amr.schule.sudoku;

import java.util.OptionalInt;

public class Solver {

	/**
	 * https://sudoku.soeinding.de/sudokuAusdrucken.php
	 */
	static int[] SAMPLE = {
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

	static long calls;

	public static void main(String[] args) {
		Solver solver = new Solver();
		Board board = new Board(SAMPLE);
		System.out.println(board);
		solver.solve(board);
		System.out.println("Recursive calls: " + calls);
	}

	public void solve(Board board) {
		++calls;
		OptionalInt emptyCell = board.emptyCell();
		if (!emptyCell.isPresent()) {
			System.out.println(board);
		} else {
			int cell = emptyCell.getAsInt();
			board.validNumbers(cell).forEach(number -> {
				board.set(cell, number);
				solve(board);
				board.set(cell, 0);
			});
		}
	}
}