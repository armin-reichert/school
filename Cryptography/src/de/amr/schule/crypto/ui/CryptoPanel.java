package de.amr.schule.crypto.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.amr.schule.crypto.alg.Caesar;
import de.amr.schule.crypto.alg.Gartenzaun;
import de.amr.schule.crypto.alg.Kamasutra;
import de.amr.schule.crypto.api.Permutation;
import net.miginfocom.swing.MigLayout;

public class CryptoPanel extends JPanel {

	private Action encryptAction = new AbstractAction("Verschlüsseln") {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = textAreaOriginalText.getText();
			String chiffre = "";
			switch (algorithm) {
			case GARTENZAUN:
				Gartenzaun gartenzaun = new Gartenzaun(gartenzaunZeilen);
				Permutation perm = gartenzaun.permutation(text.length());
				System.out.println(perm);
				chiffre = perm.apply(text);
				break;
			case KAMASUTRA:
				if (kamasutra == null) {
					kamasutra = new Kamasutra();
				}
				chiffre = kamasutra.encrypt(text);
				break;
			case CAESAR:
				Caesar caesar = new Caesar();
				chiffre = caesar.encrypt(text);
				break;
			default:
				throw new IllegalArgumentException();
			}
			textAreaEncryptedText.setText(chiffre);
		}
	};

	private Action decryptAction = new AbstractAction("Entschlüsseln") {

		@Override
		public void actionPerformed(ActionEvent e) {
			String chiffre = textAreaEncryptedText.getText();
			String text = "";
			switch (algorithm) {
			case GARTENZAUN:
				Gartenzaun gartenzaun = new Gartenzaun(gartenzaunZeilen);
				Permutation perm = gartenzaun.permutation(chiffre.length()).inv();
				System.out.println(perm);
				text = perm.apply(chiffre);
				break;
			case KAMASUTRA:
				if (kamasutra == null) {
					kamasutra = new Kamasutra();
				}
				text = kamasutra.decrypt(chiffre);
				break;
			case CAESAR:
				Caesar caesar = new Caesar();
				text = caesar.decrypt(chiffre);
				break;
			default:
				throw new IllegalArgumentException();
			}
			textAreaOriginalText.setText(text);
		}
	};

	private JTextArea textAreaOriginalText;
	private JButton btnEncrypt;
	private JTextArea textAreaEncryptedText;
	private JPanel panel;
	private JButton btnDecrypt;

	private Algorithm algorithm;
	private Kamasutra kamasutra;
	private int gartenzaunZeilen = 2;

	public int getGartenzaunZeilen() {
		return gartenzaunZeilen;
	}

	public void setGartenzaunZeilen(int gartenzaunZeilen) {
		this.gartenzaunZeilen = gartenzaunZeilen;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public CryptoPanel() {
		setLayout(new MigLayout("", "[200px:n,grow][grow]", "[grow,top][][]"));

		textAreaOriginalText = new JTextArea();
		textAreaOriginalText.setLineWrap(true);
		textAreaOriginalText.setRows(20);
		textAreaOriginalText.setColumns(60);
		textAreaOriginalText.setText("NAHT IHR EUCH WIEDER SCHWANKENDE GESTALTEN");
		add(textAreaOriginalText, "cell 0 0");

		panel = new JPanel();
		add(panel, "cell 0 1,alignx center");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnEncrypt = new JButton("Verschlüsseln");
		panel.add(btnEncrypt);
		btnEncrypt.setAction(encryptAction);

		btnDecrypt = new JButton("Entschlüsseln");
		panel.add(btnDecrypt);
		btnDecrypt.setAction(decryptAction);

		textAreaEncryptedText = new JTextArea();
		textAreaEncryptedText.setLineWrap(true);
		textAreaEncryptedText.setRows(20);
		textAreaEncryptedText.setColumns(60);
		add(textAreaEncryptedText, "cell 0 2");

		// initialize
		algorithm = Algorithm.GARTENZAUN;

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
