package de.amr.schule.crypto.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import de.amr.schule.crypto.alg.Gartenzaun;
import de.amr.schule.crypto.api.Permutation;
import net.miginfocom.swing.MigLayout;

public class CryptoPanel extends JPanel {

	private Action encryptAction = new AbstractAction("Verschlüsseln") {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = textAreaOriginalText.getText();
			int numRows = (Integer) spinnerNumRows.getValue();
			Gartenzaun gartenzaun = new Gartenzaun(numRows);
			Permutation perm = gartenzaun.permutation(text.length());
			System.out.println(perm);
			String code = perm.apply(text);
			textAreaEncryptedText.setText(code);
		}
	};

	private Action decryptAction = new AbstractAction("Entschlüsseln") {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textAreaEncryptedText.getText();
			int numRows = (Integer) spinnerNumRows.getValue();
			Gartenzaun gartenzaun = new Gartenzaun(numRows);
			Permutation perm = gartenzaun.permutation(code.length()).inv();
			System.out.println(perm);
			String text = perm.apply(code);
			textAreaOriginalText.setText(text);
		}
	};

	private JTextArea textAreaOriginalText;
	private JButton btnEncrypt;
	private JTextArea textAreaEncryptedText;
	private JPanel panel;
	private JButton btnDecrypt;
	private JSpinner spinnerNumRows;
	private JLabel lblAnzahlZeilen;

	public CryptoPanel() {
		setLayout(new MigLayout("", "[200px:n,grow][grow][200px:n,grow]", "[500:500,grow]"));

		textAreaOriginalText = new JTextArea();
		textAreaOriginalText.setLineWrap(true);
		textAreaOriginalText.setRows(40);
		textAreaOriginalText.setColumns(60);
		textAreaOriginalText.setText("NAHT IHR EUCH WIEDER SCHWANKENDE GESTALTEN");
		add(textAreaOriginalText, "cell 0 0,growy");

		panel = new JPanel();
		add(panel, "cell 1 0,alignx center,aligny center");
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		lblAnzahlZeilen = new JLabel("Anzahl Zeilen");
		panel.add(lblAnzahlZeilen);

		spinnerNumRows = new JSpinner();
		spinnerNumRows.setToolTipText("Anzahl Zeilen");
		panel.add(spinnerNumRows);
		spinnerNumRows.setValue(Integer.valueOf(2));

		btnEncrypt = new JButton("Verschlüsseln");
		panel.add(btnEncrypt);
		btnEncrypt.setAction(encryptAction);

		btnDecrypt = new JButton("Entschlüsseln");
		panel.add(btnDecrypt);
		btnDecrypt.setAction(decryptAction);

		textAreaEncryptedText = new JTextArea();
		textAreaEncryptedText.setLineWrap(true);
		textAreaEncryptedText.setRows(40);
		textAreaEncryptedText.setColumns(60);
		add(textAreaEncryptedText, "cell 2 0,growy");
	}

	public JTextArea getTextAreaOriginalText() {
		return textAreaOriginalText;
	}

	public JButton getBtnEncrypt() {
		return btnEncrypt;
	}

	public JTextArea getTextAreaEncryptedText() {
		return textAreaEncryptedText;
	}
}
