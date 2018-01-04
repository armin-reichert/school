package de.amr.schule.darts.counter.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.amr.schule.darts.counter.model.DartsGameModel;
import de.amr.schule.darts.counter.model.PlayerModel;
import net.miginfocom.swing.MigLayout;

public class DartsCounterUI extends JFrame {

	private DartsGameModel game;
	private int inputMode; // single, double, triple
	private int dartsThrownInTake;

	private JButton button25;
	private JPanel playerCounterGrid;
	private PlayerCounterUI playerCounter0;
	private PlayerCounterUI playerCounter1;
	private PlayerCounterUI playerCounter2;
	private PlayerCounterUI playerCounter3;
	private final ButtonGroup inputModeButtonGroup = new ButtonGroup();
	private JRadioButton rbSingle;
	private JRadioButton rbDouble;
	private JRadioButton rbTriple;

	private void setInputMode(int mode) {
		inputMode = mode;
		rbSingle.setSelected(mode == 1);
		rbDouble.setSelected(mode == 2);
		rbTriple.setSelected(mode == 3);
		button25.setEnabled(mode == 1 || mode == 2);
	}

	public void newGame(int numPlayers) {

		dartsThrownInTake = 0;

		game = new DartsGameModel(numPlayers, 501);

		playerCounter0.setPlayer(game.getPlayer(0));
		playerCounter1.setPlayer(game.getPlayer(1));

		if (numPlayers >= 3) {
			playerCounter2.setPlayer(game.getPlayer(2));
			playerCounter2.setVisible(true);
		} else {
			playerCounter2.setVisible(false);
		}

		if (numPlayers >= 4) {
			playerCounter3.setPlayer(game.getPlayer(3));
			playerCounter3.setVisible(true);
		} else {
			playerCounter3.setVisible(false);
		}

		setInputMode(1);
		updateView();
	}

	private void saveScore(int field) {
		final int score = inputMode * field;
		final PlayerModel player = game.getCurrentPlayer();

		dartsThrownInTake += 1;

		if (score > player.getPointsRemaining()) {
			noScore();
			return;
		}

		player.addToScore(score);
		player.addToScoreInTake(score);
		player.setPointsAverage(player.getPointsScored() / (player.getLegsCompleted() + 1));

		if (dartsThrownInTake == 3) {
			player.setLegsCompleted(player.getLegsCompleted() + 1);
			game.nextPlayer();
			game.getCurrentPlayer().setPointsInTake(0);
			dartsThrownInTake = 0;
		}
		setInputMode(1);

		updateView();
	}

	private void noScore() {
		final PlayerModel player = game.getCurrentPlayer();
		player.addToScore(-player.getPointsInTake());
		player.setLegsCompleted(player.getLegsCompleted() + 1);
		player.setPointsInTake(0);
		game.nextPlayer();
		game.getCurrentPlayer().setPointsInTake(0);
		dartsThrownInTake = 0;
		setInputMode(1);

		updateView();
	}

	public void updateView() {
		playerCounter0.updateView();
		playerCounter1.updateView();
		if (game.getNumPlayers() >= 3) {
			playerCounter2.updateView();
		}
		if (game.getNumPlayers() >= 4) {
			playerCounter3.updateView();
		}
	}

