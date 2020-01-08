package com.JoL.PathTracer.render;

import com.JoL.PathTracer.Camera;
import com.JoL.PathTracer.Matrix4x4;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;

public class Sample {
	private final int width, height;
	public Pixel[] screen;

	public static final double FOV = 60;
	public static double aspect, yFOV;
	public static final Camera cam = new Camera(new Vector3(0, 0, 0), new Vector3(1, 0, 0));
	
	public static double[] sin, cos;
	
	public Sample(int width, int height) {
		this.width = width;
		this.height = height;
		
		aspect = (double) width / (double) height;
		yFOV = FOV / aspect;
		
		screen = new Pixel[width * height];
		
		for (int i = 0; i < screen.length; i++) {
			screen[i] = new Pixel(0);
		}
		
		//Init sin and cos
		if (sin == null) {
			sin = new double[width];
			cos = new double[width];
			
			for (int x = 0; x < width; x++) {
				double xAngle = ((double) x / width - 0.5) * Math.toRadians(FOV);

				sin[x] = Math.sin(xAngle);
				cos[x] = Math.cos(xAngle);
			}
		}
	}
	
	public void render() {
		Geometry s = new Sphere(new Vector3(0, 0, 2), 1);
		
		for (int y = 0; y < height; y++) {
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
				
				Ray ray = new Ray(cam.pos, dir);
				
				//Drawing the directions
				screen[x+y*width].setColor(s.collides(ray) ? 0 : 0xffffff);
			}
		}
	}
}
