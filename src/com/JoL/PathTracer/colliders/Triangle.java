package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Triangle extends Plane {
	Vector3 v0;
	Vector3 v1;
	Vector3 v2;

	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, Material mat) {
		super(v0, v0.minus(v1).cross(v0.minus(v2)), mat);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
	}

	public Hit collides(Ray ray) {
		Hit hit = super.collides(ray);
		
		if (hit == null) return null;
		
		Vector3 edge0 = v1.minus(v0);
		Vector3 edge1 = v2.minus(v1);
		Vector3 edge2 = v0.minus(v2);
		
		Vector3 c0 = hit.pos.minus(v0);
		Vector3 c1 = hit.pos.minus(v1);
		Vector3 c2 = hit.pos.minus(v2);
		
		if (hit.normal.dot(edge0.cross(c0)) > 0 && 
			hit.normal.dot(edge1.cross(c1)) > 0 &&
			hit.normal.dot(edge2.cross(c2)) > 0) return hit;
		
		return null;
	}
}
