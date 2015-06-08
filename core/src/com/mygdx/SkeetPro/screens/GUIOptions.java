package com.mygdx.SkeetPro.screens;

import com.mygdx.SkeetPro.main.SkeetPro;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20; 
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.SkeetPro.main.Resources;

public class GUIOptions extends GUIScreen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton exit, Nfail, Vel;
	private float timepassed=0;
	public static int Nfailt = 1;
	public static int Velt = 1;
	private boolean failChange = false;
	private boolean velChange = false;
	boolean is1Pressed, is2Pressed, is3Pressed, is4Pressed, is5Pressed, isSpacePressed;
	MyTextInputListener listener;
	
	public GUIOptions(SkeetPro game) {
		super(game);
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        exit = new TextButton("EXIT", Resources.style); //** Button text and style **//
        Nfail = new TextButton("          Plates fail num", Resources.style);
        Vel = new TextButton("        Plates per sec", Resources.style);
        
        exit.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        Nfail.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        Vel.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
           
        stage.addActor(exit);
        stage.addActor(Nfail);
        stage.addActor(Vel);
        
        checkButtonListeners();
        listener = new MyTextInputListener();
		
	}
	
	@Override
	public void show(){
       // FileSaving.LoadGameState("Jogador.cenas");
       // SkeetPro.SaveState.LoadSave();
		
		
		Gdx.input.setInputProcessor(stage);
		
		refreshButtonsPosition();      
	}
	
	private void refreshButtonsPosition() {
		Nfail.setPosition(Gdx.graphics.getWidth()/2 - Nfail.getWidth()/2, Gdx.graphics.getHeight()-Nfail.getHeight()- (Gdx.graphics.getHeight()-5*Nfail.getHeight())/2); //** Button location **//
		Vel.setPosition(Gdx.graphics.getWidth()/2 - Vel.getWidth()/2,Gdx.graphics.getHeight()-Vel.getHeight()- (Gdx.graphics.getHeight()-3*Vel.getHeight())/2); //** Button location **//
        exit.setPosition(Gdx.graphics.getWidth()/2 - exit.getWidth()/2,Gdx.graphics.getHeight()-exit.getHeight()- (Gdx.graphics.getHeight()-1*exit.getHeight())/2); //** Button location **//
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
        
        Resources.white.draw(batch, "" + Nfailt,Gdx.graphics.getWidth()/2 - Nfail.getWidth()/2- Resources.white.getSpaceWidth()*3/2, Gdx.graphics.getHeight()- (Gdx.graphics.getHeight()-5*Nfail.getHeight())/2);
        Resources.white.draw(batch, "" + Velt, Gdx.graphics.getWidth()/2 - Vel.getWidth()/2- Resources.white.getSpaceWidth()*3/2,Gdx.graphics.getHeight()- (Gdx.graphics.getHeight()-3*Vel.getHeight())/2);
       
        is1Pressed = Gdx.input.isKeyPressed(Keys.NUM_1);
        is2Pressed = Gdx.input.isKeyPressed(Keys.NUM_2);
        is3Pressed = Gdx.input.isKeyPressed(Keys.NUM_3);
        is4Pressed = Gdx.input.isKeyPressed(Keys.NUM_4);
        is5Pressed = Gdx.input.isKeyPressed(Keys.NUM_5);
        isSpacePressed = Gdx.input.isKeyPressed(Keys.SPACE);
        
        
        if (listener.getNome() != null ){
        	changeAmm(listener.getNome());
        }
        
        if (is1Pressed){
			changeAmm(1);
			System.out.println("NUM_1 Pressed");
        }
        else if (is2Pressed){
			changeAmm(2);
			System.out.println("NUM_2 Pressed");
        }
        else if (is3Pressed){
			changeAmm(3);
			System.out.println("NUM_3 Pressed");
        }
        else if (is4Pressed){
			changeAmm(4);
			System.out.println("NUM_4 Pressed");
        }
        else if (is5Pressed){
			changeAmm(5);
			System.out.println("NUM_5 Pressed");
        }
        else if (isSpacePressed){
			System.out.println("Space Pressed");
        }
        	
        
        batch.end();
        stage.draw();
	}
	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}
	
	@Override
	public boolean keyDown(int mKeycode) {
		switch (mKeycode) {
		case Input.Keys.NUM_1:
			changeAmm(1);
			System.out.println("NUM_1 Pressed");
			break;
		case Input.Keys.NUM_2:
			changeAmm(2);
			break;
		case Input.Keys.NUM_3:
			changeAmm(3);
			break;
		case Input.Keys.NUM_4:
			changeAmm(4);
			break;
		case Input.Keys.NUM_5:
			changeAmm(5);
			break;
		case Input.Keys.NUMPAD_1:
			System.out.println("NUMPAD_1 Pressed");
			changeAmm(1);
			break;
		case Input.Keys.NUMPAD_2:
			changeAmm(2);
			break;
		case Input.Keys.NUMPAD_3:
			changeAmm(3);
			break;
		case Input.Keys.NUMPAD_4:
			changeAmm(4);
			break;
		case Input.Keys.NUMPAD_5:
			changeAmm(5);
			break;
		case Input.Keys.SPACE:
			System.out.println("Space Pressed");
			changeAmm(1);
			break;
		}
		return false;
	}
	
	public void changeAmm(int key){
		if (failChange){
			Nfailt = key;
			failChange = false;
			System.out.println("Fail modified: " + Nfailt);
		}
		else if(velChange){
			Velt = key;
			velChange = false;
			System.out.println("Vel modified: " + Velt);
		}
	}
	
	public void changeAmm(String sn){
		int n = 1;
		if (sn.equals("1"))
			n = 1;
		else if (sn.equals("2"))
			n = 2;
		else if (sn.equals("3"))
			n = 3;
		else if (sn.equals("4"))
			n = 4;
		else if (sn.equals("5"))
			n = 5;
		else{
			if (failChange)
				n = Nfailt;
			if(velChange)
				n = Velt;
		}
		
		
		if (failChange){
			Nfailt = n;
			failChange = false;
			System.out.println("Fail modified: " + Nfailt);
			listener.setNome(null);
		}
		else if(velChange){
			Velt = n;
			velChange = false;
			System.out.println("Vel modified: " + Velt);
			listener.setNome(null);
		}
	}
	
	public void checkButtonListeners(){
		Nfail.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println("Nfail");  
	            Gdx.input.getTextInput(listener, "Number of fails allowed", "Write a number between 1-5", null);
	            failChange = true;
	        	return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	System.out.println("Nfail"); 
	        }
	    });
		
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
		
		Vel.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println("Vel");  
	            Gdx.input.getTextInput(listener, "Number of fails allowed", "Write a number between 1-5", null);
	            velChange = true;
	        	return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	System.out.println("Vel"); 
	        }
	    });
	}
}
