package com.JoL.PathTracer.render;

import com.JoL.PathTracer.Vector3;

public class Material {
	public Vector3 diffuse, emission;

	public Material() {
		diffuse = new Vector3(0, 0, 0);
		emission = new Vector3(0, 0, 0);
	}
	
	public Material(Vector3 diffuse, Vector3 emission) {
		this.diffuse = diffuse;
		this.emission = emission;
	}
	
	public static Material generateMaterialWithDiffuse(Vector3 diffuse) {
		Material mat = new Material();
		
		mat.diffuse = diffuse;
		
		return mat;
	}
	
	public static Material generateMaterialWithEmission(Vector3 emission) {
		Material mat = new Material();
		
		mat.emission = emission;
		
		return mat;
	}
}
