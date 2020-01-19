package com.JoL.PathTracer.render;

import com.JoL.PathTracer.Vector3;

public class Pixel {
	private Vector3 color;
	
	public Pixel(Vector3 color) {
		this.color = color;
	}
	
	public Pixel(int color) {
		setColor(color);
	}
	
	public void setColor(int color) {
		int rc = (color >> 16) & 0xff;
		int gc = (color >> 8)  & 0xff;
		int bc =  color        & 0xff;
		
		this.color = new Vector3(rc / 255.0, gc / 255.0, bc / 255.0);
	}
	
	public void setColor(Vector3 c) {
		color = c;
	}
	
	public void addColor(Pixel p) {
		color = color.add(p.color);
	}
	
	public Pixel scaleColor(double scale) {
		return new Pixel(color.mult(scale));
	}
	
	public int getIntColor() {
		double r = color.x;
		double g = color.y;
		double b = color.z;
		
		if (r > 1) r = 1;
		if (g > 1) g = 1;
		if (b > 1) b = 1;
		
		return ((int) (r * 255.0) << 16) | ((int) (g * 255.0) << 8) | (int) (b * 255.0);
	}
	
	public Vector3 getColorVector() {
		return color;
	}

	public Pixel capOne() {
		if (color.x > 1) color.x = 1;
		if (color.y > 1) color.y = 1;
		if (color.z > 1) color.z = 1;
		
		return this;
	}
}