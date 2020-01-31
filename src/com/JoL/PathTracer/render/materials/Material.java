package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;

public abstract class Material {
	public Vector3 color, emission;

	public Material() {
		color = new Vector3(0, 0, 0);
		emission = new Vector3(0, 0, 0);
	}
	
	public Material(Vector3 color, Vector3 emission) {
		this.color = color;
		this.emission = emission;
	}
	
	public abstract Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal, Vector3 inColor);
}
