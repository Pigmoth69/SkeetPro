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
	private Player player1;
	private Scope scope;
	private int failPlates; 
	private int bestscore;
	private int bullets;
	private float reload_time;
	private boolean is_reloading;
	private int plateID=0;
	private boolean shootDuck = false;
	/**
	 * The constructor for GameState class 
	 * @param player1 the player data that will play the game 
	 * */     
	public GameState(Player player1){
		this.player1= player1;
		plates = new HashMap<Integer,Plate>();
		ducks = new ArrayList<Duck>();
		scope = new Scope();
		failPlates=0;
		bullets = 4;
        reload_time = 0;
        is_reloading = false;
		//createPlate(0);
	}
	/**
	 * This function if to create One random duck for the GameState
	 * @param delta the time of the current frame
	 * @return void
	 * */
	public void createDucks(float delta){
		Random rand = new Random();
		int width =152;
		int height = 95;
		int direction = rand.nextInt(2)+0;
		int initialPoint = rand.nextInt((int) (Gdx.graphics.getHeight()*0.85)) + 0;
		double speed =Gdx.graphics.getWidth()*0.003;
		ducks.add(new Duck(width,height,initialPoint,direction,speed));
	}
	/**
	 * This function create One plate for the GameState
	 * @param delta the time of the current frame
	 * @return void
	 * */
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
	/**
	 * This function add a Plate to the HashMap<Integer, Plate> plates
	 * @param width the width of the plate
	 * @param height the height of the plate
	 * @param initialPoint the position where the plate will start
	 * @param finalPoint the position where the plate will end
	 * @param speed the speed which the plate will move
	 * @return void
	 * */
	public void addPlate(int width, int height, int initialPoint, int finalPoint, double speed){
		plates.put(plateID++, new Plate(width,height,initialPoint,finalPoint,speed));
	}
	/**
	 * The constructor for GameState class 
	 * @param player1 the player data that will play the game 
	 * @param player2 the player data that will play the game 
	 * */   
	public GameState(Player player1,Player player2){
		this.player1 = player1;
		plates = new HashMap<Integer,Plate>();
		
	}
	/**
	 * The function that moves all the plates in the GameState
	 * @param delta time of the current frame
	 * */ 
	public void movePlates(float delta){
		for(Entry<Integer, Plate> entry : plates.entrySet()){
			movePlate(entry.getKey(),delta);
		}
	}
	/**
	 * The function that move one the plate with a certain HashMap position
	 * @param pos position of the plate in the HashMap
	 * @param delta time of the current frame
	 * 
	 * */ 
	private void movePlate(int pos,float delta) {
		Plate plate = plates.get(pos);
		plate.setX((double) (plate.getX()+plate.getSpeed()*plate.getDirection()));
		plate.setY(function(plate));
		plate.setWidth(plate.getWidth()*(1-delta*0.35f));
		plate.setHeight(plate.getHeight()*(1-delta*0.35f));
	}
	/**
	 * Function that checks the collision of the plates with the scope
	 * @return number of the broken plates
	 *  */  
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
	/**
	 * Function that checks the collision of the ducks with the scope
	 * @return true if a duck has collided with the scope otherwise false
	 *  */  
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
	/**
	 * Function that removes all the broken plates in the HashMap
	 * @return return a HashMap with the broken plates;
	 *  */  
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
	/**
	 * Function that updates all the plates in the game
	 * @return number of the broken plates;
	 *  */  
	public int updatePlates(){ // se acertou ou não em pratos
		updateFailedPlates();
		int brokenPlates = checkPlatesCollisionWithScope();
		
		return brokenPlates;
	}
	
	/**
	 * Function that has the calculation on the plate movimentation
	 * @param plate receives a plate
	 * @return return the calculation of the position of the plate Y new position
	 *  */  
	private float function(Plate plate){
		return (float) (plate.getK()*(plate.getX()-plate.getFinalPoint())*(plate.getX()-plate.getInitialPoint()));		
	}
	
	/**
	 * Gets the HashMap<Integer,Plate> from the GameState
	 * @return plates the plates of the HashMap
	 *  */  
	public HashMap<Integer,Plate> getPlates(){
		return plates;
	}

	/**
	 * Resets the GameState
	 * @return void
	 *  */ 
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
	
	/**
	 * Sets the Scope in a certain position
	 * @param x  new position x of the scope
	 * @param y  new position y of the scope
	 * @return void
	 *  */ 
	public void setScope(double x, double y){
		scope.setX(x);
		scope.setY(y);
	}
	/**
	 * Updates the failed plates of the HashMap that holds the plates on the GameState
	 * @return void
	 *  */ 
	private void updateFailedPlates(){
		for(Entry<Integer, Plate> entry : plates.entrySet()){
			
			if(entry.getValue().getY() <0){
				entry.getValue().setStatus('B');
				failPlates++;
			}
		}
		removeBrokenPlates();
	}
	/**
	 * Gets the number of the failed plates from the GameState
	 * @return failPlates the number of the failed plates
	 *  */ 
	public int getFailPlates(){
		return failPlates;
	}
	/**
	 * Resets the scope
	 * @return void
	 *  */ 
	public void resetScope(){
		scope.reset();
	}
	/**
	 * Gets the best Score in the game
	 * @return bestscore the best score of the game
	 *  */ 
	public int getBestscore(){
		return bestscore;
	}
	
	/**
	 * Sets the new Best score
	 * @param bestscore the bestcore of the game
	 * @return void
	 *  */ 
	public void setBestScore(int bestscore){
		this.bestscore=bestscore;
	}
	/**
	 * Gets the number of the bullets
	 * @return bullets number of bullets that still exist
	 *  */ 
	public int getBullets() {
		return bullets;
	}
	/**
	 * Sets a new number of bullets
	 * @param bullets the number of bullets
	 * @return void
	 *  */ 
	public void setBullets(int bullets){
		this.bullets= bullets;
	}
	/**
	 * Return the reload time
	 * @return the reload time -1 if already reloaded
	 *  */ 
	public float reloadTime(){
		if (is_reloading)
			return reload_time;
		else
			return -1;
	}
	/**
	 * Increases the reload time
	 * @param  amount The value of time to increase
	 * @return the current reload time
	 *  */ 
	public float incReload(float ammount){
		reload_time+= ammount;
		return reload_time;
	}
	/**
	 * Sets if it is reloading or not
	 * @param  bot the state of reloading
	 * @return void
	 *  */ 
	public void setReload(boolean bol){
		is_reloading = bol;
	}
	/**
	 * Resets the reload time
	 * @return void
	 *  */ 
	public void resetReload(){
		reload_time = 0;
	}
	/**
	 * Sets the state of the reload
	 * @param  delta the current time of the frame
	 * @return void
	 *  */ 
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
	/**
	 * Verifies if there are Bullets 
	 * @return true if there still bullets in game otherwise return false
	 *  */ 
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
	/**
	 * Resets the gamestate of the current player
	 * @param p1 new player for the reseted GameState
	 * @return void
	 *  */ 
	public void resetGameState(Player p1){
		this.player1= p1;
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
	/**
	 * Update the ducks
	 * @return void
	 *  */ 
	public void updateDucks(){
		moveDucks();
		removeDucks();
	}
	
	/**
	 * Removes the ducks from the ArrayList of the GameState
	 * @return void
	 *  */ 
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
	/**
	 * Moves all the ducks
	 * @return void
	 *  */ 
	public void moveDucks() {
		for(int i = 0; i < ducks.size();i++){
			ducks.get(i).move();
		}
		
	}
	/**
	 * Gets the Player1
	 * @return player1 the player1 of the current game
	 *  */ 
	public Player getPlayer1() {
		return player1;
	}	
	/**
	 * Return if the a duck was shoot or not
	 * @return shootDuck the state of the duck (if shooted or not)
	 *  */ 
	
	public boolean getDuckShoot(){
		return shootDuck;
	}
	/**
	 * Return the ArrayList of the GameState ducks
	 * @return ducks return the arrayList ducks
	 *  */ 
	public ArrayList<Duck> getDucks(){
		return ducks;
	}
	
	

}

