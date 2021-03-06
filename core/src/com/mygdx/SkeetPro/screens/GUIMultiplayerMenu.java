package com.mygdx.SkeetPro.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.main.SkeetPro;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.client.ClientNetworkListener;
import com.mygdx.SkeetPro.multiplayer.client.GameClient;

public class GUIMultiplayerMenu extends GUIScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private TextButton host,join,returnMenu;
	private float timepassed=0;
	int duck_x_right,duck_y_right, duck_x_left,duck_y_left,duckSpeedRight,duckSpeedLeft;
	MyTextInputListener listener;
	
	public GUIMultiplayerMenu(SkeetPro parent) {
		super(parent);
		listener = new MyTextInputListener();
		camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //** w/h ratio = 1.66 **//
        batch = new SpriteBatch();
        stage = new Stage();        //** window is stage **//
        
        host = new TextButton("Host Game", Resources.style); //** Button text and style **//
        join = new TextButton("Join Game", Resources.style); //** Button text and style **//
        returnMenu= new TextButton("Return", Resources.style); //** Button text and style **//

        
        host.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        join.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));
        returnMenu.setSize((int)(Gdx.graphics.getWidth()*0.31), (int)(Gdx.graphics.getHeight()*0.14));          
        
        stage.addActor(host);
        stage.addActor(join);
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
		
     //   stage.clear();
		
        
        
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        //modificar isto!
       
        
        
        
        
        
        refreshButtonsPosition();      
        
        
        
        
		
	}

	private void refreshButtonsPosition() {
		host.setPosition(Gdx.graphics.getWidth()/2 - host.getWidth()/2, Gdx.graphics.getHeight()-host.getHeight()- (Gdx.graphics.getHeight()-5*host.getHeight())/2); //** Button location **//
        join.setPosition(Gdx.graphics.getWidth()/2 - join.getWidth()/2,Gdx.graphics.getHeight()-join.getHeight()- (Gdx.graphics.getHeight()-3*host.getHeight())/2); //** Button location **//
        returnMenu.setPosition(Gdx.graphics.getWidth()/2 - returnMenu.getWidth()/2,Gdx.graphics.getHeight()-returnMenu.getHeight()- (Gdx.graphics.getHeight()-1*join.getHeight())/2); //** Button location **//
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
        
        connectToServer();
        
        batch.end();
        stage.draw();
	}

	private void connectToServer() {
		
		if(listener.getInputDone() && listener.getNome()!=null){
			SkeetPro.client.connectClient(listener.getNome());
			listener.setNome(null);
			if(GameClient.isConnected){
				GameClient.isConnected=false;
				ClientNetworkListener.client.sendTCP(new Packets.PacketStartGame());
			}
			System.out.println("entrou no connectToServer!");
			listener.setInputDone(false);
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
	    TextureRegion[] texturas = Resources.duckAnimationRight.getKeyFrames();
	    TextureRegion tex = texturas[0];
	    tex.getTexture();
	    
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
		host.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("hostgame1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	SkeetPro.gameserver.startServer();
            	System.out.println("hostgame2"); 
            	if(Packets.IP == null){
        			Packets.IP = "Please connect to a wifi internet!";
        		}else{
        			System.out.println("Connectou!");
        			
        			SkeetPro.client.connectClient("localhost");
        		}
            	game.switchTo(SkeetPro.State.MULTIPLAYER_MENU_HOST);
            }
        });
		
		join.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("joingame1");
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("joingame2");
            		Gdx.input.getTextInput(listener, "Connect", "Enter the IP adress here: ", null);
    				
    				if(listener.getNome()==null){
    					System.out.println("� nulo!");
    				}
    				else{
    					System.out.println("n�o � nulo!");
    				}
						
            }
        });
		
		returnMenu.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("returnMenu1");  
            	return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("ReturnMenu2"); 
            	game.switchTo(SkeetPro.State.MAIN_MENU);
            }
        });
		
	}

}
