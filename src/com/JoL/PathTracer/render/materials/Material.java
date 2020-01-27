package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;

public abstract class Material {
	public Vector3 color, emission;
	
	public Geometry geometry;
	public int imageIndex = -1;

	public Material() {
		color = new Vector3(0, 0, 0);
		emission = new Vector3(0, 0, 0);
	}
	
	public Material(Vector3 color, Vector3 emission) {
		this.color = color;
		this.emission = emission;
	}
	
	public Material(int imageIndex) {
		this();
		
		this.imageIndex = imageIndex;
	}

	public abstract Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal, Vector3 hitPos);
	
	public Vector3 getColor(Vector3 hitPos) {
		if (imageIndex == -1)
			return color;
		
		return geometry.imageColor(hitPos, imageIndex);
	}
	
	public abstract Material makeCopy();
}
