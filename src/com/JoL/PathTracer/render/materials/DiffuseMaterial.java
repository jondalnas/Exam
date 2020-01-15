package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;

public class DiffuseMaterial extends Material {
	public static Material generateMaterialWithDiffuse(Vector3 color) {
		Material mat = new DiffuseMaterial();
		
		mat.color = color;
		
		return mat;
	}
	
	public static Material generateMaterialWithEmission(Vector3 emission) {
		Material mat = new DiffuseMaterial();
		
		mat.emission = emission;
		
		return mat;
	}

	public Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal) {
		return color.mult(1.0/Math.PI);
	}
}
