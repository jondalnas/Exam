package com.JoL.PathTracer;

public class Camera {
	public Vector3 pos;
	public Vector3 rot;
	
	public Camera(Vector3 pos, Vector3 rot) {
		this.pos = pos;
		this.rot = rot;
	}
	
	public Matrix4x4 getRotation() {
		return Matrix4x4.generateRotationMatrix(rot.x, rot.y, rot.z);
	}
}
