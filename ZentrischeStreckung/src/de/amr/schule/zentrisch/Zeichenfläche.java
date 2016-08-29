package de.amr.schule.zentrisch;

import static de.amr.easy.game.math.Vector2.sum;
import static de.amr.easy.game.math.Vector2.times;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import de.amr.easy.game.math.Vector2;

public class Zeichenfläche extends JPanel {

	Point2D.Float S = new Point2D.Float(20, 120); // Streckungszentrum
	float k = 1.5f; // Streckfaktor

	List<Shape> figuren = new ArrayList<>();

	Timer timer;
	float dx, dy, dk;
	int alpha;

	public Zeichenfläche(int breite, int höhe) {
		setPreferredSize(new Dimension(breite, höhe));
		setMinimumSize(getPreferredSize());

		figuren.add(new Rectangle2D.Float(200, 200, 40, 200));
		figuren.add(new Rectangle2D.Float(400, 300, 80, 80));
		figuren.add(new Ellipse2D.Float(400, 400, 100, 100));

		belegeTaste("PLUS", this::action_streckFaktorVergrößern);
		belegeTaste("MINUS", this::action_streckFaktorVerkleinern);
		belegeTaste("LEFT", this::action_streckZentrumNachLinks);
		belegeTaste("RIGHT", this::action_streckZentrumNachRechts);
		belegeTaste("UP", this::action_streckZentrumNachOben);
		belegeTaste("DOWN", this::action_streckZentrumNachUnten);
		belegeTaste("control LEFT", this::action_FigurNachLinks);
		belegeTaste("control RIGHT", this::action_FigurNachRechts);
		belegeTaste("control UP", this::action_FigurNachOben);
		belegeTaste("control DOWN", this::action_FigurNachUnten);

		dx = 1;
		dy = 1;
		dk = 0.01f;
		alpha = 0;
		timer = new Timer(0, e -> {
			S.x += dx;
			if (S.x > breite || S.x < 0) {
				dx = -dx;
			}
			S.y += dy;
			if (S.y > höhe || S.y < 0) {
				dy = -dy;
			}
			k += dk;
			if (k > 2 || k < 0.5) {
				dk = -dk;
			}
			alpha++;
			if (alpha == 256) {
				alpha = 0;
			}
			repaint();
		});
		// timer.start();
	}

	public void action_FigurNachLinks(ActionEvent e) {
		// figur.x -= 2;
		repaint();
	}

	public void action_FigurNachRechts(ActionEvent e) {
		// figur.x += 2;
		repaint();
	}

	public void action_FigurNachOben(ActionEvent e) {
		// figur.y -= 2;
		repaint();
	}

	public void action_FigurNachUnten(ActionEvent e) {
		// figur.y += 2;
		repaint();
	}

	public void action_streckZentrumNachLinks(ActionEvent e) {
		S.x -= 2;
		repaint();
	}

	public void action_streckZentrumNachRechts(ActionEvent e) {
		S.x += 2;
		repaint();
	}

	public void action_streckZentrumNachOben(ActionEvent e) {
		S.y -= 2;
		repaint();
	}

	public void action_streckZentrumNachUnten(ActionEvent e) {
		S.y += 2;
		repaint();
	}

	public void action_streckFaktorVergrößern(ActionEvent e) {
		k += 0.1f;
		repaint();
	}

