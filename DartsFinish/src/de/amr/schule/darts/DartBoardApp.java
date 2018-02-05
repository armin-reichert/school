package de.amr.schule.darts;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.amr.schule.darts.counter.ui.DartBoard;

public class DartBoardApp {

	private DartBoard board;

	public DartBoardApp(int boardSize) {
		try {
			BufferedImage boardImage = ImageIO
					.read(DartBoard.class.getResourceAsStream("/dartboard.png"));
			board = new DartBoard(boardImage, boardSize);
		} catch (Exception x) {
			throw new RuntimeException("Board image not found");
		}
	}

	public static void main(String... args) {
		DartBoardApp app = new DartBoardApp(800);
		EventQueue.invokeLater(() -> {
			JFrame frame = new JFrame("Dart Board");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.getContentPane().add(app.board);
			frame.pack();
			frame.setVisible(true);
			app.board.addPropertyChangeListener(evt -> {
				Integer points = (Integer) evt.getNewValue();
				JOptionPane.showMessageDialog(frame, points + " points");
			});
		});
	}
}
