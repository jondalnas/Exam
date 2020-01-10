package com.JoL.PathTracer.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.colliders.Disk;
import com.JoL.PathTracer.colliders.Geometry;
import com.JoL.PathTracer.colliders.Ray;

class DiskTest {

	@Test
	void testCollides() {
		for (int pos = -200; pos <= 200; pos++) {
			Ray ray = new Ray(new Vector3(pos/100.0, 0, 0), new Vector3(0, 1, 0));
			Geometry d = new Disk(new Vector3(0, 1, 0), new Vector3(0, 1, 0), 1);
			
			if (pos < 100 && pos > -100) 
				assertTrue(d.collides(ray) != null);
			else 
				assertFalse(d.collides(ray) != null);
			
		}
	}

}
