package com.mygdx.SkeetPro.gamestate;

import java.util.*;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;  
import com.mygdx.SkeetPro.elements.Duck;
import com.mygdx.SkeetPro.elements.Plate;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.elements.Scope;

public class GameState {
	private HashMap<Integer,Plate> plates;
	private ArrayList<Duck> ducks;
	private Player player1,player2;
	private Scope scope;
	private int failPlates; 
	private int bestscore;
	private int bullets;
	private float reload_time;
	private boolean is_reloading;
	private int plateID=0;
	private boolean shootDuck = false;
	     
	public GameState(Player player1){
		this.player1= player1;
		this.player2 = null;
		plates = new HashMap<Integer,Plate>();
		ducks = new ArrayList<Duck>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
		//createPlate(0);
	}
/*public Duck(float width,float height,int initialPoint,int direction,double speed){*/
	public void createDucks(float delta){
		Random rand = new Random();
		int width =152;
		int height = 95;
		int direction = rand.nextInt(2)+0;
		int initialPoint = rand.nextInt((int) (Gdx.graphics.getHeight()*0.85)) + 0;
		double speed =Gdx.graphics.getWidth()*0.003;
		ducks.add(new Duck(width,height,initialPoint,direction,speed));
	}

	public void createPlate(float delta){

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
		addPlate(width,height,initialPoint,finalPoint,speed);
	}
	
	public void addPlate(int width, int height, int initialPoint, int finalPoint, double speed){
		plates.put(plateID++, new Plate(width,height,initialPoint,finalPoint,speed));
	}

	public GameState(Player player1,Player player2){
		this.player1 = player1;
		this.player2 = player2;
		plates = new HashMap<Integer,Plate>();
		
	}
	
	public void movePlates(float delta){
		for(Entry<Integer, Plate> entry : plates.entrySet()){
			movePlate(entry.getKey(),delta);
		}
	}

	private void movePlate(int pos,float delta) {
		Plate plate = plates.get(pos);
		plate.setX((double) (plate.getX()+plate.getSpeed()*plate.getDirection()));
		plate.setY(function(plate));
		plate.setWidth(plate.getWidth()*(1-delta*0.35f));
		plate.setHeight(plate.getHeight()*(1-delta*0.35f));
	}
	
	private int checkPlatesCollisionWithScope(){
		int numOfBrokenPlates=0;
		for(Entry<Integer, Plate> entry : plates.entrySet()){
			if(scope.getX() >= entry.getValue().getX() && scope.getX() <=entry.getValue().getX()+entry.getValue().getWidth() && scope.getY()>= entry.getValue().getY() && scope.getY() <= entry.getValue().getY()+entry.getValue().getHeight()){
					numOfBrokenPlates++;
					entry.getValue().setStatus('B');
				}	
		}
		return numOfBrokenPlates;
	}
	
	public boolean checkDuckCollisionWithScope(){
		for(int i = 0; i < ducks.size();i++){
			if(scope.getX() >= ducks.get(i).getX() && scope.getX() <=ducks.get(i).getX()+ducks.get(i).getWidth() && scope.getY()>= ducks.get(i).getY() && scope.getY() <= ducks.get(i).getY()+ducks.get(i).getHeight()){
				shootDuck=true;
				ducks.get(i).setStatus('B');
				return true;
			}
		}
	return false;
}
	
	private ArrayList<Integer> removeBrokenPlates(){

		ArrayList<Integer> brokenPlates;
		brokenPlates = new ArrayList<Integer>();
		
		for(Entry<Integer, Plate> entry : plates.entrySet()){
		    if(entry.getValue().getStatus() == 'B'){
		    	brokenPlates.add(entry.getKey());
		    }	
		}
		
		for(Integer key: brokenPlates){
			plates.remove(key);
			
		}
		
		return brokenPlates;
	}
	
	public int updatePlates(){ // se acertou ou não em pratos
		updateFailedPlates();
		int brokenPlates = checkPlatesCollisionWithScope();
		
		return brokenPlates;
	}
	
	private float function(Plate plate){
		return (float) (plate.getK()*(plate.getX()-plate.getFinalPoint())*(plate.getX()-plate.getInitialPoint()));		
	}
	
	public HashMap<Integer,Plate> getPlates(){
		return plates;
	}

	public void reset() {
		plates = new HashMap<Integer,Plate>();
		ducks = new ArrayList<Duck>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
        shootDuck = false;
	}
	
	public void setScope(double x, double y){
		scope.setX(x);
		scope.setY(y);
	}
	
	private void updateFailedPlates(){
		for(Entry<Integer, Plate> entry : plates.entrySet()){
			
			if(entry.getValue().getY() <0){
				entry.getValue().setStatus('B');
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
		plates = new HashMap<Integer,Plate>();
		ducks = new ArrayList<Duck>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
        shootDuck = false;
		//createPlate(0);
	}
	
	public void updateDucks(){
		moveDucks();
		removeDucks();
	}
	

	private void removeDucks() {
		ArrayList<Duck> d = new ArrayList<Duck>();
		
		
		for(int i = 0; i < ducks.size(); i++){
			if(ducks.get(i).getDirection() == 1){
				if(ducks.get(i).getX() > Gdx.graphics.getWidth()){
					ducks.get(i).setStatus('B');
				}
			}
			else{
				if(ducks.get(i).getX() < -ducks.get(i).getWidth()){
					ducks.get(i).setStatus('B');
				}
			}
		}
		for(int i = 0; i < ducks.size();i++){
			if(ducks.get(i).getStatus() != 'B')
				d.add(ducks.get(i));
		}
		ducks = d;	
	}
	public void moveDucks() {
		for(int i = 0; i < ducks.size();i++){
			ducks.get(i).move();
		}
		
	}

	public Player getPlayer1() {
		return player1;
	}
	
	public boolean getDuckShoot(){
		return shootDuck;
	}
	public ArrayList<Duck> getDucks(){
		return ducks;
	}
	
	

}

