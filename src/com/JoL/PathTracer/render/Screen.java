package com.JoL.PathTracer.render;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.sql.Savepoint;

import javax.imageio.ImageIO;

import com.JoL.PathTracer.Vector3;

public class Screen extends Canvas {
	private BufferedImage img;
	private int[] pixelArray;
	private Pixel[] pixels;
	private static int NUM_CPUS = Runtime.getRuntime().availableProcessors();
	public int sampleCount = 0;
	public static int currentSample = 0;
	private Runnable thread;
	
	private static final short IMAGE_INTERVAL = 100;
	
	public Screen(int width, int height) {
		setSize(width, height);
		
		thread = new Runnable() {
			@Override
			public void run() {
				Sample sample = new Sample(width, height);
				
				while(true) {
					sample.render();

					synchronized (thread) {
						int sc = sampleCount++;
						
						for (int i = 0; i < pixels.length; i++) {
							pixels[i].addColor(sample.screen[i]);
						}
						
						render(sc);
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
	
	public void render(int sampleCount) {
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
		
		//TODO: Save images at specific intervals
		if (sampleCount % IMAGE_INTERVAL == 0) {
			try {
				ImageIO.write(img, "png", new File("res/out/Image @" + sampleCount + " sample count.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Rendered " + sampleCount + " frames");
		}
		
		g.dispose();
		bs.show();
	}
	
	public void start() {
		for (int i = 0; i < NUM_CPUS; i++) {
			new Thread(thread).start();
		}
	}
}
