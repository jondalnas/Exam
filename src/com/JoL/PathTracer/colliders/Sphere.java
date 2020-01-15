package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Sphere extends Geometry {
	public double r;
	public Material mat;
	
	public Sphere(Vector3 pos, double r, Material mat) {
		super(pos);
		this.r = r;
		this.mat = mat;
	}

	public Hit collides(Ray ray) {
		Vector3 toSphere = pos.minus(ray.pos);
		double cc = toSphere.dot(ray.dir);
		double dist2 = toSphere.sqrtMag()- cc*cc;
		if (dist2 > r*r) return null;
		
		double tDist = Math.sqrt(r*r-dist2);
		double t0 = cc - tDist;
		double t1 = cc + tDist;
		
		if (t0 > t1) {
			double temp = t0;
			t0 = t1;
			t1 = temp;
		}
		
		if (t0 < 1e-6) {
			t0 = t1;
			if (t0 < 1e-6) return null;
		}
		Vector3 pos = ray.dir.mult(t0).add(ray.pos);
		
		return new Hit(pos, t0, pos.minus(this.pos).mult(1.0/r), mat);
	}

}
