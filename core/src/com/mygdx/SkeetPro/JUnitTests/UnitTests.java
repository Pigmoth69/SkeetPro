package com.mygdx.SkeetPro.JUnitTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.GameState;



public class UnitTests {
	 private GameState g;// = new GameState(new Player("test", 0));;
	 
	 
	 public void StartGS(String name, int score){
		 g = new GameState(new Player(name, score));
	 }
	 
	 
	@Test
	public void testPlateMov(){
		g = new GameState(new Player("test", 0));
		g.addPLate(100, 100, 100, 500, 2);

		assertEquals(2, 2);
		assertEquals(g.getPlates().size(), 1);
		assertEquals(g.getPlates().get(0).getDirection(), 1);
		assertEquals(g.getPlates().get(0).getK(), -0.0155, 0.001);
		assertEquals(g.getPlates().get(0).getX(), 100, 0.1);
		g.movePlates(0.1f);
		assertEquals(g.getPlates().get(0).getX(), 102, 0.1);
		assertEquals(g.getPlates().get(0).getY(), 12.34, 0.1);
		assertEquals(g.getPlates().get(0).getWidth(), 96.5, 0.1);
	}
	
	@Test
	public void ShotPlate(){
		g = new GameState(new Player("test", 0));
		g.addPLate(100, 100, 100, 500, 2);
		g.addPLate(100, 100, 200, 800, 2);

		g.setScope(600, 400);
		assertEquals(g.updatePlates(), 0);
		assertEquals(g.getPlates().size(), 2);
		assertEquals(g.getPlates().get(0).getX(), 100, 0.1);
		g.movePlates(0.1f);
		g.setScope(145, 20);
		assertEquals(g.getPlates().get(0).getX(), 102, 0.1);
		assertEquals(g.getPlates().get(0).getY(), 12.34, 0.1);
		assertEquals(g.getPlates().get(0).getWidth(), 96.5, 0.1);
		assertEquals(g.updatePlates(), 1);
		g.movePlates(0.1f);
		g.setScope(220, 20);
		assertEquals(g.getPlates().get(1).getX(), 204, 0.1);
		assertEquals(g.getPlates().get(1).getY(), 16.4, 0.1);
		assertEquals(g.getPlates().get(1).getWidth(), 93.1, 0.1);
		assertEquals(g.updatePlates(), 1);
		
		
	}

	
	
	
	
}
