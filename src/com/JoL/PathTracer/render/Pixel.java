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
	
	public void addColor(Pixel p) {
		color = color.add(p.color);
	}
	
	public Pixel scaleColor(double scale) {
		return new Pixel(color.mult(scale));
	}
	
	public int getIntColor() {
		return ((int) (color.x * 255.0) << 16) | ((int) (color.y * 255.0) << 8) | (int) (color.z * 255.0);
	}
}