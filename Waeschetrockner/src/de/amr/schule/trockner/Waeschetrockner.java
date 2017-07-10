package de.amr.schule.trockner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

import de.amr.easy.game.Application;
import de.amr.easy.game.entity.GameEntity;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.statemachine.StateMachine;

public class Waeschetrockner extends GameEntity {

	private StateMachine<String, String> hauptAutomat;
	private StateMachine<String, String> türAutomat;
	private StateMachine<String, String> zeitAutomat;

	public Waeschetrockner(WaeschetrocknerApp app) {
		hauptAutomat = new StateMachine<>("Trockner", String.class, "Aus");

		// Aus
		hauptAutomat.changeOnInput("EinAusTaste", "Aus", "Bereit");

		// Bereit
		hauptAutomat.changeOnInput("EinAusTaste", "Bereit", "Aus");
		hauptAutomat.changeOnInput("StartTaste", "Bereit", "Läuft", () -> türAutomat.is("Zu"));

		// Läuft
		hauptAutomat.state("Läuft").entry = state -> {
			if (zeitAutomat.is("15")) {
				state.setDuration(15 * 60);
			} else if (zeitAutomat.is("20")) {
				state.setDuration(20 * 60);
			}
		};
		hauptAutomat.changeOnInput("EinAusTaste", "Läuft", "Aus", (läuft, aus) -> {
			türAutomat.addInput("TürAuf");
		});
		hauptAutomat.changeOnInput("TürAuf", "Läuft", "Aus");
		hauptAutomat.changeOnTimeout("Läuft", "Aus", (läuft, aus) -> app.assets.sound("fertig.mp3").play());

		türAutomat = new StateMachine<>("Tür", String.class, "Zu");
		türAutomat.changeOnInput("TürAuf", "Zu", "Auf");
		türAutomat.changeOnInput("TürZu", "Auf", "Zu");

		zeitAutomat = new StateMachine<>("Zeitwahl", String.class, "15");
		zeitAutomat.changeOnInput("Auf20", "15", "20", () -> hauptAutomat.is("Bereit"));
		zeitAutomat.changeOnInput("Auf15", "20", "15", () -> hauptAutomat.is("Bereit"));
	}

	@Override
	public void init() {
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(automat -> automat.setLogger(Application.LOG));
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(StateMachine::init);
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_E)) {
			hauptAutomat.addInput("EinAusTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_S)) {
			hauptAutomat.addInput("StartTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_T)) {
			hauptAutomat.addInput("TürAuf");
			türAutomat.addInput("TürAuf");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_Z)) {
			türAutomat.addInput("TürZu");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_2)) {
			zeitAutomat.addInput("Auf20");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_1)) {
			zeitAutomat.addInput("Auf15");
		}
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(StateMachine::update);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.setFont(new Font("Sans", Font.PLAIN, 30));
		g.drawString(String.format("Trockner: %s, Tür: %s, Zeit %s", hauptAutomat.stateID(), türAutomat.stateID(),
				zeitAutomat.stateID()), 100, 100);
	}
}