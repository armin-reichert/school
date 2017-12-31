package de.amr.schule.darts.counter;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class PlayerCounter extends JPanel {

	private CounterModel model;
	private int player;
	private CheckOutsTableModel tableModelCheckOuts;

	private JTable tblCheckOuts;
	private JLabel lblRemainingPoints;
	private JLabel lblPointsInTake;

	public PlayerCounter() {
		tableModelCheckOuts = new CheckOutsTableModel();

		setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		lblRemainingPoints = new JLabel("999");
		lblRemainingPoints.setFont(new Font("Tahoma", Font.BOLD, 80));
		add(lblRemainingPoints, "cell 0 0,alignx center");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1,grow");

		tblCheckOuts = new JTable();
		tblCheckOuts.setModel(tableModelCheckOuts);
		scrollPane.setViewportView(tblCheckOuts);

		lblPointsInTake = new JLabel("000");
		lblPointsInTake.setForeground(Color.RED);
		lblPointsInTake.setFont(new Font("Tahoma", Font.BOLD, 80));
		add(lblPointsInTake, "cell 0 2,alignx center");
	}

	public void setModel(CounterModel model) {
		this.model = model;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void updateView() {
		lblRemainingPoints.setText("" + model.getPointsRemaining(player));
		lblRemainingPoints.setForeground(model.getTurn() == player ? Color.RED : Color.BLACK);
		lblPointsInTake.setText("" + model.getPointsInTake(player));
		lblPointsInTake.setForeground(model.getTurn() == player  ? Color.RED : Color.GRAY);

		tableModelCheckOuts
				.setResults(CounterModel.CHECKOUT_TABLE.getCheckOuts(model.getPointsRemaining(player)));
	}

}
