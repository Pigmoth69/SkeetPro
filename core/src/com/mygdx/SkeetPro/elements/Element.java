package com.mygdx.SkeetPro.elements;
//sou merda!
public class Element {
	private double x;
	private double y;
	private char status;
	
	
	public Element(int x, int y,char status) {
		this.x=x;
		this.y=y;
		this.status=status;
	}

	public void setX(double d){
		this.x = d;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setStatus(char status){
		this.status = status;
	}
	
	public char getStatus(){
		return status;
	}
	

}
 