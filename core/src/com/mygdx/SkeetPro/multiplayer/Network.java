
package com.mygdx.SkeetPro.multiplayer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.SkeetPro.elements.Plate;
import com.mygdx.SkeetPro.elements.Player;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(AddPlate.class);
		kryo.register(RemovePlate.class);
		kryo.register(HitPlate.class);
		kryo.register(PlayerConnected.class);
		kryo.register(PlayerDisconnected.class);
		kryo.register(PlayerLogin.class);
	}

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
	
	public static class PlayerDisconnected{
		public Player player;
		public int playerID;
	}
	
	public static class PlayerLogin{
		public Player player;
	}
}
