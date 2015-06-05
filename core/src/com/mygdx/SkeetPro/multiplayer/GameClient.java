package com.mygdx.SkeetPro.multiplayer;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;


public class GameClient {
	public Client client;
	public static Scanner scanner;
	private int tcp_port = 54559; 
	private int udp_port = 54779;
	
	public GameClient(){
		scanner = new Scanner(System.in);
		client = new Client();
		register();
		
		ClientNetworkListener nl = new ClientNetworkListener();
		nl.init(client);
		client.addListener(nl);
		
		client.start();
		
		InetAddress address = client.discoverHost(udp_port, 5000);
	    System.out.println(address);
		try {
			Log.info("Please enter the specified IP!");
			client.connect(120000, scanner.nextLine(), tcp_port,udp_port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			client.stop(); 
		}
		
		while(true){
			if(!client.isConnected()){
				try {
					client.connect(5000, address, tcp_port,udp_port);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
}
