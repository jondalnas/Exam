package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.Material;

public class Plane extends Geometry {
	private Vector3 normal;
	private Material mat;
	
	public Plane(Vector3 pos, Vector3 normal, Material mat) {
		super(pos);
		this.normal = normal;
		this.mat = mat;
	}

	public Hit collides(Ray ray) {
		Vector3 l0 = ray.pos;
		Vector3 l = ray.dir.normal();
		Vector3 n = normal;
		Vector3 p0 = pos;
		
		double denom = n.dot(l);
		if (Math.abs(denom) > 1e-6) {
			Vector3 p0l0 = p0.minus(l0);
			double dist = p0l0.dot(n)/denom;
			if (dist < 1e-6) return null;
			Vector3 p = l.mult(dist).add(l0);
			
			return new Hit(p, dist, normal, mat);
		}
		
		return null;
	}
	
}
