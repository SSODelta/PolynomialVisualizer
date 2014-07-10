package com.ignatieff.cplot;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Fractal {
	
	private Polynomial p;
	private int width, height;
	private double res, dr;
	
	public Fractal(int width, int height, double resolution, Polynomial p){
		this.width  = width;
		this.height = height;
		this.res = resolution;
		this.dr = resolution * 2;
		this.p = p;
	}
	
	public static void generateAndSaveFractal(String path, int width, int height, double resolution, int minDegree, int maxDegree){
		BufferedImage img = generateFractal(width, height, resolution, minDegree, maxDegree);
		try {
			ImageIO.write(img, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage writeToImage(BufferedImage image, String textToWrite){
		String text = textToWrite.replace("*", "·");
		Graphics2D g = image.createGraphics();
		g.setFont(new Font("Sans-Serif", Font.BOLD, 17));
		FontMetrics fm = g.getFontMetrics(); 
		int w = fm.stringWidth(text) + 10;
		int h = fm.getHeight() + 3;
		g.setColor(Color.WHITE);
		g.fillRect(0, image.getHeight()-h, w, h);
		g.setColor(Color.BLACK);
		g.drawString(text, 5, image.getHeight() - 6);
		g.dispose();
		return image;
	}
	
	public static void generateAndSaveFractal(String path, int degree){
		generateAndSaveFractal(path, 1280, 720, 14, degree);
	}
	
	public static void generateAndSaveFractal(String path, int width, int height, double resolution, int degree){
		BufferedImage img = generateFractal(width, height, resolution, degree);
		try {
			ImageIO.write(img, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage generateFractal(int width, int height, double resolution, int minDegree, int maxDegree){
		return generateFractal(width, height, resolution, (int)(minDegree + Math.floor(Math.random()*(maxDegree-minDegree))));
	}
	
	public static BufferedImage generateFractal(int width, int height, double resolution, int degree){
		Polynomial p = Polynomial.random(degree);
		Fractal f = new Fractal(width, height, resolution, p);
		BufferedImage img =  f.renderFractal(false);
		return writeToImage(img, p.toString());
	}
	
	public BufferedImage renderFractal(boolean debug){
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		double MAX = Math.hypot(width, height);
		
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){

				Complex z = getComplex(x,y);
				Complex result = p.getValue(z);
				
				Vector v = new Vector(z, result);

				double d = v.getLength() / MAX;
				
				HSV hsv = new HSV(v.getAngle(), Math.cos(d), Math.sin(d));
				
				RGB rgb = hsv.getRGB();

				img.setRGB(x, y, rgb.getRGB());
				
				if(!debug)continue;
				
				System.out.println("Report for point ["+x+", "+y+"]:");
				System.out.println("	vector = " + v.toString());
				System.out.println("	angle  = " + v.getAngle());
				System.out.println("	d = " + d);
				System.out.println("	HSV: " + hsv.toString());
				System.out.println("	RGB: " + rgb.toString()+ " ("+rgb.getRGB()+")\n");
				
			}
		}
		
		return img;
	}
	
	private Complex getComplex(int x, int y){
		return new Complex(getValueFromPixel(x, width, 1),
				getValueFromPixel(y, height, (double)width / (double) height)).update();
	}
	
	private double getValueFromPixel(int z, int size, double mul){
		return -(res/mul) + ((double)(dr/mul))*(((double)z) / size);
	}
}
