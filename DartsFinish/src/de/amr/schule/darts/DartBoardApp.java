package de.amr.schule.darts;

import java.awt.EventQueue;

import javax.swing.JFrame;

import de.amr.schule.darts.counter.ui.DartBoard;

public class DartBoardApp {

	public static void main(String... args) {
		EventQueue.invokeLater(() -> {
			DartBoard board = new DartBoard("/dartboard.png", 900);
			board.addPropertyChangeListener(evt -> {
				Integer points = (Integer) evt.getNewValue();
				System.out.println(points + " Punkte");
			});
			JFrame frame = new JFrame("Dart Board");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.getContentPane().add(board);
			frame.pack();
			frame.setVisible(true);
		});
	}
}
