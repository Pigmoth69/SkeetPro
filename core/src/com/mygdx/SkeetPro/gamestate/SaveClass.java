package com.mygdx.SkeetPro.gamestate;

import java.io.Serializable;
import java.util.*;

import com.badlogic.gdx.Gdx;  
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.GameState;

public class SaveClass implements Serializable{
	private ArrayList<Player> highScores;
	
	
	SaveClass(Player p1){
		nome = p1.getName();
		score = p1.getScore();
	}
	
	public void LoadSave(){
       System.out.println("Score guardado: " + nome + " - " + score);
	}
	
}
