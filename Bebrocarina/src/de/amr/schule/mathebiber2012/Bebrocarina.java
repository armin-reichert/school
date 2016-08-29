package de.amr.schule.mathebiber2012;

import java.util.logging.Level;

import de.amr.easy.fsm.FSM;

public class Bebrocarina extends FSM<Integer, Character> {

	public static void main(String[] args) {
		FSM.LOG.setLevel(Level.OFF);
		Bebrocarina acceptor = new Bebrocarina();
		acceptor.prüfeObSpielbar("+ooo+ooo+ooo+ooo+");
		acceptor.prüfeObSpielbar("---o+-o--ooo+");
		acceptor.prüfeObSpielbar("-----o+++++o-----");
		acceptor.prüfeObSpielbar("--+--+--o-+--");
	}

	public Bebrocarina() {
		/*@formatter:off*/
		beginFSM()
			.acceptedEvents('+', 'o', '-')
			.initialState(1)
			.state(1).keep().on('o').into(2).on('+').into(-1).on('-').end()
			.state(2).keep().on('o').into(3).on('+').into(1).on('-').end()
			.state(3).keep().on('o').into(4).on('+').into(2).on('-').end()
			.state(4).keep().on('o').into(5).on('+').into(3).on('-').end()
			.state(5).keep().on('o').into(6).on('+').into(4).on('-').end()
			.state(6).keep().on('o').into(-1).on('+').into(5).on('-').end()
			.state(-1).keep().on('o').keep().on('-').keep().on('+').end()
		.endFSM();
		/*@formatter:on*/
	}

	void prüfeObSpielbar(String wort) {
		for (int initialState = 1; initialState <= 6; ++initialState) {
			setInitialState(initialState);
			init();
			for (int i = 0; i < wort.length(); ++i) {
				run(wort.charAt(i));
			}
			if (!getCurrentState().equals(-1)) {
				System.out.println(wort + ": spielbar");
				return;
			}
		}
		System.out.println(wort + ": nicht spielbar");
	}
}
