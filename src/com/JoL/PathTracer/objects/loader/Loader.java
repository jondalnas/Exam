package com.JoL.PathTracer.objects.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.objects.Object3D;
import com.JoL.PathTracer.objects.Object3D.Face;

public class Loader {
	
	public Object3D load(String file) {
		Scanner scan;
		try {
			scan = new Scanner(Loader.class.getResource(file).openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<Face> faces = new ArrayList<Face>();
		List<Vector3> vectors = new ArrayList<Vector3>();
		
		while (scan.hasNextLine()) {
			String line = scan.next().toLowerCase();
			
			switch(line.charAt(0)) {
			case '#':
				break;
			case 'f':
				line = line.substring(2);
				String[] indices = line.split(" ");
				faces.add(new Object3D.Face(new int[] {Integer.parseInt(indices[0]), Integer.parseInt(indices[1], Integer.parseInt(indices[2]))}));
				break;
			case 'v':
				line = line.substring(2);
				String[] coords = line.split(" ");
				vectors.add(new Vector3(Integer.parseInt(coords[0]), Integer.parseInt(coords[	1]), Integer.parseInt(coords[2])));
				break;
			}
			
		}
		
		scan.close();
		
		return new Object3D((Vector3[]) vectors.toArray(), (Face[]) faces.toArray());
	}
}
