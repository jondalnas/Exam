package com.JoL.PathTracer.objects.loader;

import com.JoL.PathTracer.Vector3;

public class LoaderObject3D {
	public static class Face {
		public final int[] vectorIndex;
		
		public Face(int[] vectorIndex) {
			this.vectorIndex = vectorIndex;
			
		}
	}
	
	public final Vector3[] vectors;
	public final Face[] faces;
	
	public LoaderObject3D(Vector3[] vectors, Face[] faces) {
		this.faces = faces;
		this.vectors = vectors;
	}
}
