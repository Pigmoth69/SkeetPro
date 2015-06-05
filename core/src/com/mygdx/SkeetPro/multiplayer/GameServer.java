package com.mygdx.SkeetPro.multiplayer;
import java.io.IOException;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;


public class GameServer {
	private Server server;
	private int tcp_port = 54559;
	private int udp_port = 54779;
	
	
	public GameServer(){
		server = new Server();
		registerPackets();
		server.start(); 
		server.addListener(new ServerNetworkListener());
		try {
			server.bind(tcp_port,udp_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 
	}
	 
	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packets.Packet0LoginRequest.class);
		kryo.register(Packets.Packet1LoginAwnser.class);
		kryo.register(Packets.Packet2Message.class);
		
		
	}

	public static void main(String[] args){
		new GameServer();
		Log.set(Log.LEVEL_DEBUG);
	}
	
	
	
	
}
