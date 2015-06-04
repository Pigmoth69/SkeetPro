package com.mygdx.SkeetPro.main;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.FileSaving;
import com.mygdx.SkeetPro.gamestate.SaveClass;
import com.mygdx.SkeetPro.screens.GUIGame;
import com.mygdx.SkeetPro.screens.GUIMainMenu;
import com.mygdx.SkeetPro.screens.GUIScore;
import com.mygdx.SkeetPro.screens.GUIScreen;
import com.mygdx.SkeetPro.screens.GUISplash;

public class SkeetPro extends Game { 

	private GUIScreen current;
	private ArrayList<GUIScreen> menus;
	private Stack<State> stack;
	public static SaveClass SaveState;
	
	public static enum State
	{
		MAIN_MENU,
		PLAY_GAME,
		SPLASH,
		SCORE
	};
	
	protected void switchScreen(Screen scr){
		this.setScreen(scr);
	}
	
	@Override
	public void create () {
		menus = new ArrayList<GUIScreen>();
		stack = new Stack<State>();
		menus.add(new GUIMainMenu(this));
		menus.add(new GUIGame(this));
		menus.add(new GUISplash(this));
		menus.add(new GUIScore(this));
		SaveState = FileSaving.LoadGameState("Jogador.cenas");
		if (SaveState == null){
			System.out.println("iniciar saveState");
			SaveState = new SaveClass();
		}
		switchTo(State.SPLASH);
	}
	
	public void switchTo(State st)
	{
		
		if (current != null)
		{
			current.hide();
		}
		
		switch (st)
		{
		case MAIN_MENU:
			current = menus.get(0);
			break;
		case PLAY_GAME:
			current = menus.get(1);
			break;
		case SPLASH:
			current = menus.get(2);
			break;
		case SCORE:
			current = menus.get(3);
			break;
		}
		
		stack.push(st);
		Gdx.input.setInputProcessor(current);
		setScreen(current);
		current.show();
	}
	
	public void back()
	{
		State st = stack.pop();
		
		switch (st)
		{
		case MAIN_MENU:
			current = menus.get(0);
			break;
		case PLAY_GAME:
			current = menus.get(1);
			break;
		}

		Gdx.input.setInputProcessor(current);
		setScreen(current);
		current.show();
	}

	@Override
	public void render () {
		super.render();
	}

	public static void SaveScore(Player p1){
		SaveState = FileSaving.SaveScores("Jogador.cenas", p1);
	}

}
