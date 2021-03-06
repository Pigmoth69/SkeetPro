package com.mygdx.SkeetPro.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.main.SkeetPro;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.client.ClientNetworkListener;
import com.mygdx.SkeetPro.multiplayer.client.GameClient;

public class GUIMainMenu extends GUIScreen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton play,options,multiplayer,exit,soundButton;
	private boolean soundActive = true;
	private float timepassed=0;
	int duck_x_right,duck_y_right, duck_x_left,duck_y_left,duckSpeedRight,duckSpeedLeft;

	
	public GUIMainMenu(SkeetPro parent) {
		super(parent);

		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        play = new TextButton("PLAY", Resources.style); //** Button text and style **//
        exit = new TextButton("EXIT", Resources.style); //** Button text and style **//
        options = new TextButton("OPTIONS", Resources.style); //** Button text and style **//
        multiplayer = new TextButton("       MULTIPLAYER", Resources.style); //** Button text and style **//
        soundButton = new TextButton(" ",Resources.soundButtonStyle);
        
        play.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        exit.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        options.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        multiplayer.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));;
           
        
        stage.addActor(play);
        stage.addActor(multiplayer);
        stage.addActor(options);
        stage.addActor(exit);
        stage.addActor(soundButton);
        
        duck_x_right=0;
        duck_y_right=(int) (Gdx.graphics.getHeight()*0.75);
        duckSpeedRight = 5;

        
        duck_x_left = Gdx.graphics.getWidth();
        duck_y_left=(int) (Gdx.graphics.getHeight()*0.50);
        duckSpeedLeft = 4;
        
        
        Resources.music.setLooping(true);  
        checkButtonListeners();
        
	}

	@Override
	public void show() {
		
		if(soundActive)
			Resources.music.play();
		
     //   stage.clear();

        
        
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        //modificar isto!
       
        
        
        
        
        
        refreshButtonsPosition();      
        
        
        
        
		
	}

	private void refreshButtonsPosition() {
		play.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, Gdx.graphics.getHeight()-play.getHeight()- (Gdx.graphics.getHeight()-5*play.getHeight())/2); //** Button location **//
        multiplayer.setPosition(Gdx.graphics.getWidth()/2 - multiplayer.getWidth()/2,Gdx.graphics.getHeight()-multiplayer.getHeight()- (Gdx.graphics.getHeight()-3*play.getHeight())/2); //** Button location **//
        options.setPosition(Gdx.graphics.getWidth()/2 - options.getWidth()/2,Gdx.graphics.getHeight()-options.getHeight()- (Gdx.graphics.getHeight()-1*play.getHeight())/2); //** Button location **//
        exit.setPosition(Gdx.graphics.getWidth()/2 - options.getWidth()/2,Gdx.graphics.getHeight()-options.getHeight()- (Gdx.graphics.getHeight()+1*play.getHeight())/2);      
        soundButton.setPosition(Gdx.graphics.getWidth()-soundButton.getWidth()-10, 10);
	}

	@Override
	public void render(float delta) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        timepassed+=Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.draw(Resources.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        batch.draw(Resources.duckAnimationRight.getKeyFrame(timepassed,true),duck_x_right,duck_y_right);
        batch.draw(Resources.duckAnimationLeft.getKeyFrame(timepassed,true),duck_x_left,duck_y_left);
        duck_x_right+=duckSpeedRight;
        duck_x_left-=duckSpeedLeft;
        
        if(duck_x_right > Gdx.graphics.getWidth()){
        	Random rand = new Random();
        	int  height = rand.nextInt((int) (Gdx.graphics.getHeight()*0.85)) + 0;
        	int speed = rand.nextInt(3) + 8;
        	duck_x_right= - 200;
        	duck_y_right = height;
        	duckSpeedRight = speed;
        }
        if(duck_x_left < -200){
        	Random rand = new Random();
        	int  height = rand.nextInt((int) (Gdx.graphics.getHeight()*0.85)) + 0;
        	int speed = rand.nextInt(3) + 8;
        	duck_x_left=Gdx.graphics.getWidth();
        	duck_y_left = height;
        	duckSpeedLeft = speed;
        }
        	
        	
        batch.end();
        stage.draw();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public void resize(int width, int height) {
		refreshButtonsPosition();
	    stage.getViewport().update(width, height, true);
	    
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
			game.switchTo(SkeetPro.State.PLAY_GAME);
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
	
	public void checkButtonListeners(){
		play.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("play1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("play2"); 
            	game.switchTo(SkeetPro.State.PLAY_GAME);
            }
        });
		
		options.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("options1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("options2");
            	game.switchTo(SkeetPro.State.OPTIONS);
            }
        });
		
		multiplayer.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("multiplayer1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("multiplayer2");
            	SkeetPro.gameserver.refreshIP();
            	if(Packets.IP!=null){
            	game.switchTo(SkeetPro.State.MULTIPLAYER_MENU);
            	}
            	
            	
            }
        });
		
		exit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("exit1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("exit2"); 
            	Gdx.app.exit();
            }
        });
		
		
		soundButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if(soundActive){
            		Resources.soundButtonStyle.up = Resources.soundButtonSkin.getDrawable("soundOFF");
            		Resources.music.pause();
            		soundActive=false;
            	}else{
            		Resources.soundButtonStyle.up = Resources.soundButtonSkin.getDrawable("soundON");
            		Resources.music.play();
            		soundActive=true;
            	}
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("sound2"); 
            }
        });
	}
	
}