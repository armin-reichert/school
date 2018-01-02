package de.amr.schule.darts.counter.model;

public class DartsGameModel {

	private int startPoints;
	private int numPlayers;
	private int turn;

	private String playerNames[];
	private int pointsRemaining[];
	private int pointsInTake[];
	private int dartsThrown[];

	public DartsGameModel(int numPlayers, int startPoints) {
		this.numPlayers = numPlayers;
		this.startPoints = startPoints;
		this.turn = 0;
		playerNames = new String[numPlayers];
		pointsRemaining = new int[numPlayers];
		pointsInTake = new int[numPlayers];
		dartsThrown = new int[numPlayers];
		for (int player = 0; player < numPlayers; player += 1) {
			playerNames[player] = "Player " + (player + 1);
			pointsRemaining[player] = startPoints;
			pointsInTake[player] = 0;
			dartsThrown[player] = 0;
		}
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public int getStartPoints() {
		return startPoints;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void nextTurn() {
		turn = (turn + 1) % numPlayers;
	}

	public int getPointsRemaining(int player) {
		return pointsRemaining[player];
	}

	public void setPointsRemaining(int player, int points) {
		pointsRemaining[player] = points;
	}

	public void addPointsRemaining(int player, int points) {
		pointsRemaining[player] += points;
	}

	public int getPointsInTake(int player) {
		return pointsInTake[player];
	}

	public void setPointsInTake(int player, int points) {
		pointsInTake[player] = points;
	}

	public String getPlayerName(int player) {
		return playerNames[player];
	}

	public void setPlayerName(int player, String name) {
		playerNames[player] = name;
	}

	public int getDartsThrown(int player) {
		return dartsThrown[player];
	}

	public void addDartsThrown(int player, int n) {
		dartsThrown[player] += n;
	}
}
