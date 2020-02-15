package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.Vector3;

public class RefractiveMaterial extends Material {
	public double indexOfRefraction;
	
	public RefractiveMaterial(Vector3 color, double indexOfRefraction) {
		this.color = color;
		this.indexOfRefraction = indexOfRefraction;
	}
	
	public Vector3 refract(Vector3 in, Vector3 normal, double rayRefractiveIndex, boolean entering) {
		Vector3 n = new Vector3(normal.x, normal.y, normal.z);
		
		double theta = in.dot(n);
		
		double refractiveIndex = rayRefractiveIndex / indexOfRefraction;
		
		if (!entering) refractiveIndex = 1.0 / refractiveIndex;
		
		//If not inside object, then flip cosDir else flip normal
		if (theta < 0) {
			theta = -theta;
		}
		else {
			n.multEqual(-1);
		}
		
		//
		double k = 1.0 - refractiveIndex * refractiveIndex * (1 - theta * theta);
		
		if (k < 0) return null;
		else return in.mult(refractiveIndex).add(n.mult(refractiveIndex * theta - Math.sqrt(k))).normalize();
	}
	
	public Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal, Vector3 hitPos, Vector3 inColor) {
		return inColor.mult(getColor(hitPos).mult(1.0/Math.PI));
	}

	public Material makeCopy() {
		RefractiveMaterial mat = new RefractiveMaterial(color, indexOfRefraction);
		mat.emission = emission;
		mat.geometry = geometry;
		return mat;
	}
}
