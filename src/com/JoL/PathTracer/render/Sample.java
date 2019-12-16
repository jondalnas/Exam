package com.JoL.PathTracer.render;

public class Sample {
	private final int width, height;
	public Pixel[] screen;
	
	public Sample(int width, int height) {
		this.width = width;
		this.height = height;
		
		screen = new Pixel[width * height];
		
		for (int i = 0; i < screen.length; i++) {
			screen[i] = new Pixel(0);
		}
	}
	
	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				screen[x+y*width].setColor(Math.random() / (((width/2-x)*(width/2-x)+(height/2-y)*(height/2-y))/1000.0+1.0) > 0.05 ? 0 : 0xffffff);
			}
		}
	}
}
