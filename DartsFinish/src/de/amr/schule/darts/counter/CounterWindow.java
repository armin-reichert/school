package de.amr.schule.darts.counter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class CounterWindow extends JFrame {

	private int mode; // single, double, triple
	private int enterCount;
	private int pointsThrownLeft;
	private int pointsThrownRight;

	private CounterModel model;

	private CheckOutsTableModel tableModelCheckOutsLeft;
	private JTable tableCheckOutsLeft;

	private CheckOutsTableModel tableModelCheckOutsRight;
	private JTable tableCheckOutsRight;

	private JLabel lblPointsRemainingLeft;
	private JLabel lblPointsRemainingRight;
	private final ButtonGroup modeButtonGroup = new ButtonGroup();
	private JButton button;
	private JRadioButton rbSingle;
	private JLabel lblThrownLeft;
	private JLabel lblThrownRight;

	public void updateView() {

		int turn = model.getTurn();

		lblPointsRemainingLeft.setText("" + model.getPointsRemaining(0));
		lblPointsRemainingLeft.setForeground(turn == 0 ? Color.RED : Color.BLACK);
		lblPointsRemainingRight.setText("" + model.getPointsRemaining(1));
		lblPointsRemainingRight.setForeground(turn == 1 ? Color.RED : Color.BLACK);

		lblThrownLeft.setText("" + pointsThrownLeft);
		lblThrownLeft.setForeground(turn == 0 ? Color.RED : Color.GRAY);
		lblThrownRight.setText("" + pointsThrownRight);
		lblThrownRight.setForeground(turn == 1 ? Color.RED : Color.GRAY);

		tableModelCheckOutsLeft
				.setResults(model.getCheckOutTable().getCheckOuts(model.getPointsRemaining(0)));
		tableModelCheckOutsRight
				.setResults(model.getCheckOutTable().getCheckOuts(model.getPointsRemaining(1)));
	}

	public CounterWindow() {

		setTitle("Darts Counter");
		setPreferredSize(new Dimension(800, 800));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][grow][][grow][grow][]"));

		lblPointsRemainingLeft = new JLabel("999");
		lblPointsRemainingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblPointsRemainingLeft.setFont(new Font("Tahoma", Font.PLAIN, 80));
		getContentPane().add(lblPointsRemainingLeft, "cell 0 0,alignx center");

		lblPointsRemainingRight = new JLabel("999");
		lblPointsRemainingRight.setHorizontalAlignment(SwingConstants.CENTER);
		lblPointsRemainingRight.setFont(new Font("Tahoma", Font.PLAIN, 80));
		getContentPane().add(lblPointsRemainingRight, "cell 1 0,alignx center");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 1,grow");

		tableCheckOutsLeft = new JTable();
		scrollPane.setViewportView(tableCheckOutsLeft);

		JScrollPane scrollPane_1 = new JScrollPane();
		getContentPane().add(scrollPane_1, "cell 1 1,grow");

		tableCheckOutsRight = new JTable();
		scrollPane_1.setViewportView(tableCheckOutsRight);

		lblThrownLeft = new JLabel("0");
		lblThrownLeft.setForeground(Color.RED);
		lblThrownLeft.setFont(new Font("Tahoma", Font.PLAIN, 80));
		getContentPane().add(lblThrownLeft, "cell 0 2,alignx center");

		lblThrownRight = new JLabel("0");
		lblThrownRight.setForeground(Color.RED);
		lblThrownRight.setFont(new Font("Tahoma", Font.PLAIN, 80));
		getContentPane().add(lblThrownRight, "cell 1 2,alignx center");

		tableModelCheckOutsLeft = new CheckOutsTableModel();
		tableModelCheckOutsRight = new CheckOutsTableModel();
		tableCheckOutsLeft.setModel(tableModelCheckOutsLeft);
		tableCheckOutsRight.setModel(tableModelCheckOutsRight);

		JPanel keyboard = new JPanel();
		getContentPane().add(keyboard, "cell 0 3 2 1,growy");
		keyboard.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][]", "[][][][]"));

		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(1);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_1, "cell 0 0,grow");

		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(2);
			}
		});
		button_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_2, "cell 1 0,grow");

		JButton button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(3);
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_3, "cell 2 0,grow");

		JButton button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(4);
			}
		});
		button_4.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_4, "cell 3 0,grow");

		JButton button_5 = new JButton("5");
		button_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(5);
			}
		});
		button_5.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_5, "cell 4 0,grow");

		JButton button_6 = new JButton("6");
		button_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(6);
			}
		});
		button_6.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_6, "cell 5 0,grow");

		JButton button_7 = new JButton("7");
		button_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(7);
			}
		});
		button_7.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_7, "cell 6 0,grow");

		JButton button_8 = new JButton("8");
		button_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(8);
			}
		});
		button_8.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_8, "cell 7 0,grow");

		JButton button_9 = new JButton("9");
		button_9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(9);
			}
		});
		button_9.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_9, "cell 8 0,grow");

		JButton button_10 = new JButton("10");
		button_10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(10);
			}
		});
		button_10.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_10, "cell 9 0,grow");

		JButton button_11 = new JButton("11");
		button_11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(11);
			}
		});
		button_11.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_11, "cell 0 1,grow");

		JButton button_12 = new JButton("12");
		button_12.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(12);
			}
		});
		button_12.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_12, "cell 1 1,grow");

		JButton button_13 = new JButton("13");
		button_13.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(13);
			}
		});
		button_13.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_13, "cell 2 1,grow");

		JButton button_14 = new JButton("14");
		button_14.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(14);
			}
		});
		button_14.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_14, "cell 3 1,grow");

		JButton button_15 = new JButton("15");
		button_15.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(15);
			}
		});
		button_15.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_15, "cell 4 1,grow");

		JButton button_16 = new JButton("16");
		button_16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(16);
			}
		});
		button_16.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_16, "cell 5 1,grow");

		JButton button_17 = new JButton("17");
		button_17.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(17);
			}
		});
		button_17.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_17, "cell 6 1,grow");

		JButton button_18 = new JButton("18");
		button_18.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(18);
			}
		});
		button_18.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_18, "cell 7 1,grow");

		JButton button_19 = new JButton("19");
		button_19.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(19);
			}
		});
		button_19.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_19, "cell 8 1,grow");

		JButton button_20 = new JButton("20");
		button_20.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(20);
			}
		});
		button_20.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button_20, "cell 9 1,grow");

		button = new JButton("25");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numberEntered(25);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyboard.add(button, "cell 10 1,grow");

		JPanel modePanel = new JPanel();
		getContentPane().add(modePanel, "cell 0 4 2 1,grow");
		modePanel.setLayout(new GridLayout(1, 0, 0, 0));

		rbSingle = new JRadioButton("Single");
		rbSingle.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbSingle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 1;
				button.setEnabled(true);
			}
		});
		modeButtonGroup.add(rbSingle);
		modePanel.add(rbSingle);

		JRadioButton rbDouble = new JRadioButton("Double");
		rbDouble.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbDouble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 2;
				button.setEnabled(true);
			}
		});
		modeButtonGroup.add(rbDouble);
		modePanel.add(rbDouble);

		JRadioButton rbTriple = new JRadioButton("Triple");
		rbTriple.setFont(new Font("Tahoma", Font.BOLD, 30));
		rbTriple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 3;
				button.setEnabled(false);
			}
		});
		modeButtonGroup.add(rbTriple);
		modePanel.add(rbTriple);

		mode = 1;
		rbSingle.setSelected(true);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		btnNewGame.setForeground(Color.BLUE);
		btnNewGame.setFont(new Font("Tahoma", Font.BOLD, 40));
		getContentPane().add(btnNewGame, "cell 0 5 2 1,growx");

		pack();

	}

	private void numberEntered(int number) {
		int thrown = mode * number;
		int turn = model.getTurn();
		int pointsRemaining = model.getPointsRemaining(turn);
		if (pointsRemaining - thrown >= 0) {
			model.setPointsRemaining(turn, pointsRemaining - thrown);
		}
		if (turn == 0) {
			pointsThrownLeft += thrown;
		} else {
			pointsThrownRight += thrown;
		}
		++enterCount;
		if (enterCount == 3) {
			enterCount = 0;
			model.setTurn(1 - model.getTurn());
			if (model.getTurn() == 0) {
				pointsThrownLeft = 0;
			} else {
				pointsThrownRight = 0;
			}
		}
		updateView();
	}

	public void newGame() {
		model = new CounterModel();
		mode = 1;
		enterCount = 0;
		pointsThrownLeft = pointsThrownRight = 0;
		getButton_21().setEnabled(true);
		getRbSingle().setSelected(true);
		updateView();
	}

	public JButton getButton_21() {
		return button;
	}

	public JRadioButton getRbSingle() {
		return rbSingle;
	}

	public JLabel getLblThrownLeft() {
		return lblThrownLeft;
	}

	public JLabel getLblThrownRight() {
		return lblThrownRight;
	}
}
