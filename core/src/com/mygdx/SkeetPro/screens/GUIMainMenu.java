package com.mygdx.SkeetPro.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.SkeetPro.main.SkeetPro;

public class GUIMainMenu extends GUIScreen {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture background;
	private Stage stage;
	private Skin buttonSkin,soundButtonSkin;
	private Label label;
	private TextButton play,highscores,options,multiplayer,exit,soundButton;
	private TextureAtlas buttonsAtlas,soundButtonAtlas;
	private BitmapFont white,black;
	private Music music;
	private TextButtonStyle style;
	private TextButtonStyle soundButtonStyle;
	private boolean soundActive = true;
	
	 private TextureAtlas duckAtlasRight;
	 private Animation duckAnimationRight;
	 
	 private TextureAtlas duckAtlasLeft;
	 private Animation duckAnimationLeft;
	 private float timepassed=0;
	 int duck_x_right,duck_y_right, duck_x_left,duck_y_left,duckSpeedRight,duckSpeedLeft;
	
	
	public GUIMainMenu(SkeetPro parent) {
		super(parent);
		background = new Texture(Gdx.files.internal("forest.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.wav"));
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        buttonsAtlas = new TextureAtlas("MenuButtons.atlas"); //** button atlas image **// 
        soundButtonAtlas = new TextureAtlas("SoundButtons.atlas");
        buttonSkin = new Skin();
        soundButtonSkin = new Skin();
        soundButtonSkin.addRegions(soundButtonAtlas);
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        white = new BitmapFont(Gdx.files.internal("teste.fnt"),false); //** font 
        
        
        style = new TextButtonStyle(); //** Button properties **//
		soundButtonStyle = new TextButtonStyle(); //** Button properties **//
		
        style.up = buttonSkin.getDrawable("ButtonON");
        style.down = buttonSkin.getDrawable("ButtonOFF");
        soundButtonStyle.up = soundButtonSkin.getDrawable("soundON");
        soundButtonStyle.down = soundButtonSkin.getDrawable("soundOFF");
        style.font = white;
        soundButtonStyle.font = white;
        stage = new Stage();        //** window is stage **//
        
        play = new TextButton("PLAY", style); //** Button text and style **//
        exit = new TextButton("EXIT", style); //** Button text and style **//
        highscores= new TextButton("     HIGHSCORES", style); //** Button text and style **//
        options = new TextButton("OPTIONS", style); //** Button text and style **//
        multiplayer = new TextButton("       MULTIPLAYER", style); //** Button text and style **//
        soundButton = new TextButton(" ",soundButtonStyle);
        
        play.setSize(400, 100);
        exit.setSize(400, 100);
        highscores.setSize(400, 100);
        options.setSize(400, 100);
        multiplayer.setSize(400, 100);
           
        
        stage.addActor(play);
        stage.addActor(multiplayer);
        stage.addActor(highscores);
        stage.addActor(options);
        stage.addActor(exit);
        stage.addActor(soundButton);
        
        
        duckAtlasRight = new TextureAtlas(Gdx.files.internal("duckAnimationRigth.atlas")); 
        duckAnimationRight = new Animation(1/8f,duckAtlasRight.getRegions());
        
        duckAtlasLeft = new TextureAtlas(Gdx.files.internal("duckAnimationLeft.atlas")); 
        duckAnimationLeft = new Animation(1/8f,duckAtlasLeft.getRegions());
        
        duck_x_right=0;
        duck_y_right=(int) (Gdx.graphics.getHeight()*0.75);
        duckSpeedRight = 5;

        
        duck_x_left = Gdx.graphics.getWidth();
        duck_y_left=(int) (Gdx.graphics.getHeight()*0.50);
        duckSpeedLeft = 4;
        
        
        music.setLooping(true);  
        checkButtonListeners();
        
	}

	@Override
	public void show() {
		
		if(soundActive)
			music.play();
		
     //   stage.clear();

        
        
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        //modificar isto!
       
        
        
        
        
        
        refreshButtonsPosition();      
        
        
        
        
		
	}

	private void refreshButtonsPosition() {
		play.setPosition(Gdx.graphics.getWidth()/2 - play.getWidth()/2, Gdx.graphics.getHeight()-play.getHeight()- (Gdx.graphics.getHeight()-5*play.getHeight())/2); //** Button location **//
        multiplayer.setPosition(Gdx.graphics.getWidth()/2 - multiplayer.getWidth()/2,Gdx.graphics.getHeight()-multiplayer.getHeight()- (Gdx.graphics.getHeight()-3*play.getHeight())/2); //** Button location **//
        highscores.setPosition(Gdx.graphics.getWidth()/2 - highscores.getWidth()/2,Gdx.graphics.getHeight()-highscores.getHeight()- (Gdx.graphics.getHeight()-1*play.getHeight())/2); //** Button location **//
        options.setPosition(Gdx.graphics.getWidth()/2 - options.getWidth()/2,Gdx.graphics.getHeight()-options.getHeight()- (Gdx.graphics.getHeight()+1*play.getHeight())/2);      
        exit.setPosition(Gdx.graphics.getWidth()/2 - exit.getWidth()/2,Gdx.graphics.getHeight()-exit.getHeight()- (Gdx.graphics.getHeight()+3*play.getHeight())/2);
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
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        batch.draw(duckAnimationRight.getKeyFrame(timepassed,true),duck_x_right,duck_y_right);
        batch.draw(duckAnimationLeft.getKeyFrame(timepassed,true),duck_x_left,duck_y_left);
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
	    TextureRegion[] texturas = duckAnimationRight.getKeyFrames();
	    TextureRegion tex = texturas[0];
	    Texture tex2 = tex.getTexture();
	    
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
		batch.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        white.dispose();
        stage.dispose();
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
		
		highscores.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("highscores1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("highscores2"); 
            }
        });
		
		options.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("options1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("options2"); 
            }
        });
		
		multiplayer.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("multiplayer1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("multiplayer2"); 
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
            		soundButtonStyle.up = soundButtonSkin.getDrawable("soundOFF");
            		music.pause();
            		soundActive=false;
            	}else{
            		soundButtonStyle.up = soundButtonSkin.getDrawable("soundON");
            		music.play();
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