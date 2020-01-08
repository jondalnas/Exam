package com.JoL.PathTracer.unitTest;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Ray;
import com.JoL.PathTracer.colliders.Sphere;

class SphereTest {

	@Test
	public void testCollidesXZ() {
		Geometry s = new Sphere(new Vector3(0,0,2), 1);
		//Tests x,z plane collision to see if the rays hit the circle between 60 and 120 degrees and misses outside that span.
		for (int deg = 0; deg < 180; deg++) {
			Ray r = new Ray(new Vector3(0,0,0), new Vector3(Math.cos(Math.toRadians(deg)),0,Math.sin(Math.toRadians(deg))));
			
			if (deg > 60 && deg <= 120)
				assertTrue(deg + "Works", s.collides(r) != null);
			else
				assertFalse(deg + "Works", s.collides(r) != null);
		}
	}
	
	@Test
	public void testCollidesYZ() {
		Geometry s = new Sphere(new Vector3(0, 0, 2), 1);
		
		for (int deg = 0; deg < 180; deg++) {
			Ray r = new Ray(new Vector3(0, 0, 0), new Vector3(0, Math.cos(Math.toRadians(deg)), Math.sin(Math.toRadians(deg))));
			
			if (deg > 60 && deg <= 120)
				assertTrue(deg + " Works", s.collides(r) != null);
			else
				assertFalse(deg + " Works", s.collides(r) != null);
		}
	}

}
