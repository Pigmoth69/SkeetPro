package com.mygdx.SkeetPro.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.SkeetPro.multiplayer.server.ServerNetworkListener;

public class GUIMultiplayerHOST extends GUIScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton IPadress,returnMenu; 
	private float timepassed=0; 
	int duck_x_right,duck_y_right, duck_x_left,duck_y_left,duckSpeedRight,duckSpeedLeft;

	
	public GUIMultiplayerHOST(SkeetPro parent) {
		super(parent);
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        
        IPadress = new TextButton( null, Resources.style2); //** Button text and style **//7;

        
        returnMenu= new TextButton("Return", Resources.style); //** Button text and style **//

        
        IPadress.setSize((int)(Gdx.graphics.getWidth()*0.5), (int)(Gdx.graphics.getHeight()*0.28));
        returnMenu.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));         
        
        stage.addActor(IPadress);
        stage.addActor(returnMenu);
     
       
        duck_x_right=0;
        duck_y_right=(int) (Gdx.graphics.getHeight()*0.75);
        duckSpeedRight = 5;

        
        duck_x_left = Gdx.graphics.getWidth();
        duck_y_left=(int) (Gdx.graphics.getHeight()*0.50);
        duckSpeedLeft = 4;
        
        

        checkButtonListeners();
        
        
        
	}

	@Override
	public void show() {
		IPadress.setText("IP: "+Packets.IP);
		//   stage.clear();*/

		
        
        
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        //modificar isto!
       
        
        
        
        
        
        refreshButtonsPosition();      
        
        
        
        
		
	}

	private void refreshButtonsPosition() {
		IPadress.setPosition(Gdx.graphics.getWidth()/2 - IPadress.getWidth()/2, Gdx.graphics.getHeight()-(int)(Gdx.graphics.getHeight()*0.5)); //** Button location **//
		returnMenu.setPosition(Gdx.graphics.getWidth()/2 - returnMenu.getWidth()/2,Gdx.graphics.getHeight()-(int)(Gdx.graphics.getHeight()*0.5)-returnMenu.getHeight()); //** Button location **//
      
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
        startWait();
        
	}

	private void startWait() {
		if(ServerNetworkListener.clients.size()==2){
			game.switchTo(SkeetPro.State.MULTIPLAYER_WAITING);
		}

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
	public void hide() {
	}

	@Override
	public void dispose() {

	}
	 
	public void checkButtonListeners(){ 
		 
		returnMenu.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("returnMenu1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("ReturnMenu2"); 
            	Packets.IP = null;
            	/*ClientNetworkListener.client.sendTCP(new Packets.PakcetResetConnection());*/
            	SkeetPro.gameserver.closeServer();
            	game.switchTo(SkeetPro.State.MULTIPLAYER_MENU);
            	
            }
        });
		
	}

}
