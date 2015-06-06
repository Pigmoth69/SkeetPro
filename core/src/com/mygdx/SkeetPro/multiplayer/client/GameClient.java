package com.mygdx.SkeetPro.multiplayer.client;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.main.Resources;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet0LoginRequest;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet1LoginAwnser;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet2Message;


public class GameClient implements Runnable {
	public Client client;
	public static boolean  isConnected;
	public boolean notFound;
	private int tcp_port = 54559; 
	private int udp_port = 54779;
	
	
	
	public GameClient(){
		
	}
	
	private void register(){
		Kryo kryo = client.getKryo();
		kryo.register(Packets.Packet0LoginRequest.class);
		kryo.register(Packets.Packet1LoginAwnser.class);
		kryo.register(Packets.Packet2Message.class);
	}
	
	public static void main(String[] args){
		new GameClient();
		Log.set(Log.LEVEL_DEBUG);
	}
	public void disconectClient(){
		isConnected= false;
	}
	public boolean connectClient(String ip){
		try {
			client.connect(5000, ip, tcp_port,udp_port);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean getFound(){
		return notFound;
	}

	@Override
	public void run() {
		isConnected =true;
		notFound=true;
		client = new Client();
		register();
		
		ClientNetworkListener nl = new ClientNetworkListener(); 
		nl.init(client);
		client.addListener(nl); 
		
		client.start();
		
		while(isConnected){
		}
		
	}
}
