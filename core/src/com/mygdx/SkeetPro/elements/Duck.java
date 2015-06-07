package com.mygdx.SkeetPro.elements;

import com.badlogic.gdx.Gdx;

public class Duck extends Element{
	
	float width,height; // altura do sprite
	int initialPoint;
	double speed;
	int direction; // 0 ou 1 : se 0 vai para a -> se 1 <-
	

	public Duck(float width,float height,int initialPoint,int direction,double speed){
		super(-(int)width,initialPoint,'N');
		
		this.width=width; 
		this.height = height;
		this.initialPoint=initialPoint;
		
		if(direction ==1){
			this.direction=1;
			this.speed = speed;
		}else{
			this.direction=-1;
			this.setX(Gdx.graphics.getWidth());
			this.speed = -speed;
		}
		
		
	}
	

	public int getDirection(){
		return direction;
	}
	public void setWidth(float width){
		this.width=width;
	}
	public void setHeight(float height){
		this.height=height;
	}
	public float getWidth(){
		return width;
	}
	public float getHeight(){
		return height;
	}
	public void setInitialPoint(int initialPoint){
		this.initialPoint=initialPoint;
	}	
	
	public double getSpeed(){
		return speed;
	}


	public void move() {
		this.setX(this.getX()+speed);		
	}
}
