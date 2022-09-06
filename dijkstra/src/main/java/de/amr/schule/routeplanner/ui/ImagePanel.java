/*
MIT License

Copyright (c) 2022 Armin Reichert

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package de.amr.schule.routeplanner.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Armin Reichert
 */
public class ImagePanel extends JPanel {

	private final Logger LOGGER = LogManager.getFormatterLogger();

	private BufferedImage image;

	private Consumer<KeyEvent> onKeyPressed = e -> {
		LOGGER.info("onKeyPressed(%s)".formatted(e));
	};

	private Consumer<KeyEvent> onKeyReleased = e -> {
		LOGGER.info("onKeyRleased(%s)".formatted(e));
	};

	private Consumer<Graphics2D> fnCustomDraw = g -> {
	};

	public ImagePanel() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				onKeyPressed.accept(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				onKeyReleased.accept(e);
			}
		});
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setFnCustomDraw(Consumer<Graphics2D> fnCustomDraw) {
		this.fnCustomDraw = fnCustomDraw;
	}

	public void setOnKeyPressed(Consumer<KeyEvent> onKeyPressed) {
		this.onKeyPressed = onKeyPressed;
	}

	public void setOnKeyReleased(Consumer<KeyEvent> onKeyReleased) {
		this.onKeyReleased = onKeyReleased;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		}
		fnCustomDraw.accept((Graphics2D) g);
	}
}