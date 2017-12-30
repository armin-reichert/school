package de.amr.schule.darts.counter;

import de.amr.schule.darts.checkout.CheckOutTable;

public class CounterModel {

	private int turn; // 0,1
	private final int pointsRemaining[];
	private int pointsThrown;
	private final CheckOutTable checkOutTable;
	
	public CounterModel() {
		turn = 0;
		pointsRemaining = new int[2];
		pointsRemaining[0] = pointsRemaining[1] = 501;
		pointsThrown = 0;
		checkOutTable = new CheckOutTable();
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int getPointsThrown() {
		return pointsThrown;
	}
	
	public void setPointsThrown(int pointsThrown) {
		this.pointsThrown = pointsThrown;
	}
	
	public int getPointsRemaining(int turn) {
		return pointsRemaining[turn];
	}

	public void setPointsRemaining(int turn, int points) {
		pointsRemaining[turn] = points;
	}
	
	public CheckOutTable getCheckOutTable() {
		return checkOutTable;
	}
	
}
