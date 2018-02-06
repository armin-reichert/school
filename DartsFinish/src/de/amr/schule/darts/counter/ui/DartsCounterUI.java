package de.amr.schule.darts.counter.ui;

import java.awt.BorderLayout;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import de.amr.schule.darts.counter.model.DartsGame;
import de.amr.schule.darts.counter.model.Player;
import net.miginfocom.swing.MigLayout;

public class DartsCounterUI extends JFrame {

	private DartsGame game;
	private int dartsThrownInTake;

	private JButton button25;
	private PlayerCounterUI playerCounter0;
	private PlayerCounterUI playerCounter1;
	private PlayerCounterUI playerCounter2;
	private PlayerCounterUI playerCounter3;
	private final ButtonGroup inputModeButtonGroup = new ButtonGroup();
	private JRadioButton rbSingle;
	private JRadioButton rbDouble;
	private JRadioButton rbTriple;
	private final ButtonGroup startPointsButtonGroup = new ButtonGroup();
	private JRadioButtonMenuItem miRbPoints501;
	private JRadioButtonMenuItem miRbPoints301;
	private JRadioButtonMenuItem miRbPoints101;
	private final ButtonGroup numPlayersButtonGroup = new ButtonGroup();
	private JRadioButtonMenuItem miPlayers2;
	private JRadioButtonMenuItem miPlayers3;
	private JRadioButtonMenuItem miPlayers4;
	private JFrame boardEditorWindow;

	private void setInputMode(int mode) {
		switch (mode) {
		case 1:
			rbSingle.setSelected(true);
			button25.setEnabled(true);
			break;
		case 2:
			rbDouble.setSelected(true);
			button25.setEnabled(true);
			break;
		case 3:
			rbTriple.setSelected(true);
			button25.setEnabled(false);
			break;
		default:
			rbSingle.setSelected(true);
			button25.setEnabled(true);
		}
	}

	private int getInputMode() {
		if (rbSingle.isSelected())
			return 1;
		if (rbDouble.isSelected())
			return 2;
		if (rbTriple.isSelected())
			return 3;
		return 1;
	}

	public void setStartPoints(int points) {
		switch (points) {
		case 101:
			miRbPoints101.setSelected(true);
			break;
		case 301:
			miRbPoints301.setSelected(true);
			break;
		case 501:
			miRbPoints501.setSelected(true);
			break;
		default:
			miRbPoints501.setSelected(true);
		}
	}

	public int getStartPoints() {
		if (miRbPoints101.isSelected())
			return 101;
		if (miRbPoints301.isSelected())
			return 301;
		if (miRbPoints501.isSelected())
			return 501;
		return 501;
	}

	public void setNumPlayers(int number) {
		switch (number) {
		case 2:
			miPlayers2.setSelected(true);
			break;
		case 3:
			miPlayers3.setSelected(true);
			break;
		case 4:
			miPlayers4.setSelected(true);
			break;
		default:
			miPlayers4.setSelected(true);
		}
	}

	public int getNumPlayers() {
		if (miPlayers2.isSelected())
			return 2;
		if (miPlayers3.isSelected())
			return 3;
		if (miPlayers4.isSelected())
			return 4;
		return 4;
	}

	public void newGame() {
		int numPlayers = getNumPlayers();
		game = new DartsGame(numPlayers, getStartPoints());
		PlayerCounterUI[] playerCounters = { playerCounter0, playerCounter1, playerCounter2,
				playerCounter3 };
		for (int i = 0; i < playerCounters.length; ++i) {
			playerCounters[i].setPlayer(game.getPlayer(i));
			playerCounters[i].setVisible(i < numPlayers);
		}
		dartsThrownInTake = 0;
		setInputMode(1);
		updateView();
	}

	private void saveScore(int number) {
		final int points = getInputMode() * number;
		final Player player = game.getCurrentPlayer();
		dartsThrownInTake += 1;
		if (points > player.getPointsRemaining()) {
			noScore();
			return;
		}
		player.addToScore(points);
		player.addToScoreInTake(points);
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
		final Player player = game.getCurrentPlayer();
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
		playerCounter2.updateView();
		playerCounter3.updateView();
	}
	
	private void showBoardEditorWindow() {
		if (boardEditorWindow == null) {
			DartBoardUI board = new DartBoardUI((int)(0.9*getHeight()));
			board.addPointsListener(evt -> {
				saveScore((int)evt.getNewValue());
			});
			boardEditorWindow = new JFrame("Board Editor");
			boardEditorWindow.setResizable(false);
			boardEditorWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		  boardEditorWindow.getContentPane().add(board, BorderLayout.CENTER);
			boardEditorWindow.pack();
		}
		boardEditorWindow.setVisible(true);
	}

