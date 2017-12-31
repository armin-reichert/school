package de.amr.schule.darts.counter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

public class CounterWindow extends JFrame {

	private CounterModel model;

	private int inputMode; // single, double, triple
	private int numbersEntered;

	private final ButtonGroup modeButtonGroup = new ButtonGroup();
	private JRadioButton rbSingle;
	private JButton button25;
	private JPanel playerCounterGrid;
	private PlayerCounter playerCounter0;
	private PlayerCounter playerCounter1;
	private PlayerCounter playerCounter2;
	private PlayerCounter playerCounter3;

	public void newGame(int players) {
		model = new CounterModel(players, 501);

		inputMode = 1;
		getRbSingle().setSelected(true);
		getButton_25().setEnabled(true);

		numbersEntered = 0;

		playerCounter0.setModel(model);
		playerCounter0.setPlayer(0);

		playerCounter1.setModel(model);
		playerCounter1.setPlayer(1);

		if (players >= 3) {
			playerCounter2.setModel(model);
			playerCounter2.setPlayer(2);
			playerCounter2.setVisible(true);
		} else {
			playerCounter2.setVisible(false);
		}

		if (players >= 4) {
			playerCounter3.setModel(model);
			playerCounter3.setPlayer(3);
			playerCounter3.setVisible(true);
		} else {
			playerCounter3.setVisible(false);
		}

		updateView();
	}

	private void numberEntered(int number) {
		final int thrownPoints = inputMode * number;
		final int turn = model.getTurn();

		if (model.getPointsRemaining(turn) - thrownPoints < 0) {
			noScore(); // überworfen
			return;
		}

		model.setPointsRemaining(turn, model.getPointsRemaining(turn) - thrownPoints);
		model.setPointsInTake(turn, model.getPointsInTake(turn) + thrownPoints);

		++numbersEntered;
		if (numbersEntered == 3) {
			numbersEntered = 0;
			model.nextTurn();
			model.setPointsInTake(model.getTurn(), 0);
		}

		inputMode = 1;
		// TODO move to updateView()
		getRbSingle().setSelected(true);

		updateView();
	}

	private void noScore() {
		final int turn = model.getTurn();
		model.setPointsRemaining(turn, model.getPointsRemaining(turn) + model.getPointsInTake(turn));
		model.setPointsInTake(turn, 0);
		numbersEntered = 0;
		model.nextTurn();
		model.setPointsInTake(model.getTurn(), 0);
		inputMode = 1;
		// TODO move to updateView()
		getRbSingle().setSelected(true);

		updateView();
	}

	public void updateView() {
		playerCounter0.updateView();
		playerCounter1.updateView();
		if (model.getNumPlayers() >= 3) {
			playerCounter2.updateView();
		}
		if (model.getNumPlayers() >= 4) {
			playerCounter3.updateView();
		}
	}

