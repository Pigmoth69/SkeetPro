package com.mygdx.SkeetPro.main;

import java.util.ArrayList;
import java.util.Stack;






import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.SkeetPro.multiplayer.client.GameClient;
import com.mygdx.SkeetPro.multiplayer.server.GameServer;
import com.mygdx.SkeetPro.screens.GUIGame;
import com.mygdx.SkeetPro.screens.GUIMainMenu;
import com.mygdx.SkeetPro.screens.GUIMultiplayerGame;
import com.mygdx.SkeetPro.screens.GUIMultiplayerHOST;
import com.mygdx.SkeetPro.screens.GUIMultiplayerMenu;
import com.mygdx.SkeetPro.screens.GUIOptions;
import com.mygdx.SkeetPro.screens.GUIResult;
import com.mygdx.SkeetPro.screens.GUIScreen;
import com.mygdx.SkeetPro.screens.GUISplash;
import com.mygdx.SkeetPro.screens.GUIWaitingGameMenu;

public class SkeetPro extends Game { 

	private GUIScreen current;
	private ArrayList<GUIScreen> menus;
	private Stack<State> stack;
	public static GameClient client;
	public static GameServer gameserver;
	
	public static enum State
	{
		MAIN_MENU,
		PLAY_GAME,
		SPLASH,
		MULTIPLAYER_MENU,
		MULTIPLAYER_MENU_HOST,
		MULTIPLAYER_WAITING,
		OPTIONS,
		RESULT,
		MULTIPLAYER_GAME
	};
	
	protected void switchScreen(Screen scr){
		this.setScreen(scr);
	}
	
	@Override
	public void create () {
		new Resources();
		client = new GameClient(); 
		gameserver = new GameServer();
		/*gameserver.startServer();*/
    	Thread myThread = new Thread(client);
    	myThread.setDaemon(true); // important, otherwise JVM does not exit at end of main()
    	myThread.start(); 
		menus = new ArrayList<GUIScreen>();
		stack = new Stack<State>();
		menus.add(new GUIMainMenu(this));
		menus.add(new GUIGame(this, 1, 1));
		menus.add(new GUISplash(this));
		menus.add(new GUIMultiplayerMenu(this));
		menus.add(new GUIMultiplayerHOST(this));
		menus.add(new GUIWaitingGameMenu(this));
		menus.add(new GUIOptions(this));
		menus.add(new GUIResult(this));
		menus.add(new GUIMultiplayerGame(this));
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
			GUIGame.Velocity = GUIOptions.Velt;
			GUIGame.limitFailedPlates = GUIOptions.Nfailt; 
			current = menus.get(1);
			break;
		case SPLASH:
			current = menus.get(2);
			break;
		case MULTIPLAYER_MENU:
			current = menus.get(3);
			break;
		case MULTIPLAYER_MENU_HOST:
			current = menus.get(4);
			break;
		case MULTIPLAYER_WAITING:
			current = menus.get(5);
			break;
		case OPTIONS:
			current = menus.get(6);
			break;
		case RESULT:
			current = menus.get(7);
			break;
		case MULTIPLAYER_GAME:
			current = menus.get(8);
			break;			
		}
		
		stack.push(st);
		Gdx.input.setInputProcessor(current);
		setScreen(current);
		current.show();
	}
	
	/*public void back()
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
	}*/

	@Override
	public void render () {
		super.render();
	}


}
