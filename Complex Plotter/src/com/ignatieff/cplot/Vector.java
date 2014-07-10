package com.ignatieff.cplot;

public class Vector {
	
	private double x, y;
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector(Complex a, Complex b){
		this(a.re - b.re, a.im - b.im);
	}
	
	public double getAngle(){
		return Math.atan2(y, x);
	}
	
	public double getLength(){
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
	}
	
	@Override
	public String toString(){
		return "<" + x + ", " + y + ">";
	}
}
