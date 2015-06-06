package com.mygdx.SkeetPro.screens;

import com.mygdx.SkeetPro.main.SkeetPro;
import com.badlogic.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.files.FileSaving;
import com.mygdx.SkeetPro.files.SaveClass;
import com.mygdx.SkeetPro.gamestate.GameState;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.main.SkeetPro;

public class GUIScore extends GUIScreen{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton exit;
	private boolean soundActive = true;
	private float timepassed=0;
	
	public GUIScore(SkeetPro game) {
		super(game);
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        exit = new TextButton("EXIT", Resources.style); //** Button text and style **//
        
        exit.setSize(200, 50);
           
        stage.addActor(exit);
        
        Resources.music.setLooping(true);  
        checkButtonListeners();
		
	}
	
	@Override
	public void show(){
        FileSaving.LoadGameState("Jogador.cenas");
        SkeetPro.SaveState.LoadSave();
		
		Resources.music.play();
		
		Gdx.input.setInputProcessor(stage);
		
		refreshButtonsPosition();      
	}
	
	private void refreshButtonsPosition() {
		exit.setPosition(Gdx.graphics.getWidth()/2 - exit.getWidth()/2,Gdx.graphics.getHeight()-exit.getHeight()- (Gdx.graphics.getHeight()+100)/2);    
	}
	
	@Override
	public void render(float delta){
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        timepassed+=Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.draw(Resources.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        for(int i = 0; i < SkeetPro.SaveState.getHS().size(); i++){
	        Resources.scoreFont.draw(batch, "" + SkeetPro.SaveState.getHS().get(i).getName() + " -> " + SkeetPro.SaveState.getHS().get(i).getScore(), 100, Gdx.graphics.getHeight() - i*Resources.scoreFont.getCapHeight() - i*20 - 150);
        }
    	
        batch.end();
        stage.draw();
	}
	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}
	
	public void checkButtonListeners(){
		exit.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println("exit3");  
	        	return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	System.out.println("main menu"); 
	        	game.switchTo(SkeetPro.State.MAIN_MENU);
	        }
	    });
	}

}
