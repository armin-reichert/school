package de.amr.schule.darts.game.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import de.amr.schule.darts.game.model.DartsGame;
import de.amr.schule.darts.game.model.Player;
import net.miginfocom.swing.MigLayout;

public class DartsCounterUI extends JFrame {

	private DartsGame game;
	private PlayerCounterUI playerCounter0;
	private PlayerCounterUI playerCounter1;
	private PlayerCounterUI playerCounter2;
	private PlayerCounterUI playerCounter3;
	private PointsKeyboard pointsKeyboard;
	private NewGameDialog newGameDialog;
	private JPanel panelPlayers;
	private DartBoardUI dartBoardUI;

	public void newGame(int numPlayers, int startPoints) {
		game = new DartsGame(numPlayers, startPoints);
		PlayerCounterUI[] playerCounters = { playerCounter0, playerCounter1, playerCounter2, playerCounter3 };
		for (int i = 0; i < playerCounters.length; ++i) {
			playerCounters[i].setPlayer(game.getPlayer(i));
			playerCounters[i].setVisible(i < numPlayers);
		}
		updateView();
	}

	private void openNewGameDialog() {
		if (newGameDialog == null) {
			newGameDialog = new NewGameDialog(this);
			newGameDialog.setTitle("Neues Spiel");
			newGameDialog.onOK = () -> newGame(newGameDialog.getNumPlayers(), newGameDialog.getStartPoints());
			newGameDialog.pack();
		}
		newGameDialog.setLocationRelativeTo(panelPlayers);
		newGameDialog.setVisible(true);
	}

	private void updateScore(int points) {
		final Player player = game.getCurrentPlayer();
		player.setDartsThrown(player.getDartsThrown() + 1);
		game.setDartsThrownInTake(game.getDartsThrownInTake() + 1);
		if (points > player.getPointsRemaining()) {
			noScore();
			return;
		}
		player.addToScore(points);
		player.addToScoreInTake(points);
		player.setPointsAverage((float)player.getPointsScored() / player.getDartsThrown());
		if (game.getDartsThrownInTake() == 3) {
			player.setLegsCompleted(player.getLegsCompleted() + 1);
			game.nextPlayer();
			game.getCurrentPlayer().setPointsInTake(0);
			game.setDartsThrownInTake(0);
		}
		updateView();
	}

	private void noScore() {
		final Player player = game.getCurrentPlayer();
		player.addToScore(-player.getPointsInTake());
		player.setLegsCompleted(player.getLegsCompleted() + 1);
		player.setPointsInTake(0);
		player.setDartsThrown(player.getDartsThrown() - game.getDartsThrownInTake() + 3);
		player.setPointsAverage((float)player.getPointsScored() / player.getDartsThrown());
		game.nextPlayer();
		game.getCurrentPlayer().setPointsInTake(0);
		game.setDartsThrownInTake(0);
		updateView();
	}

	public void updateView() {
		pointsKeyboard.reset();
		playerCounter0.updateView();
		playerCounter1.updateView();
		playerCounter2.updateView();
		playerCounter3.updateView();
	}

	public DartsCounterUI() {
		setPreferredSize(new Dimension(1360, 700));
		getContentPane().setBackground(new Color(245, 245, 220));

		setTitle("Darts");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[][grow,fill]", "[][]"));

		panelPlayers = new JPanel();
		panelPlayers.setMaximumSize(new Dimension(800, 32767));
		panelPlayers.setOpaque(false);
		getContentPane().add(panelPlayers, "cell 0 0,growy");
		panelPlayers.setLayout(new MigLayout("", "[][][][]", "[][]"));

		playerCounter0 = new PlayerCounterUI();
		panelPlayers.add(playerCounter0, "cell 0 0,grow");

		playerCounter1 = new PlayerCounterUI();
		panelPlayers.add(playerCounter1, "cell 1 0,grow");

		playerCounter2 = new PlayerCounterUI();
		panelPlayers.add(playerCounter2, "cell 2 0,grow");

		playerCounter3 = new PlayerCounterUI();
		panelPlayers.add(playerCounter3, "cell 3 0,grow");
		
		dartBoardUI = new DartBoardUI();
		getContentPane().add(dartBoardUI, "cell 1 0 1 2,growx,aligny center");

		JPanel panelKeyboard = new JPanel();
		panelKeyboard.setBackground(new Color(245, 245, 220));
		getContentPane().add(panelKeyboard, "flowx,cell 0 1,growy");
		panelKeyboard.setLayout(new MigLayout("", "[grow][]", "[grow]"));

		pointsKeyboard = new PointsKeyboard();
		panelKeyboard.add(pointsKeyboard, "cell 0 0");

		JButton button_NoScore = new JButton("No Score");
		button_NoScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				noScore();
			}
		});
		button_NoScore.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelKeyboard.add(button_NoScore, "cell 1 0,growy");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuGame = new JMenu("Spiel");
		menuGame.setFont(new Font("Arial Black", Font.PLAIN, 16));
		menuBar.add(menuGame);

		JMenuItem miNewGame = new JMenuItem("Neu...");
		miNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openNewGameDialog();
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

		// Initialize
		pointsKeyboard.addPropertyChangeListener(PointsKeyboard.PROPERTY_POINTS, evt -> {
			int points = (int) evt.getNewValue();
			updateScore(points);
		});
	}
}
