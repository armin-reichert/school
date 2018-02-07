package de.amr.schule.darts.counter.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import de.amr.schule.darts.counter.model.DartsGame;
import de.amr.schule.darts.counter.model.Player;
import net.miginfocom.swing.MigLayout;

public class DartsCounterUI extends JFrame {

	private DartsGame game;
	private int dartsThrownInTake;
	private PlayerCounterUI playerCounter0;
	private PlayerCounterUI playerCounter1;
	private PlayerCounterUI playerCounter2;
	private PlayerCounterUI playerCounter3;
	private final ButtonGroup startPointsButtonGroup = new ButtonGroup();
	private JRadioButtonMenuItem miRbPoints501;
	private JRadioButtonMenuItem miRbPoints301;
	private JRadioButtonMenuItem miRbPoints101;
	private final ButtonGroup numPlayersButtonGroup = new ButtonGroup();
	private JRadioButtonMenuItem miPlayers2;
	private JRadioButtonMenuItem miPlayers3;
	private JRadioButtonMenuItem miPlayers4;
	private JFrame boardEditorWindow;
	private PointsKeyboard pointsKeyboard;

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
		updateView();
	}

	private void saveScore(int points) {
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
		updateView();
	}

	public void updateView() {
		pointsKeyboard.reset();
		playerCounter0.updateView();
		playerCounter1.updateView();
		playerCounter2.updateView();
		playerCounter3.updateView();
	}

	private void showBoardEditorWindow() {
		if (boardEditorWindow == null) {
			DartBoardUI board = new DartBoardUI((int) (0.9 * getHeight()));
			board.addPointsListener(evt -> {
				saveScore((int) evt.getNewValue());
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
		getContentPane().setPreferredSize(new Dimension(800, 600));

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

		JPanel panelKeyboard = new JPanel();
		getContentPane().add(panelKeyboard, "flowx,cell 0 3 2 1,growy");
		panelKeyboard.setLayout(new MigLayout("", "[grow][]", "[grow]"));

		pointsKeyboard = new PointsKeyboard();
		panelKeyboard.add(pointsKeyboard, "cell 0 0,grow");

		JButton btnNoScore = new JButton("No Score");
		btnNoScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				noScore();
			}
		});
		btnNoScore.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelKeyboard.add(btnNoScore, "cell 1 0,grow");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuGame = new JMenu("Spiel");
		menuGame.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuBar.add(menuGame);

		JMenuItem miNewGame = new JMenuItem("Neu");
		miNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		miNewGame.setFont(new Font("Arial Black", Font.PLAIN, 16));
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
		miQuit.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuGame.add(miQuit);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_1);

		JMenu menuSettings = new JMenu("Einstellungen");
		menuSettings.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuBar.add(menuSettings);

		JMenu menuPlayers = new JMenu("Spieler");
		menuSettings.add(menuPlayers);
		menuPlayers.setFont(new Font("Arial Black", Font.PLAIN, 16));

		miPlayers2 = new JRadioButtonMenuItem("2 Spieler");
		numPlayersButtonGroup.add(miPlayers2);
		miPlayers2.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuPlayers.add(miPlayers2);

		miPlayers3 = new JRadioButtonMenuItem("3 Spieler");
		numPlayersButtonGroup.add(miPlayers3);
		miPlayers3.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuPlayers.add(miPlayers3);

		miPlayers4 = new JRadioButtonMenuItem("4 Spieler");
		numPlayersButtonGroup.add(miPlayers4);
		miPlayers4.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuPlayers.add(miPlayers4);

		JMenu menuInitialPoints = new JMenu("Anfangspunkte");
		menuSettings.add(menuInitialPoints);
		menuInitialPoints.setFont(new Font("Arial Black", Font.PLAIN, 16));

		miRbPoints501 = new JRadioButtonMenuItem("501");
		miRbPoints501.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStartPoints(501);
			}
		});
		startPointsButtonGroup.add(miRbPoints501);
		miRbPoints501.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuInitialPoints.add(miRbPoints501);

		miRbPoints301 = new JRadioButtonMenuItem("301");
		miRbPoints301.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStartPoints(301);
			}
		});
		startPointsButtonGroup.add(miRbPoints301);
		miRbPoints301.setFont(new Font("Arial Black", Font.PLAIN, 16));
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
		miRbPoints101.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuInitialPoints.add(miRbPoints101);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		menuBar.add(horizontalStrut_2);

		JMenu mnAnsicht = new JMenu("Ansicht");
		mnAnsicht.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuBar.add(mnAnsicht);

		JMenuItem miBoardAnzeigen = new JMenuItem("Board anzeigen");
		miBoardAnzeigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showBoardEditorWindow();
			}
		});
		miBoardAnzeigen.setFont(new Font("Arial Black", Font.PLAIN, 16));
		mnAnsicht.add(miBoardAnzeigen);

		// Initialize
		setStartPoints(501);
		setNumPlayers(4);
		pointsKeyboard.addPropertyChangeListener(PointsKeyboard.PROPERTY_POINTS, evt -> {
			int points = (int) evt.getNewValue();
			saveScore(points);
		});
	}
}
