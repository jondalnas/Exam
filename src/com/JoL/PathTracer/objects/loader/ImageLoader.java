package com.JoL.PathTracer.objects.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum ImageLoader {
	cage("Cage.png"),
	tile("Floor.jpg"),
	ornamentRoughness("Ornament Roughness map.png"),
	tileRoughness("Floor Roughness map.png");
	
	public final BufferedImage image;
	
	private ImageLoader(String file) {
		image = loadImage(file);
	}
	
	public static BufferedImage loadImage(String file) {
		BufferedImage bi;
		
		try {
			bi = ImageIO.read(ImageLoader.class.getResource("/tex/" + file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return bi;
	}
}