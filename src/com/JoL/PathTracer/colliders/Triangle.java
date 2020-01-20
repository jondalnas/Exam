package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Triangle extends Disk {
	private Vector3 v0;
	private Vector3 v1;
	private Vector3 v2;
	
	private Vector3 edge0;
	private Vector3 edge1;
	private Vector3 edge2;
	
	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, Material mat) {
		super(null, v1.minus(v0).cross(v2.minus(v0)).normalize(), 0, mat);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;

		edge0 = v1.minus(v0);
		edge1 = v2.minus(v1);
		edge2 = v0.minus(v2);
		
		//Calculate center of disk that contains all three point in it
		double denom = 2.0 * v0.minus(v1).cross(v1.minus(v2)).sqrtMag();
		double alpha = v1.minus(v2).sqrtMag() * v0.minus(v1).dot(v0.minus(v2)) / denom;
		double beta = v0.minus(v2).sqrtMag() * v1.minus(v0).dot(v1.minus(v2)) / denom;
		double gamma = v0.minus(v1).sqrtMag() * v2.minus(v0).dot(v2.minus(v1)) / denom;
		
		pos = v0.mult(alpha).add(v1.mult(beta)).add(v2.mult(gamma));
		r = pos.minus(v0).mag();
	}

	public Hit collides(Ray ray) {
		Hit hit = super.collides(ray);
		
		if (hit == null) return null;
		
		Vector3 c0 = hit.pos.minus(v0);
		Vector3 c1 = hit.pos.minus(v1);
		Vector3 c2 = hit.pos.minus(v2);

		Vector3 ec0 = edge0.cross(c0);
		Vector3 ec1 = edge1.cross(c1);
		Vector3 ec2 = edge2.cross(c2);
		
		if (hit.normal.dot(ec0) >= 0 && 
			hit.normal.dot(ec1) >= 0 &&
			hit.normal.dot(ec2) >= 0) return hit;
		
		if (hit.normal.dot(ec0) <= 0 && 
			hit.normal.dot(ec1) <= 0 &&
			hit.normal.dot(ec2) <= 0) return hit;
		
		return null;
	}
}
