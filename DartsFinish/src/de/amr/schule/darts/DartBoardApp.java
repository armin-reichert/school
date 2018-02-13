package de.amr.schule.darts;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.amr.schule.darts.game.ui.DartBoardUI;

public class DartBoardApp {

	public DartBoardApp(int boardSize) {
		JFrame frame = new JFrame("Dart Board");
		DartBoardUI boardUI = new DartBoardUI(boardSize);
		boardUI.addPropertyChangeListener(DartBoardUI.PROPERTY_POINTS, evt -> {
			Integer points = (Integer) evt.getNewValue();
			JOptionPane.showMessageDialog(frame, points + " points");
		});
		frame.getContentPane().add(boardUI);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String... args) {
		EventQueue.invokeLater(() -> new DartBoardApp(900));
	}
}
