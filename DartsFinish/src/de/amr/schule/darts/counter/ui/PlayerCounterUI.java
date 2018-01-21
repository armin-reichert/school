package de.amr.schule.darts.counter.ui;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.amr.schule.darts.checkout.CheckOutTable;
import de.amr.schule.darts.counter.model.Player;
import net.miginfocom.swing.MigLayout;

public class PlayerCounterUI extends JPanel {

	private Player player;

	private CheckOutsTableModel tblModelCheckOuts;
	private JTable tblCheckOuts;
	private JTextField txtName;
	private JLabel lblPointsRemaining;
	private JLabel lblPointsInTake;
	private JLabel lblPointsAverage;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void updateView() {
		if (player == null)
			return;
		txtName.setText(player.getName());
		lblPointsRemaining.setText(format("%d", player.getPointsRemaining()));
		lblPointsRemaining.setForeground(player.isTurn() ? Color.RED : Color.BLACK);
		lblPointsInTake.setText(format("%d", player.getPointsInTake()));
		lblPointsInTake.setForeground(player.isTurn() ? Color.RED : Color.GRAY);
		lblPointsAverage.setText(format("%.2f", player.getPointsAverage()));
		tblModelCheckOuts.setResults(CheckOutTable.getCheckOuts(player.getPointsRemaining()));
	}

	public PlayerCounterUI() {
		tblModelCheckOuts = new CheckOutsTableModel();

		setLayout(new MigLayout("", "[grow]", "[][][][][][grow][][]"));

		txtName = new JTextField();
		txtName.setEnabled(false);
		txtName.setEditable(false);
		txtName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editName();
				}
			}
		});
		txtName.setForeground(Color.BLUE);
		txtName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setName(txtName.getText());
			}
		});
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setFont(new Font("Tahoma", Font.BOLD, 32));
		txtName.setText("Player Name");
		add(txtName, "cell 0 0,growx");
		txtName.setColumns(10);

		lblPointsRemaining = new JLabel("501");
		lblPointsRemaining.setForeground(Color.RED);
		lblPointsRemaining.setToolTipText("Points remaining");
		lblPointsRemaining.setFont(new Font("Tahoma", Font.BOLD, 72));
		add(lblPointsRemaining, "cell 0 1,alignx center");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2,grow");

		tblCheckOuts = new JTable();
		tblCheckOuts.setFont(new Font("Courier New", Font.PLAIN, 16));
		tblCheckOuts.setModel(tblModelCheckOuts);
		scrollPane.setViewportView(tblCheckOuts);
		tblCheckOuts.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		lblPointsInTake = new JLabel("42");
		lblPointsInTake.setToolTipText("Points in current take");
		lblPointsInTake.setForeground(Color.RED);
		lblPointsInTake.setFont(new Font("Tahoma", Font.BOLD, 72));
		add(lblPointsInTake, "cell 0 3,alignx center");

		lblPointsAverage = new JLabel("59.25");
		lblPointsAverage.setToolTipText("Points Average");
		lblPointsAverage.setForeground(Color.GRAY);
		lblPointsAverage.setFont(new Font("Tahoma", Font.BOLD, 36));
		add(lblPointsAverage, "cell 0 4,alignx center");
	}

	protected void editName() {
		String newName = JOptionPane.showInputDialog(this, "Name für Spieler", player.getName());
		if (newName != null && newName.trim().length() > 0) {
			player.setName(newName);
			updateView();
		}
	}
}
