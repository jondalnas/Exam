package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Disk extends Plane {
	private double r;

	public Disk(Vector3 pos, Vector3 normal, double radius, Material mat) {
		super(pos, normal, mat);
		r = radius;
	}
	
	public Hit collides(Ray ray) {
		Hit hit = super.collides(ray);
		
		if (hit == null) return null;
		
		Vector3 toVec = pos.minus(hit.pos);
		double dist = toVec.dot(toVec);
		if (dist < r*r) return hit;
		
		return null;
	}
}
