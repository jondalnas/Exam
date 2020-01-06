package com.JoL.PathTracer.render;

import com.JoL.PathTracer.Vector3;

public class Sample {
	private final int width, height;
	public Pixel[] screen;

	public static final double FOV = Math.toRadians(60);
	public static double aspect, yFOV;
	
	public Sample(int width, int height) {
		this.width = width;
		this.height = height;
		
		aspect = (double) width / (double) height;
		yFOV = FOV / aspect;
		
		screen = new Pixel[width * height];
		
		for (int i = 0; i < screen.length; i++) {
			screen[i] = new Pixel(0);
		}
	}
	
	public void render() {
		for (int y = 0; y < height; y++) {
			double yAngle = (0.5 - (double) y / height) * yFOV;
			double tanY = Math.tan(yAngle);
			double size = 1.0 / Math.sqrt(1 + tanY*tanY);
			
			for (int x = 0; x < width; x++) {
				double xAngle = ((double) x / width - 0.5) * FOV;
				
				Vector3 dir = new Vector3(Math.sin(xAngle), tanY, Math.cos(xAngle));
				
				//Normalize
				dir.multEqual(size);
				
				screen[x+y*width].setColor(dir.add(new Vector3(1, 1, 1)).mult(0.5));
			}
		}
	}
}
