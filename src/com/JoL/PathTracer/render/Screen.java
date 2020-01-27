package com.JoL.PathTracer.render;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.JoL.PathTracer.Vector3;

public class Screen extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	private int[] pixelArray;
	private Pixel[] pixels;
	private static int NUM_CPUS = Runtime.getRuntime().availableProcessors();
	public int sampleCount = 0;
	public static int currentSample = 0;
	private Runnable thread;
	
	private static int loadImageIndex = -1;
	
	private static final short IMAGE_INTERVAL = 100;
	
	public Screen(int width, int height) {
		setSize(width, height);
		
		//Initial sample and all lookup tables
		new Sample(width, height);
		
		thread = new Runnable() {
			@Override
			public void run() {
				Sample sample = new Sample(width, height);
				
				while(true) {
					sample.render();

					synchronized (pixels) {
						int sc = sampleCount++;
						
						for (int i = 0; i < pixels.length; i++) {
							pixels[i].addColor(sample.screen[i]);
						}
						
						render(sc);
					}
				}
			}
		};
		
		loadImageData();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixelArray = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		if (pixels == null) {
			pixels = new Pixel[pixelArray.length];
			
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = new Pixel(0);
			}
		}
	}
	
	public void render(int sampleCount) {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		for (int i = 0; i < pixels.length; i++) {
			pixelArray[i] = pixels[i].scaleColor(1.0/sampleCount).getIntColor();
		}
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		//TODO: Save images at specific intervals
		if (sampleCount % IMAGE_INTERVAL == 0) {
			try {
				ImageIO.write(img, "png", new File("res/out/Image @" + sampleCount + " sample count.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			saveImageData();
			
			System.out.println("Rendered " + sampleCount + " frames");
		}
		
		g.dispose();
		bs.show();
	}
	
	public void loadImage() {
		if (loadImageIndex == -1) return;
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File("res/out/Image @" + loadImageIndex + " sample count.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				pixels[x + y * img.getWidth()].setColor(img.getRGB(x, y));
				
				pixels[x + y * img.getWidth()] = pixels[x + y * img.getWidth()].scaleColor(loadImageIndex);
			}
		}
		
		sampleCount = loadImageIndex+1;
		currentSample = loadImageIndex+1;
	}
	
	public void saveImageData() {
		File image = new File("res/out/Image.tmp");
		
		if (image.exists()) image.delete();
		
		OutputStream fos = null;
		DataOutputStream dos = null;
		try {
			fos = new FileOutputStream(image);
			dos = new DataOutputStream(fos);

			dos.writeInt(sampleCount);
			dos.writeInt(getWidth());
			dos.writeInt(getHeight());
			
			for (int i = 0; i < pixels.length; i++) {
				dos.writeDouble(pixels[i].getColorVector().x);
				dos.writeDouble(pixels[i].getColorVector().y);
				dos.writeDouble(pixels[i].getColorVector().z);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				dos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		File oldImage = new File("res/out/Image.raw");
		if (oldImage.exists()) oldImage.delete();
		
		image.renameTo(oldImage);
	}
	
	public void loadImageData() {
		File image = new File("res/out/Image.raw");
		
		if (!image.exists()) return;
		
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream(new File("res/out/Image.raw"));
			dis = new DataInputStream(fis);

			byte[] sampleCountData = new byte[Integer.BYTES];
			byte[] widthData = new byte[Integer.BYTES];
			byte[] heightData = new byte[Integer.BYTES];

			dis.read(sampleCountData);
			dis.read(widthData);
			dis.read(heightData);
			
			sampleCount = ByteBuffer.wrap(sampleCountData).getInt();
			currentSample = sampleCount;
			int w = ByteBuffer.wrap(widthData).getInt();
			int h = ByteBuffer.wrap(heightData).getInt();
			
			setSize(w, h);
			
			pixels = new Pixel[w * h];

			byte[] redData = new byte[Double.BYTES];
			byte[] greenData = new byte[Double.BYTES];
			byte[] blueData = new byte[Double.BYTES];
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					dis.read(redData);
					dis.read(greenData);
					dis.read(blueData);

					double r = ByteBuffer.wrap(redData).getDouble();
					double g = ByteBuffer.wrap(greenData).getDouble();
					double b = ByteBuffer.wrap(blueData).getDouble();
					
					pixels[x + y * w] = new Pixel(new Vector3(r, g, b));
				}
			}
			
			fis.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		for (int i = 0; i < NUM_CPUS; i++) {
			new Thread(thread).start();
		}
	}
}