	public void action_streckFaktorVerkleinern(ActionEvent e) {
		k -= 0.1f;
		if (k <= 0) {
			k = 0.1f;
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics gg) {
		Graphics2D stift = (Graphics2D) gg;

		super.paintComponent(stift);
		
		stift.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		stift.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Stroke thick = new BasicStroke(2);
		Stroke normal = new BasicStroke();

		stift.setColor(Color.WHITE);
		stift.fillRect(0, 0, getWidth(), getHeight());

		// Streckzentrum als Kreis
		int r = 2;
		stift.setColor(Color.RED);
		stift.fillOval((int) S.x - r, (int) S.y - r, 2 * r, 2 * r);

		stift.setColor(Color.BLUE);
		for (Shape figur : figuren) {
			stift.setStroke(thick);
			stift.draw(figur);
		}

		for (Shape figur : figuren) {
			if (figur instanceof Rectangle2D.Float) {
				Rectangle2D.Float rechteck = (Rectangle2D.Float) figur;
				Rectangle2D.Float rechteck_ = streckeRechteck(rechteck);
				stift.setColor(Color.RED);
				stift.setStroke(thick);
				stift.draw(rechteck_);
				stift.setStroke(normal);
				stift.setColor(Color.LIGHT_GRAY);
				zeichneStrahl(stift, rechteck_);
			} else if (figur instanceof Ellipse2D.Float) {
				Ellipse2D.Float ellipse = (Ellipse2D.Float) figur;
				Ellipse2D.Float ellipse_ = streckeEllipse(ellipse);
				stift.setColor(Color.RED);
				stift.setStroke(thick);
				stift.draw(ellipse_);
				stift.setStroke(normal);
				stift.setColor(Color.LIGHT_GRAY);
				zeichneStrahlZuEllipse(stift, ellipse_);
			}
		}

		// Streckfaktor als Text
		stift.setColor(Color.BLACK);
		stift.drawString("Streckfaktor=" + k, getWidth() - 150, getHeight() - 20);
	}

	void zeichneStrahlZuEllipse(Graphics2D stift, Ellipse2D.Float ellipse) {
		stift.drawLine((int) S.x, (int) S.y, (int) (ellipse.getX() + ellipse.width / 2),
				(int) ellipse.getY());
		stift.drawLine((int) S.x, (int) S.y, (int) (ellipse.getX() + ellipse.width / 2),
				(int) (ellipse.getY() + ellipse.height));
	}

	void zeichneStrahl(Graphics2D stift, Shape figur) {
		Rectangle2D bounds = figur.getBounds2D();
		stift.drawLine((int) S.x, (int) S.y, (int) bounds.getX(), (int) bounds.getY());
		stift.drawLine((int) S.x, (int) S.y, (int) bounds.getMaxX(), (int) bounds.getY());
		stift.drawLine((int) S.x, (int) S.y, (int) bounds.getMaxX(), (int) bounds.getMaxY());
		stift.drawLine((int) S.x, (int) S.y, (int) bounds.getX(), (int) bounds.getMaxY());
	}

	Point2D.Float streckePunkt(Point2D.Float P) {
		Vector2 v = new Vector2(P.x - S.x, P.y - S.y);
		Vector2 s = new Vector2(S.x, S.y);
		Vector2 p_ = sum(s, times(k, v));
		return new Point2D.Float(p_.x, p_.y);
	}

	Rectangle2D.Float streckeRechteck(Rectangle2D.Float rechteck) {
		Point2D.Float LO = new Point2D.Float(rechteck.x, rechteck.y);
		Point2D.Float RO = new Point2D.Float(LO.x + rechteck.width, LO.y);
		Point2D.Float LU = new Point2D.Float(LO.x, LO.y + rechteck.height);
		Point2D.Float RU = new Point2D.Float(RO.x, LU.y);
		Point2D.Float LO_ = streckePunkt(LO);
		Point2D.Float RO_ = streckePunkt(RO);
		Point2D.Float LU_ = streckePunkt(LU);
		Point2D.Float RU_ = streckePunkt(RU);
		return new Rectangle2D.Float(LO_.x, LO_.y, RU_.x - LU_.x, RU_.y - RO_.y);
	}

	Ellipse2D.Float streckeEllipse(Ellipse2D.Float kreis) {
		Point2D.Float LO = new Point2D.Float(kreis.x, kreis.y);
		Point2D.Float LO_ = streckePunkt(LO);
		return new Ellipse2D.Float(LO_.x, LO_.y, kreis.width * k, kreis.height * k);
	}

	private void belegeTaste(String taste, Consumer<ActionEvent> action) {
		getInputMap().put(KeyStroke.getKeyStroke(taste), action.toString());
		getActionMap().put(action.toString(), new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				action.accept(e);
			}
		});
	}

}
