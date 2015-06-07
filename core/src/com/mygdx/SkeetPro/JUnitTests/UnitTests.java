package com.mygdx.SkeetPro.JUnitTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.gamestate.GameState;



public class UnitTests {
	 private GameState g;// = new GameState(new Player("test", 0));;
	 
	 
	@Test
	public void testPlateMov(){
		g = new GameState(new Player("test", 0));
		g.addPlate(100, 100, 100, 500, 2);
		g.createPlate(0);

		assertEquals(g.getPlates().size(), 2);
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
		g.addPlate(100, 100, 100, 500, 2);
		g.addPlate(100, 100, 200, 800, 2);

		g.resetScope();
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

	@Test
	public void ScoreTest(){
		g = new GameState(new Player("test", 0));
		
		g.getPlayer1().resetScore();
		assertEquals(g.getPlayer1().getName(), "test");
		assertEquals(g.getPlayer1().getScore(), 0);
		assertEquals(g.getBestscore(), 0);
		g.getPlayer1().setName("test2");
		assertEquals(g.getPlayer1().getName(), "test2");
		g.getPlayer1().addScore(0);
		g.getPlayer1().addScore(1);
		g.setBestScore(1);
		g.getPlayer1().addScore(2);
		assertEquals(g.getPlayer1().getScore(), 5);
		assertEquals(g.getBestscore(), 1);
		g.setBestScore(5);
		g.getPlayer1().addScore(3);
		g.setBestScore(13);
		assertEquals(g.getPlayer1().getScore(), 13);
		
		
	}
	
	@Test
	public void ReloadTest(){
		g = new GameState(new Player("test", 0));
		g.resetGameState(new Player("test2", 0));
		assertEquals(g.getPlayer1().getName(), "test2");
		
		assertEquals(g.reloadTime(), -1, 0.1);
		assertEquals(g.getBullets(), 4);
		g.setBullets(1);
		assertEquals(g.getBullets(), 1);
		assertEquals(g.touchDownShot(), true);
		assertEquals(g.getBullets(), 0);
		assertEquals(g.touchDownShot(), false);
		g.manageReload(0.1f);
		assertEquals(g.incReload(0.2f), 0.3, 0.01);
		assertEquals(g.reloadTime(), 0.3, 0.01);
		assertEquals(g.touchDownShot(), false);
		g.manageReload(1);
		assertEquals(g.getBullets(), 4);
	}
	
	
}
