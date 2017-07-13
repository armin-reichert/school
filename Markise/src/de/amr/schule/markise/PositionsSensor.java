package de.amr.schule.markise;

public class PositionsSensor {

	private float startPosition;
	private float endPosition;

	private final Markise markise;

	public PositionsSensor(Markise markise) {
		this.markise = markise;
	}

	public void setStartPosition(float startPosition) {
		this.startPosition = startPosition;
	}

	public void setEndPosition(float endPosition) {
		this.endPosition = endPosition;
	}

	public boolean endPositionErreicht() {
		return markise.getPosition() >= endPosition;
	}

	public boolean startPositionErreicht() {
		return markise.getPosition() <= startPosition;
	}

}