package com.JoL.PathTracer;

public class Matrix4x4 {
	public double[] m;
	
	public Matrix4x4() {
		m = new double[] {1, 0, 0, 0,
						  0, 1, 0, 0,
						  0, 0, 1, 0,
						  0, 0, 0, 1};
	}
	
	public Matrix4x4(Matrix4x4 mat) {
		for (int i = 0; i < m.length; i++) {
			m[i] = mat.m[i];
		}
	}
	
	public Vector3 mult(Vector3 v) {
		return new Vector3(m[0+0*4] * v.x + m[1+0*4] * v.y + m[2+0*4] * v.z,
						   m[0+1*4] * v.x + m[1+1*4] * v.y + m[2+1*4] * v.z,
						   m[0+2*4] * v.x + m[1+2*4] * v.y + m[2+2*4] * v.z);
	}
	
	public Matrix4x4 mult(Matrix4x4 mat) {
		Matrix4x4 newMat = new Matrix4x4();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int i = 0; i < 4; i++) {
					newMat.m[x+y*4] += m[i+y*4]*mat.m[x+i*4];
				}
			}
		}
		
		return newMat;
	}
	
	public static Matrix4x4 generateRotationMatrix(double xTheta, double yTheta, double zTheta) {
		Matrix4x4 rx = new Matrix4x4();
		double xCos = Math.cos(xTheta);
		double xSin = Math.cos(xTheta);
		rx.m[1+1*4] = xCos;
		rx.m[2+1*4] = -xSin;
		rx.m[1+2*4] = xSin;
		rx.m[2+2*4] = xCos;
		
		Matrix4x4 ry = new Matrix4x4();
		double yCos = Math.cos(yTheta);
		double ySin = Math.cos(yTheta);
		ry.m[0+0*4] = yCos;
		ry.m[2+0*4] = ySin;
		ry.m[0+2*4] = -ySin;
		ry.m[2+2*4] = yCos;
		
		Matrix4x4 rz = new Matrix4x4();
		double zCos = Math.cos(zTheta);
		double zSin = Math.cos(zTheta);
		rz.m[0+0*4] = zCos;
		rz.m[1+0*4] = -zSin;
		rz.m[0+1*4] = zSin;
		rz.m[1+1*4] = zCos;

		return new Matrix4x4(rx).mult(ry).mult(rz);
	}
}
