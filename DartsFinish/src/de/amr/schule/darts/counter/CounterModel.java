package de.amr.schule.darts.counter;

import de.amr.schule.darts.checkout.CheckOutTable;

public class CounterModel {

	public static final CheckOutTable CHECKOUT_TABLE = new CheckOutTable();

	private final int numPlayers;
	private final int startPoints;

	private int turn; // 0,1,...,numPlayers-1
	private final int pointsRemaining[];
	private final int pointsInTake[];

	public CounterModel(int numPlayers, int startPoints) {
		this.numPlayers = numPlayers;
		this.startPoints = startPoints;
		turn = 0;
		pointsRemaining = new int[numPlayers];
		for (int player = 0; player < numPlayers; player += 1) {
			pointsRemaining[player] = startPoints;
		}
		pointsInTake = new int[numPlayers];
		for (int player = 0; player < numPlayers; player += 1) {
			pointsInTake[player] = 0;
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
	
	public int getPointsInTake(int player) {
		return pointsInTake[player];
	}
	
	public void setPointsInTake(int player, int points) {
		pointsInTake[player] = points;
	}
}
