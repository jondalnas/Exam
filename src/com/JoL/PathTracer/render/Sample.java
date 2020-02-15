package com.JoL.PathTracer.render;

import java.util.Random;

import com.JoL.PathTracer.Camera;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;

public class Sample {
	private final int width, height;
	public Pixel[] screen;

	public static final double FOV = 60;
	public static double aspect, yFOV;
	public static final Camera cam = new Camera(new Vector3(0, 2, -8), new Vector3(Math.toRadians(10), 0, 0));
	
	public static double[] sin, cos;
	public static Hit[] firstHits;
	public static Ray[] rays;
	
	private static Scene scene;
	
	public Sample(int width, int height) {
		this.width = width;
		this.height = height;
		
		aspect = (double) width / (double) height;
		yFOV = FOV / aspect;
		
		screen = new Pixel[width * height];
		
		for (int i = 0; i < screen.length; i++) {
			screen[i] = new Pixel(0);
		}
		
		if (scene == null) {
			System.out.println("Loading scene...");
			scene = new Scene();
			
			System.out.println("Initializing lookup tables...");
			System.out.println("Sin and cos");
			//Init sin and cos
			sin = new double[width];
			cos = new double[width];
			
			for (int x = 0; x < width; x++) {
				double xAngle = ((double) x / width - 0.5) * Math.toRadians(FOV);
		
				sin[x] = Math.sin(xAngle);
				cos[x] = Math.cos(xAngle);
			}
			System.out.println("Done!");
		
			System.out.println("FirstHits and rays");
			//Initialize firstHits and rays lookup tables
			firstHits = new Hit[width * height];
			rays = new Ray[width * height];
			
			for (int y = 0; y < height; y++) {
				if (y % (height / 10) == 0) {
					System.out.println("	" + ((double) y / height * 100.0) + "%");
				}
				
				//It's "0.5 -" instead of "- 0.5" because we are flipping the y-axis 
				double yAngle = (0.5 - (double) y / height) * Math.toRadians(yFOV);
				double tanY = Math.tan(yAngle);
				//We know that the xz components have a length of 1, so we only need to calculate the length of the tan component
				double scale = 1.0 / Math.sqrt(1 + tanY*tanY);
				
				for (int x = 0; x < width; x++) {
					Vector3 dir = new Vector3(sin[x], tanY, cos[x]);
					
					//Normalize
					dir.multEqual(scale);
					
					//Rotate ray based on camera orientation
					dir = cam.getRotation().mult(dir).normal();
					
					rays[x + y * width] = new Ray(cam.pos, dir);
					
					firstHits[x + y * width] = scene.getClosest(rays[x + y * width]);
				}
			}
			
			System.out.println("Done!");
		}
	}
	
	public void render() {
		int currentSampleCount = Screen.currentSample++;
		
		Random random = new Random(currentSampleCount * width*height);
		
		for (int y = 0; y < height; y++) {
			if (y % 50 == 0) System.out.println(currentSampleCount + ", " + y);
			for (int x = 0; x < width; x++) {
				
				//Make a clone of ray and hit so they don't get modefied
				Ray ray = new Ray(rays[x + y * width]);
				Hit hit = firstHits[x + y * width] == null ? null : new Hit(firstHits[x + y * width]);
				
				//Drawing the directions
				screen[x+y*width].setColor(scene.getColor(ray, random, hit));
			}
		}
	}
}
