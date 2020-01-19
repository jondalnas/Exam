package com.JoL.PathTracer.objects;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
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
	
	public Object3D(Vector3[] vectors, Face[] faces) {
		super(new Vector3(0, 0, 0));
		this.faces = faces;
		this.vectors = vectors;
		triangles = new Triangle[faces.length];
		
		for (int i = 0; i < faces.length; i++) {
			Face face = faces[i];
			
			triangles[i] = new Triangle(vectors[face.vectorIndex[0]-1], vectors[face.vectorIndex[1]-1], vectors[face.vectorIndex[2]-1], null);
		}
	}

	public Hit collides(Ray ray) {
		Ray rayClone = new Ray(ray.pos.minus(pos), ray.dir);
		
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

