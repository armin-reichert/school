package de.amr.schule.darts.counter.model;

public class DartsGameModel {

	private final int startPoints;
	private final PlayerModel[] players;
	private int turn;

	public DartsGameModel(int numPlayers, int startPoints) {
		this.startPoints = startPoints;
		turn = 0;
		players = new PlayerModel[numPlayers];
		for (int i = 0; i < numPlayers; i += 1) {
			players[i] = new PlayerModel(this);
			players[i].setName("Player " + (i + 1));
			players[i].setPointsScored(0);
			players[i].setPointsInTake(0);
			players[i].setPointsAverage(0);
			players[i].setLegsCompleted(0);
		}
	}

	public int getNumPlayers() {
		return players.length;
	}

	public int getStartPoints() {
		return startPoints;
	}

	public void nextPlayer() {
		turn = (turn + 1) % players.length;
	}

	public PlayerModel getCurrentPlayer() {
		return players[turn];
	}

	public PlayerModel getPlayer(int i) {
		return i < players.length ? players[i] : null;
	}
}