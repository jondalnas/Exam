package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public class Hit {
	public Vector3 pos;
	public double dist;
	public Vector3 normal;
	public Material mat;
	
	public Hit(Vector3 pos, double dist, Vector3 normal, Material mat) {
		this.pos = pos;
		this.dist = dist;
		this.normal = normal;
		this.mat = mat;
	}

	public Hit(Hit hit) {
		pos = hit.pos;
		dist = hit.dist;
		normal = hit.normal;
		mat = hit.mat;
	}
}
