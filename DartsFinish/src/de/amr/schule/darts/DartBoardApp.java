package de.amr.schule.darts;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.amr.schule.darts.counter.ui.DartBoardUI;

public class DartBoardApp {

	private final DartBoardUI boardUI;

	public DartBoardApp(int boardSize) {
		boardUI = new DartBoardUI(boardSize);
	}

	public static void main(String... args) {
		EventQueue.invokeLater(() -> {
			DartBoardApp app = new DartBoardApp(900);
			JFrame frame = new JFrame("Dart Board");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.getContentPane().add(app.boardUI);
			frame.pack();
			frame.setVisible(true);
			app.boardUI.addPropertyChangeListener(DartBoardUI.PROPERTY_POINTS, evt -> {
				Integer points = (Integer) evt.getNewValue();
				JOptionPane.showMessageDialog(frame, points + " points");
			});
		});
	}
}
