package de.amr.schule.bernoulli;

import static de.amr.schule.bernoulli.Functions.b;
import static de.amr.schule.bernoulli.Functions.b_geq;
import static de.amr.schule.bernoulli.Functions.b_greater;
import static de.amr.schule.bernoulli.Functions.b_leq;
import static de.amr.schule.bernoulli.Functions.b_less;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class Bernoulli extends JFrame {

	private int n, k;
	private double p;

	private JTextField field_n;
	private JTextField field_p;
	private JTextField field_k;
	private JTextField fieldResult;
	private JComboBox<String> comboRelation;
	private JLabel lblResult;
	private JLabel lbl_n;
	private JLabel lbl_p;
	private JLabel lbl_k;
	private JLabel lblTitle;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
			} catch (Exception x) {
				x.printStackTrace();
			}
			Bernoulli b = new Bernoulli();
			b.pack();
			b.setLocation(200, 200);
			b.setVisible(true);
		});
	}

	public Bernoulli() {
		n = 10;
		k = 1;
		p = 0.5;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bernoulliketten");

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[][][][][][]", "[][][]"));

		lblTitle = new JLabel("Bernoulliketten-Rechner");
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Arial Black", Font.PLAIN, 18));
		panel.add(lblTitle, "cell 0 0 6 1");

		lbl_n = new JLabel("n");
		lbl_n.setFont(new Font("Arial Black", Font.BOLD, 16));
		panel.add(lbl_n, "cell 0 1");

		lbl_p = new JLabel("p");
		lbl_p.setFont(new Font("Arial Black", Font.BOLD, 16));
		panel.add(lbl_p, "cell 1 1");

		lbl_k = new JLabel("k");
		lbl_k.setFont(new Font("Arial Black", Font.BOLD, 16));
		panel.add(lbl_k, "cell 3 1");

		lblResult = new JLabel("P(X=k)");
		lblResult.setFont(new Font("Arial Black", Font.BOLD, 16));
		panel.add(lblResult, "cell 4 1");

		field_n = new JTextField();
		field_n.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCompute(e);
			}
		});
		field_n.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel.add(field_n, "cell 0 2");
		field_n.setColumns(5);

		field_p = new JTextField();
		field_p.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCompute(e);
			}
		});
		field_p.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel.add(field_p, "cell 1 2");
		field_p.setColumns(10);

		comboRelation = new JComboBox<>();
		comboRelation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCompute(e);
			}
		});
		comboRelation.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboRelation.setModel(new DefaultComboBoxModel<>(new String[] { "<", "<=", "=", ">=", ">" }));
		panel.add(comboRelation, "cell 2 2");

		field_k = new JTextField();
		field_k.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCompute(e);
			}
		});
		field_k.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel.add(field_k, "cell 3 2");
		field_k.setColumns(5);

		JButton btnCompute = new JButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCompute(e);
			}
		});

		fieldResult = new JTextField();
		fieldResult.setEditable(false);
		fieldResult.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel.add(fieldResult, "cell 4 2");
		fieldResult.setColumns(20);
		panel.add(btnCompute, "cell 5 2");

		field_k.setText(String.valueOf(k));
		field_n.setText(String.valueOf(n));
		field_p.setText(String.valueOf(p));
		onCompute(null);
	}

	private void onCompute(ActionEvent e) {
		updateResultLabel();
		try {
			double result = computeProbability((String) comboRelation.getSelectedItem());
			fieldResult.setText(String.format("%20.10E", result));
		} catch (Exception x) {
			System.err.println(x.getMessage());
		}
	}

	private void updateResultLabel() {
		lblResult.setText(String.format("P(X %s %s)", comboRelation.getSelectedItem(), field_k.getText()));
	}

	protected double computeProbability(String relation) {
		String input = null;
		try {
			input = field_n.getText().trim();
			n = Integer.valueOf(input);
		} catch (Exception x) {
			throw new IllegalArgumentException(String.format("Eingabe für n ('%s') ist keine Zahl", input));
		}
		try {
			input = field_p.getText().trim();
			p = Double.valueOf(input);
		} catch (Exception x) {
			throw new IllegalArgumentException(String.format("Eingabe für p ('%s') ist keine Zahl", input));
		}
		try {
			input = field_k.getText().trim();
			k = Integer.valueOf(input);
		} catch (Exception x) {
			throw new IllegalArgumentException(String.format("Eingabe für k ('%s') ist keine Zahl", input));
		}
		switch (relation) {
		case "<":
			return b_less(n, p, k);
		case "<=":
			return b_leq(n, p, k);
		case "=":
			return b(n, p, k);
		case ">":
			return b_greater(n, p, k);
		case ">=":
			return b_geq(n, p, k);
		default:
			return -1;
		}
	}
}
