package de.amr.schule.markise;

import de.amr.easy.game.entity.GameEntity;

public class Motor extends GameEntity {

	private final Markise markise;

	public Motor(Markise markise) {
		this.markise = markise;
	}

	void zurück() {
		markise.tf.setVelocityX(-2);
	}

	void schnellZurück() {
		markise.tf.setVelocityX(-4);
	}

	void vor() {
		markise.tf.setVelocityX(2);
	}

	void stop() {
		markise.tf.setVelocityX(0);
	}

	@Override
	public void update() {
		if (markise.tf.getX() + markise.tf.getVelocityX() < 0) {
			markise.tf.setVelocityX(-markise.tf.getX());
		}
		markise.tf.move();
	}

}
