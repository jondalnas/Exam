package com.JoL.PathTracer.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Plane;
import com.JoL.PathTracer.colliders.Ray;

class PlaneTest {

	@Test
	void testRangeCollides() {
		Geometry p = new Plane(new Vector3(0,0,0), new Vector3(0,1,0));
		for (int deg = -90; deg < 90; deg++) {
			Ray r = new Ray(new Vector3(0, 1, 0), new Vector3(0, Math.sin(Math.toRadians(deg)), Math.cos(Math.toRadians(deg))));
			
			if (deg < 0)
				assertTrue(deg + " Works", p.collides(r) != null);
			else
				assertFalse(deg + " Works", p.collides(r) != null);
		}
	}

	@Test
	void testCollides() {
		Geometry p = new Plane(new Vector3(0,0,0), new Vector3(0,1,0));
		Ray r = new Ray(new Vector3(0, -1, 0), new Vector3(0, 1, 0));
		
		assertTrue(p.collides(r) != null);
		
	}

}
