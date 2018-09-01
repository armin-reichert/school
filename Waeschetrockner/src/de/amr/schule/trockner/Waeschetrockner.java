package de.amr.schule.trockner;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import de.amr.easy.game.Application;
import de.amr.easy.game.assets.Assets;
import de.amr.easy.game.entity.GameEntityUsingSprites;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.sprite.Sprite;
import de.amr.easy.statemachine.StateMachine;

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

		hauptAutomat = new StateMachine<>("Trockner", String.class, "Aus");

		// Aus
		hauptAutomat.changeOnInput("EinAusTaste", "Aus", "Bereit");

		// Bereit
		hauptAutomat.changeOnInput("EinAusTaste", "Bereit", "Aus");
		hauptAutomat.changeOnInput("StartTaste", "Bereit", "Läuft", () -> türAutomat.is("Zu"));

		// Läuft
		hauptAutomat.state("Läuft").entry = state -> state.setDuration(Application.CLOCK.secToTicks(zeitAutomat.stateID()));
		hauptAutomat.changeOnInput("EinAusTaste", "Läuft", "Aus", t -> türAutomat.addInput("TürAuf"));
		hauptAutomat.changeOnInput("TürAuf", "Läuft", "Aus");
		hauptAutomat.changeOnTimeout("Läuft", "Aus", t -> Assets.sound("fertig.mp3").play());

		türAutomat = new StateMachine<>("Tür", String.class, "Zu");
		türAutomat.changeOnInput("TürAuf", "Zu", "Auf");
		türAutomat.changeOnInput("TürZu", "Auf", "Zu");

		zeitAutomat = new StateMachine<>("Zeitwahl", Integer.class, 15);
		zeitAutomat.changeOnInput("Auf20", 15, 20, () -> hauptAutomat.is("Bereit"));
		zeitAutomat.changeOnInput("Auf15", 20, 15, () -> hauptAutomat.is("Bereit"));
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
			automat.setLogger(Application.LOGGER);
			automat.ticksToSec = Application.CLOCK::ticksToSec;
		});
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

	// hot spots

	private Map<String, Rectangle> hotSpots = new HashMap<>();
	{
		hotSpots.put("StartTaste", new Rectangle(505, 209, 60, 30));
		hotSpots.put("TürAuf", new Rectangle(679, 201, 74, 33));
		hotSpots.put("EinAusTaste", new Rectangle(694, 146, 61, 32));
		hotSpots.put("Auf20", new Rectangle(34, 171, 103, 32));
		hotSpots.put("Auf15", new Rectangle(38, 205, 84, 22));
	}

	public void handleMouseClick(int x, int y) {
		// LOG.info(String.format("Click at x=%d y=%d", x, y));
		for (String key : hotSpots.keySet()) {
			Rectangle r = hotSpots.get(key);
			if (r.contains(x, y)) {
				// LOG.info(key);
				Stream.of(hauptAutomat, türAutomat, zeitAutomat).forEach(a -> a.addInput(key));
			}
		}
	}
}