package com.JoL.PathTracer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.JoL.PathTracer.render.Screen;

public class Main implements Runnable {
	public static int WIDTH = 640, HEIGHT = 480;
	
	public Screen screen;
	
	public Main() {
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		
		Main main = new Main();
		main.screen = new Screen(WIDTH, HEIGHT);
		
		panel.add(main.screen);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		 
		frame.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setPreferredSize(frame.getSize());
		
		main.start();
		main.screen.start();
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {
		while(true) {
			//screen.render();
		}
	}
}
