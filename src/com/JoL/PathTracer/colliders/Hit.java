package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;

public class Hit {
	public Vector3 pos;
	public double dist;
	public Vector3 normal;
	
	
	public Hit(Vector3 pos, double dist, Vector3 normal) {
		super();
		this.pos = pos;
		this.dist = dist;
		this.normal = normal;
	}
	
}
