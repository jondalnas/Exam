package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;

public class RefractiveMaterial extends Material {
	public double indexOfRefraction;
	
	public RefractiveMaterial(Vector3 color, double indexOfRefraction) {
		this.color = color;
		this.indexOfRefraction = indexOfRefraction;
	}
	
	public Vector3 refract(Vector3 in, Vector3 normal, double rayRefractiveIndex) {
		double theta = in.dot(normal);
		
		double refractiveIndex = rayRefractiveIndex / indexOfRefraction;
		
		//If not inside object, then flip cosDir else flip normal and refractive index
		if (theta < 0) {
			theta = -theta;
		}
		else {
			normal.multEqual(-1);
			refractiveIndex = 1.0 / refractiveIndex;
		}
		
		//
		double k = 1.0 - refractiveIndex * refractiveIndex * (1 - theta * theta);
		
		if (k < 0) return null;
		else return in.mult(refractiveIndex).add(normal.mult(refractiveIndex * theta - Math.sqrt(k))).normalize();
	}
	
	public Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal) {
		return color.mult(1.0/Math.PI);
	}
}
