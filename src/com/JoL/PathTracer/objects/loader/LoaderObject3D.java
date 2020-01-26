package com.JoL.PathTracer.objects.loader;

import com.JoL.PathTracer.Vector3;

public class LoaderObject3D {
	public static class Face {
		public final int[] vectorIndex;
		public final int[] normalIndex;
		public final int[] textureIndex;
		
		public Face(int[] vectorIndex) {
			this.vectorIndex = vectorIndex;
			normalIndex = null;
			textureIndex = null;
		}
		
		public Face(int[] vectorIndex, int[] textureIndex, int[] normalIndex) {
			this.vectorIndex = vectorIndex;
			this.normalIndex = normalIndex;
			this.textureIndex = textureIndex;
		}
	}

	public final Vector3[] vectors;
	public final Vector3[] normals;
	public final Vector3[] textures;
	public final Face[] faces;
	
	public LoaderObject3D(Vector3[] vectors, Vector3[] textures, Vector3[] normals, Face[] faces) {
		this.faces = faces;
		this.vectors = vectors;
		this.normals = normals;
		this.textures = textures;
	}
}
