package com.mygdx.SkeetPro.screens;

import java.util.HashMap;
import java.util.Map.Entry;
 
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera; 
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.SkeetPro.elements.Plate;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.GameState;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.main.SkeetPro;

public class GUIGame extends GUIScreen {
	private  SpriteBatch batch;
	private OrthographicCamera camera;
	private GameState gamestate;
	private Player p1;
	private float time;
	private float time2;
	private boolean firstNameInput = true;
	MyTextInputListener listener;
	private float timepassed = 0;
	public static int limitFailedPlates = 1;
	public static int Velocity = 1;
	
	public GUIGame(SkeetPro parent, int limitFail, int Vel) {
		super(parent);
		Resources.score.setPosition(0, Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Resources.plateSprite.setSize((float)(Gdx.graphics.getWidth()*0.13),(float)(Gdx.graphics.getHeight()*0.23));
        p1 = new Player("unkown", 0);
        gamestate = new GameState(p1);
        time = 0;
		time2 = 0;
		listener = new MyTextInputListener();
		
        limitFailedPlates = limitFail;
        Velocity = Vel;
		
		
	}

	@Override
	public void show() {
		reset();	
	}
   
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.000f, 1.000f, 0.000f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		Sprite sp = new Sprite(Resources.plateTexture);
		timepassed+=Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(Resources.gameBackground, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Resources.white.draw(batch, "Score: "+ p1.getScore(), 0, Gdx.graphics.getHeight());
		Resources.white.draw(batch, "Failed Plates: "+ gamestate.getFailPlates(), 0, Gdx.graphics.getHeight()-2*Resources.white.getCapHeight()-2*10);
		Resources.white.draw(batch, "BestScore: "+ gamestate.getBestscore(), 0, Gdx.graphics.getHeight()-Resources.white.getCapHeight()-10);
		drawPlates();
			
		gamestate.manageReload(delta);
		drawShells();
		drawDucks();
		batch.end();
		gamestate.updateDucks();
		gamestate.checkDuckCollisionWithScope();
		if(time2 > 3){
			gamestate.createDucks(delta);
			time2=0;
		}
		else
			time2+=delta;
		
		if(!(gamestate.getFailPlates()>= limitFailedPlates)){
			gamestate.movePlates(delta);	
			
			if(time > ((float)1/Velocity)){
				gamestate.createPlate(delta);
				time = 0;
			}
			else
				time += delta;
			
			if(gamestate.getPlates().size()==0)
				gamestate.createPlate(delta);
		}

		
		if(gamestate.getFailPlates()>= limitFailedPlates || gamestate.getDuckShoot()){
			String nome;
			
			if(firstNameInput){
				nome=null;
		      
				Gdx.input.getTextInput(listener, "Score", "Write your name here", null);
				
				
				firstNameInput = false;
			}
			
			if(listener.getInputDone()){ 
		        nome = listener.getNome();
		        	        
		        System.out.println(listener.getNome());
		        
		        if (nome == null)
		        	nome = "bot";
				Player p2 = new Player(nome, 0);
				p1.setName(nome);
				SkeetPro.SaveScore(p1);
				gamestate.resetGameState(p2);
				p1 = p2;
				firstNameInput = true;
				listener.setInputDone(false);
				game.switchTo(SkeetPro.State.MAIN_MENU);
			}
		}
		
		if(!(gamestate.getFailPlates()>= limitFailedPlates)){
			
			if(gamestate.getBestscore()<p1.getScore())
				gamestate.setBestScore(p1.getScore());
			
			int brokenplates = gamestate.updatePlates();
			
			
			p1.addScore(brokenplates);
			switch(brokenplates){
			case 2:
				Resources.doubleKill.play();
				break;
			case 3:
				Resources.tripleKill.play();
				break;
			default:
			}
			gamestate.resetScope();
		}
		
	}

	private void drawShells() {
		switch(gamestate.getBullets()){
		case 0:
			batch.draw(Resources.shells.get(0),0,0);
			break;
		case 1:
			batch.draw(Resources.shells.get(1),0,0);
			break;
		case 2:
			batch.draw(Resources.shells.get(2),0,0);
			break;
		case 3:
			batch.draw(Resources.shells.get(3),0,0);
			break;
		case 4:
			batch.draw(Resources.shells.get(4),0,0);
			break;
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//game.switchTo(SkeetPro.State.MAIN_MENU);

		if(gamestate.touchDownShot()){
			Resources.shotgun.play();
			gamestate.setScope(screenX,Math.abs(Gdx.graphics.getHeight()-screenY));
		}


		return true;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int mKeycode) {
		switch (mKeycode) {
		case Input.Keys.SPACE:
			game.switchTo(SkeetPro.State.MAIN_MENU);
			break;
		}
		return false;
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
	
	
	private void drawPlates(){
		HashMap<Integer,Plate> copy = gamestate.getPlates();
		for(Entry<Integer, Plate> entry : copy.entrySet()){
			Plate p = entry.getValue();
			batch.draw(Resources.plateSprite, (float)p.getX(), (float)p.getY(), p.getWidth(), p.getHeight());
		}
	}

	private void drawDucks(){
			for(int i = 0; i < gamestate.getDucks().size();i++){
				if(gamestate.getDucks().get(i).getDirection()==1){
					batch.draw(Resources.duckAnimationRight.getKeyFrame(timepassed,true),(float)gamestate.getDucks().get(i).getX(),(float)gamestate.getDucks().get(i).getY());
				}else
					batch.draw(Resources.duckAnimationLeft.getKeyFrame(timepassed,true),(float)gamestate.getDucks().get(i).getX(),(float)gamestate.getDucks().get(i).getY());
			}

	}

	private void reset(){
		Resources.plateSprite.setSize((float)(Gdx.graphics.getWidth()*0.13),(float)(Gdx.graphics.getHeight()*0.23));
		gamestate.reset();
		p1.resetScore();
		timepassed = 0;
	}
}
