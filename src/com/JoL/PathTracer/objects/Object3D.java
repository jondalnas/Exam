package com.JoL.PathTracer.objects;

import com.JoL.PathTracer.Matrix4x4;
import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;
import com.JoL.PathTracer.colliders.Triangle;
import com.JoL.PathTracer.objects.loader.LoaderObject3D;
import com.JoL.PathTracer.objects.loader.LoaderObject3D.Face;
import com.JoL.PathTracer.render.materials.Material;

public class Object3D extends Geometry {
	public final Triangle[] triangles;
	public Material material;
	private Sphere sphere;
	
	public Object3D(LoaderObject3D lo, Matrix4x4 transform) {
		super(new Vector3(0, 0, 0));
		triangles = new Triangle[lo.faces.length];

		for (int i = 0; i < lo.vectors.length; i++) {
			lo.vectors[i] = transform.mult(lo.vectors[i]);
		}

		for (int i = 0; i < lo.faces.length; i++) {
			Face face = lo.faces[i];
			
			triangles[i] = new Triangle(lo.vectors[face.vectorIndex[0]-1], lo.vectors[face.vectorIndex[1]-1], lo.vectors[face.vectorIndex[2]-1], null);
		}
		
		Vector3 sphereCenter = new Vector3(0, 0, 0);
		for (int i = 0; i < lo.vectors.length; i++) {
			sphereCenter = sphereCenter.add(lo.vectors[i]);
		}
		sphereCenter.multEqual(1.0 / lo.vectors.length);
		
		double radius2 = 0;
		for (int i = 0; i < lo.vectors.length; i++) {
			Vector3 toCenter = lo.vectors[i].minus(sphereCenter);
			if (toCenter.sqrtMag() > radius2) radius2 = toCenter.sqrtMag();
		}
		
		sphere = new Sphere(sphereCenter, Math.sqrt(radius2), null);
		
		System.out.println("Done!");
	}

	public Hit collides(Ray ray) {
		Ray rayClone = new Ray(ray.pos.minus(pos), ray.dir);
		
		if (sphere.collides(rayClone) == null) return null;

		Hit closest = null;
		for (int i = 0; i < triangles.length; i++) {
			Hit hit = triangles[i].collides(rayClone);
			if (hit == null) continue;
			
			if (closest == null || hit.dist < closest.dist) {
				closest = hit;
			}
			
		}
		
		if (closest != null) closest.mat = material;
		
		return closest;
	}
}

