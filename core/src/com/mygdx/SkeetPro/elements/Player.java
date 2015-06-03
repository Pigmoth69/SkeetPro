package com.mygdx.SkeetPro.elements;

public class Player {
	private String name;
	private int score;
	
	
	public Player(String name,int score){
		this.name = name;
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	public String getName(){
		return name;
	}

	public void addScore(int brokenplates) {
		switch(brokenplates){
		case 0:
			return;
		case 1:
			score++;
			break;
		case 2:
			score+= Math.pow(2, 2);
			break;
		case 3:
			score+= Math.pow(2, 3);
			break;
		default:

		}
	}

	public void resetScore() {
		score = 0;
		
	}
}
