package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public byte ittration = 0;
	
	public double refractiveIndex = 1;
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
}
