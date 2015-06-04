
package com.mygdx.SkeetPro.gamestate;

import java.io.Serializable;
import java.util.*;

import com.badlogic.gdx.Gdx;  
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.GameState;

public class SaveClass implements Serializable{
	private ArrayList<Player> highScores;
	
	public SaveClass(){
		highScores = new ArrayList<Player>();
	}
	
	public void Save(Player p1){
		
		if (highScores == null){
			System.out.println("novo saveState");
			 highScores = new ArrayList<Player>();
		}
		
		
		System.out.println("Size HS: " + highScores.size());
		
		/*if (highScores.size() >= 5){
			if (p1.getScore() > highScores.get(4).getScore()){
				highScores.add(p1);
			}
		}
		else*/
			highScores.add(p1);
			
			this.LoadSave();
		
		System.out.println("Inicio do sort");
		Collections.sort(highScores);
		
		
		while (highScores.size() > 5){
			highScores.remove(highScores.size() - 1);
		}
			
	}
	
	public void LoadSave(){
		for(int i = 0; i < highScores.size(); i++){
			System.out.println(highScores.get(i).getName() + " -> " + highScores.get(i).getScore());
		}
	}
	

	
}