package com.ignatieff.cplot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Analyzer {

	public static void analyzeAndSaveImage(String path, String result){
		BufferedImage img;
		try {
			img = analyzeImage(path);
			ImageIO.write(img, "png", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage analyzeImage(String path) throws IOException{
		return analyzeImage(ImageIO.read(new File(path)));
	}
	
	public static BufferedImage analyzeImage(BufferedImage img){
		
		int w = img.getWidth();
		int h = img.getHeight() / 2;
		
		BufferedImage res = new BufferedImage(w, h, img.getType());
		
		for(int x=0; x<w; x++){
			for(int y=0; y<h; y++){
				
				int rgb = img.getRGB(x, y) ^ img.getRGB(x, h - y);
				
				res.setRGB(x, y, rgb);
			}
		}
		
		return res;
	}
}
