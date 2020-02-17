package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Plane extends Geometry {
	protected Vector3 normal;
	
	public Plane(Vector3 pos, Vector3 normal, Material mat) {
		super(pos);
		this.normal = normal;
		this.mat = mat;
	}

	public Hit collides(Ray ray) {
		double denom = normal.dot(ray.dir);
		if (Math.abs(denom) > 1e-9) {
			Vector3 p0l0 = pos.minus(ray.pos);
			double dist = p0l0.dot(normal)/denom;
			if (dist < 1e-9) return null;
			Vector3 p = ray.dir.mult(dist).add(ray.pos);
			
			return new Hit(p, dist, normal, mat);
		}
		
		return null;
	}

	public Vector3 imageColor(Vector3 pos, int imageIndex) {
		return null;
	}
}
