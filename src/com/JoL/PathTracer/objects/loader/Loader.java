package com.JoL.PathTracer.objects.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.JoL.PathTracer.Vector3;
import com.JoL.PathTracer.objects.loader.LoaderObject3D.Face;

public class Loader {
	
	public static LoaderObject3D load(String file) {
		Scanner scan;
		
		try {
			scan = new Scanner(Loader.class.getResource("/obj/"+file).openStream());
		} catch (Exception e) {
			System.out.println("Couldn't load /obj/" + file);
			throw new RuntimeException(e);
		}
		
		List<Face> faces = new ArrayList<Face>();
		List<Vector3> vectors = new ArrayList<Vector3>();
		List<Vector3> normals = new ArrayList<Vector3>();
		List<Vector3> textures = new ArrayList<Vector3>();
		
		
		System.out.println("Loading " + file + "...");
		while (scan.hasNextLine()) {
			String line = scan.nextLine().toLowerCase();
			
			if (line.isEmpty()) continue;
			
			switch(line.split(" ")[0]) {
			case "#":
				break;
			case "f":
				line = line.substring(2);
				String[] indices = line.split(" ");
				
				if (!line.contains("/")) {
					faces.add(new LoaderObject3D.Face(new int[] {Integer.parseInt(indices[0]),
																 Integer.parseInt(indices[1]),
																 Integer.parseInt(indices[2])}));
				} else {
					faces.add(new LoaderObject3D.Face(new int[] {Integer.parseInt(indices[0].split("/")[0]),
																 Integer.parseInt(indices[1].split("/")[0]),
																 Integer.parseInt(indices[2].split("/")[0])},
							
													  new int[] {Integer.parseInt(indices[0].split("/")[1]),
															  	 Integer.parseInt(indices[1].split("/")[1]),
															  	 Integer.parseInt(indices[2].split("/")[1])},
														
													  new int[] {Integer.parseInt(indices[0].split("/")[2]),
															  	 Integer.parseInt(indices[1].split("/")[2]),
															  	 Integer.parseInt(indices[2].split("/")[2])}));
				}
				
				break;
				
			case "v":
				line = line.substring(2);
				String[] coords = line.split(" ");
				vectors.add(new Vector3(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
				break;
				
			case "vn":
				line = line.substring(3);
				String[] norms = line.split(" ");
				normals.add(new Vector3(Double.parseDouble(norms[0]), Double.parseDouble(norms[1]), Double.parseDouble(norms[2])));
				break;
				
			case "vt":
				line = line.substring(3);
				String[] tex = line.split(" ");
				textures.add(new Vector3(Double.parseDouble(tex[0]), Double.parseDouble(tex[1]), 0));
				break;
			}
			
		}
		System.out.println("Done!");
		
		scan.close();
		
		System.out.println("Setting up triangles...");
		Vector3[] vector = new Vector3[vectors.size()];
		Vector3[] normal = new Vector3[normals.size()];
		Vector3[] texture = new Vector3[textures.size()];
		Face[] face = new Face[faces.size()];
		return new LoaderObject3D(vectors.toArray(vector), textures.toArray(texture), normals.toArray(normal), faces.toArray(face));
	}
}
