package com.mygdx.SkeetPro.gamestate;

import java.util.*;

import com.badlogic.gdx.Gdx;  
import com.mygdx.SkeetPro.elements.Plate;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.elements.Scope;

public class GameState {
	private ArrayList<Plate> plates;
	private Player player1,player2;
	private Scope scope;
	private int failPlates; 
	private int bestscore;
	private int bullets;
	private float reload_time;
	private boolean is_reloading;
	     
	public GameState(Player player1){
		this.player1= player1;
		this.player2 = null;
		plates = new ArrayList<Plate>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
		createPlate(0);
	}

	public void createPlate(float delta){
		/*time+=delta;

		if(time > 0.01f){
			time = 0;
			return; 
		}*/

		Random rand = new Random();
		int initialPoint= 0;
		int finalPoint=0;

		int width =(int) (Gdx.graphics.getWidth()*0.13) ;
		int height = (int) (Gdx.graphics.getHeight()*0.23);
		
		do{
			initialPoint = rand.nextInt(Gdx.graphics.getWidth()+1-width);
			finalPoint = rand.nextInt(Gdx.graphics.getWidth()+1-width);
		}while(initialPoint == finalPoint );

		
		double speed = Math.abs((finalPoint-initialPoint)/(float)(rand.nextInt(256)+ 128) /*(double)stage.getStageSpeed()*/);
		plates.add(new Plate(width,height,initialPoint,finalPoint,speed));
	}

	public GameState(Player player1,Player player2){
		this.player1 = player1;
		this.player2 = player2;
		plates = new ArrayList<Plate>();
		
	}
	
	public void movePlates(float delta){
		for(int pos = 0; pos < plates.size();pos++){
			movePlate(pos,delta);
		}
	}

	private void movePlate(int pos,float delta) {
		Plate plate = plates.get(pos);
		plate.setX((double) (plate.getX()+plate.getSpeed()*plate.getDirection()));
		plate.setY(function(plate));
		plate.setWidth(plate.getWidth()*(1-delta*0.35f));
		plate.setHeight(plate.getHeight()*(1-delta*0.35f));
		/*System.out.println("Plate: -------------------------------------");
		System.out.println("X: "+plate.getX());
		System.out.println("Y: "+plate.getY());
		System.out.println("initialPoint: "+ plate.getInitialPoint());
		System.out.println("finallPoint: "+ plate.getFinalPoint());
		System.out.println("speed: "+ plate.getSpeed());
		System.out.println("K: "+ plate.getK());*/
	}
	
	private int checkPlatesCollisionWithScope(){
		int numOfBrokenPlates=0;
		for(int i = 0; i < plates.size();i++){
			Plate p = plates.get(i);
			if(scope.getX() >= p.getX() && scope.getX() <=p.getX()+p.getWidth() && scope.getY()>= p.getY() && scope.getY() <= p.getY()+p.getHeight()){
					numOfBrokenPlates++;
					p.setStatus('B');
				}	
		}
		return numOfBrokenPlates;
	}
	
	private void removeBrokenPlates(){
		ArrayList<Plate> novo = new ArrayList<Plate>();
		for(int i = 0; i < plates.size();i++){
			Plate p = plates.get(i);
			if(p.getStatus() == 'N')
				novo.add(p);
		}
		plates = novo;
	}
	
	public int updatePlates(){ // se acertou ou não em pratos
		updateFailedPlates();
		int brokenPlates = checkPlatesCollisionWithScope();
		if(brokenPlates == 0)
			return 0;
		return brokenPlates;
	}
	
	private float function(Plate plate){
		return (float) (plate.getK()*(plate.getX()-plate.getFinalPoint())*(plate.getX()-plate.getInitialPoint()));		
	}
	
	public ArrayList<Plate> getPlates(){
		return plates;
	}

	public void reset() {
		failPlates=0;
		
	}
	
	public void setScope(double x, double y){
		scope.setX(x);
		scope.setY(y);
	}
	
	private void updateFailedPlates(){
		for(int i = 0; i < plates.size();i++){
			Plate p = plates.get(i);
			if(p.getY() <0){
				p.setStatus('B');
				failPlates++;
			}
		}
		removeBrokenPlates();
	}
	
	public int getFailPlates(){
		return failPlates;
	}
	
	public void resetScope(){
		scope.reset();
	}
	
	public int getBestscore(){
		return bestscore;
	}
	
	public void setBestScore(int bestscore){
		this.bestscore=bestscore;
	}

	public int getBullets() {
		return bullets;
	}
	
	public void setBullets(int bullets){
		this.bullets= bullets;
	}
	
	public float reloadTime(){
		if (is_reloading)
			return reload_time;
		else
			return -1;
	}
	
	public float incReload(float ammount){
		reload_time+= ammount;
		return reload_time;
	}
	
	public void setReload(boolean bol){
		is_reloading = bol;
	}
	
	public void resetReload(){
		reload_time = 0;
	}

	public void manageReload(float delta){
		if(is_reloading){
			reload_time += delta;
		}
		if(reload_time > 1){
			is_reloading = false;
			resetReload();
			setBullets(4);
		}
		if(getBullets()==0)
			is_reloading = true;
	}

	public boolean touchDownShot(){
		if(is_reloading){
			return false;
		}
		
		
		if(getBullets()==0){
			resetScope();
			setReload(true);	
		}
		else{
			setBullets(getBullets()-1);
			return true;
		}
		
		return false;
	}

	public void resetGameState(Player p1){
		this.player1= p1;
		this.player2 = null;
		plates = new ArrayList<Plate>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
		createPlate(0);
	}
}
