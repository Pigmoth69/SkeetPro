package com.mygdx.SkeetPro.multiplayer.client;
import java.io.IOException;



import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.multiplayer.Packets;


public class GameClient implements Runnable {
	public Client client;
	public static boolean  isConnected;
	public boolean notFound;
	private int tcp_port = 25565;
	private int udp_port = 25567;
	
	
	
	public GameClient(){
		
	}
	
	private void register(){
		Kryo kryo = client.getKryo();
		kryo.register(Packets.PacketClientLogin.class);
		kryo.register(Packets.PacketSendDuck.class);
		kryo.register(Packets.PacketPlayerLost.class);
		kryo.register(Packets.PacketStartGame.class);
		kryo.register(Packets.PacketKeepAlive.class);
		

	}
	
	public static void main(String[] args){
		new GameClient();
		Log.set(Log.LEVEL_DEBUG);
	}
	public void disconectClient(){
		isConnected= false;
	}
	public boolean connectClient(String ip){
		System.out.println("entrou no connect client0");
		try {
			client.connect(5000, ip, tcp_port,udp_port);
			isConnected = true;
			return true;
		} catch (IOException e) {
			System.out.println("entrou no connect client2");
			isConnected = false;
			return false;
		}
	}
	
	public boolean getFound(){
		return notFound;
	}

	@Override
	public void run() {
		isConnected =false;
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
