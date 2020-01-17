package com.JoL.PathTracer.objects;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Hit;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Triangle;

public class Object3D extends Geometry {
	
	public static class Face {
		public final int[] vectorIndex;
		
		public Face(int[] vectorIndex) {
			this.vectorIndex = vectorIndex;
			
		}
	}
	
	public final Vector3[] vectors;
	public final Face[] faces;
	
	public Object3D(Vector3[] vectors, Face[] faces) {
		super(new Vector3(0, 0, 0));
		this.faces = faces;
		this.vectors = vectors;
	}

	public Hit collides(Ray ray) {
		ray.pos = ray.pos.minus(pos);
		
		Hit closest = null;
		
		for (int i = 0; i < faces.length; i++) {
			Face face = faces[i];
			
			Triangle triangle = new Triangle(vectors[face.vectorIndex[0]-1], vectors[face.vectorIndex[1]-1], vectors[face.vectorIndex[2]-1]);
			
			Hit hit = triangle.collides(ray);
			if (hit == null) continue;
			
			if (closest == null || hit.dist < closest.dist) {
				closest = hit;
			}
			
		}
		
		return closest;
	}
}

