package de.amr.schule.graphdrawing.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MainWindowLayout extends JPanel {

	public MainWindowLayout(JPanel canvas, JPanel tableView, JPanel configView) {
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(tableView);
		right.add(Box.createVerticalStrut(20));
		configView.setOpaque(false);
		configView.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		right.add(configView);
		add(right, BorderLayout.EAST);
	}
}
