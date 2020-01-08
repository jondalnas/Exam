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
	
	public Vector3 multEqual(double k) {
		x *= k;
		y *= k;
		z *= k;
		
		return this;
	}

	public Vector3 minus(Vector3 v) {
		return new Vector3(x - v.x, y - v.y, z - v.z);
	}
	
	public double dot(Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
	}
	
	public double sqrtMag() {
		return x*x + y*y + z*z;
	}
	
	public double mag() {
		return Math.sqrt(sqrtMag());
	}
	
	public Vector3 normal() {
		return new Vector3(x, y, z).mult(1/mag());
	}

	public Vector3 normalize() {
		double d = mag();
		
		x /= d;
		y /= d;
		z /= d;
		
		return this;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
