package com.JoL.PathTracer;

public class Vector3 {
	public double x, y, z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3 mult(Vector3 v) {
		return new Vector3(x * v.x, y * v.y, z * v.z);
	}
	
	public Vector3 mult(double k) {
		return new Vector3(x * k, y * k, z * k);
	}
}
