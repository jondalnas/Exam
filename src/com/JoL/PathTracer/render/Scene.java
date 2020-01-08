package com.JoL.PathTracer.render;

import java.util.ArrayList;
import java.util.List;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;

public class Scene {
	private List<Geometry> scene = new ArrayList<Geometry>();
	
	public Scene() {
		//Load scene
		scene.add(new Sphere(new Vector3(-2, 0, 8), 1, Material.generateMaterialWithEmission(new Vector3(1, 1, 1))));
		scene.add(new Sphere(new Vector3(2, 0, 8), 1, Material.generateMaterialWithDiffuse(new Vector3(1, 0, 1))));
	}
	
	public Vector3 getColor(Ray ray) {
		if (ray.ittration++ > 5) return new Vector3(0, 0, 0);
		
		Hit closest = getClosest(ray);
		
		if (closest == null) return new Vector3(0, 0, 0);
		
		return closest.mat.emission;// + closest.mat.diffuse * getColor(newRay)
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
