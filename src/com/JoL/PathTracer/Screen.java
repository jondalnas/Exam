package com.JoL.PathTracer;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Canvas {
	private BufferedImage img;
	private int[] pixels;
	
	public Screen(int width, int height) {
		setSize(width, height);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		pixels[0] = 0xffffff;
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
}
