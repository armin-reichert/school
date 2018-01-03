package de.amr.schule.darts.counter.ui;

import static de.amr.schule.darts.checkout.CheckOutTable.CHECKOUTS;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.amr.schule.darts.counter.model.DartsGameModel;
import net.miginfocom.swing.MigLayout;

public class PlayerCounterUI extends JPanel {

	private DartsGameModel model;
	private int player;

	private CheckOutsTableModel tableModelCheckOuts;
	private JTable tblCheckOuts;
	private JLabel lblRemainingPoints;
	private JLabel lblPointsInTake;
	private JTextField txtName;
	private JLabel lblPointsAvg;

	public PlayerCounterUI() {
		tableModelCheckOuts = new CheckOutsTableModel();

		setLayout(new MigLayout("", "[grow]", "[][][grow][][]"));

		txtName = new JTextField();
		txtName.setForeground(Color.BLUE);
		txtName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setPlayerName(player, txtName.getText());
			}
		});
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setFont(new Font("Tahoma", Font.BOLD, 40));
		txtName.setText("Name");
		add(txtName, "cell 0 0,growx");
		txtName.setColumns(10);

		lblRemainingPoints = new JLabel("999");
		lblRemainingPoints.setFont(new Font("Tahoma", Font.BOLD, 80));
		add(lblRemainingPoints, "cell 0 1,alignx center");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2,grow");

		tblCheckOuts = new JTable();
		tblCheckOuts.setModel(tableModelCheckOuts);
		scrollPane.setViewportView(tblCheckOuts);

		lblPointsInTake = new JLabel("000");
		lblPointsInTake.setForeground(Color.RED);
		lblPointsInTake.setFont(new Font("Tahoma", Font.BOLD, 80));
		add(lblPointsInTake, "cell 0 3,alignx center");

		lblPointsAvg = new JLabel("000");
		lblPointsAvg.setForeground(Color.GRAY);
		lblPointsAvg.setFont(new Font("Tahoma", Font.BOLD, 40));
		add(lblPointsAvg, "cell 0 4,alignx center");
	}

	public void setModel(DartsGameModel model) {
		this.model = model;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void updateView() {
		txtName.setText(model.getPlayerName(player));
		lblRemainingPoints.setText("" + model.getPointsRemaining(player));
		lblRemainingPoints.setForeground(model.getTurn() == player ? Color.RED : Color.BLACK);
		lblPointsInTake.setText("" + model.getPointsInTake(player));
		lblPointsInTake.setForeground(model.getTurn() == player ? Color.RED : Color.GRAY);
		lblPointsAvg.setText(String.format("%.2f", model.getPointsAverage(player)));
		tableModelCheckOuts.setResults(CHECKOUTS.getCheckOuts(model.getPointsRemaining(player)));
	}

}
