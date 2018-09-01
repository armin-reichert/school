package de.amr.schule.trockner;

import static de.amr.easy.game.Application.CLOCK;
import static de.amr.easy.game.Application.LOGGER;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.entity.GameEntityUsingSprites;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.sprite.Sprite;
import de.amr.statemachine.MatchStrategy;
import de.amr.statemachine.StateMachine;

public class Waeschetrockner extends GameEntityUsingSprites {

	private WaeschetrocknerApp app;

	public StateMachine<String, String> hauptAutomat;
	public StateMachine<String, String> türAutomat;
	public StateMachine<Integer, String> zeitAutomat;

	public Waeschetrockner(WaeschetrocknerApp app) {
		this.app = app;
		setSprite("s_trockner", Sprite.ofAssets("trockner.jpg"));
		setCurrentSprite("s_trockner");

		// Steuerung

		//@formatter:off
		hauptAutomat = StateMachine.define(String.class, String.class, MatchStrategy.BY_EQUALITY)
			.description("Trockner")
			.initialState("Aus")
		
		.states()
			.state("Aus")
			.state("Bereit")
			.state("Läuft").timeoutAfter(() -> CLOCK.sec(zeitAutomat.getState()))
				
		.transitions()
			.when("Aus").then("Bereit").on("EinAusTaste")
			.when("Aus").then("Läuft").on("StartTaste").condition(() -> türAutomat.getState().equals("Zu"))
			.stay("Aus").on("StartTaste").act(() -> LOGGER.info("Bitte Tür schließen"))
			
			.when("Bereit").then("Aus").on("EinAusTaste")
			.when("Bereit").then("Läuft").on("StartTaste").condition(() -> türAutomat.getState().equals("Zu"))
			
			.when("Läuft").then("Aus").on("EinAusTaste").act(() -> türAutomat.process("TürAuf"))
			.when("Läuft").then("Aus").on("TürAuf")
			.when("Läuft").then("Aus").onTimeout().act(() -> Assets.sound("fertig.mp3").play())
	
		.endStateMachine();

		türAutomat = StateMachine.define(String.class, String.class, MatchStrategy.BY_EQUALITY)
				.description("Tür")
				.initialState("Zu")
				
		.states()
			.state("Auf")
			.state("Zu")
			
		.transitions()
			.when("Zu").then("Auf").on("TürAuf")
			.stay("Zu").on("TürZu")
			.when("Auf").then("Zu").on("TürZu")
			.stay("Auf").on("TürAuf")
		
		.endStateMachine();

		zeitAutomat = StateMachine.define(Integer.class, String.class, MatchStrategy.BY_EQUALITY)
			.description("Zeitwahl")
			.initialState(15)

		.states()
			.state(15)
			.state(20)
			
		.transitions()
			.when(15).then(20).on("Auf20").condition(() -> hauptAutomat.getState().equals("Bereit"))
			.stay(15).on("Auf15").act(() -> LOGGER.info("Trockner nicht bereit"))
			.stay(15).on("Auf20").act(() -> LOGGER.info("Trockner nicht bereit"))
			.when(20).then(15).on("Auf15").condition(() -> hauptAutomat.getState().equals("Bereit"))
			.stay(20).on("Auf15").act(() -> LOGGER.info("Trockner nicht bereit"))
			.stay(20).on("Auf20").act(() -> LOGGER.info("Trockner nicht bereit"))
			
	  .endStateMachine();
		//@formatter:on
	}

	@Override
	public void init() {
		app.getShell().getCanvas().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e.getX(), e.getY());
			}
		});
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(automat -> {
			automat.traceTo(LOGGER, CLOCK::getFrequency);
		});
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(StateMachine::init);
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_E)) {
			hauptAutomat.enqueue("EinAusTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_S)) {
			hauptAutomat.enqueue("StartTaste");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_T)) {
			hauptAutomat.enqueue("TürAuf");
			türAutomat.enqueue("TürAuf");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_Z)) {
			türAutomat.enqueue("TürZu");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_2)) {
			zeitAutomat.enqueue("Auf20");
		} else if (Keyboard.keyPressedOnce(KeyEvent.VK_1)) {
			zeitAutomat.enqueue("Auf15");
		}
		Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(StateMachine::update);
	}

	// hot spots

	private Map<String, Rectangle> sensors = new HashMap<>();
	{
		sensors.put("StartTaste", new Rectangle(505, 209, 60, 30));
		sensors.put("TürAuf", new Rectangle(679, 201, 74, 33));
		sensors.put("EinAusTaste", new Rectangle(694, 146, 61, 32));
		sensors.put("Auf20", new Rectangle(34, 171, 103, 32));
		sensors.put("Auf15", new Rectangle(38, 205, 84, 22));
	}

	private void dispatch(String event) {
		switch (event) {
		case "StartTaste":
		case "EinAusTaste":
			hauptAutomat.enqueue(event);
			return;
		case "TürAuf":
			türAutomat.enqueue(event);
			return;
		case "Auf15":
		case "Auf20":
			zeitAutomat.enqueue(event);
			return;
		}
	}

	public void handleMouseClick(int x, int y) {
		for (String event : sensors.keySet()) {
			if (sensors.get(event).contains(x, y)) {
				dispatch(event);
			}
		}
	}
}