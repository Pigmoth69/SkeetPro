package com.mygdx.SkeetPro.screens;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
		private String nome;
	
	   @Override
	   public void input (String text) {
		  nome = text;
	   }

	   @Override
	   public void canceled () { 
		   
	   }
	    
	   
	   public String getNome(){
		   return nome;
	   }
	
		
	
	}
