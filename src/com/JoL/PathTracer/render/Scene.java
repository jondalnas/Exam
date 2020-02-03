package com.JoL.PathTracer.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.JoL.PathTracer.MathTools;
import com.JoL.PathTracer.Matrix4x4;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Disk;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Plane;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;
import com.JoL.PathTracer.objects.Object3D;
import com.JoL.PathTracer.objects.loader.ImageLoader;
import com.JoL.PathTracer.objects.loader.Loader;
import com.JoL.PathTracer.render.materials.CookTorranceMaterial;
import com.JoL.PathTracer.render.materials.DiffuseMaterial;
import com.JoL.PathTracer.render.materials.Material;
import com.JoL.PathTracer.render.materials.RefractiveMaterial;

public class Scene {
	private List<Geometry> scene = new ArrayList<Geometry>();
	
	public Scene() {
		//Load scene
		addObject(new Disk(new Vector3(0, 3, 8), new Vector3(0, -1, 0), 1, DiffuseMaterial.generateMaterialWithEmission(new Vector3(1, 1, 1).mult(6))));
		//addObject(new Sphere(new Vector3(-1, -2, 8), 1, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		/*Object3D teapot = Loader.load("teapot.obj");
		teapot.material = new RefractiveMaterial(new Vector3(0.6118, 0.8275, 85.88), 1.33);//DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1));
		teapot.pos = new Vector3(0, -1, 8);
		addObject(teapot);*/
		
		Object3D teapot = new Object3D(Loader.load("teapot.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, -3, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		//addObject(teapot, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 0, 0.8)));
		
		/*Object3D crystal = new Object3D(Loader.load("Crystal wo normals and textures.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, -1.8, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		crystal.material = new RefractiveMaterial(new Vector3(0.6, 0.4, 0.8), 1.544);
		scene.add(crystal);*/
		
		/*Object3D spherew = new Object3D(Loader.load("Sphere w.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(-1.5, 0, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		spherew.material = new CookTorranceMaterial(new Vector3(1, 215/255.0, 0), 0.3, 3);
		scene.add(spherew);*/
		
		//Object3D teapot = new Object3D(Loader.load("teapot.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, -3, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		//addObject(teapot, new CookTorranceMaterial(new Vector3(1, 215/255.0, 0), 0.3, 3));
		
		//scene.add(new Sphere(new Vector3(-1.5, -0.5, 8), 1, new CookTorranceMaterial(new Vector3(1, 215.0/255.0, 0), 0.3, 3)));
		
		//Object3D spherewo = new Object3D(Loader.load("Sphere wo.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(1.5, 0, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		//spherewo.material = new RefractiveMaterial(new Vector3(0.6, 0.4, 0.8), 1.544);
		//scene.add(spherewo);
		//Object3D lens = new Object3D(Loader.load("Lens 2m 1m.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, /*3.86847195-3*/2.6, 8), new Vector3(3, 3, 1), new Vector3(Math.toRadians(90), 0, 0)));
		//lens.material = new RefractiveMaterial(new Vector3(1, 1, 1), 1.517);
		//scene.add(lens);
		
		/*Object3D crystal = new Object3D(Loader.load("Crystal.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, 0, 8), new Vector3(2, 1, 1), new Vector3(0, 0, 0)));
		crystal.material = new RefractiveMaterial(new Vector3(0.6, 0.4, 0.8), 1.544);
		addObject(crystal);*/
		
		/*Object3D spherew = new Object3D(Loader.load("Sphere w.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(-1.5, 0, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		addObject(spherew, new DiffuseMaterial(ImageLoader.cage.ordinal()));*/
		
		//Object3D plane = new Object3D(Loader.load("Plane.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, -2, 9), new Vector3(1, 1, 1), new Vector3(Math.toRadians(-90), 0, 0)));
		//addObject(plane, new DiffuseMaterial(ImageLoader.cage.ordinal()));
		
		//addObject(new Sphere(new Vector3(0, 0, 0), 1, new RefractiveMaterial(new Vector3(1, 1, 1), 1.544)));

		/*Matrix4x4 ornamentMatrix = Matrix4x4.generateTransformationMatrix(new Vector3(0, -1.5, 8), new Vector3(1.5, 1.5, 1.5), new Vector3(0, Math.toRadians(120), 0));
		Object3D ornament = new Object3D(Loader.load("Ornament.obj"), ornamentMatrix);
		addObject(ornament, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(0.831, 0.686, 0.216)));
		Object3D ornamentThread = new Object3D(Loader.load("Ornament thread.obj"), ornamentMatrix);
		addObject(ornamentThread, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(0.831, 0.686, 0.216)));
		Object3D ornamentHole = new Object3D(Loader.load("Ornament hole.obj"), ornamentMatrix);
		addObject(ornamentHole, DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(0.831, 0.686, 0.216)));*/
		
		/*Object3D spherewo = new Object3D(Loader.load("Sphere wo.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(1.5, 0, 8), new Vector3(1, 1, 1), new Vector3(0, 0, 0)));
		spherewo.material = new RefractiveMaterial(new Vector3(0.6, 0.4, 0.8), 1.544);
		addObject(spherewo);*/
		
		//addObject(new Sphere(new Vector3(1.5, 0, 6), 1, new RefractiveMaterial(new Vector3(1, 1, 1), 1.52)));
		//Sides
		addObject(new Plane(new Vector3(-3, 0, 0), new Vector3(1, 0, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(0, 1, 0))));
		addObject(new Plane(new Vector3(3, 0, 0), new Vector3(-1, 0, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 0, 0))));
		//Top-bottom
		Object3D plane = new Object3D(Loader.load("Plane.obj"), Matrix4x4.generateTransformationMatrix(new Vector3(0, -3, 5), new Vector3(5, 1, 5), new Vector3(0, 0, 0)));
		addObject(plane, new CookTorranceMaterial(ImageLoader.tile.ordinal(), 0.9, 2));
		//addObject(new Plane(new Vector3(0, -3, 0), new Vector3(0, 1, 0), new CookTorranceMaterial(new Vector3(0.9, 0.9, 0.9), 0.1, 30)));//DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		addObject(new Plane(new Vector3(0, 3, 0), new Vector3(0, -1, 0), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		//Front-back
		addObject(new Plane(new Vector3(0, 0, 10), new Vector3(0, 0, -1), DiffuseMaterial.generateMaterialWithDiffuse(new Vector3(1, 1, 1))));
		addObject(new Plane(new Vector3(0, 0, -2), new Vector3(0, 0, 1), DiffuseMaterial.generateMaterialWithEmission(new Vector3(1, 1, 1))));
	}
	
	public Vector3 getColor(Ray ray, Random rand, Hit... hit) {
		//Return background if ray has hit too many objects
		if (ray.ittration++ > 5) return new Vector3(0, 0, 0);
		
		//Get closest pint of collision
		Hit closest = null;
		if (hit.length == 0) {
			closest = getClosest(ray);
		} else {
			closest = hit[0];
		}
		
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
			return closest.mat.emission.add(closest.mat.BRDF(ray.dir.mult(-1), newDir, closest.normal, closest.pos, getColor(newRay, rand)).mult(cosTheta).mult(1.0/pdf));
		} else {
			double refractiveIndexOfRay = ray.getRefractiveIndex();
			
			Vector3 refractiveDir = ((RefractiveMaterial) closest.mat).refract(ray.dir, closest.normal, refractiveIndexOfRay);
			
			Vector3 reflectColor = closest.mat.emission.add(closest.mat.BRDF(ray.dir.mult(-1), newDir, closest.normal, closest.pos, getColor(newRay, rand)).mult(cosTheta).mult(1.0/pdf));
			
			if (refractiveDir != null) {
				Ray refractRay = new Ray(closest.pos, refractiveDir);
				refractRay.ittration = ray.ittration;
				refractRay.refractiveIndex = ray.refractiveIndex;
				if (ray.dir.dot(closest.normal) < 0) refractRay.refractiveIndex.push(((RefractiveMaterial) closest.mat).indexOfRefraction);
				else if (!refractRay.refractiveIndex.isEmpty()) refractRay.refractiveIndex.pop();
				
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
	
	public void addObject(Geometry geometry) {
		geometry.mat.geometry = geometry;
		scene.add(geometry);
	}
	
	public void addObject(Geometry geometry, Material mat) {
		geometry.mat = mat;
		geometry.mat.geometry = geometry;
		scene.add(geometry);
	}
}
