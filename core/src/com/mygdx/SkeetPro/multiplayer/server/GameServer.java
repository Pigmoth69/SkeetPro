package com.mygdx.SkeetPro.multiplayer.server;
import java.io.IOException;



import java.net.InetAddress;
import java.net.UnknownHostException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet0LoginRequest;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet1LoginAwnser;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet2Message;


public class GameServer implements Runnable{
	private Server server;
	private int tcp_port = 54559;
	private int udp_port = 54779;
	
	
	public GameServer(){

	}
	 
	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packets.Packet0LoginRequest.class);
		kryo.register(Packets.Packet1LoginAwnser.class);
		kryo.register(Packets.Packet2Message.class);
		
		
	}


	@Override
	public void run() {
		server = new Server();
		registerPackets();
		server.start(); 
		server.addListener(new ServerNetworkListener());
		try {
			server.bind(tcp_port,udp_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			String adress = InetAddress.getLocalHost().getHostAddress();
			Packets.IP = adress;
			System.out.println("O meu ip: "+adress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
