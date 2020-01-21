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
		m = new double[4*4];
		
		for (int i = 0; i < m.length; i++) {
			m[i] = mat.m[i];
		}
	}
	
	public Vector3 mult(Vector3 v) {
		return new Vector3(m[0+0*4] * v.x + m[1+0*4] * v.y + m[2+0*4] * v.z + m[3+0*4],
						   m[0+1*4] * v.x + m[1+1*4] * v.y + m[2+1*4] * v.z + m[3+1*4],
						   m[0+2*4] * v.x + m[1+2*4] * v.y + m[2+2*4] * v.z + m[3+2*4]).multEqual(1.0/m[3+3*4]);
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
		double xSin = Math.sin(xTheta);
		rx.m[1+1*4] = xCos;
		rx.m[2+1*4] = -xSin;
		rx.m[1+2*4] = xSin;
		rx.m[2+2*4] = xCos;
		
		Matrix4x4 ry = new Matrix4x4();
		double yCos = Math.cos(yTheta);
		double ySin = Math.sin(yTheta);
		ry.m[0+0*4] = yCos;
		ry.m[2+0*4] = ySin;
		ry.m[0+2*4] = -ySin;
		ry.m[2+2*4] = yCos;
		
		Matrix4x4 rz = new Matrix4x4();
		double zCos = Math.cos(zTheta);
		double zSin = Math.sin(zTheta);
		rz.m[0+0*4] = zCos;
		rz.m[1+0*4] = -zSin;
		rz.m[0+1*4] = zSin;
		rz.m[1+1*4] = zCos;

		return new Matrix4x4(rx).mult(ry).mult(rz);
	}
	
	public static Matrix4x4 generateScaleMatrix(double xScale, double yScale, double zScale) {
		Matrix4x4 matrix = new Matrix4x4();
		matrix.m[0+0*4] = xScale;
		matrix.m[1+1*4] = yScale;
		matrix.m[2+2*4] = zScale;
		
		return matrix;
	}
	
	public static Matrix4x4 generateTranslationMatrix(double x, double y, double z) {
		Matrix4x4 matrix = new Matrix4x4();
		matrix.m[3+0*4] = x;
		matrix.m[3+1*4] = y;
		matrix.m[3+2*4] = z;
		
		return matrix;
	}
	
	public static Matrix4x4 generateTransformationMatrix(Vector3 translation, Vector3 scale, Vector3 rotation) {
		return generateScaleMatrix(scale.x, scale.y, scale.z).mult(
			   generateRotationMatrix(rotation.x, rotation.y, rotation.z)).mult(
			   generateTranslationMatrix(translation.x, translation.y, translation.z));
	}
}