	public CounterWindow() {

		setTitle("Darts Counter");
		setPreferredSize(new Dimension(920, 760));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(
				new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][][grow][grow][grow][]"));

		playerCounterGrid = new JPanel();
		getContentPane().add(playerCounterGrid, "cell 0 0,grow");
		playerCounterGrid.setLayout(new GridLayout(0, 4, 0, 0));

		playerCounter0 = new PlayerCounter();
		playerCounterGrid.add(playerCounter0);

		playerCounter1 = new PlayerCounter();
		playerCounterGrid.add(playerCounter1);

		playerCounter2 = new PlayerCounter();
		playerCounterGrid.add(playerCounter2);

		playerCounter3 = new PlayerCounter();
		playerCounterGrid.add(playerCounter3);

		JPanel keyboard = new JPanel();
		getContentPane().add(keyboard, "cell 0 3 2 1,growy");
		keyboard.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][]", "[][][][]"));

		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(1);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_1, "cell 0 0,grow");

		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(2);
			}
		});
		button_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_2, "cell 1 0,grow");

		JButton button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(3);
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_3, "cell 2 0,grow");

		JButton button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(4);
			}
		});
		button_4.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_4, "cell 3 0,grow");

		JButton button_5 = new JButton("5");
		button_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(5);
			}
		});
		button_5.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_5, "cell 4 0,grow");

		JButton button_6 = new JButton("6");
		button_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(6);
			}
		});
		button_6.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_6, "cell 5 0,grow");

		JButton button_7 = new JButton("7");
		button_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(7);
			}
		});
		button_7.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_7, "cell 6 0,grow");

		JButton button_8 = new JButton("8");
		button_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(8);
			}
		});
		button_8.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_8, "cell 7 0,grow");

		JButton button_9 = new JButton("9");
		button_9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(9);
			}
		});
		button_9.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_9, "cell 8 0,grow");

		JButton button_10 = new JButton("10");
		button_10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(10);
			}
		});
		button_10.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_10, "cell 9 0,grow");

		JButton button_11 = new JButton("11");
		button_11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(11);
			}
		});

		JButton btnNewButton = new JButton("Out");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(0);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(btnNewButton, "cell 11 0,growx");
		button_11.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_11, "cell 0 1,grow");

		JButton button_12 = new JButton("12");
		button_12.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(12);
			}
		});
		button_12.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_12, "cell 1 1,grow");

		JButton button_13 = new JButton("13");
		button_13.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(13);
			}
		});
		button_13.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_13, "cell 2 1,grow");

		JButton button_14 = new JButton("14");
		button_14.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(14);
			}
		});
		button_14.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_14, "cell 3 1,grow");

		JButton button_15 = new JButton("15");
		button_15.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(15);
			}
		});
		button_15.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_15, "cell 4 1,grow");

		JButton button_16 = new JButton("16");
		button_16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(16);
			}
		});
		button_16.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_16, "cell 5 1,grow");

		JButton button_17 = new JButton("17");
		button_17.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(17);
			}
		});
		button_17.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_17, "cell 6 1,grow");

		JButton button_18 = new JButton("18");
		button_18.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(18);
			}
		});
		button_18.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_18, "cell 7 1,grow");

		JButton button_19 = new JButton("19");
		button_19.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(19);
			}
		});
		button_19.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_19, "cell 8 1,grow");

		JButton button_20 = new JButton("20");
		button_20.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(20);
			}
		});
		button_20.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_20, "cell 9 1,grow");

		button25 = new JButton("25");
		button25.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(25);
			}
		});
		button25.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button25, "cell 10 1,grow");

		JButton btnNoScore = new JButton("No Score");
		btnNoScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				noScore();
			}
		});
		btnNoScore.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(btnNoScore, "cell 11 1,growx");

		JPanel modePanel = new JPanel();
		getContentPane().add(modePanel, "cell 0 4 2 1,grow");
		modePanel.setLayout(new GridLayout(1, 0, 0, 0));

		rbSingle = new JRadioButton("Single");
		rbSingle.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbSingle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 1;
				button25.setEnabled(true);
			}
		});
		modeButtonGroup.add(rbSingle);
		modePanel.add(rbSingle);

		JRadioButton rbDouble = new JRadioButton("Double");
		rbDouble.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbDouble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 2;
				button25.setEnabled(true);
			}
		});
		modeButtonGroup.add(rbDouble);
		modePanel.add(rbDouble);

		JRadioButton rbTriple = new JRadioButton("Triple");
		rbTriple.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbTriple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 3;
				button25.setEnabled(false);
			}
		});
		modeButtonGroup.add(rbTriple);
		modePanel.add(rbTriple);

		inputMode = 1;
		rbSingle.setSelected(true);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 5,grow");
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btnNewGame2 = new JButton("2 Players");
		btnNewGame2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame(2);
			}
		});
		panel.add(btnNewGame2);
		btnNewGame2.setForeground(Color.BLUE);
		btnNewGame2.setFont(new Font("Tahoma", Font.BOLD, 40));

		JButton btnNewGame3 = new JButton("3 Players");
		btnNewGame3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame(3);
			}
		});
		btnNewGame3.setForeground(Color.BLUE);
		btnNewGame3.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(btnNewGame3);

		JButton btnNewGame4 = new JButton("4 Players");
		btnNewGame4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame(4);
			}
		});
		btnNewGame4.setForeground(Color.BLUE);
		btnNewGame4.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(btnNewGame4);
	}

	public JButton getButton_25() {
		return button25;
	}

	public JRadioButton getRbSingle() {
		return rbSingle;
	}

}
