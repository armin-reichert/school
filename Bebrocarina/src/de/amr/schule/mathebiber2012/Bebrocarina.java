package de.amr.schule.mathebiber2012;

import de.amr.easy.statemachine.StateMachine;

public class Bebrocarina extends StateMachine<Integer, Character> {

	public static void main(String[] args) {
		Bebrocarina acceptor = new Bebrocarina();
		acceptor.prüfeObSpielbar("+ooo+ooo+ooo+ooo+");
		acceptor.prüfeObSpielbar("---o+-o--ooo+");
		acceptor.prüfeObSpielbar("-----o+++++o-----");
		acceptor.prüfeObSpielbar("--+--+--o-+--");
	}

	public Bebrocarina() {
		super("Bebrocarina", Integer.class, 1);

		changeOnInput('o', 1, 1);
		changeOnInput('+', 1, 2);
		changeOnInput('-', 1, -1);

		changeOnInput('o', 2, 2);
		changeOnInput('+', 2, 3);
		changeOnInput('-', 2, 1);

		changeOnInput('o', 3, 3);
		changeOnInput('+', 3, 4);
		changeOnInput('-', 3, 2);

		changeOnInput('o', 4, 4);
		changeOnInput('+', 4, 5);
		changeOnInput('-', 4, 3);

		changeOnInput('o', 5, 5);
		changeOnInput('+', 5, 6);
		changeOnInput('-', 5, 4);

		changeOnInput('o', 6, 6);
		changeOnInput('+', 6, -1);
		changeOnInput('-', 6, 5);

		changeOnInput('o', -1, -1);
		changeOnInput('+', -1, -1);
		changeOnInput('-', -1, -1);
	}

	void prüfeObSpielbar(String wort) {
		init();
		for (int i = 0; i < wort.length(); ++i) {
			addInput(wort.charAt(i));
			update();
		}
		if (stateID() != -1) {
			System.out.println(wort + ": spielbar");
			return;
		}
		System.out.println(wort + ": nicht spielbar");
	}
}
