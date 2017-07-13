package de.amr.schule.markise;

public class PositionsSensor {

	private float startPositionX;
	private float endPositionX;

	private final Markise markise;

	public PositionsSensor(Markise markise) {
		this.markise = markise;
	}

	public void setStartPositionX(float startPositionX) {
		this.startPositionX = startPositionX;
	}

	public void setEndPositionX(float endPositionX) {
		this.endPositionX = endPositionX;
	}

	public boolean endPositionErreicht() {
		return markise.tf.getX() >= endPositionX;
	}

	public boolean startPositionErreicht() {
		return markise.tf.getX() <= startPositionX;
	}

}
