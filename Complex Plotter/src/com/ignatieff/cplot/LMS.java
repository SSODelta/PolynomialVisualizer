package com.ignatieff.cplot;

public class LMS {
	public double L, M, S;
	
	public LMS(double L, double M, double S){
		this.L = L;
		this.M = M;
		this.S = S;
	}
	
	public LMS(){
		L = 0;
		M = 0;
		S = 0;
	}
	
	public LMS(RGB rgb){
		LMS lms = rgb.getLMS();
		L = lms.L;
		M = lms.M;
		S = lms.S;
	}
	
	public LMS getProtanopia(){
		LMS lms = new LMS();
		lms.L = 2.02344 * M - 2.5281*S;
		lms.M = M;
		lms.S = S;
		return lms;
	}
	
	public RGB getRGB(){
		RGB rgb = new RGB();
		rgb.red = 0.0809 * L - 0.1305 * M + 0.1167 * S;
		rgb.green = -0.0102 * L + 0.0540 * M - 0.1136 * S;
		rgb.blue = -0.0003 * L - 0.0041 * M + 0.6935 * S;
		return rgb;
	}
}
