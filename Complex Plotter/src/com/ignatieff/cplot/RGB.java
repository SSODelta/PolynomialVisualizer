package com.ignatieff.cplot;

public class RGB {

	public static RGB UNDEFINED = new RGB(1024,1024,1024);
	
	double red, green, blue;
	
	public RGB(double intensity){
		this(intensity,intensity,intensity);
	}
	
	public RGB(){
		this(0.0);
	}
	
	public RGB(int rgb){
		this(	rgb & 255,
				(rgb >> 8) & 255,
				(rgb >> 16) & 255);
	}
	
	public RGB(int r, int g, int b){
		this(	((double)r)/255.0,
				((double)g)/255.0,
				((double)b)/255.0);
	}
	
	public RGB(double r, double g, double b){
		red = r;
		green = g;
		blue = b;
	}
	
	public double getMax(){
		return Math.max(Math.max(red, green), blue);
	}
	
	public double getMin(){
		return Math.min(Math.min(red, green), blue);
	}
	
	public LMS getLMS(){
		LMS lms = new LMS();
		lms.L = 17.8824 * red + 43.5161 * green + 4.1193 * blue;
		lms.M = 3.45570 * red + 27.1554 * green + 3.8671 * blue;
		lms.S = 0.02996 * red + 0.18431 * green + 1.4670 * blue;
		return lms;
	}
	
	public double getChroma(){
		double M = getMax();
		double m = getMin();
		return M - m;
	}
	
	public double getHue(){
		double M = getMax();
		double C = getChroma();
		double H = 0;
		if(C==0)return 0.0;
		if(M==red){H = ((green-blue)/C)%6;};
		if(M==blue){H = ((blue-red)/C)+2;};
		if(M==green){H = ((red-green)/C)+4;};
		return 60 * H;
	}
	
	public boolean isUndefined(){
		if(red > 1.0 && blue > 1.0 && green > 1.0)return true;
		return false;
	}
	
	public int getRGB(){
		int r = (int)Math.floor(red*255);
		int g = (int)Math.floor(green*255);
		int b = (int)Math.floor(blue*255);
		
		return (r << 16) | (g << 8) | (b << 0);
	}
	
	public double distance(RGB b){
		if(isUndefined())return 0.0;
		if(b.isUndefined())return 0.0;
		
		HSV h1 = getHSV();
		
		HSV h2 = b.getHSV();
		
		double d = h1.distance(h2);
		
		return d;
	}
	
	public double getValue(){
		return getMax();
	}
	
	public RGB getProtanopia(){
		LMS lms = getLMS();
		return lms.getProtanopia().getRGB();
	}
	
	public double getSaturation(){
		return getChroma() / getValue();
	}
	
	public HSV getHSV(){
		return new HSV( getHue(),
						getSaturation(),
						getValue());
	}
	
	@Override
	public String toString(){
		return "[" + red + ", " + green + ", " + blue + "]";
	}
}
