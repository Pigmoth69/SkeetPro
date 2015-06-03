package com.mygdx.SkeetPro.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.SkeetPro.main.SkeetPro;
import com.mygdx.SkeetPro.tween.SpriteAccessor;

public class GUISplash extends GUIScreen implements Screen {
	
	
	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	private Texture splashTexture;
	private Sound som;

	
	public GUISplash(SkeetPro game) {
		super(game);
		splashTexture = new Texture(Gdx.files.internal("splash.png"));	
		som = Gdx.audio.newSound(Gdx.files.internal("intro.wav"));
		batch = new SpriteBatch();
		splash = new Sprite(splashTexture);
		
	}
	
	@Override
	public void show() {
		som.play();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Tween.set(splash,SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash,SpriteAccessor.ALPHA,2).target(1).repeatYoyo(1, 1).setCallback(new TweenCallback(){
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.switchTo(SkeetPro.State.MAIN_MENU);
			}
		}).start(tweenManager);  
		
	}
	
	@Override
	public void render(float delta) { // time in second from the last frame to the current frame
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();	
		som.dispose();
	}

}
