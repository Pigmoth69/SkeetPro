package com.mygdx.SkeetPro.screens;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
		private String nome;
		private boolean inputDone = false;
	
	   @Override
	   public void input (String text) {
		  nome = text;
		  inputDone = true;
	   }

	   @Override
	   public void canceled () { 
		   inputDone = true;
	   }
	    
	   
	   public String getNome(){
		   return nome;
	   }
	   
	   public boolean getInputDone(){
		   return inputDone;
	   }
	
		public boolean setInputDone(boolean bol){
			inputDone = bol;
			return inputDone;
		}
	
	}
