package com.JoL.PathTracer.objects;

import com.JoL.PathTracer.Vector3;

public class Object3D {
	
	public static class Face {
		public final int[] vectorIndex;
		
		public Face(int[] vectorIndex) {
			this.vectorIndex = vectorIndex;
			
		}
	}
	
	public final Vector3[] vectors;
	public final Face[] faces;
	
	public Object3D(Vector3[] vectors, Face[] faces) {
		this.faces = faces;
		this.vectors = vectors;
	}
}

