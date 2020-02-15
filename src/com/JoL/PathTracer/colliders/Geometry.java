package com.JoL.PathTracer.colliders;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.render.materials.Material;

public abstract class Geometry {
	public Material mat;
	public Vector3 pos;
	
	public short index = -1;
	
	public Geometry(Vector3 pos) {
		this.pos = pos;
	}

	public abstract Hit collides(Ray ray);

	public abstract Vector3 imageColor(Vector3 pos, int imageIndex);

	public short getIndex() {
		return index;
	}
}
