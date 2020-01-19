package com.JoL.PathTracer.objects.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.objects.Object3D;
import com.JoL.PathTracer.objects.Object3D.Face;

public class Loader {
	
	public static Object3D load(String file) {
		Scanner scan;
		
		try {
			scan = new Scanner(Loader.class.getResource("/obj/"+file).openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		List<Face> faces = new ArrayList<Face>();
		List<Vector3> vectors = new ArrayList<Vector3>();
		
		
		System.out.println("Loading " + file + "...");
		while (scan.hasNextLine()) {
			String line = scan.nextLine().toLowerCase();
			
			if (line.isEmpty()) continue;
			
			switch(line.charAt(0)) {
			case '#':
				break;
			case 'f':
				line = line.substring(2);
				String[] indices = line.split(" ");
				
				faces.add(new Object3D.Face(new int[] {Integer.parseInt(indices[0]),
													   Integer.parseInt(indices[1]),
													   Integer.parseInt(indices[2])}));
				break;
			case 'v':
				line = line.substring(2);
				String[] coords = line.split(" ");
				vectors.add(new Vector3(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
				break;
			}
			
		}
		System.out.println("Done!");
		
		scan.close();
		
		System.out.println("Setting up triangles...");
		Vector3[] vector = new Vector3[vectors.size()];
		Face[] face = new Face[faces.size()];
		return new Object3D(vectors.toArray(vector), faces.toArray(face));
	}
}
