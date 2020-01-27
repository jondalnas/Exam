package com.JoL.PathTracer.colliders;

import java.util.Stack;

import com.JoL.PathTracer.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public byte ittration = 0;
	
	public Stack<Double> refractiveIndex = new Stack<Double>();
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public Ray(Ray ray) {
		pos = ray.pos;
		dir = ray.dir;
	}

	public double getRefractiveIndex() {
		if (refractiveIndex.empty()) return 1;
		
		return refractiveIndex.peek();
	}
}
