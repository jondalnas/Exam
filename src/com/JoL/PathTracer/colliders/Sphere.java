package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;

public class Sphere extends Geometry {
	public double r;
	
	public Sphere(Vector3 pos, double r) {
		super(pos);
		this.r = r;
	}

	public boolean collides(Ray ray) {
		Vector3 toSphere = pos.minus(ray.pos);
		double cc = toSphere.dot(ray.dir);
		double dist2 = toSphere.sqrtMag()- cc*cc;
		if (dist2 > r*r) return false;
		
		double tDist = Math.sqrt(r*r-dist2);
		double t0 = cc - tDist;
		double t1 = cc + tDist;
		
		if (t0 > t1) {
			double temp = t0;
			t0 = t1;
			t1 = temp;
		}
		
		if (t0 < 0) {
			t0 = t1;
			if (t0 < 0) return false;
		}
		
		return true;
	}

}
