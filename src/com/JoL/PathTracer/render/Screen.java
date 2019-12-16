package com.JoL.PathTracer.render;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.JoL.PathTracer.Vector3;

public class Screen extends Canvas {
	private BufferedImage img;
	private int[] pixelArray;
	private Pixel[] pixels;
	private static int NUM_CPUS = 8;
	public int sampleCount = 0;
	private Runnable thread;
	
	public Screen(int width, int height) {
		setSize(width, height);
		
		thread = new Runnable() {
			@Override
			public void run() {
				Sample sample = new Sample(width, height);
				
				while(true) {
					sample.render();

					synchronized (thread) {
						sampleCount++;
						for (int i = 0; i < pixels.length; i++) {
							pixels[i].addColor(sample.screen[i]);
						}
						
						render();
					}
				}
			}
		};

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixelArray = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		pixels = new Pixel[pixelArray.length];
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = new Pixel(0);
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		for (int i = 0; i < pixels.length; i++) {
			pixelArray[i] = pixels[i].scaleColor(1.0/sampleCount).getIntColor();
		}
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public void start() {
		for (int i = 0; i < NUM_CPUS; i++) {
			new Thread(thread).start();
		}
	}
}
