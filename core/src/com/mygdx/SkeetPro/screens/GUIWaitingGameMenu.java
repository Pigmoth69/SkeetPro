package com.mygdx.SkeetPro.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.main.SkeetPro;

public class GUIWaitingGameMenu extends GUIScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private float timepassed=0; 
	private float timeToStart = 0;
	int duck_x_right,duck_y_right, duck_x_left,duck_y_left,duckSpeedRight,duckSpeedLeft;

	
	public GUIWaitingGameMenu(SkeetPro parent) {
		super(parent);
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        duck_x_right=0;
        duck_y_right=(int) (Gdx.graphics.getHeight()*0.75);
        duckSpeedRight = 5;

        
        duck_x_left = Gdx.graphics.getWidth();
        duck_y_left=(int) (Gdx.graphics.getHeight()*0.50);
        duckSpeedLeft = 4;  
	}

	@Override
	public void show() {
		
	}


	@Override
	public void render(float delta) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        timepassed+=Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        timeToStart+=delta;
        batch.begin();
        batch.draw(Resources.waitingBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
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
        startGame();

	}

	private void startGame() {
		if(timeToStart > 5){
			game.switchTo(SkeetPro.State.MULTIPLAYER_GAME); // onde come�a o multiplayer
			timeToStart=0;
		}
	}



	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
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
	public void hide() {
	}

	@Override
	public void dispose() {

	}
	 
}
