package com.JoL.PathTracer.objects;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;
import com.JoL.PathTracer.colliders.Triangle;
import com.JoL.PathTracer.render.materials.Material;

public class Object3D extends Geometry {
	
	public static class Face {
		public final int[] vectorIndex;
		
		public Face(int[] vectorIndex) {
			this.vectorIndex = vectorIndex;
			
		}
	}
	
	public final Vector3[] vectors;
	public final Face[] faces;
	public final Triangle[] triangles;
	public Material material;
	private Sphere sphere;
	
	public Object3D(Vector3[] vectors, Face[] faces) {
		super(new Vector3(0, 0, 0));
		this.faces = faces;
		this.vectors = vectors;
		triangles = new Triangle[faces.length];
		
		for (int i = 0; i < faces.length; i++) {
			Face face = faces[i];
			
			triangles[i] = new Triangle(vectors[face.vectorIndex[0]-1], vectors[face.vectorIndex[1]-1], vectors[face.vectorIndex[2]-1], null);
		}
		
		Vector3 sphereCenter = new Vector3(0, 0, 0);
		for (int i = 0; i < vectors.length; i++) {
			sphereCenter = sphereCenter.add(vectors[i]);
		}
		sphereCenter.mult(1.0 / vectors.length);
		
		double radius = 0;
		for (int i = 0; i < vectors.length; i++) {
			Vector3 toCenter = vectors[i].minus(sphereCenter);
			if (toCenter.mag() > radius) radius = toCenter.mag();
		}
		
		sphere = new Sphere(sphereCenter, radius, null);
		
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

