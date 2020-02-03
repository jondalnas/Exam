package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;

public class DiffuseMaterial extends Material {
	
	public DiffuseMaterial(int imageIndex) {
		super(imageIndex);
	}
	
	public static Material generateMaterialWithDiffuse(Vector3 color) {
		Material mat = new DiffuseMaterial(-1);
		
		mat.color = color;
		
		return mat;
	}
	
	public static Material generateMaterialWithEmission(Vector3 emission) {
		Material mat = new DiffuseMaterial(-1);
		
		mat.emission = emission;
		
		return mat;
	}

	public Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal, Vector3 hitPos, Vector3 inColor) {
		return inColor.mult(getColor(hitPos).mult(1.0/Math.PI));
	}

	public Material makeCopy() {
		Material mat = new DiffuseMaterial(imageIndex);
		mat.color = color;
		mat.emission = emission;
		mat.geometry = geometry;
		return mat;
	}
}
