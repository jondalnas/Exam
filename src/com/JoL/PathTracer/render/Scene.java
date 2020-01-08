package com.JoL.PathTracer.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.JoL.PathTracer.MathTools;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;

public class Scene {
	private List<Geometry> scene = new ArrayList<Geometry>();
	
	public Scene() {
		//Load scene
		scene.add(new Sphere(new Vector3(0, 0, 8), 1, new Material(new Vector3(1,0,0), new Vector3(1,1,1))));//Material.generateMaterialWithEmission(new Vector3(1, 1, 1))));
		scene.add(new Sphere(new Vector3(2, 0, 8), 1, Material.generateMaterialWithDiffuse(new Vector3(1, 0, 0))));
	}
	
	public Vector3 getColor(Ray ray, Random rand) {
		//Return background if ray has hit too many objects
		if (ray.ittration++ > 5) return new Vector3(0, 0, 0);
		
		//Get closest pint of collision
		Hit closest = getClosest(ray);
		
		//Return background if ray doesn't hit anything
		if (closest == null) return new Vector3(0, 0, 0);
		
		//Generate new ray
		Vector3 dir = MathTools.generateHemisphereVector(closest.normal, rand);
		Ray newRay = new Ray(closest.pos, dir);
		newRay.ittration = ray.ittration;
		
		double cosTheta = dir.dot(closest.normal);
		double pdf = 1.0 / Math.PI;
		
		return closest.mat.emission.add(closest.mat.diffuse.mult(getColor(newRay, rand)).mult(cosTheta).mult(1.0/Math.PI).mult(1.0/pdf));
	}
	
	public Hit getClosest(Ray ray) {
		Hit closest = null;
		
		for (Geometry g : scene) {
			Hit curr = g.collides(ray);
			
			if (curr == null) continue;
			
			if (closest == null || (curr.dist < closest.dist)) {
				closest = curr;
			}
		}
		
		return closest;
	}
}
