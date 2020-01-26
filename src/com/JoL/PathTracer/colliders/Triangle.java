package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Triangle extends Disk {
	private Vector3 v0, v1, v2;
	private Vector3 n0, n1, n2;
	
	private Vector3 edge0, edge1, edge2;
	
	private double area2;
	
	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, Material mat) {
		super(null, v1.minus(v0).cross(v2.minus(v0)).normalize(), 0, mat);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;

		edge0 = v1.minus(v0);
		edge1 = v2.minus(v1);
		edge2 = v0.minus(v2);
		
		area2 = edge0.cross(edge2.mult(-1)).mag();
		
		//Calculate center of disk that contains all three point in it
		double denom = 2.0 * v0.minus(v1).cross(v1.minus(v2)).sqrtMag();
		double alpha = v1.minus(v2).sqrtMag() * v0.minus(v1).dot(v0.minus(v2)) / denom;
		double beta = v0.minus(v2).sqrtMag() * v1.minus(v0).dot(v1.minus(v2)) / denom;
		double gamma = v0.minus(v1).sqrtMag() * v2.minus(v0).dot(v2.minus(v1)) / denom;
		
		pos = v0.mult(alpha).add(v1.mult(beta)).add(v2.mult(gamma));
		r = pos.minus(v0).mag();
	}
	
	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 n0, Vector3 n1, Vector3 n2, Material mat) {
		this(v0, v1, v2, mat);

		if (normal.dot(n0.add(n1).add(n2).mult(1.0/3.0)) < 0)
			normal.multEqual(-1);
		
		this.n0 = n0;
		this.n1 = n1;
		this.n2 = n2;
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
	
	public Vector3 getNormal(Vector3 point) {
		if (n0 == null) return null;

		double C0 = edge1.cross(point.minus(v1)).mag() / area2;
		double C1 = edge2.cross(point.minus(v2)).mag() / area2;
		double C2 = edge0.cross(point.minus(v0)).mag() / area2;
		
		return n0.mult(C0).add(n1.mult(C1)).add(n2.mult(C2));
	}
}
