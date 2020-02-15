package com.JoL.PathTracer.colliders;

import java.util.Stack;

import com.JoL.PathTracer.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public byte ittration = 0;
	
	public class RefractiveIndexGeometry {
		double refractiveIndex = 1;
		short geometry = -1;
		
		public RefractiveIndexGeometry() {
		}
		
		public RefractiveIndexGeometry(double refractiveIndex, short geometry) {
			this.refractiveIndex = refractiveIndex;
			this.geometry = geometry;
		}
		
		public double getRefractiveIndex() {
			return refractiveIndex;
		}

		public boolean isSameGeometry(short index) {
			return geometry == index;
		}
	}
	
	public Stack<RefractiveIndexGeometry> refractiveIndex = new Stack<RefractiveIndexGeometry>();
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public Ray(Ray ray) {
		pos = ray.pos;
		dir = ray.dir;
	}

	public RefractiveIndexGeometry getRefractiveIndex() {
		if (refractiveIndex.empty()) return new RefractiveIndexGeometry();
		
		return refractiveIndex.peek();
	}
}
