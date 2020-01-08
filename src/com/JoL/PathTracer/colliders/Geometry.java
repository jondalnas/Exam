package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;

public abstract class Geometry {
	public Vector3 pos;
	
	public Geometry(Vector3 pos) {
		this.pos = pos;
	}

	public abstract Hit collides(Ray ray);
}
