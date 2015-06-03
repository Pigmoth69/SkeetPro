package com.mygdx.SkeetPro.elements;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class Plate extends Element{
	
	float width,height; // altura do sprite
	int initialPoint,finalPoint;
	double speed;
	int direction; // 0 ou 1 : se 0 vai para a -> se 1 <-
	double K;
	

	public Plate(float width,float height,int initialPoint,int finalPoint,double speed){
		super(initialPoint,0,'N');
		this.width=width; 
		this.height = height;
		this.initialPoint=initialPoint;
		this.finalPoint = finalPoint;
		if(finalPoint < initialPoint){
			this.direction=-1;
		}else{
			this.direction=1;
		}
		
		this.speed = speed;
		this.setK();
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
	public void setFinalPoint(int finalPoint){
		this.finalPoint=finalPoint;
	}
	public int getInitialPoint(){
		return initialPoint;
	}
	public int getFinalPoint(){
		return finalPoint;
	}
	
	private void setK(){
		int MAX = Gdx.graphics.getHeight()-(int)this.getHeight();
		K= (-4*MAX)/(Math.pow(getFinalPoint(), 2)-2*getFinalPoint()*getInitialPoint()+Math.pow(getInitialPoint(), 2));
	}
	
	public double getK(){
		return K;
	}
	
	public double getSpeed(){
		return speed;
	}

	
	
	

}
