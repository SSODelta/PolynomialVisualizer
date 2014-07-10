package com.ignatieff.cplot;

public class HSV {
	
	public double hue, saturation, value;
	
	public HSV(double h, double s, double v){
		hue = h;
		saturation = s;
		value = v;
	}
	
	public HSV(RGB rgb){
		HSV h = rgb.getHSV();
		hue = h.hue;
		value = h.value;
		saturation = h.saturation;
	}
	
	public HSV blend(int[] rgb, double intensity){
		HSV[] arr = new HSV[rgb.length];
		for(int i=0; i<rgb.length; i++){
			arr[i] = new HSV(new RGB(rgb[i]));
		}
		return blend(arr, intensity);
	}
	
	@Override
	public String toString(){
		return "[" + hue + ", " + saturation + ", " + value + "]";
	}
	
	public HSV blend(HSV[] others, double blend){
		
		double b = blend / others.length;
		
		double h=0, s=0, v=0;
		
		for(int i=0; i<others.length; i++){
			h+=others[i].hue;
			v+=others[i].value;
			s+=others[i].saturation;
		}
		
		return new HSV(	((h*b)+hue)/(1+blend),		
						((s*b)+saturation)/(1+blend),
						((v*b)+value)/(1+blend)		);
	}
	
	public double distance(HSV h){
		double dh = Math.abs(hue - h.hue)/360;
		double ds = Math.abs(saturation - h.saturation);
		double dv = Math.abs(value - h.value);
		return (dh+ds+dv)/3;
	}
	
	public RGB getRGB(){
		double H = hue/60;
		double C = value * saturation;
		double X = C * (1 - Math.abs(H%2 - 1));
		double m = value - C;
		
		double r1 = 0, g1 = 0, b1 = 0;
		
		if(H < 1){
			r1 = C;
			g1 = X;
		} else if(H < 2){
			r1 = X;
			g1 = C;
		} else if(H < 3){
			g1 = C;
			b1 = X;
		} else if(H < 4){
			g1 = X;
			b1 = C;
		} else if(H < 5){
			r1 = X;
			b1 = C;
		} else {
			r1 = C;
			b1 = X;
		}
	
		return new RGB(r1+m, g1+m, b1+m);
	}
}
