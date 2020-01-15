package com.JoL.PathTracer;

import java.util.Random;

public class MathTools {
	public static Vector3 generateHemisphereVector(Vector3 normal, Random rand) {
		Vector3 tangent = Math.abs(normal.x) == 1 ? new Vector3(0, 0, 1) : new Vector3(0, normal.z, -normal.y);
		Vector3 bitangent = Math.abs(normal.x) == 1 ? new Vector3(0, -1, 0) : new Vector3(-normal.y * normal.y - normal.z * normal.z, normal.x * normal.y, normal.x * normal.z).normalize();

		/*double r = Math.sqrt(rand.nextDouble());
		double theta = rand.nextDouble() * Math.PI * 2.0;

		double x = r * Math.cos(theta);
		double y = r * Math.sin(theta);
		double z = Math.sqrt(1.0 - (x * x + y * y));*/

		double z = rand.nextDouble();
		double theta = rand.nextDouble() * Math.PI * 2;

		double x = Math.sqrt(1-z*z)*Math.cos(theta);
		double y = Math.sqrt(1-z*z)*Math.sin(theta);
		
		return normal.mult(z).add(tangent.mult(x)).add(bitangent.mult(y));
	}
	
	public static double fresnel(Vector3 in, Vector3 normal, double refractiveIndexOfRay, double refractiveIndexOfObject) {
		double theta = in.dot(normal);
		
		if (theta > 0) {
			double ri = refractiveIndexOfRay;
			refractiveIndexOfRay = refractiveIndexOfObject;
			refractiveIndexOfObject = ri;
		}
		
		double sinTargetSqrt = (refractiveIndexOfRay * refractiveIndexOfRay) / (refractiveIndexOfObject * refractiveIndexOfObject) * (1 - theta * theta);

        if (sinTargetSqrt >= 1) {
            return 1;
        } else {
            double cosTarget = Math.sqrt(1 - sinTargetSqrt);
            theta = Math.abs(theta);
            double rs = ((refractiveIndexOfObject * theta) - (refractiveIndexOfRay * cosTarget)) / ((refractiveIndexOfObject * theta) + (refractiveIndexOfRay * cosTarget));
            double rp = ((refractiveIndexOfRay * theta) - (refractiveIndexOfObject * cosTarget)) / ((refractiveIndexOfRay * theta) + (refractiveIndexOfObject * cosTarget));

            return (rs * rs + rp * rp) / 2;
        }
	}
}