	public DartsCounterUI() {
		getContentPane().setPreferredSize(new Dimension(900, 700));

		setTitle("Darts");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(
				new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][]"));

		JPanel playerCounterGrid = new JPanel();
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

		JButton btnNewButton = new JButton("Aus");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveScore(0);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(btnNewButton, "cell 10 0,growx");

		Component horizontalStrut = Box.createHorizontalStrut(25);
		keyboard.add(horizontalStrut, "cell 11 0");

		JButton btnNoScore = new JButton("No Score");
		btnNoScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				noScore();
			}
		});
		btnNoScore.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(btnNoScore, "cell 12 0 1 2,grow");
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

		JPanel panelInputMode = new JPanel();
		getContentPane().add(panelInputMode, "flowx,cell 0 4,alignx left");

		rbSingle = new JRadioButton("1x");
		rbSingle.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbSingle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInputMode(1);
			}
		});
		panelInputMode.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
		inputModeButtonGroup.add(rbSingle);
		panelInputMode.add(rbSingle);

		rbDouble = new JRadioButton("2x");
		rbDouble.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbDouble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInputMode(2);
			}
		});
		inputModeButtonGroup.add(rbDouble);
		panelInputMode.add(rbDouble);

		rbTriple = new JRadioButton("3x");
		rbTriple.setFont(new Font("Tahoma", Font.BOLD, 24));
		rbTriple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInputMode(3);
			}
		});
		inputModeButtonGroup.add(rbTriple);
		panelInputMode.add(rbTriple);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuGame = new JMenu("Spiel");
		menuGame.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuBar.add(menuGame);

		JMenuItem miNewGame = new JMenuItem("Neu");
		miNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		miNewGame.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuGame.add(miNewGame);

		JSeparator separator = new JSeparator();
		menuGame.add(separator);

		JMenuItem miQuit = new JMenuItem("Beenden");
		miQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		miQuit.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuGame.add(miQuit);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);

		JMenu menuSettings = new JMenu("Einstellungen");
		menuSettings.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuBar.add(menuSettings);

		JMenu menuPlayers = new JMenu("Spieler");
		menuSettings.add(menuPlayers);
		menuPlayers.setFont(new Font("Arial Black", Font.PLAIN, 20));

		miPlayers2 = new JRadioButtonMenuItem("2 Spieler");
		numPlayersButtonGroup.add(miPlayers2);
		miPlayers2.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuPlayers.add(miPlayers2);

		miPlayers3 = new JRadioButtonMenuItem("3 Spieler");
		numPlayersButtonGroup.add(miPlayers3);
		miPlayers3.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuPlayers.add(miPlayers3);

		miPlayers4 = new JRadioButtonMenuItem("4 Spieler");
		numPlayersButtonGroup.add(miPlayers4);
		miPlayers4.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuPlayers.add(miPlayers4);

		JMenu menuInitialPoints = new JMenu("Anfangspunkte");
		menuSettings.add(menuInitialPoints);
		menuInitialPoints.setFont(new Font("Arial Black", Font.PLAIN, 20));

		miRbPoints501 = new JRadioButtonMenuItem("501");
		miRbPoints501.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStartPoints(501);
			}
		});
		startPointsButtonGroup.add(miRbPoints501);
		miRbPoints501.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuInitialPoints.add(miRbPoints501);

		miRbPoints301 = new JRadioButtonMenuItem("301");
		miRbPoints301.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStartPoints(301);
			}
		});
		startPointsButtonGroup.add(miRbPoints301);
		miRbPoints301.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuInitialPoints.add(miRbPoints301);

		miRbPoints101 = new JRadioButtonMenuItem("101");
		miRbPoints101.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStartPoints(101);
			}
		});
		startPointsButtonGroup.add(miRbPoints101);
		miRbPoints101.setSelected(false);
		miRbPoints101.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuInitialPoints.add(miRbPoints101);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_2);
		
		JMenu mnAnsicht = new JMenu("Ansicht");
		mnAnsicht.setFont(new Font("Arial Black", Font.PLAIN, 20));
		menuBar.add(mnAnsicht);
		
		JMenuItem miBoardAnzeigen = new JMenuItem("Board anzeigen");
		miBoardAnzeigen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBoardEditorWindow();
			}
		});
		miBoardAnzeigen.setFont(new Font("Arial Black", Font.PLAIN, 20));
		mnAnsicht.add(miBoardAnzeigen);

		setStartPoints(501);
		setNumPlayers(4);
	}
}
