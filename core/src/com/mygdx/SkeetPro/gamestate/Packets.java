package com.mygdx.SkeetPro.gamestate;

import com.mygdx.SkeetPro.elements.Plate;
import com.mygdx.SkeetPro.elements.Player;

public class Packets {
	
	public static class AddPlate
	{
		public int plateID;
		public Plate plate;
	}
	
	public static class RemovePlate
	{
		public int plateID;
	}
	
	public static class HitPlate{
		public int plateID;
		public int playerID;
	}
	
	public static class PlayerConnected{
		public Player player;
		public int playerID;
	}

}
