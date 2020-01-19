package com.JoL.PathTracer.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.JoL.PathTracer.MathTools;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Disk;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Plane;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;
import com.JoL.PathTracer.objects.Object3D;
import com.JoL.PathTracer.objects.loader.Loader;
import com.JoL.PathTracer.render.materials.DiffuseMaterial;
import com.JoL.PathTracer.render.materials.RefractiveMaterial;

public class Scene {
	private List<Geometry> scene = new ArrayList<Geometry>();
	
	public Scene() {
		//Load scene
		scene.add(new Disk(new Vector3(0, 3, 8), new Vector3(0, -1, 0), 1, DiffuseMaterial.generateMaterialWithEmission(new Vector3(1, 1, 1).mult(6))));
		//scene.add(new Sphere(new Vector3(-1, -2, 8), 1, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		Object3D teapot = Loader.load("teapot.obj");
		teapot.material = DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1));
		teapot.pos = new Vector3(0, -2, 8);
		scene.add(teapot);
		//scene.add(new Sphere(new Vector3(1.5, 0, 6), 1, new RefractiveMaterial(new Vector3(1, 1, 1), 1.52)));
		//Sides
		//scene.add(new Plane(new Vector3(-3, 0, 0), new Vector3(1, 0, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(0, 1, 0))));
		//scene.add(new Plane(new Vector3(3, 0, 0), new Vector3(-1, 0, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 0, 0))));
		//Top-bottom
		scene.add(new Plane(new Vector3(0, -3, 0), new Vector3(0, 1, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		//scene.add(new Plane(new Vector3(0, 3, 0), new Vector3(0, -1, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		//Front-back
		//scene.add(new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		//scene.add(new Plane(new Vector3(0, 0, -2), new Vector3(0, 0, 1), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
	}
	
	public Vector3 getColor(Ray ray, Random rand) {
		//Return background if ray has hit too many objects
		if (ray.ittration++ > 5) return new Vector3(0, 0, 0);
		
		//Get closest pint of collision
		Hit closest = getClosest(ray);
		
		//Return background if ray doesn't hit anything
		if (closest == null) return new Vector3(0, 0, 0);
		
		//Generate new ray
		Vector3 newDir = MathTools.generateHemisphereVector(closest.normal, rand);
		Ray newRay = new Ray(closest.pos, newDir);
		newRay.ittration = ray.ittration;
		newRay.refractiveIndex = ray.refractiveIndex;
		
		double cosTheta = newDir.dot(closest.normal);
		double pdf = 1.0 / (2.0 * Math.PI);
		
		if (!(closest.mat instanceof RefractiveMaterial)) {
			//L_e + (L_i * f_r * (w_i . n)) / pdf
			//f_r = diffues / PI
			return closest.mat.emission.add(getColor(newRay, rand).mult(closest.mat.BRDF(ray.dir.mult(-1), newDir, closest.normal)).mult(cosTheta).mult(1.0/pdf));
		} else {
			double refractiveIndexOfRay = ray.getRefractiveIndex();
			
			Vector3 refractiveDir = ((RefractiveMaterial) closest.mat).refract(ray.dir, closest.normal, refractiveIndexOfRay);
			
			Vector3 reflectColor = closest.mat.emission.add(getColor(newRay, rand).mult(closest.mat.BRDF(ray.dir.mult(-1), newDir, closest.normal)).mult(cosTheta).mult(1.0/pdf));
			
			if (refractiveDir != null) {
				Ray refractRay = new Ray(closest.pos, refractiveDir);
				refractRay.ittration = ray.ittration;
				refractRay.refractiveIndex.push(((RefractiveMaterial) closest.mat).indexOfRefraction);
				
				Vector3 refractColor = getColor(refractRay, rand);
				
				double fr = MathTools.fresnel(ray.dir, closest.normal, refractiveIndexOfRay, ((RefractiveMaterial) closest.mat).indexOfRefraction);
				
				return reflectColor.mult(fr).add(refractColor.mult(1 - fr));
			}
			
			return reflectColor;
		}
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
