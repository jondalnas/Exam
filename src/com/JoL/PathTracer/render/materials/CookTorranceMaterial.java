package com.JoL.PathTracer.render.materials;

import com.JoL.PathTracer.MathTools;
import com.JoL.PathTracer.Vector3;

public class CookTorranceMaterial extends Material {
	public double roughness;
	public double refractiveIndex;
	private double roughness2;
	
	public CookTorranceMaterial(Vector3 color, double roughness, double refractiveIndex) {
		this.color = color;
		this.refractiveIndex = refractiveIndex;
		this.roughness = roughness;
		
		roughness2 = roughness * roughness;
	}
	
	public CookTorranceMaterial(int imageIndex, double roughness, double refractiveIndex) {
		this.imageIndex = imageIndex;
		this.refractiveIndex = refractiveIndex;
		this.roughness = roughness;
		
		roughness2 = roughness * roughness;
	}
	
	public Vector3 BRDF(Vector3 dirIn, Vector3 dirOut, Vector3 normal, Vector3 hitPos, Vector3 inColor) {
		Vector3 V = dirIn;
		Vector3 L = dirOut;
		Vector3 H = L.add(V).mult(1.0/2.0).normal();
		double theta = V.dot(H);
		//double a = 1.0/(roughness*(Math.sqrt(1-theta*theta)/(theta)));
		//double G = 1.0;
		//if (a < 1.6) G = (3.535*a+2.181*a*a) / (1+2.276*a+2.577*a*a);
		
		double k_spec = ((DBeckmann(H, normal, theta, roughness)*MathTools.fresnel(V, H, 1, refractiveIndex)*GTorrance(V, L, normal, H, theta))/(4.0*(V.dot(normal)*(normal.dot(L)))));
		
		return inColor.mult(k_spec).add(inColor.mult(getColor(hitPos).mult(1 - MathTools.fresnel(L.mult(-1), normal, 1, refractiveIndex))));
	}

	public int chi(double a) {
		return a > 0 ? 1 : 0;
	}
	
	public double G1(Vector3 V, Vector3 H, Vector3 N, double G) {
		return chi((V.dot(H)/V.dot(N)))*G;
	}
	
	public double GTorrance(Vector3 V, Vector3 L, Vector3 normal, Vector3 H, double theta) {
		double GPre = 2 * normal.dot(H) / theta;
		
		double G0 = normal.dot(V) * GPre;
		double G1 = normal.dot(L) * GPre;
		return Math.min(1, Math.min(G0, G1));
	}
	
	public double DBeckmann(Vector3 H, Vector3 n, double theta, double roughness) {
		double nh2 = n.dot(H);
		nh2 *= nh2;
		
		return Math.exp((nh2 - 1) / (roughness2 * nh2))/(Math.PI * roughness2 * nh2 * nh2);
		//return (chi(a)*(H.dot(n))/(Math.PI)*roughness*theta*theta*theta*theta)*Math.exp(((1-theta*theta)/(theta*theta*roughness*roughness)));
	}

	public Material makeCopy() {
		CookTorranceMaterial mat = new CookTorranceMaterial(color, roughness, refractiveIndex);
		mat.emission = emission;
		mat.imageIndex = imageIndex;
		
		return mat;
	}
}