	public DartsCounterUI() {
		getContentPane().setPreferredSize(new Dimension(900, 750));

		setTitle("Darts");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(
				new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][]"));

		playerCounterGrid = new JPanel();
		getContentPane().add(playerCounterGrid, "cell 0 0,grow");
		playerCounterGrid.setLayout(new GridLayout(0, 4, 0, 0));

		playerCounter0 = new PlayerCounterUI();
		playerCounterGrid.add(playerCounter0);

		playerCounter1 = new PlayerCounterUI();
		playerCounterGrid.add(playerCounter1);

		playerCounter2 = new PlayerCounterUI();
		playerCounterGrid.add(playerCounter2);

		playerCounter3 = new PlayerCounterUI();
		playerCounterGrid.add(playerCounter3);

		JPanel keyboard = new JPanel();
		getContentPane().add(keyboard, "cell 0 3 2 1,growy");
		keyboard.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][]", "[][][][]"));

		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(1);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_1, "cell 0 0,grow");

		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(2);
			}
		});
		button_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_2, "cell 1 0,grow");

		JButton button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(3);
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_3, "cell 2 0,grow");

		JButton button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(4);
			}
		});
		button_4.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_4, "cell 3 0,grow");

		JButton button_5 = new JButton("5");
		button_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(5);
			}
		});
		button_5.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_5, "cell 4 0,grow");

		JButton button_6 = new JButton("6");
		button_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(6);
			}
		});
		button_6.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_6, "cell 5 0,grow");

		JButton button_7 = new JButton("7");
		button_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(7);
			}
		});
		button_7.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_7, "cell 6 0,grow");

		JButton button_8 = new JButton("8");
		button_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(8);
			}
		});
		button_8.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_8, "cell 7 0,grow");

		JButton button_9 = new JButton("9");
		button_9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(9);
			}
		});
		button_9.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_9, "cell 8 0,grow");

		JButton button_10 = new JButton("10");
		button_10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(10);
			}
		});
		button_10.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_10, "cell 9 0,grow");

		JButton button_11 = new JButton("11");
		button_11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(11);
			}
		});

		JButton btnNewButton = new JButton("Out");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(0);
			}
		});

		Component horizontalStrut = Box.createHorizontalStrut(20);
		keyboard.add(horizontalStrut, "cell 11 0");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(btnNewButton, "cell 12 0,growx");
		button_11.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_11, "cell 0 1,grow");

		JButton button_12 = new JButton("12");
		button_12.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(12);
			}
		});
		button_12.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_12, "cell 1 1,grow");

		JButton button_13 = new JButton("13");
		button_13.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(13);
			}
		});
		button_13.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_13, "cell 2 1,grow");

		JButton button_14 = new JButton("14");
		button_14.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(14);
			}
		});
		button_14.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_14, "cell 3 1,grow");

		JButton button_15 = new JButton("15");
		button_15.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(15);
			}
		});
		button_15.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_15, "cell 4 1,grow");

		JButton button_16 = new JButton("16");
		button_16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(16);
			}
		});
		button_16.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_16, "cell 5 1,grow");

		JButton button_17 = new JButton("17");
		button_17.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(17);
			}
		});
		button_17.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_17, "cell 6 1,grow");

		JButton button_18 = new JButton("18");
		button_18.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(18);
			}
		});
		button_18.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_18, "cell 7 1,grow");

		JButton button_19 = new JButton("19");
		button_19.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(19);
			}
		});
		button_19.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_19, "cell 8 1,grow");

		JButton button_20 = new JButton("20");
		button_20.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(20);
			}
		});
		button_20.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_20, "cell 9 1,grow");

		button25 = new JButton("25");
		button25.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(25);
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
		keyboard.add(btnNoScore, "cell 12 1,growx");

		JPanel panelInputMode = new JPanel();
		getContentPane().add(panelInputMode, "cell 0 4 2 1,alignx left,growy");

		rbSingle = new JRadioButton("Single");
		rbSingle.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbSingle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 1;
				button25.setEnabled(true);
			}
		});
		panelInputMode.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		inputModeButtonGroup.add(rbSingle);
		panelInputMode.add(rbSingle);

		rbDouble = new JRadioButton("Double");
		rbDouble.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbDouble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 2;
				button25.setEnabled(true);
			}
		});
		inputModeButtonGroup.add(rbDouble);
		panelInputMode.add(rbDouble);

		rbTriple = new JRadioButton("Triple");
		rbTriple.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbTriple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputMode = 3;
				button25.setEnabled(false);
			}
		});
		inputModeButtonGroup.add(rbTriple);
		panelInputMode.add(rbTriple);

		Component verticalStrut = Box.createVerticalStrut(20);
		getContentPane().add(verticalStrut, "cell 0 5");

		JPanel panelGameModes = new JPanel();
		getContentPane().add(panelGameModes, "cell 0 6,grow");
		panelGameModes.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btnNewGame2 = new JButton("2 Players");
		btnNewGame2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame(2);
			}
		});
		panelGameModes.add(btnNewGame2);
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
		panelGameModes.add(btnNewGame3);

		JButton btnNewGame4 = new JButton("4 Players");
		btnNewGame4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame(4);
			}
		});
		btnNewGame4.setForeground(Color.BLUE);
		btnNewGame4.setFont(new Font("Tahoma", Font.BOLD, 40));
		panelGameModes.add(btnNewGame4);
	}
}
