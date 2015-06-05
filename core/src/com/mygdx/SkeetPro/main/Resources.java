package com.mygdx.SkeetPro.main;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Resources {
	// Textures
	public static Texture background;
	public static Texture gameBackground;
	public static Texture plateTexture;
	public static ArrayList<Texture> shells;
	public static Texture splashTexture;
	
	//TextureAtlas
	public static TextureAtlas buttonsAtlas,soundButtonAtlas;
	public static TextureAtlas duckAtlasRight;
	public static TextureAtlas duckAtlasLeft;
	
	//BitmapFont
	public static BitmapFont white,black;
	public static BitmapFont scoreFont;
	
	//Music
	public static Music music;
	
	//Animation
	public static Animation duckAnimationRight;
	public static Animation duckAnimationLeft;
	
	//Sprite
	public static Sprite plateSprite;
	public static Sprite splash;
	
	//Sound
	public static Sound shotgun;
	public static Sound doubleKill;
	public static Sound tripleKill;
	public static Sound som;
	
	//LabelStyle
	public static LabelStyle scoreStyle;
	
	//Skin
	public static Skin buttonSkin,soundButtonSkin;
	
	//TextButtonStyle
	public static TextButtonStyle style;
	public static TextButtonStyle soundButtonStyle;
	
	//Label
	public static Label score;

	
	public Resources(){
		//Textures
		loadTextures();	
		//Skin
        loadSkins();
		//TextureAtlas
		loadTextureAtlas();
        //Music
		loadMusic();		
        //BitmapFont
        loadBitmapFont();        
        //TextButtonStyle
        loadTextButtonStyles();        
         //Animation
        loadAnimations();        
        //LabelStyle
		loadLabelStyles();
		//Label
		loadLabels();
		//Sprite
		loadSprites();
		//Audio
		loadAudio(); 
	}
	
	private void loadTextures(){
		background = new Texture(Gdx.files.internal("forest.jpg"));
		shells = new ArrayList<Texture>();
		shells.add(new Texture(Gdx.files.internal("shell0.png")));
		shells.add(new Texture(Gdx.files.internal("shell1.png")));
		shells.add(new Texture(Gdx.files.internal("shell2.png")));
		shells.add(new Texture(Gdx.files.internal("shell3.png")));
		shells.add(new Texture(Gdx.files.internal("shell4.png")));
		gameBackground = new Texture(Gdx.files.internal("gameBackground.jpg"));
		gameBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		plateTexture = new Texture(Gdx.files.internal("plate.png"));
		splashTexture = new Texture(Gdx.files.internal("splash.png"));	
	}
	private void loadTextureAtlas(){
		buttonsAtlas = new TextureAtlas("MenuButtons.atlas"); //** button atlas image **// 
        soundButtonAtlas = new TextureAtlas("SoundButtons.atlas");
        soundButtonSkin.addRegions(soundButtonAtlas);
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        duckAtlasRight = new TextureAtlas(Gdx.files.internal("duckAnimationRigth.atlas"));
        duckAtlasLeft = new TextureAtlas(Gdx.files.internal("duckAnimationLeft.atlas"));
        
		
	}
	private void loadMusic(){
		music = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.wav"));
	}
	private void loadSkins(){
		buttonSkin = new Skin();
        soundButtonSkin = new Skin();
	}
	private void loadBitmapFont(){
		white = new BitmapFont(Gdx.files.internal("teste.fnt"),false); //** font
        scoreFont = new BitmapFont(Gdx.files.internal("scoreFont.fnt"));
	}
	private void loadTextButtonStyles(){
		style = new TextButtonStyle(); //** Button properties **//  
		soundButtonStyle = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("ButtonON");
        style.down = buttonSkin.getDrawable("ButtonOFF");
        soundButtonStyle.up = soundButtonSkin.getDrawable("soundON");
        soundButtonStyle.down = soundButtonSkin.getDrawable("soundOFF");
        style.font = white;
        soundButtonStyle.font = white;		
	}
	private void loadAnimations(){
		duckAnimationRight = new Animation(1/8f,duckAtlasRight.getRegions());
        duckAnimationLeft = new Animation(1/8f,duckAtlasLeft.getRegions());
	}
	private void loadLabelStyles(){
		scoreStyle = new LabelStyle(scoreFont,Color.WHITE);
	}
	
	private void loadLabels(){
		score = new Label("Score", scoreStyle);
		score.setPosition(0, Gdx.graphics.getHeight());
	}
	
	private void loadSprites(){
		plateSprite = new Sprite(plateTexture);
		splash = new Sprite(splashTexture);
	}
	
	private void loadAudio(){
		shotgun = Gdx.audio.newSound(Gdx.files.internal("shotgunSound.wav"));
		doubleKill = Gdx.audio.newSound(Gdx.files.internal("doubleKill.mp3"));
		tripleKill = Gdx.audio.newSound(Gdx.files.internal("tripleKill.mp3")); // da erro de memory allocation
		som = Gdx.audio.newSound(Gdx.files.internal("intro.wav"));
		
	}
}
