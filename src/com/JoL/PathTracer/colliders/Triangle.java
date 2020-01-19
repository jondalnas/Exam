package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Triangle extends Plane {
	private Vector3 v0;
	private Vector3 v1;
	private Vector3 v2;
	
	private Vector3 edge0;
	private Vector3 edge1;
	private Vector3 edge2;
	
	private Vector3 centerOfDisk;
	private double radius2;

	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, Material mat) {
		super(v0, v1.minus(v0).cross(v2.minus(v0)).normalize(), mat);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;

		edge0 = v1.minus(v0);
		edge1 = v2.minus(v1);
		edge2 = v0.minus(v2);
		
		//Calculate center of disk that contains all three point in it
		Vector3 v01 = v1.minus(v0);
		Vector3 v02 = v2.minus(v0);
		
		Vector3 toCenterOfDisk = (normal.cross(v02).mult(v01.sqrtMag()).add(v01.cross(normal).mult(v02.sqrtMag()))).mult(1.0 / (2.0 * normal.sqrtMag()));
		radius2 = toCenterOfDisk.sqrtMag();
		
		centerOfDisk = v0.add(toCenterOfDisk);
	}

	public Hit collides(Ray ray) {
		Hit hit = super.collides(ray);
		
		if (hit == null) return null;
		
		if (hit.pos.minus(centerOfDisk).sqrtMag() > radius2) return null;
		
		Vector3 c0 = hit.pos.minus(v0);
		Vector3 c1 = hit.pos.minus(v1);
		Vector3 c2 = hit.pos.minus(v2);
		
		if (hit.normal.dot(edge0.cross(c0)) >= 0 && 
			hit.normal.dot(edge1.cross(c1)) >= 0 &&
			hit.normal.dot(edge2.cross(c2)) >= 0) return hit;
		
		return null;
	}
}
