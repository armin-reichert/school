package de.amr.schule.markise;

import java.util.stream.Stream;

import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.sprite.Sprite;

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
		int newPosition = Math.round(markise.getPosition() + markise.tf.getVelocityX());
		if (newPosition < 0) {
			markise.tf.setVelocityX(-markise.getPosition());
			markise.setPosition(Math.round(markise.getPosition() + markise.tf.getVelocityX()));
		} else {
			markise.setPosition(newPosition);
		}
	}

	@Override
	public Sprite currentSprite() {
		return null;
	}

	@Override
	public Stream<Sprite> getSprites() {
		return Stream.empty();
	}
}